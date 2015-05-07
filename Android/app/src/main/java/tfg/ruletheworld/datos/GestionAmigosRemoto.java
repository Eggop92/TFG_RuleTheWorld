package tfg.ruletheworld.datos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Egoitz on 03/08/2014.
 */
public class GestionAmigosRemoto extends AsyncTask<String, Void, JSONObject> {

    private Context c;
    private ProgressDialog barProgressDialog;
    private static final String ip = ""; //IP SERVIDOR PHP
    private static final String url = "http://"+ip+"/TFG/ruletheworld/amigos.php";

    public GestionAmigosRemoto(Context pc) {
        c=pc;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        barProgressDialog = new ProgressDialog(c);
        barProgressDialog.setTitle("Descargando datos sensibles");
        barProgressDialog.setMessage("Se estan descargando datos necesarios para la ejecuccion de la aplicacion.");
        //barProgressDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject jsonObject = null;
        try {
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
            HttpConnectionParams.setSoTimeout(httpParameters, 15000);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            String funcion = params[0];
            ArrayList<NameValuePair> parametros = new ArrayList<NameValuePair>();
            parametros.add(new BasicNameValuePair("FUNCION", funcion));
            Log.e("GESTIONACTUALIZACIONESREMOTO", "FUNCION:" + funcion);
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
            String jugador = sp.getString("ID", null);
            parametros.add(new BasicNameValuePair("ID", jugador));
            if(funcion.equals("ANADIR")){
                parametros.add(new BasicNameValuePair("AMIGO", params[1]));
            }else if(funcion.equals("ENVIAR")){
                parametros.add(new BasicNameValuePair("AMIGO", params[1]));
                parametros.add(new BasicNameValuePair("LAT", params[2]));
                parametros.add(new BasicNameValuePair("LON", params[3]));
                parametros.add(new BasicNameValuePair("OBJETO", params[4]));
            }
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(parametros));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            jsonObject = new JSONObject(result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("GESTIONAMIGOSREMOTO", jsonObject.toString());
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        barProgressDialog.dismiss();
    }
}
