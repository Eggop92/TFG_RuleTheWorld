package tfg.ruletheworld.logica;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.util.LinkedList;

import tfg.ruletheworld.datos.BaseDeDatos;
import tfg.ruletheworld.interfaz.MainActivity;

public class Jugador {

    private static float DISTANCIA_MINIMA_RECOGER = 100;
    private static double DIFERENCIA = -0.005;
    private static double SALTO = 0.010;

    private static Context c;

    private static Jugador miJugador;
    private Location miLocation;
    private int distanciaRecorrida;
    private LinkedList<Materia> materiasEnElMundo;

    private Jugador(){
        distanciaRecorrida = 0;
        materiasEnElMundo = new LinkedList<Materia>();
    }

    public static Jugador getMiJugador(Context pc){
        if(miJugador == null)
            miJugador = new Jugador();
        c = pc;
        return miJugador;
    }
    public static Jugador getMiJugador(){
        if(miJugador == null)
            miJugador = new Jugador();
        return miJugador;
    }


    public void actualizarPosicion(Location location){
        Log.i("Jugador", "actualizarPosicion");
        actualizarDistancia(location);
        recogerMateriaCercanca(location);
    }

    private void recogerMateriaCercanca(Location location) {
        if(location != null){
            float min = Float.MAX_VALUE;
            Materia rdo = null;
            for(Materia m: materiasEnElMundo){
                float[] dist = new float[1];
                Location.distanceBetween(location.getLatitude(), location.getLongitude(), m.getLatitud(), m.getLongitude(), dist);
                if(dist[0] < min){
                    rdo = m;
                    min = dist[0];
                }
            }
            if(min < DISTANCIA_MINIMA_RECOGER){
                MainActivity.ma.getMapaFragment().eliminarMateria(rdo);
                materiasEnElMundo.remove(rdo);
            }
        }
    }

    private void actualizarDistancia(Location location) {
        if(miLocation != null && location != null) {
            float[] rdo = new float[1];
            Location.distanceBetween(location.getLatitude(), location.getLongitude(), miLocation.getLatitude(), miLocation.getLongitude(), rdo);
            distanciaRecorrida = distanciaRecorrida + Math.round(rdo[0]);
        }
        miLocation = location;
    }

    public Location obtenerLocalizacion(){
        Log.i("Jugador", "obtenerLocalizacion");
        return miLocation;
    }

    public int distanciaRecorrida() {
        return distanciaRecorrida;
    }

    public LinkedList<Materia> getMasMaterias() {
        LinkedList<Materia> rdo  = new LinkedList<Materia>();
        double lat, lng;
        Materia m;
        while(rdo.size() < 20){
            lat = (Math.random() * (SALTO) + DIFERENCIA) + miLocation.getLatitude();
            lng = (Math.random() * (SALTO) + DIFERENCIA) + miLocation.getLongitude();
            BaseDeDatos bd = new BaseDeDatos(c);
            Objeto o = bd.getObjetoRandom();
            bd.close();
            if(o != null){
                m = new Materia(lat, lng, o);
                rdo.add(m);
            }
        }
        materiasEnElMundo.addAll(rdo);
        return rdo;
    }

    public void anadirObjeto(Objeto objeto) {
        BaseDeDatos bd = new BaseDeDatos(c);
        bd.sumarAlInventario(objeto);
    }

    public void reiniciarDistancia() {
        distanciaRecorrida = 0;
    }
}
