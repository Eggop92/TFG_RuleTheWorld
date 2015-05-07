package tfg.ruletheworld.datos;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.Random;

import tfg.ruletheworld.logica.Enemigo;
import tfg.ruletheworld.logica.Objeto;

import static tfg.ruletheworld.logica.Stats.*;

public class BaseDeDatos extends SQLiteOpenHelper {

    private SQLiteDatabase db=getWritableDatabase();

    public BaseDeDatos(Context context/*, String name, SQLiteDatabase.CursorFactory factory, int version*/) {
        super(context, "ruletheworld", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("BaseDeDatos", "CREATING DATABASE");
        db.execSQL("CREATE TABLE Niveles ('nivel' INT, 'experiencia' INT)");
        //db.execSQL("INSERT INTO 'Niveles' VALUES (1, 0)");
        //db.execSQL("INSERT INTO 'Niveles' VALUES (2, 20)");
        //db.execSQL("INSERT INTO 'Niveles' VALUES (3, 90)");
        //db.execSQL("INSERT INTO 'Niveles' VALUES (4, 150)");
       // db.execSQL("INSERT INTO 'Niveles' VALUES (5, 270)");
        //db.execSQL("INSERT INTO 'Niveles' VALUES (6, 510)");
        db.execSQL("CREATE TABLE Estadisticas ('vida' INT, 'defensa' INT, 'ataque' INT, 'experiencia' INT, 'idCabeza' INT, 'idTorso' INT, 'idPiernas' INT, 'idPies' INT, 'idArma' INT)");
        db.execSQL("INSERT INTO 'Estadisticas' VALUES ('100', '10', '10', '0', '0', '0','0', '0', '0')");
        db.execSQL("CREATE TABLE Objeto ('id' INT, 'nombre' TEXT, 'minLevel' INT, 'cantidad' INT, 'vida' INT, 'ataque' INT, 'defensa' INT, 'cabeza' INT, 'torso' INT, 'piernas' INT, 'pies' INT, 'arma' INT)");
        //db.execSQL("INSERT INTO 'Objeto' VALUES ('1', 'Palo', '0', '0', '0', '3', '0', '0', '0', '0', '0', '1')");
        //db.execSQL("INSERT INTO 'Objeto' VALUES ('2', 'Piedra', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0')");
        //db.execSQL("INSERT INTO 'Objeto' VALUES ('3', 'Tela', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0')");
        //db.execSQL("INSERT INTO 'Objeto' VALUES ('4', 'Tierra', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0')");
        //db.execSQL("INSERT INTO 'Objeto' VALUES ('5', 'Casco', '0', '0', '1', '0', '10', '1', '0', '0', '0', '0')");
        //db.execSQL("INSERT INTO 'Objeto' VALUES ('6', 'Camiseta', '0', '0', '1', '0', '10', '0', '1', '0', '0', '0')");
        //db.execSQL("INSERT INTO 'Objeto' VALUES ('7', 'Pantalones de tela', '0', '1', '0', '0', '10', '0', '0', '1', '0', '0')");
        //db.execSQL("INSERT INTO 'Objeto' VALUES ('8', 'Botas de cuero', '0', '1', '0', '0', '10', '0', '0', '0', '1', '0')");
        //db.execSQL("INSERT INTO 'Objeto' VALUES ('9', 'Cuchillo', '0', '0', '0', '10', '10', '0', '0', '0', '0', '1')");
        //db.execSQL("INSERT INTO 'Objeto' VALUES ('10', 'Bandera', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0')");
        //db.execSQL("INSERT INTO 'Objeto' VALUES ('11', 'Tirachinas', '0', '0', '0', '5', '0', '0', '0', '0', '0', '1')");
        //db.execSQL("INSERT INTO 'Objeto' VALUES ('12', 'Cuerda', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0')");
        db.execSQL("CREATE TABLE Combinaciones ('objeto1' INT, 'objeto2' INT, 'resultado' INT)");
        //db.execSQL("INSERT INTO 'Combinaciones' VALUES ('1', '12', '11')");
        //db.execSQL("INSERT INTO 'Combinaciones' VALUES ('1', '3', '10')");
        db.execSQL("CREATE TABLE Amigos ('nombre' TEXT)");
        db.execSQL("CREATE TABLE Enemigos ('nombre' TEXT, 'vida' INT, 'ataque' INT, 'defensa' INT, 'experiencia' INT)");

    }
    //Cursor c = db.rawQuery("SELECT tipo, Descripcion, frecuencia from Lineas where numero='"+linea+"'", null);

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void cargarNivel(int nivel, int exp) {
        Log.i("BASEDEDATOS", "nivel:"+nivel);
        db.execSQL("INSERT INTO 'Niveles' VALUES ("+nivel+", "+exp+")");
    }

    public void cargarObjeto(int id, String nombre, int minLevel, int vida, int defensa, int ataque, int cabeza, int torso, int piernas, int pies, int arma) {
        Log.i("BASEDEDATOS", "objeto:"+nombre);
        db.execSQL("INSERT INTO 'Objeto' VALUES ('"+id+"', '"+nombre+"', '"+minLevel+"', '0', '"+vida+"', '"+ataque+"', '"+defensa+"', '"+cabeza+"', '"+torso+"', '"+piernas+"', '"+pies+"', '"+arma+"')");
    }

    public void cargarCombinacion(int obj1, int obj2, int rdo) {
        Log.i("BASEDEDATOS", "combinacion:"+rdo);
        db.execSQL("INSERT INTO 'Combinaciones' VALUES ('"+obj1+"', '"+obj2+"', '"+rdo+"')");
    }



    public Objeto getObjetoRandom() {
        Objeto o = null;
        Cursor c = db.rawQuery("SELECT id, nombre, minLevel, vida, ataque, defensa, cabeza, torso, piernas, pies, arma FROM Objeto", null);
        int max = c.getCount();
        Random r = new Random();
        int i = r.nextInt(max)+1;
        if(c.move(i)){
            //Log.i("BaseDeDatos", "nombre:" + c.getString(1));
            o = new Objeto(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5), c.getInt(6)==1, c.getInt(7)==1, c.getInt(8)==1, c.getInt(9)==1, c.getInt(10)==1);
        }
        c.close();
        return o;
    }

