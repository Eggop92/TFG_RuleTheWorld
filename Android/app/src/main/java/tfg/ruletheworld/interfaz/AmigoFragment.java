package tfg.ruletheworld.interfaz;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.LinkedList;

import tfg.ruletheworld.R;
import tfg.ruletheworld.logica.Amigos;
import tfg.ruletheworld.logica.Combinaciones;
import tfg.ruletheworld.logica.Descargas;
import tfg.ruletheworld.logica.Jugador;


public class AmigoFragment extends Fragment {



    public static AmigoFragment newInstance() {
        AmigoFragment fragment = new AmigoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AmigoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amigo, container, false);

        return view;
    }

    public void onResume(){
        super.onResume();
        Button botonNuevosAmigos = (Button) getActivity().findViewById(R.id.boton_nuevos_amigos);
        botonNuevosAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog= new AlertDialog.Builder(getActivity());
                dialog.setTitle("Buscar Amigo");
                final CharSequence[] opciones = Descargas.getDescargas(getActivity()).obtenerJugadoresServidor();
                dialog.setSingleChoiceItems(opciones, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Amigos.getAmigos(getActivity()).añadirAmigo(opciones[which].toString());
                        dialog.dismiss();
                        onResume();
                    }
                });
                dialog.show();
            }
        });
        ListView lista = (ListView) getActivity().findViewById(R.id.listView_amigos);
        final LinkedList<String> amigos = Amigos.getAmigos(getActivity()).getAmigos();
        ListAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, amigos);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialog= new AlertDialog.Builder(getActivity());
                dialog.setTitle("Enviar a amigo");
                final int pos = position;
                final CharSequence[] opciones = Combinaciones.getCombinaciones(getActivity()).getObjetosUtilizables();
                dialog.setSingleChoiceItems(opciones, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Amigos.getAmigos(getActivity()).enviarAAmigo(opciones[which].toString(), amigos.get(pos));
                        dialog.dismiss();
                        onResume();
                    }
                });
                dialog.show();
            }
        });
    }



}
