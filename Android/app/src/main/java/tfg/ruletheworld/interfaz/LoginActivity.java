package tfg.ruletheworld.interfaz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import tfg.ruletheworld.R;
import tfg.ruletheworld.datos.GestionUsuariosRemoto;

public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button entrarboton = (Button) findViewById(R.id.buttonEntrar);
        entrarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correcto = true;
                String email = ((EditText)findViewById(R.id.editEmail)).getText().toString();
                if(email == null || email.isEmpty() || !email.contains("@")){
                    ((EditText)findViewById(R.id.editEmail)).setError("El campo debe de contener un email valido.");
                    correcto = false;
                }
                String pass = ((EditText)findViewById(R.id.editPass)).getText().toString();
                if(pass == null || pass.isEmpty() || pass.length() < 4){
                    ((EditText)findViewById(R.id.editPass)).setError("El campo debe de contener una contraseña con mas de 4 caracteres.");
                    correcto = false;
                }
                if(correcto){
                    boolean ok = identificar(email, pass);
                    if(ok){
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        ((EditText)findViewById(R.id.editEmail)).setError("El email y la contraseña no coinciden.");
                        findViewById(R.id.editEmail).requestFocus();
                    }

                }
            }
        });
        TextView registrarText = (TextView)findViewById(R.id.textRegistro);
        registrarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(i);
            }
        });
    }

    private boolean identificar(String email, String pass) {
        //todo
        boolean rdo = false;
        try {
            JSONObject json = new GestionUsuariosRemoto().execute("IDENTIFICAR", email, hash(pass)).get();
            if(json.has("ERROR")){
                Log.e("LOGINACTIVITY", json.getString("ERROR"));
            }else{
                String id = null;
                if(json.has("ID")){
                    id = json.getString("ID");
                    rdo = true;
                }
                if(rdo){
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                    sp.edit().putBoolean("REGISTRADO", true).apply();
                    sp.edit().putString("ID", id).apply();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
