package tfg.ruletheworld.interfaz;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tfg.ruletheworld.R;
import tfg.ruletheworld.logica.Jugador;
import tfg.ruletheworld.logica.Objeto;
import tfg.ruletheworld.logica.Objetos;

public class ObjetoDetailActivity extends ActionBarActivity {

    private Objeto o;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto_detail);
        int id = getIntent().getExtras().getInt("IDOBJETO");
        //Log.i("ObjetoDetailActivity", "IDOBJETO:"+id);
        o = Objetos.getObjetos(getApplicationContext()).obtenerInfoObjeto(id);
        int cantidad = Objetos.getObjetos(getApplicationContext()).obtenerCantidad(id);

        TextView txtNombre = (TextView)findViewById(R.id.nombre);
        TextView txtCantidad = (TextView)findViewById(R.id.Cantidad);
        TextView txtVida = (TextView)findViewById(R.id.info_vida);
        TextView txtAtaque = (TextView)findViewById(R.id.info_ataque);
        TextView txtDefensa = (TextView)findViewById(R.id.info_defensa);
        Button btnTirar = (Button)findViewById(R.id.tirarBoton);

        txtNombre.setText(o.getNombre());
        txtCantidad.setText(cantidad+"");
        txtVida.setText(o.getVida()+"  ");
        txtAtaque.setText(o.getAtaque()+"  ");
        txtDefensa.setText(o.getDefensa()+"  ");

        if(o.isCasco()){
            ImageView imagenCasco = (ImageView)findViewById(R.id.imagenCasco);
            imagenCasco.setImageResource(R.drawable.icon_casco_color);
        }
        if(o.isTorso()){
            ImageView imagenTorso = (ImageView)findViewById(R.id.imagenPeto);
            imagenTorso.setImageResource(R.drawable.icon_torso_color);
        }
        if(o.isPiernas()){
            ImageView imagenPiernas = (ImageView)findViewById(R.id.imagenPiernas);
            imagenPiernas.setImageResource(R.drawable.icon_piernas_color);
        }
        if(o.isPies()){
            ImageView imagenPies = (ImageView)findViewById(R.id.imagenPies);
            imagenPies.setImageResource(R.drawable.icon_pies_color);
        }
        if(o.isArma()){
            ImageView imagenArma = (ImageView)findViewById(R.id.imagenArma);
            imagenArma.setImageResource(R.drawable.icon_arma_color);
        }

        btnTirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objetos.getObjetos(getApplicationContext()).tirarObjeto(o);
                startActivity(getIntent());
                finish();
            }
        });
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.objeto_detail, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
