package tfg.ruletheworld.interfaz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.LinkedList;

import tfg.ruletheworld.R;
import tfg.ruletheworld.logica.Equipo;
import tfg.ruletheworld.logica.Jugador;
import tfg.ruletheworld.logica.Objeto;
import tfg.ruletheworld.logica.Stats;


public class EquipoFragment extends Fragment{

    private int vida, vidaExtra, ataque, ataqueExtra, defensa, defensaExtra;


        public static EquipoFragment newInstance(/*String param1, String param2*/) {
            EquipoFragment fragment = new EquipoFragment();
            Bundle args = new Bundle();
            //args.putString(ARG_PARAM1, param1);
            //args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * Mandatory empty constructor for the fragment manager to instantiate the
         * fragment (e.g. upon screen orientation changes).
         */
        public EquipoFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_equipo, container, false);


            return view;
        }

        public void onResume(){
            super.onResume();

            //inicializamos variables de la interfaz
            TextView textoVida = (TextView)getActivity().findViewById(R.id.textEquipoVida);
            TextView textoVidaExtra = (TextView)getActivity().findViewById(R.id.textEquipoVidaExtra);
            TextView textoDefensa = (TextView)getActivity().findViewById(R.id.textEquipoDefensa);
            TextView textoDefensaExtra = (TextView)getActivity().findViewById(R.id.textEquipoDefensaExtra);
            TextView textoAtaque = (TextView)getActivity().findViewById(R.id.textEquipoAtaque);
            TextView textoAtaqueExtra = (TextView)getActivity().findViewById(R.id.textEquipoAtaqueExtra);

            ImageButton botonCabeza = (ImageButton)getActivity().findViewById(R.id.botonImagenCasco);
            ImageButton botonPecho = (ImageButton)getActivity().findViewById(R.id.botonImagenPecho);
            ImageButton botonPiernas = (ImageButton)getActivity().findViewById(R.id.botonImagenPiernas);
            ImageButton botonPies = (ImageButton)getActivity().findViewById(R.id.botonImagenBotas);
            ImageButton botonArma = (ImageButton)getActivity().findViewById(R.id.botonImagenArma);

            //inicializamos los valores para mostrar la informacion
            vida = Equipo.getEquipo(getActivity()).getStats().getVida();
            defensa =  Equipo.getEquipo(getActivity()).getStats().getDefensa();
            ataque =  Equipo.getEquipo(getActivity()).getStats().getAtaque();

            vidaExtra = 0;
            defensaExtra = 0;
            ataqueExtra = 0;

            TextView textNivel = (TextView) getActivity().findViewById(R.id.text_equipo_nivel);
            textNivel.setText( Equipo.getEquipo(getActivity()).getNivel()+"");
            ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar_equipo);
            progressBar.setMax( Equipo.getEquipo(getActivity()).getExperienciaNecesaria());
            //progressBar.setBackgroundColor(Color.BLUE);
            progressBar.setProgress( Equipo.getEquipo(getActivity()).getStats().getExperiencia());


            //Comprobamos si hay un objeto equipado. En caso correcto, se cambia el color de la imagen del boton
            // se escribe el nombre del objeto al lado del boton y se suman las estadisitcas para mostrar.
            Objeto cabeza =  Equipo.getEquipo(getActivity()).getStats().getCabeza();
            TextView textoCasco = (TextView)getActivity().findViewById(R.id.textEquipoCasco);
            if(cabeza != null){
                botonCabeza.setImageResource(R.drawable.icon_casco_color);
                textoCasco.setText(cabeza.getNombre());
                //sumarEstadisticas(cabeza);
            }else{
                botonCabeza.setImageResource(R.drawable.icon_casco_gris);
                textoCasco.setText(getActivity().getText(R.string.noEquipado));
                //restarEstadisticas(cabeza);
            }

            Objeto torso =  Equipo.getEquipo(getActivity()).getStats().getPecho();
            TextView textoTorso = (TextView)getActivity().findViewById(R.id.textEquipoPecho);
            if(torso != null){
                botonPecho.setImageResource(R.drawable.icon_torso_color);
                textoTorso.setText(torso.getNombre());
                //sumarEstadisticas(torso);
            }else{
                botonPecho.setImageResource(R.drawable.icon_torso_gris);
                textoTorso.setText(getActivity().getText(R.string.noEquipado));
            }

            Objeto piernas =  Equipo.getEquipo(getActivity()).getStats().getPiernas();
            TextView textoPiernas = (TextView)getActivity().findViewById(R.id.textEquipoPiernas);
            if(piernas != null){
                botonPiernas.setImageResource(R.drawable.icon_piernas_color);
                textoPiernas.setText(piernas.getNombre());
                //sumarEstadisticas(piernas);
            }else{
                botonPiernas.setImageResource(R.drawable.icon_piernas_gris);
                textoPiernas.setText(getActivity().getText(R.string.noEquipado));
            }

            Objeto pies =  Equipo.getEquipo(getActivity()).getStats().getPies();
            TextView textoPies = (TextView)getActivity().findViewById(R.id.textEquipoBotas);
            if(pies != null){
                botonPies.setImageResource(R.drawable.icon_pies_color);
                textoPies.setText(pies.getNombre());
                //sumarEstadisticas(pies);
            }else{
                botonPies.setImageResource(R.drawable.icon_pies_gris);
                textoPies.setText(getActivity().getText(R.string.noEquipado));
            }

            Objeto arma =  Equipo.getEquipo(getActivity()).getStats().getArma();
            TextView textoArma = (TextView)getActivity().findViewById(R.id.textEquipoArma);
            if(arma != null){
                botonArma.setImageResource(R.drawable.icon_arma_color);
                textoArma.setText(arma.getNombre());
                //sumarEstadisticas(arma);
            }else{
                botonArma.setImageResource(R.drawable.icon_arma_gris);
                textoArma.setText(getActivity().getText(R.string.noEquipado));
            }

            //mostramos las estadisitcas en la pantalla.
            textoVida.setText(vida+"");
            if(vidaExtra!= 0){
                //textoVidaExtra.setText("(+"+vidaExtra+")");
            }else{
                textoVidaExtra.setText("");
            }
            textoDefensa.setText(defensa+"");
            if(defensaExtra!=0){
                //textoDefensaExtra.setText("(+"+defensaExtra+")");
            }else{
                textoDefensaExtra.setText("");
            }
            textoAtaque.setText(ataque+"");
            if(ataqueExtra!=0){
                //textoAtaqueExtra.setText("(+"+ataqueExtra+")");
            }else{
                textoAtaqueExtra.setText("");
            }

            Log.e("EquipoFragment", "establecer Listeners");
            //establecemos los listeners de los botones para poder cambiar el inventario.
            botonCabeza.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("Equipar un casco");
                    final String[] opciones =  Equipo.getEquipo(getActivity()).getObjetosEquipables(Stats.nombreEstadisticas.CABEZA);
                    dialog.setSingleChoiceItems(opciones, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Equipo.getEquipo(getActivity()).equipar(Stats.nombreEstadisticas.CABEZA, opciones[which]);
                            onResume();
                            dialog.cancel();

                        }
                    });
                    dialog.setCancelable(true);
                    dialog.show();
                }
            });

            botonPecho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("Equipar un casco");
                    final String[] opciones =  Equipo.getEquipo(getActivity()).getObjetosEquipables(Stats.nombreEstadisticas.CABEZA);
                    dialog.setSingleChoiceItems(opciones, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Equipo.getEquipo(getActivity()).equipar(Stats.nombreEstadisticas.CABEZA, opciones[which]);
                            onResume();
                            dialog.cancel();

                        }
                    });
                    dialog.setCancelable(true);
                    dialog.show();
                }
            });

            botonPecho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("Equipar torso");
                    final String[] opciones =  Equipo.getEquipo(getActivity()).getObjetosEquipables(Stats.nombreEstadisticas.PECHO);
                    dialog.setSingleChoiceItems(opciones, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Equipo.getEquipo(getActivity()).equipar(Stats.nombreEstadisticas.PECHO, opciones[which]);
                            onResume();
                            dialog.cancel();

                        }
                    });
                    dialog.setCancelable(true);
                    dialog.show();
                }
            });

            botonPiernas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("Equipar pantalones");
                    final String[] opciones =  Equipo.getEquipo(getActivity()).getObjetosEquipables(Stats.nombreEstadisticas.PIERNAS);
                    dialog.setSingleChoiceItems(opciones, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Equipo.getEquipo(getActivity()).equipar(Stats.nombreEstadisticas.PIERNAS, opciones[which]);
                            onResume();
                            dialog.cancel();

                        }
                    });
                    dialog.setCancelable(true);
                    dialog.show();
                }
            });

            botonPies.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("Equipar botas");
                    final String[] opciones =  Equipo.getEquipo(getActivity()).getObjetosEquipables(Stats.nombreEstadisticas.PIES);
                    dialog.setSingleChoiceItems(opciones, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Equipo.getEquipo(getActivity()).equipar(Stats.nombreEstadisticas.PIES, opciones[which]);
                            onResume();
                            dialog.cancel();

                        }
                    });
                    dialog.setCancelable(true);
                    dialog.show();
                }
            });

            botonArma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("Equipar un casco");
                    final String[] opciones =  Equipo.getEquipo(getActivity()).getObjetosEquipables(Stats.nombreEstadisticas.ARMA);
                    dialog.setSingleChoiceItems(opciones, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Equipo.getEquipo(getActivity()).equipar(Stats.nombreEstadisticas.ARMA, opciones[which]);
                            onResume();
                            dialog.cancel();

                        }
                    });
                    dialog.setCancelable(true);
                    dialog.show();
                }
            });
        }


    private void sumarEstadisticas(Objeto o) {
        vida = vida + o.getVida();
        vidaExtra = vidaExtra + o.getVida();
        defensa = defensa + o.getDefensa();
        defensaExtra = defensaExtra + o.getDefensa();
        ataque = ataque + o.getAtaque();
        ataqueExtra = ataqueExtra + o.getAtaque();
    }


}