    public void sumarAlInventario(Objeto objeto) {
        Cursor c = db.rawQuery("SELECT cantidad FROM Objeto WHERE id="+objeto.getId(), null);
        Log.i("BaseDeDatos", "sumarAlInventario");
        if(c.moveToFirst()){
            int cant = c.getInt(0);
            cant = cant+1;
            db.execSQL("UPDATE Objeto SET cantidad=" + cant + " WHERE id=" + objeto.getId());
            Log.e("BaseDeDatos", "UPDATE Objeto SET cantidad=" + cant + " WHERE id=" + objeto.getId());
        }else{
            Log.e("BaseDeDatos", "sumarAlInventario: Error objeto no encontrado");
        }
        c.close();
    }

    public LinkedList<String> getListaObjetos() {
        LinkedList<String> rdo = new LinkedList<String>();
        String nombre;
        int cantidad;
        Cursor c = db.rawQuery("SELECT cantidad, nombre FROM Objeto WHERE cantidad > 0 ORDER BY id", null);
        while(c.moveToNext()){
            nombre = c.getString(1);
            cantidad = c.getInt(0);
            Log.i("BaseDeDatos", nombre+" ("+cantidad+")");
            rdo.add(nombre + " (" + cantidad + ")");
        }
        c.close();
        return rdo;
    }

