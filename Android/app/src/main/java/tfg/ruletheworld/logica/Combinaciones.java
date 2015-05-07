package tfg.ruletheworld.logica;


import android.content.Context;

import tfg.ruletheworld.datos.BaseDeDatos;

public class Combinaciones {


    private static Context c;
    private static Combinaciones miCombinaciones;

    private Combinaciones(){}

    public static Combinaciones getCombinaciones(Context pc){
        if(miCombinaciones == null){
            miCombinaciones = new Combinaciones();
        }
        c = pc;
        return miCombinaciones;
    }

    public String[] getObjetosUtilizables() {
        return new BaseDeDatos(c).getObjetosUtilizables();
    }

    public String combinar(String ing1, String ing2) {
        return new BaseDeDatos(c).combinar(ing1, ing2);
    }

}
