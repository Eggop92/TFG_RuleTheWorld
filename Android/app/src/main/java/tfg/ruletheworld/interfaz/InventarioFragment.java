package tfg.ruletheworld.interfaz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;

import tfg.ruletheworld.R;
import tfg.ruletheworld.datos.BaseDeDatos;
import tfg.ruletheworld.logica.Jugador;
import tfg.ruletheworld.logica.Objetos;


public class InventarioFragment extends ListFragment {

    private LinkedList<String> lista;

    public static InventarioFragment newInstance() {
        InventarioFragment fragment = new InventarioFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public InventarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventario, container, false);

    }

    public void onResume(){
        super.onResume();
        lista = Objetos.getObjetos(getActivity().getApplicationContext()).getListaObjetos();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lista);
        setListAdapter(adapter);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //idObjeto = posicion+1
        Log.i("InventarioFragment", "onListItemClick: "+lista.get(position));
        int index = lista.get(position).indexOf(" (");
        int idObjeto = new BaseDeDatos(getActivity().getApplicationContext()).getIdObjeto(lista.get(position).substring(0, index).trim());
        Intent i = new Intent(getActivity(), ObjetoDetailActivity.class);
        i.putExtra("IDOBJETO", idObjeto);
        startActivity(i);
    }

}
