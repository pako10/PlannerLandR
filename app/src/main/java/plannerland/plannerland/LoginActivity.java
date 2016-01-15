package plannerland.plannerland;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class LoginActivity extends AppCompatActivity {
    //Botones Login
    TextView tvResult;
    EditText etUserLogin;
    EditText etPassLogin;
    Button btnOk;
    Button btnCancel;
    TextView parameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserLogin = (EditText) findViewById(R.id.et_user_login);
        etPassLogin = (EditText) findViewById(R.id.et_user_password);
        btnOk       = (Button) findViewById(R.id.btn_user_value_ok);
        //btnCancel   = (Button) findViewById(R.id.btn_user_value_cancel);
        tvResult = (TextView) findViewById(R.id.tv_user_result);
        parameter = (TextView) findViewById(R.id.parameter);
        TextView etRegister = (TextView) findViewById(R.id.registerL);


            etRegister.setOnClickListener(new TextView.OnClickListener(){
                @Override
                public void onClick(View v){
                    register();
                }
            });
            btnOk.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String values = "Se executó";
                    tvResult.setText(values);
                    new HttpRequestTask().execute();

                    if (etUserLogin.getText().toString().trim().length() == 0) {
                        etUserLogin.setError("Porfavor ingresa tu usuario");
                        return;
                    }
                    if (etPassLogin.getText().toString().trim().length() == 0) {
                        etPassLogin.setError("Porfavor ingresa tu contraseña");
                        return;
                    }
                    /*if(!isOnline()){
                        Toast.makeText(getBaseContext(),"Comprueba tu internet",Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }*/


                    Intent i = new Intent(LoginActivity.this, Calendar.class);
                    i.putExtra("param", "param");
                    startActivity(i);

                }
            });
       /* if (!verificaConexion(this)) {
            Toast.makeText(getBaseContext(),
                    "Comprueba tu conexión a Internet.", Toast.LENGTH_SHORT)
                    .show();
            this.finish();
            return;
        }*/

        /*btnCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText(getResources().getString(R.string.tv_user_result));
                etUserLogin.setText("");
                etPassLogin.setText("");
            }
        });*/

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, User> {

        @Override
        protected User doInBackground(Void... params) {
            try {
                //final String urlWS = "http://plannerlandws.azurewebsites.net/api/PID/";
                final String urlWS = "http://192.168.1.67:82/api/PID/";
                String url = urlWS + etUserLogin.getText().toString() + "/" + etPassLogin.getText().toString();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                User user = restTemplate.getForObject(url, User.class);
                return user;
            }catch(Exception ex){
                Log.e("Login", ex.getMessage(), ex);
            }
            return null;
        }

        protected void onPostExecute(User user){
            String nombres = user.Nombres;
            String apellidos = user.Apellidos;
            String errores = user.Errores;
            //boolean existe = user.Activado;
            //if( existe ) {
            tvResult.setText(nombres + " " + apellidos);
            //} else {
            //   tvResult.setText(errores);
            //}
            parameter.setText(nombres);
        }
    }

    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // bucle para ver las diferentes tipos de conexiones
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void register(){
        Intent i = new Intent(this,RegistroActivity.class);
        startActivity(i);
    }
}
