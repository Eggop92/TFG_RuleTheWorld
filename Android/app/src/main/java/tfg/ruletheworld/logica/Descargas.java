package tfg.ruletheworld.logica;


import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import tfg.ruletheworld.datos.BaseDeDatos;
import tfg.ruletheworld.datos.GestionAmigosRemoto;

public class Descargas {

    private static Context c;
    private static Descargas miDescargas;

    private Descargas(){}

    public static Descargas getDescargas(Context pc){
        if(miDescargas == null){
            miDescargas = new Descargas();
        }
        c = pc;
        return miDescargas;
    }

    public void cargarObjetos(JSONArray objetos) throws JSONException {
        Log.e("JUGADOR", "cargarObjetos");
        BaseDeDatos bd = new BaseDeDatos(c);
        JSONObject json;
        int id, minLevel, vida, ataque, defensa, cabeza, torso, piernas, pies, arma;
        String nombre;
        for(int i = 0; i < objetos.length(); i++){
            json = objetos.getJSONObject(i);
            id = json.getInt("id");
            nombre = json.getString("nombre");
            minLevel = json.getInt("minLevel");
            vida = json.getInt("vida");
            defensa = json.getInt("defensa");
            ataque = json.getInt("ataque");
            cabeza = json.getInt("cabeza");
            torso = json.getInt("torso");
            piernas = json.getInt("piernas");
            pies = json.getInt("pies");
            arma = json.getInt("arma");
            bd.cargarObjeto(id, nombre, minLevel, vida, defensa, ataque, cabeza, torso, piernas, pies, arma);
        }
        bd.close();
    }

    public void cargarCombinaciones(JSONArray combinaciones) throws JSONException{
        Log.e("JUGADOR", "cargarCombinaciones");
        BaseDeDatos bd = new BaseDeDatos(c);
        JSONObject json;
        int obj1, obj2, rdo;
        for(int i = 0; i < combinaciones.length(); i++){
            json = combinaciones.getJSONObject(i);
            //{"objeto1":"1","objeto2":"3","resultado":"10"},
            obj1 = json.getInt("objeto1");
            obj2 = json.getInt("objeto2");
            rdo = json.getInt("resultado");
            bd.cargarCombinacion(obj1, obj2, rdo);
        }
        bd.close();
    }

    public CharSequence[] obtenerJugadoresServidor() {
        CharSequence[] rdo = null;
        try {
            GestionAmigosRemoto gar = new GestionAmigosRemoto(c);
            JSONObject json = gar.execute("DESCARGAR").get();
            if(json.has("ERROR")){
                Log.e("JUGADOR", json.getString("ERROR"));
            }else{
                if(json.has("AMIGOS")){
                    JSONArray amigos = json.getJSONArray("AMIGOS");
                    rdo = new CharSequence[amigos.length()];
                    for(int i =0; i < amigos.length(); i++){
                        rdo[i]=amigos.getString(i);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rdo;
    }

    public void cargarEnvios(JSONArray envios) throws JSONException {
        Log.e("JUGADOR", "cargarCombinaciones");
        BaseDeDatos bd = new BaseDeDatos(c);
        JSONObject json;
        int obj;
        double lat, lon;
        Materia m;
        LinkedList<Materia> lEnvios = new LinkedList<Materia>();
        for(int i = 0; i < envios.length(); i++){
            json = envios.getJSONObject(i);
            //{"objeto1":"1","objeto2":"3","resultado":"10"},
            obj = json.getInt("idObjeto");
            lat = json.getDouble("lat");
            lon = json.getDouble("lon");
            m = new Materia(lat, lon, bd.getObjeto(obj));
            lEnvios.add(m);
        }
        Amigos.getAmigos(c).añadirEnvios(lEnvios);
        bd.close();
    }

    public void cargarNiveles(JSONArray niveles) throws JSONException {
        Log.e("JUGADOR", "cargarNiveles");
        BaseDeDatos bd = new BaseDeDatos(c);
        JSONObject json;
        int nivel, exp;
        for(int i = 0; i < niveles.length(); i++){
            json = niveles.getJSONObject(i);
            nivel = json.getInt("nivel");
            exp = json.getInt("experiencia");
            bd.cargarNivel(nivel, exp);
        }
        bd.close();
    }


    public void cargarEnemigos(JSONArray enemigos) throws JSONException{
        Log.e("JUGADOR", "cargarNiveles");
        BaseDeDatos bd = new BaseDeDatos(c);
        JSONObject json;
        int vida, ataque, defensa, exp;
        String nombre;
        for(int i = 0; i < enemigos.length(); i++){
            json = enemigos.getJSONObject(i);
            vida = json.getInt("vida");
            ataque = json.getInt("ataque");
            defensa = json.getInt("defensa");
            exp = json.getInt("experiencia");
            nombre = json.getString("nombre");
            bd.cargarEnemigo(vida, ataque, defensa, exp, nombre);
        }
        bd.close();
    }
}
