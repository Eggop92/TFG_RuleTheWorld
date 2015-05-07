package tfg.ruletheworld.interfaz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import tfg.ruletheworld.R;
import tfg.ruletheworld.logica.Combates;
import tfg.ruletheworld.logica.Enemigo;
import tfg.ruletheworld.logica.Equipo;

public class CombateFragment extends Fragment {

    private int vida_j;
    private int ataque_j;
    private int defensa_j;
    private Enemigo e;
    private ProgressBar barraVida;

    public static CombateFragment newInstance() {
        CombateFragment fragment = new CombateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public CombateFragment() {
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
        return inflater.inflate(R.layout.fragment_combate, container, false);
    }

    public void onResume(){
        super.onResume();
        //bloqueamos la pantalla hasta que se pulse el boton de iniciar combate
        getActivity().findViewById(R.id.combates_boton_atacar).setEnabled(false);
        getActivity().findViewById(R.id.combates_boton_defender).setEnabled(false);
        Button boton = (Button) getActivity().findViewById(R.id.combate_inicio_boton);
        boton.setText(getActivity().getText(R.string.combate_inicio_boton));
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarCombate();
            }
        });
    }

    private void iniciarCombate() {
        //tras iniciar combate, el boton se convierte en el boton de rendirse.
        Button boton = (Button) getActivity().findViewById(R.id.combate_inicio_boton);
        boton.setText(getActivity().getText(R.string.combate_text_rendirse));
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rendirse();
            }
        });
         //se cargan las estadisticas
        e = Combates.getCombates(getActivity()).getEnemigo();
        vida_j = Equipo.getEquipo(getActivity()).getStats().getVida();
        ataque_j = Equipo.getEquipo(getActivity()).getStats().getAtaque();
        defensa_j = Equipo.getEquipo(getActivity()).getStats().getDefensa();

        //se muestran las estadisticas necesarias.
        String nombre = e.getNombre();
        ((TextView)getActivity().findViewById(R.id.combates_texto_enemigo)).setText(nombre);
        barraVida = (ProgressBar) getActivity().findViewById(R.id.combates_progressBar_vida_enemigo);
        barraVida.setMax(e.getVida());
        barraVida.setProgress(0);
        ((TextView)getActivity().findViewById(R.id.combates_texto_vida)).setText(vida_j + "");
        ((TextView)getActivity().findViewById(R.id.combates_texto_ataque)).setText(ataque_j + "");
        ((TextView)getActivity().findViewById(R.id.combates_texto_defensa)).setText(defensa_j + "");

        //se accionan los botones de funcion
        Button atacar = (Button) getActivity().findViewById(R.id.combates_boton_atacar);
        atacar.setEnabled(true);
        atacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ronda(1);
            }
        });
        Button defender = (Button) getActivity().findViewById(R.id.combates_boton_defender);
        defender.setEnabled(true);
        defender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ronda(2);
            }
        });

    }

    private void rendirse() {
        terminar( "Has Abandonado el combate.\nGanas 20 puntos de experiencia.", 20);
    }

    private void terminar(String msj, int exp) {
        //mostramos mensaje
        Toast.makeText(getActivity(),msj, Toast.LENGTH_SHORT).show();
        //sumamos exp
        Equipo.getEquipo(getActivity()).añadirExperiencia(exp);
        //reseteamos pantalla.
        onResume();
    }

    private void ronda(int accion) {
        //accion = 1 -> atacar; accion = 2 -> defender
        int def = defensa_j;
        int atc = 0;
        switch (accion){
            case 1:
                atc = e.recibirAtaque(ataque_j);
                Toast.makeText(getActivity(), "Has hecho "+atc+" puntos de daño", Toast.LENGTH_SHORT).show();
                barraVida.incrementProgressBy(-atc);
                break;
            case 2:
                def = def*2;
                Toast.makeText(getActivity(), "Has hecho defendido, tu defensa se duplica este turno.", Toast.LENGTH_SHORT).show();
                break;
        }
        //se comprueba vida enemigo
        if(e.getVida() <= 0){
            terminar("Enhorabuena, has ganado la pelea.\nHas ganado "+e.getExp()+" experiencia.", e.getExp());
        }else{
            //ataca el enemigo
            if(defensa_j - e.getAtaque() < 0){
                vida_j = vida_j + (def - e.getAtaque());
                Toast.makeText(getActivity(), "Has recibido "+(def - e.getAtaque())+" puntos de daño", Toast.LENGTH_SHORT).show();
            }
            //se comprueba vida jugador
            if(vida_j <= 0){
                terminar("Has perdido la batalla.\nPero has ganado "+e.getExp()/10+" experiencia.", e.getExp()/10);
            }
            ((TextView)getActivity().findViewById(R.id.combates_texto_vida)).setText(vida_j + "");


        }
    }


}
