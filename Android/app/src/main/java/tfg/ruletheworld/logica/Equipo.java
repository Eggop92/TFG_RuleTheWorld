package tfg.ruletheworld.logica;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tfg.ruletheworld.datos.BaseDeDatos;

public class Equipo {

    private static Context c;
    private static Equipo miEquipo;
    private Stats statusJugador;

    private Equipo(){}

    public static Equipo getEquipo(Context pc){
        if(miEquipo == null){
            miEquipo = new Equipo();
        }
        c = pc;
        return miEquipo;
    }

    public Stats getStats() {
        if(statusJugador == null){
            statusJugador = new Stats(c);
        }
        return statusJugador;
    }

    public String[] getObjetosEquipables(Stats.nombreEstadisticas parte) {
        return new BaseDeDatos(c).getObjetosEquipables(parte);
    }

    public void equipar(Stats.nombreEstadisticas parte, String nombreObjeto) {
        Objeto o = new BaseDeDatos(c).equipar(parte, nombreObjeto);
        getStats().actualizar(parte, o);
    }

    public void añadirExperiencia(int puntos){
        new BaseDeDatos(c).añadirExperiencia(puntos);
        getStats().añadirExperiencia(puntos);
        Toast.makeText(c, "Has ganado " + puntos + " de experiencia", Toast.LENGTH_SHORT).show();
    }

    public int getExperienciaNecesaria() {
        return new BaseDeDatos(c).getExperienciaNecesaria();
    }

    public int getNivel() {
        return new BaseDeDatos(c).getNivel();
    }


}
