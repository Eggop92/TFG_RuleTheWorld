package tfg.ruletheworld.logica;


import android.content.Context;

import tfg.ruletheworld.datos.BaseDeDatos;

public class Stats {

    public int getExperiencia() {
        return experiencia;
    }

    public enum nombreEstadisticas {VIDA, DEFENSA, ATAQUE, EXPERIENCIA, CABEZA, PECHO, PIERNAS, PIES, ARMA}

    private int vida, defensa, ataque, experiencia;
    private Objeto cabeza, pecho, piernas, pies, arma;

    public Stats (Context c){
        BaseDeDatos bd = new BaseDeDatos(c);
        vida = bd.getStat(nombreEstadisticas.VIDA);
        defensa = bd.getStat(nombreEstadisticas.DEFENSA);
        ataque = bd.getStat(nombreEstadisticas.ATAQUE);
        experiencia = bd.getStat(nombreEstadisticas.EXPERIENCIA);
        cabeza = bd.getObjeto(bd.getStat(nombreEstadisticas.CABEZA));
        pecho = bd.getObjeto(bd.getStat(nombreEstadisticas.PECHO));
        piernas = bd.getObjeto(bd.getStat(nombreEstadisticas.PIERNAS));
        pies = bd.getObjeto(bd.getStat(nombreEstadisticas.PIES));
        arma = bd.getObjeto(bd.getStat(nombreEstadisticas.ARMA));
    }

    public int getVida() {
        int rdo = vida;
        if(cabeza != null){
            rdo = rdo + cabeza.getVida();
        }
        if(pecho != null){
            rdo = rdo + pecho.getVida();
        }
        if(piernas != null){
            rdo = rdo + piernas.getVida();
        }
        if(pies != null){
            rdo = rdo + pies.getVida();
        }
        if(arma != null){
            rdo = rdo + arma.getVida();
        }
        return rdo;
    }

    public int getDefensa() {
        int rdo = defensa;
        if(cabeza != null){
            rdo = rdo + cabeza.getDefensa();
        }
        if(pecho != null){
            rdo = rdo + pecho.getDefensa();
        }
        if(piernas != null){
            rdo = rdo + piernas.getDefensa();
        }
        if(pies != null){
            rdo = rdo + pies.getDefensa();
        }
        if(arma != null){
            rdo = rdo + arma.getDefensa();
        }
        return rdo;
    }

    public int getAtaque() {
        int rdo = ataque;
        if(cabeza != null){
            rdo = rdo + cabeza.getAtaque();
        }
        if(pecho != null){
            rdo = rdo + pecho.getAtaque();
        }
        if(piernas != null){
            rdo = rdo + piernas.getAtaque();
        }
        if(pies != null){
            rdo = rdo + pies.getAtaque();
        }
        if(arma != null){
            rdo = rdo + arma.getAtaque();
        }
        return rdo;
    }

    public Objeto getCabeza() {
        return cabeza;
    }

    public Objeto getPecho() {
        return pecho;
    }

    public Objeto getPiernas() {
        return piernas;
    }

    public Objeto getPies() {
        return pies;
    }

    public Objeto getArma() {
        return arma;
    }

    public void actualizar(nombreEstadisticas parte, Objeto o) {
        switch (parte){
            case CABEZA: cabeza = o; break;
            case PECHO: pecho = o; break;
            case PIERNAS: piernas = o; break;
            case PIES: pies = o; break;
            case ARMA: arma = o; break;
        }
    }

    public void añadirExperiencia(int puntos) {
        experiencia = experiencia+puntos;
    }

}
