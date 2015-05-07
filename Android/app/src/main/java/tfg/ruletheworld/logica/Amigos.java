package tfg.ruletheworld.logica;


import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.util.LinkedList;

import tfg.ruletheworld.datos.BaseDeDatos;
import tfg.ruletheworld.datos.GestionAmigosRemoto;

public class Amigos {

    private static Context c;
    private static Amigos miAmigos;
    private LinkedList<Materia> envios;

    private Amigos(){
        envios = new LinkedList<Materia>();
    }

    public static Amigos getAmigos(Context pc){
        if(miAmigos == null){
            miAmigos = new Amigos();
        }
        c = pc;
        return miAmigos;
    }

    public LinkedList<String> getAmigos() {
        return new BaseDeDatos(c).getListaAmigos();
    }

    public void añadirAmigo(String nombre) {
        new GestionAmigosRemoto(c).execute("ANADIR", nombre);
        new BaseDeDatos(c).añadirAmigo(nombre);
    }

    public LinkedList<Materia> obtenerEnvios() {
        return envios;
    }

    public void enviarAAmigo(String nombreObjeto, String nombreAmigo) {
        Log.e("JUGADOR", "Enviar " + nombreObjeto + " a " + nombreAmigo);
        BaseDeDatos bd = new BaseDeDatos(c);
        int idO = bd.getIdObjeto(nombreObjeto);
        bd.restarCantidad(idO, 1);
        Location miLocation = Jugador.getMiJugador(c).obtenerLocalizacion();
        new GestionAmigosRemoto(c).execute("ENVIAR", nombreAmigo, miLocation.getLatitude()+"", miLocation.getLongitude()+"", idO+"");
    }

    public void añadirEnvios(LinkedList<Materia> lEnvios) {
        if(envios == null){
            envios = new LinkedList<Materia>();
        }
        envios.addAll(lEnvios);
    }

}
