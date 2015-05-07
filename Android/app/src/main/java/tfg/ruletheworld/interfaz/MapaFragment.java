package tfg.ruletheworld.interfaz;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.LinkedList;

import tfg.ruletheworld.R;
import tfg.ruletheworld.logica.Amigos;
import tfg.ruletheworld.logica.Equipo;
import tfg.ruletheworld.logica.Jugador;
import tfg.ruletheworld.logica.Materia;
import tfg.ruletheworld.logica.Objetos;


public class MapaFragment extends Fragment implements
        //LocationListener,
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        //GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationChangeListener,
        com.google.android.gms.location.LocationListener
{


    private static final int EXPERIENCIA_RECOGER = 10;
    private static View view;

    private static final int MAS_MATERIAS = 500; //metros
    private static final long REFRESH = 5*1000;
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(REFRESH)
            .setFastestInterval(8)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    private LocationClient mLocationClient;
    //private Localizador listener;
    //private GoogleMap mapa;
    private HashMap<LatLng, Marker> hasMarks;
    //private LinkedList<Materia> materiasEnCola;


    public static MapaFragment newInstance() {
        MapaFragment fragment = new MapaFragment();
       // Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }
    public MapaFragment() {
        // Required empty public constructor
        hasMarks = new HashMap<LatLng, Marker>();
        //materiasEnCola = new LinkedList<Materia>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onResume(){
        super.onResume();
        Log.e("MapFragment", "onResume");
        conectarListener();
    }

    public void onPause(){
        super.onPause();

        if(mLocationClient.isConnected()){
            getMap().setOnMyLocationChangeListener(null);
            mLocationClient.removeLocationUpdates(this);
            mLocationClient.disconnect();
        }
    }

    /*private void cargarMateriasEnCola() {
        Log.i("MapaFragment", "cargarMateriasEnCola");
        while(!materiasEnCola.isEmpty()){
            Log.i("MapaFragment", "cargarMateriasEnCola: "+materiasEnCola.getFirst().getObjeto().getNombre());
            anadirMateria(materiasEnCola.removeFirst(), BitmapDescriptorFactory.HUE_ORANGE);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("MapFragment", "onCreateView");
        //conectarListener();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_mapa, container, false);
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_mapa, container, false);
            //inicializarMapa();
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        return view;
    }

    private void conectarListener() {
        if (mLocationClient == null) {
            //listener = new Localizador((MainActivity)getActivity());
            mLocationClient = new LocationClient(
                    getActivity(),
                    this,  // ConnectionCallbacks
                    this); // OnConnectionFailedListener

        }
        if(!mLocationClient.isConnected()){
            mLocationClient.connect();
        }
        //inicializarMapa();
    }

    private GoogleMap getMap(){
        return ((SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.elmapa)).getMap();
    }

    public void inicializarMapa() {

        Log.i("MapaFragment", "inicializarMapa");
        //mapa = ((SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.elmapa)).getMap();
        getMap().setMyLocationEnabled(true);
        getMap().setOnMyLocationChangeListener(this);
        LinkedList<Materia> lMat = Amigos.getAmigos(getActivity()).obtenerEnvios();
        for(Materia m : lMat){
            Log.e("MAPAFRAGMENT", m.getObjeto().getNombre());
            anadirMateria(m, BitmapDescriptorFactory.HUE_GREEN);
        }
        LinkedList<Materia> lm = Objetos.getObjetos(getActivity()).getTirar();
        while(!lm.isEmpty()){
            Log.e("Tirar MATERIA", "tirar");
            anadirMateria(lm.removeFirst(), BitmapDescriptorFactory.HUE_ORANGE);
        }
        //CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 7);
        //mapa.moveCamera(update);
        //actualizarMapa();
    }

    public void actualizarMapa(){
        Log.i("MapaFragment", "actualizarMapa");

        Location loc = Jugador.getMiJugador(getActivity()).obtenerLocalizacion();
        if(loc != null){
            //mapa.getCameraPosition().zoom
            Log.i("MapFragment", "actualizarMapa: zoom:"+getMap().getCameraPosition().zoom);
           // if (getMap().getCameraPosition().zoom < 10){
            if (hasMarks.size() < 3 ){
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 15);
                getMap().moveCamera(update);
                //CircleOptions c = new CircleOptions();
                //c.center(new LatLng(loc.getLatitude(), loc.getLongitude()));
                //c.radius(30.0);
                //getMap().addCircle(c);
                anadirMaterias();
            }
            if(Jugador.getMiJugador(getActivity()).distanciaRecorrida() >= MAS_MATERIAS){
                //CircleOptions c = new CircleOptions();
                //c.center(new LatLng(loc.getLatitude(), loc.getLongitude()));
                //c.radius(30.0);
                Jugador.getMiJugador(getActivity()).reiniciarDistancia();
                anadirMaterias();
            }
        }else{
            Log.e("MapaFragment", "No posición Disponible");
        }
    }

    private void anadirMaterias() {
        LinkedList<Materia> l = Jugador.getMiJugador(getActivity()).getMasMaterias();
        for (Materia m : l){
            anadirMateria(m, BitmapDescriptorFactory.HUE_AZURE);
        }
    }

    private void anadirMateria(Materia m, float color) {
        Log.e("TIRAR MATERIAS", m.getObjeto().getNombre());
        LatLng pos = new LatLng(m.getLatitud(), m.getLongitude());
        MarkerOptions markOption = new MarkerOptions();
        markOption.position(pos);
        markOption.draggable(false);
        markOption.title(m.getObjeto().getNombre());
        markOption.icon(BitmapDescriptorFactory.defaultMarker(color));
        Marker mark = getMap().addMarker(markOption);
        hasMarks.put(pos, mark);
    }

    public void eliminarMateria(Materia m) {
        //Log.d("MapaFragment", "eliminarMateria");
        LatLng pos = new LatLng(m.getLatitud(), m.getLongitude());
        Marker mark = hasMarks.get(pos);
        if(mark != null){
            try {
                Jugador.getMiJugador(getActivity().getApplicationContext()).anadirObjeto(m.getObjeto());
                Equipo.getEquipo(getActivity()).añadirExperiencia(EXPERIENCIA_RECOGER);
                Log.i("MapaFragment", "eliminarMateria:" + m.getObjeto().getNombre());
                mark.remove();
            }catch(NullPointerException e){
                Log.e("MapaFragment", "NullPointerException");
            }
        }
    }

    /*public void anadirMateriaEnCola(Materia m) {
        materiasEnCola.add(m);
    }*/



    @Override
    public void onLocationChanged(Location location) {
        Log.i("Localizador", "Localizacion cambiada");
        Jugador.getMiJugador().actualizarPosicion(location);
        try {
            actualizarMapa();
        }catch(NullPointerException e){
            Log.e("MapaFragment", "onLocationChanged NullPointerException");
        }
    }

    /*@Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("Localizador", "Estado cambiado");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("Localizador", "Proveedor Habilitado");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("Localizador", "Proveedor deshabilitado");
    }*/

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("Localizador", "Conectado");
        mLocationClient.requestLocationUpdates(REQUEST, this);
        inicializarMapa();
    }

    @Override
    public void onDisconnected() {
        Log.i("Localizador", "Desconectado");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("Localizador", "Conexión Fallida");
    }

    @Override
    public void onMyLocationChange(Location location) {
        Log.i("Localizador", "Localizacion cambiada");
        Jugador.getMiJugador().actualizarPosicion(location);
        actualizarMapa();
    }



}
