package tfg.ruletheworld.logica;


import android.content.Context;

import tfg.ruletheworld.datos.BaseDeDatos;

public class Combates {

    private static Combates miCombates;
    private static Context c;

    private Combates(){}

    public static Combates getCombates(Context pc){
        if(miCombates == null){
            miCombates = new Combates();
        }
        c = pc;
        return miCombates;
    }

    public Enemigo getEnemigo() {
        return new BaseDeDatos(c).obtenerEnemigoAleatorio();
    }
}