    public Objeto getObjeto(int id) {
        Objeto o = null;
        Cursor c = db.rawQuery("SELECT id, nombre, minLevel, vida, ataque, defensa, cabeza, torso, piernas, pies, arma FROM Objeto WHERE id=" + id, null);
        if(c.moveToFirst()){
            //Log.i("BaseDeDatos", "nombre:" + c.getString(1));
            o = new Objeto(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5), c.getInt(6)==1, c.getInt(7)==1, c.getInt(8)==1, c.getInt(9)==1, c.getInt(10)==1);
        }
        c.close();
        return o;
    }

    public int getCantidad(int id) {
        int rdo = 0;
        Cursor c = db.rawQuery("SELECT cantidad FROM Objeto WHERE id=" + id, null);
        if(c.moveToFirst()){
            //Log.i("BaseDeDatos", "nombre:" + c.getString(1));
            rdo = c.getInt(0);
        }
        c.close();
        return rdo;
    }

    public void restarCantidad(int id, int i) {
        Cursor c = db.rawQuery("SELECT cantidad FROM Objeto WHERE id="+id, null);
        Log.i("BaseDeDatos", "restarCantidad");
        if(c.moveToFirst()){
            int cant = c.getInt(0);
            cant = cant-i;
            db.execSQL("UPDATE Objeto SET cantidad=" + cant + " WHERE id=" + id);
            //Log.e("BaseDeDatos", "UPDATE Objeto SET cantidad=" + cant + " WHERE id=" + objeto.getId());
        }else{
            Log.e("BaseDeDatos", "restarCantidad: Error objeto no encontrado");
        }
        c.close();
    }

    public int getIdObjeto(String nombre) {
        int rdo = 0;
        Cursor c = db.rawQuery("SELECT id FROM Objeto WHERE nombre='" + nombre+"'", null);
        if(c.moveToFirst()){
            //Log.i("BaseDeDatos", "nombre:" + c.getString(1));
            rdo = c.getInt(0);
        }
        c.close();
        return rdo;
    }

    public int getStat(nombreEstadisticas stat) {
        int rdo=-1;
        String buscar ="";
        switch (stat){
            case VIDA: buscar = "vida"; break;
            case DEFENSA: buscar = "defensa"; break;
            case ATAQUE: buscar = "ataque"; break;
            case EXPERIENCIA: buscar = "experiencia"; break;
            case CABEZA: buscar = "idCabeza"; break;
            case PECHO: buscar = "idTorso"; break;
            case PIERNAS: buscar = "idPiernas"; break;
            case PIES: buscar = "idPies"; break;
            case ARMA: buscar = "idArma"; break;
        }
        Cursor c = db.rawQuery("SELECT "+buscar+" FROM Estadisticas", null);
        if(c.moveToFirst()){
            rdo = c.getInt(0);
        }
        c.close();
        return rdo;
    }

    public String[] getObjetosEquipables(nombreEstadisticas parte) {
        String[] rdo;
        String buscar = "";
        switch (parte){
            case CABEZA: buscar = "cabeza"; break;
            case PECHO: buscar = "torso"; break;
            case PIERNAS: buscar = "piernas"; break;
            case PIES: buscar = "pies"; break;
            case ARMA: buscar = "arma"; break;
        }
        Cursor c = db.rawQuery("SELECT nombre FROM Objeto WHERE "+buscar+" =1 AND cantidad > 0", null);
        rdo = new String[c.getCount()+1];
        rdo[0] = "Vacio";
        int i = 1;
        while(c.moveToNext()){
            rdo[i] = c.getString(0);
            i++;
        }
        c.close();
        return rdo;
    }

    public Objeto equipar(nombreEstadisticas parte, String nombreObjeto) {
        int id = getIdObjeto(nombreObjeto);
        String buscar = "";
        switch (parte){
            case CABEZA: buscar = "idCabeza"; break;
            case PECHO: buscar = "idTorso"; break;
            case PIERNAS: buscar = "idPiernas"; break;
            case PIES: buscar = "idPies"; break;
            case ARMA: buscar = "idArma"; break;
        }
        db.execSQL("UPDATE Estadisticas SET "+buscar+" = "+id);
        return getObjeto(id);
    }

    public String[] getObjetosUtilizables() {
        String[] rdo;
        Cursor c = db.rawQuery("SELECT nombre FROM Objeto WHERE cantidad > 0 ORDER BY id", null);
        rdo = new String[c.getCount()+1];
        int i = 0;
        rdo[i] = "vacio";
        while(c.moveToNext()){
            i++;
            rdo[i] = c.getString(0);
        }
        c.close();
        return rdo;
    }

    public String combinar(String ing1, String ing2) {
        int rdoid = 0;
        int id1 = getIdObjeto(ing1);
        int id2 = getIdObjeto(ing2);
        Cursor c = db.rawQuery("SELECT resultado FROM Combinaciones WHERE objeto1="+id1+" AND objeto2="+id2, null);
        if(c.moveToFirst()){
            rdoid = c.getInt(0);
        }else{
            Cursor c2 = db.rawQuery("SELECT resultado FROM Combinaciones WHERE objeto1="+id2+" AND objeto2="+id1, null);
            if(c2.moveToFirst()){
                rdoid = c2.getInt(0);
            }
            c2.close();
        }
        c.close();
        restarCantidad(id1, 1);
        restarCantidad(id2, 1);
        String rdo = null;
        if(rdoid != 0){
            rdo = getObjeto(rdoid).getNombre();
            sumarAlInventario(getObjeto(rdoid));
        }
        return rdo;
    }

    public void añadirExperiencia(int puntos) {
        Cursor c = db.rawQuery("SELECT experiencia FROM Estadisticas", null);
        Log.i("BaseDeDatos", "añadirExperiencia");
        if(c.moveToFirst()){
            int exp = c.getInt(0);
            exp = exp+puntos;
            db.execSQL("UPDATE Estadisticas SET experiencia=" + exp );
            //Log.e("BaseDeDatos", "UPDATE Objeto SET cantidad=" + cant + " WHERE id=" + objeto.getId());
        }else{
            Log.e("BaseDeDatos", "añadirExperiencia: Error objeto no encontrado");
        }
        c.close();
    }

    public int getExperienciaNecesaria() {
        int rdo = 0;
        int exp = getStat(nombreEstadisticas.EXPERIENCIA);
        Cursor c = db.rawQuery("SELECT min(experiencia) FROM Niveles WHERE experiencia > "+exp, null);
        if(c.moveToFirst()){
            rdo = c.getInt(0);
        }
        return rdo;
    }

    public int getNivel() {
        int rdo = 0;
        int exp = getStat(nombreEstadisticas.EXPERIENCIA);
        Cursor c = db.rawQuery("SELECT max(nivel) FROM Niveles WHERE experiencia <= "+exp, null);
        if(c.moveToFirst()){
            rdo = c.getInt(0);
        }
        return rdo;
    }


    public LinkedList<String> getListaAmigos() {
        LinkedList<String> rdo = new LinkedList<String>();
        Cursor c = db.rawQuery("SELECT nombre FROM Amigos", null);
        while(c.moveToNext()){
            rdo.add(c.getString(0));
        }
        return rdo;
    }

    public void añadirAmigo(String nombre) {
        db.execSQL("INSERT INTO 'Amigos' VALUES ('"+nombre+"')");
    }

    public Enemigo obtenerEnemigoAleatorio() {
        Enemigo rdo = null;
        Cursor c = db.rawQuery("SELECT nombre, vida, ataque, defensa, experiencia FROM Enemigos", null);
        int max = c.getCount();
        Random r = new Random();
        int i = r.nextInt(max)+1;
        Log.e("BaseDeDAtos", "obtenerEnemigoAleatorio+"+i);
        if (c.move(i)) {
            //Log.i("BaseDeDatos", "nombre:" + c.getString(1));
            rdo = new Enemigo(c.getInt(1), c.getInt(2), c.getInt(3), c.getString(0), c.getInt(4));
        }
        c.close();
        return rdo;
    }

    public void cargarEnemigo(int vida, int ataque, int defensa, int exp, String nombre) {
        //CREATE TABLE Enemigos ('nombre' TEXT, 'vida' INT, 'ataque' INT, 'defensa' INT, 'experiencia' INT)
        db.execSQL("INSERT INTO 'Enemigos' VALUES ('"+nombre+"', '"+vida+"', '"+ataque+"', '"+defensa+"', '"+exp+"')");
    }
}
