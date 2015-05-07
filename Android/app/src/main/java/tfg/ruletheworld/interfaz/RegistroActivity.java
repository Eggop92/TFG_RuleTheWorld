package tfg.ruletheworld.interfaz;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import tfg.ruletheworld.R;
import tfg.ruletheworld.datos.GestionUsuariosRemoto;

public class RegistroActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button boton = (Button)findViewById(R.id.boton_registrarse);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok = true;
                String alias = ((EditText)findViewById(R.id.text_registrarse_alias)).getText().toString();
                if(alias == null || alias.isEmpty()){
                    ok = false;
                    ((EditText)findViewById(R.id.text_registrarse_alias)).setError("El alias no puede estar vacío.");
                }
                String email = ((EditText)findViewById(R.id.text_registrarse_email)).getText().toString();
                if(email == null || email.isEmpty() || !email.contains("@") || !email.contains(".") || email.length() <= 4){
                    ok = false;
                    ((EditText)findViewById(R.id.text_registrarse_email)).setError("Se requiere un email valido.");
                }
                String pass = ((EditText)findViewById(R.id.text_registrarse_pass)).getText().toString();
                String passConf = ((EditText)findViewById(R.id.text_registrarse_pass_conf)).getText().toString();
                if(pass == null || pass.isEmpty() || pass.length() <= 4){
                    ok = false;
                    ((EditText)findViewById(R.id.text_registrarse_pass)).setError("La contraseña debe tener al menos 4 caracteres");
                }
                if(passConf == null || passConf.isEmpty() || passConf.length() <= 4){
                    ok = false;
                    ((EditText)findViewById(R.id.text_registrarse_pass_conf)).setError("La contraseña debe tener al menos 4 caracteres.");
                }
                if (ok && !passConf.equals(pass)){
                    ok = false;
                    ((EditText)findViewById(R.id.text_registrarse_pass)).setError("Ambas contraseñas deben de coincidir.");
                }
                if(ok){
                    registar(alias, email, pass);
                }
            }
        });
    }

    private void registar(String alias, String email, String pass) {
        try {
            JSONObject jsonObject = new GestionUsuariosRemoto().execute("REGISTRAR", alias, email, hash(pass)).get();
            if(jsonObject != null){
                if(jsonObject.has("ERROR")){
                    Log.e("REGISTROACTIVITY", jsonObject.getString("ERROR"));
                }else{
                    Toast.makeText(this, "El registro se ha realizado correctamente.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public String hash(String plaintext){
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.reset();
        m.update(plaintext.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
        while(hashtext.length() < 32 ){
            hashtext = "0"+hashtext;
        }
        return hashtext;
    }

}
