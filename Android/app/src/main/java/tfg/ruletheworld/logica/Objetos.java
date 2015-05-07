package tfg.ruletheworld.logica;


import android.content.Context;
import android.location.Location;

import java.util.LinkedList;

import tfg.ruletheworld.datos.BaseDeDatos;
import tfg.ruletheworld.interfaz.MainActivity;

public class Objetos {

    private static Objetos miObjetos;
    private static Context c;
    private LinkedList<Materia> tirar;

    private Objetos(){
        tirar = new LinkedList<Materia>();
    }

    public static Objetos getObjetos(Context pc){
        if(miObjetos == null){
            miObjetos = new Objetos();
        }
        c = pc;
        return miObjetos;
    }

    public LinkedList<String> getListaObjetos() {
        return new BaseDeDatos(c).getListaObjetos();
    }

    public Objeto obtenerInfoObjeto(int id) {
        BaseDeDatos bd = new BaseDeDatos(c);
        return bd.getObjeto(id);
    }

    public int obtenerCantidad(int id) {
        BaseDeDatos bd = new BaseDeDatos(c);
        return bd.getCantidad(id);
    }

    public void tirarObjeto(Objeto o) {
        new BaseDeDatos(c).restarCantidad(o.getId(), 1);
        Location miLocation = Jugador.getMiJugador(c).obtenerLocalizacion();
        Materia m = new Materia(miLocation.getLatitude(), miLocation.getLatitude(), o);
        //MainActivity.ma.getMapaFragment().anadirMateriaEnCola(m);
        tirar.add(m);
    }

    public LinkedList<Materia> getTirar() {
        return tirar;
    }
}
