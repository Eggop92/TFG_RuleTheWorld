package tfg.ruletheworld.interfaz;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import tfg.ruletheworld.R;
import tfg.ruletheworld.datos.GestionActualizacionesRemoto;
import tfg.ruletheworld.logica.Descargas;
import tfg.ruletheworld.logica.Jugador;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
    public static MainActivity ma;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    //private NuevoAmigoFragment nuevoAmigoFragment = null;
    private AmigoFragment amigoFragment = null;
    private CombateFragment combateFragment = null;
    private LaboratorioFragment laboratorioFragment = null;
    private EquipoFragment equipoFragment = null;
    private InventarioFragment inventarioFragment = null;
    private MapaFragment mapaFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ma = this;
        if(checkPlayServices()){
            /*try {
                //identificarse();
                //actualizar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            setContentView(R.layout.activity_main);
            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));
        }else{
            finish();
        }

    }

    private void actualizar() throws InterruptedException, ExecutionException, JSONException {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String fechaActualizacion = sp.getString("ACTUALIZACION", null);
        GestionActualizacionesRemoto gar = new GestionActualizacionesRemoto(getApplicationContext());
        if(fechaActualizacion == null){
            gar.execute("INSTALACION");
        }else{
            gar.execute("ACTUALIZACION", fechaActualizacion);
        }
        JSONObject json = gar.get();
        if(json.has("ERROR")){
            Log.e("MAINACTIVITY", json.getString("ERROR"));
        }else{
            Log.e("MAINACTIVITY", json.toString());
            if(json.has("NIVELES")){
                Descargas.getDescargas(this).cargarNiveles(json.getJSONArray("NIVELES"));
            }
            if(json.has("OBJETOS")){
                Descargas.getDescargas(this).cargarObjetos(json.getJSONArray("OBJETOS"));
            }
            if(json.has("COMBINACIONES")){
                Descargas.getDescargas(this).cargarCombinaciones(json.getJSONArray("COMBINACIONES"));
            }
            if(json.has("ENVIOS")){
                Descargas.getDescargas(this).cargarEnvios(json.getJSONArray("ENVIOS"));
            }
            if(json.has("ENEMIGOS")){
                Descargas.getDescargas(this).cargarEnemigos(json.getJSONArray("ENEMIGOS"));
            }
            if(json.has("FECHA")){
                sp.edit().putString("ACTUALIZACION", json.getString("FECHA")).apply();
            }
        }
    }

    private boolean identificarse() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean registrado = sp.getBoolean("REGISTRADO", false);
        if(!registrado){
            Intent i = new Intent(this, LoginActivity.class);
            finish();
            startActivity(i);
        }
        return registrado;
    }

    public void onResume(){
        super.onResume();

            if(checkPlayServices()) {
                try {
                    boolean identificado = identificarse();
                    if(identificado) {
                        actualizar();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                finish();
            }
    }

    public void onPause(){
        super.onPause();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments and change titles
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (position+1) {
            case 1:
                setMapFragment(ft);
                break;
            case 2:
                setInventarioFragment(ft);
                break;
            case 3:
                setEquipoFragment(ft);
                break;
            case 4:
                setLaboratorioFragment(ft);
                break;
            case 5:
                setCombateFragment(ft);
                break;
            case 6:
                setAmigosFragment(ft);
                break;
            case 7:
                desconectar();
                break;
        }
        ft.commit();
        setTitle(mTitle);
    }

    private void desconectar() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putBoolean("REGISTRADO", false).apply();
        identificarse();
    }

    private void setAmigosFragment(FragmentTransaction ft) {
        if(amigoFragment == null){
            amigoFragment = AmigoFragment.newInstance();
        }
        ft.replace(R.id.container, amigoFragment);
        mTitle = getString(R.string.title_amigos);
    }

    private void setCombateFragment(FragmentTransaction ft) {
        if(combateFragment == null){
            combateFragment = CombateFragment.newInstance();
        }
        ft.replace(R.id.container, combateFragment);
        mTitle = getString(R.string.title_combate);
    }

    private void setLaboratorioFragment(FragmentTransaction ft) {
        if(laboratorioFragment == null){
            laboratorioFragment = LaboratorioFragment.newInstance();
        }
        ft.replace(R.id.container, laboratorioFragment);
        mTitle = getString(R.string.title_laboratorio);
    }

    private void setEquipoFragment(FragmentTransaction ft) {
        if(equipoFragment == null){
            equipoFragment = EquipoFragment.newInstance();
        }
        ft.replace(R.id.container, equipoFragment);
        mTitle = getString(R.string.title_equipo);
    }

    private void setInventarioFragment(FragmentTransaction ft) {
        if(inventarioFragment == null){
            inventarioFragment = InventarioFragment.newInstance();
        }
        ft.replace(R.id.container, inventarioFragment);
        mTitle = getString(R.string.title_inventario);
    }

    private void setMapFragment(FragmentTransaction ft) {
        if(mapaFragment == null){
            mapaFragment = MapaFragment.newInstance();
        }
        ft.replace(R.id.container, mapaFragment);
        mTitle = getString(R.string.title_mapa);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //TODO
            mostrarAyuda(this, mTitle);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void mostrarAyuda(Context c, CharSequence titulo){
        Toast.makeText(c, "Pulsado en "+titulo, Toast.LENGTH_SHORT).show();
        int msg;
        if(titulo.equals(c.getText(R.string.title_mapa))){
            msg = R.string.ayuda_mapa;
        }else{
            if(titulo.equals(c.getText(R.string.title_inventario))){
                msg = R.string.ayuda_inventario;
            }else{
                if(titulo.equals(c.getText(R.string.title_equipo))){
                    msg = R.string.ayuda_equipo;
                }else{
                    if(titulo.equals(c.getText(R.string.title_laboratorio))){
                        msg = R.string.ayuda_laboratorio;
                    }else{
                        if(titulo.equals(c.getText(R.string.title_combate))){
                            msg = R.string.ayuda_arena;
                        }else{
                            if(titulo.equals(c.getText(R.string.title_amigos))){
                                msg = R.string.ayuda_amigos;
                            }else{
                                msg = R.string.ayuda_error;
                            }
                        }
                    }
                }
            }
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(c);
        dialog.setTitle(R.string.ayuda);
        dialog.setMessage(msg);
        dialog.setCancelable(true);
        dialog.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private boolean checkPlayServices() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                showErrorDialog(status);
            } else {
                Toast.makeText(this, "This device is not supported.",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    void showErrorDialog(int code) {
        GooglePlayServicesUtil.getErrorDialog(code, this,
                REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
    }

    public MapaFragment getMapaFragment() {
        return mapaFragment;
    }



}
