package tfg.ruletheworld.datos;

import android.os.AsyncTask;
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

public class GestionUsuariosRemoto extends AsyncTask<String, Void, JSONObject>{

    private static final String ip = "";//IP SERVIDOR PHP
    private static final String url = "http://"+ip+"/TFG/ruletheworld/usuarios.php";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
            Log.e("GESTIONUSUARIOSREMOTO", "FUNCION:"+funcion);
            if(funcion.equals("IDENTIFICAR")){
                parametros.add(new BasicNameValuePair("EMAIL", params[1]));
                parametros.add(new BasicNameValuePair("CONTRASENA", params[2]));
            }else if(funcion.equals("REGISTRAR")){

                parametros.add(new BasicNameValuePair("ID", params[1]));
                parametros.add(new BasicNameValuePair("EMAIL", params[2]));
                parametros.add(new BasicNameValuePair("CONTRASENA", params[3]));
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
        //Log.e("GESTIONUSUARIOSREMOTO", jsonObject.toString());
        return jsonObject;
    }
}
