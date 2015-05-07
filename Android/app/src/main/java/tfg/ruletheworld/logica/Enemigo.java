package tfg.ruletheworld.logica;

public class Enemigo {

    private int vida, ataque, defensa, exp;
    private String nombre;

    public Enemigo(int vida, int ataque, int defensa, String nombre, int exp) {
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.nombre = nombre;
        this.exp = exp;
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

    public String getNombre() {
        return nombre;
    }

    public int getExp() {
        return exp;
    }

    public int recibirAtaque(int daño) {
        int rdo = defensa - daño;
        if(rdo < 0){
            vida = vida + rdo;
        }else{
            rdo = 0;
        }
        return rdo;
    }
}
