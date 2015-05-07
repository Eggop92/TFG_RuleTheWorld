package tfg.ruletheworld.logica;


public class Materia {
    private double latitud;
    private double longitude;
    private Objeto objeto;

    public Materia(double lat, double lng, Objeto pobjeto) {
        latitud = lat;
        longitude = lng;
        this.objeto = pobjeto;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitude() {
        return longitude;
    }


    public Objeto getObjeto() {
        return objeto;
    }
}
