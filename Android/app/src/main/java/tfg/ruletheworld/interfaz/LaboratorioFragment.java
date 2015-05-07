package tfg.ruletheworld.interfaz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import tfg.ruletheworld.R;
import tfg.ruletheworld.logica.Combinaciones;
import tfg.ruletheworld.logica.Equipo;
import tfg.ruletheworld.logica.Jugador;

/**

 */
public class LaboratorioFragment extends Fragment {

    private static final int EXPERIENCIA_LABO = 20;
    private boolean usarIngrediente1 = false;
    private boolean usarIngrediente2 = false;

    public static LaboratorioFragment newInstance() {
        LaboratorioFragment fragment = new LaboratorioFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public LaboratorioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_laboratorio, container, false);
    }

    public void onResume(){
        super.onResume();
        usarIngrediente1 = false;
        usarIngrediente2 = false;
        ((TextView) getActivity().findViewById(R.id.text_labo_ingrediente1)).setText(R.string.vacio_labo);
        ((ImageButton)getActivity().findViewById(R.id.boton_labo_ingrediente1)).setImageResource(R.drawable.icono_laboratorio_gris);
        ((TextView) getActivity().findViewById(R.id.text_labo_ingrediente2)).setText(R.string.vacio_labo);
        ((ImageButton)getActivity().findViewById(R.id.boton_labo_ingrediente2)).setImageResource(R.drawable.icono_laboratorio_gris);
        ((ImageButton) getActivity().findViewById(R.id.boton_labo_mezcla)).setEnabled(false);
        ((ImageButton) getActivity().findViewById(R.id.boton_labo_mezcla)).setImageResource(R.drawable.icon_mezcla_gris);

        final ImageButton botonIngrediente1 = (ImageButton) getActivity().findViewById(R.id.boton_labo_ingrediente1);
        botonIngrediente1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Seleccionar objeto");
                final String[] opciones = Combinaciones.getCombinaciones(getActivity()).getObjetosUtilizables();
                dialog.setSingleChoiceItems(opciones, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView tx = (TextView) getActivity().findViewById(R.id.text_labo_ingrediente1);
                        ImageButton botonMezcla = (ImageButton) getActivity().findViewById(R.id.boton_labo_mezcla);
                        if (which != 0) {
                            botonIngrediente1.setImageResource(R.drawable.icono_laboratorio_color);
                            //modificar el textView con el nombre
                            tx.setText(opciones[which]);
                            usarIngrediente1 = true;
                            //habitilar el boton de mezcla
                            botonMezcla.setEnabled(true);
                            botonMezcla.setImageResource(R.drawable.icon_mezcla_color);
                        }else{
                            botonIngrediente1.setImageResource(R.drawable.icono_laboratorio_gris);
                            tx.setText(R.string.vacio_labo);
                            usarIngrediente1 = false;
                            if(!usarIngrediente2){
                                botonMezcla.setEnabled(false);
                                botonMezcla.setImageResource(R.drawable.icon_mezcla_gris);
                            }
                        }
                        dialog.cancel();
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
            }
        });

        final ImageButton botonIngrediente2 = (ImageButton) getActivity().findViewById(R.id.boton_labo_ingrediente2);
        botonIngrediente2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Seleccionar objeto");
                final String[] opciones = Combinaciones.getCombinaciones(getActivity()).getObjetosUtilizables();
                dialog.setSingleChoiceItems(opciones, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView tx = (TextView) getActivity().findViewById(R.id.text_labo_ingrediente2);
                        ImageButton botonMezcla = (ImageButton) getActivity().findViewById(R.id.boton_labo_mezcla);
                        if(which != 0) {
                            //modificar el textView con el nombre
                            botonIngrediente2.setImageResource(R.drawable.icono_laboratorio_color);
                            tx.setText(opciones[which]);
                            usarIngrediente2 = true;
                            //habitilar el boton de mezcla
                            botonMezcla.setEnabled(true);
                            botonMezcla.setImageResource(R.drawable.icon_mezcla_color);
                        }else{
                            botonIngrediente2.setImageResource(R.drawable.icono_laboratorio_gris);
                            tx.setText(R.string.vacio_labo);
                            usarIngrediente2 = false;
                            if(!usarIngrediente1){
                                botonMezcla.setEnabled(false);
                                botonMezcla.setImageResource(R.drawable.icon_mezcla_gris);
                            }
                        }
                        dialog.cancel();
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
            }
        });

        ImageButton botonMezcla = (ImageButton) getActivity().findViewById(R.id.boton_labo_mezcla);
        botonMezcla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ing1 = "", ing2 = "";
                if(usarIngrediente1){
                    ing1 = ((TextView) getActivity().findViewById(R.id.text_labo_ingrediente1)).getText().toString();
                }
                if(usarIngrediente2){
                    ing2 = ((TextView) getActivity().findViewById(R.id.text_labo_ingrediente2)).getText().toString();
                }
                String rdo = Combinaciones.getCombinaciones(getActivity()).combinar(ing1, ing2);
                if (rdo == null){
                    Toast.makeText(getActivity(), "No ha habido suerte, prueba otra combinación.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "Felicidades, has creado "+rdo, Toast.LENGTH_LONG).show();
                    Equipo.getEquipo(getActivity()).añadirExperiencia(EXPERIENCIA_LABO);
                }
                onResume();
            }
        });
    }

}
