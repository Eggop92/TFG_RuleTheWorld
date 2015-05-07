package tfg.ruletheworld.logica;


public class Objeto {
    private int id;
    private String nombre;
    private int minLevel;
    private int vida, ataque, defensa;
    private boolean cabeza, torso, piernas, pies, arma;

    public Objeto(int pid, String pnombre, int pminLevel, int pvida, int pataque, int pdefensa, boolean pcabeza, boolean ptorso, boolean ppiernas, boolean ppies, boolean parma){
        id = pid;
        nombre= pnombre;
        minLevel = pminLevel;
        vida = pvida;
        ataque = pataque;
        defensa = pdefensa;
        cabeza = pcabeza;
        torso = ptorso;
        piernas = ppiernas;
        pies = ppies;
        arma = parma;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public boolean isCasco() {
        return cabeza;
    }

    public boolean isTorso() {
        return torso;
    }

    public boolean isPiernas() {
        return piernas;
    }

    public boolean isPies() {
        return pies;
    }

    public boolean isArma() {
        return arma;
    }
}
