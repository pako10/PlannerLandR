package plannerland.plannerland;

import android.content.Intent;
import android.content.res.TypedArray;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.jar.Attributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroActivity extends MainActivity implements AdapterView.OnItemSelectedListener{
    Button   BtRegistrate;
    EditText etName;
    EditText etLastName;
    EditText etEmail;
    EditText etPassword;
    EditText etRpassword;
    TextView etTerminos;
    CheckBox spTerminos;
    Spinner  spPaises;
    Spinner  spCiudades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etName = (EditText) findViewById(R.id.Name);
        etLastName = (EditText) findViewById(R.id.LastName);
        etEmail    = (EditText) findViewById(R.id.Email);
        etPassword = (EditText) findViewById(R.id.Rpassword);
        etRpassword =(EditText) findViewById(R.id.ConfirmRpassword);
        BtRegistrate = (Button) findViewById(R.id.registrate);
        etTerminos = (TextView) findViewById(R.id.etTerminos);
        spTerminos = (CheckBox) findViewById(R.id.terminos_condiciones);

        this.spPaises =(Spinner) findViewById(R.id.paises);
        this.spCiudades =(Spinner) findViewById(R.id.ciudades);

        loadSpinnerCiudades();

        final String strUserName = etName.getText().toString();

        etTerminos.setOnClickListener(new TextView.OnClickListener() {
            @Override
        public void onClick(View v){
                executeT();
            }
        });

        BtRegistrate.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                if(etName.getText().toString().trim().length()==0){
                    etName.setError("Porfavor introduce un nombre");

                }
                if(etLastName.getText().toString().trim().length()==0){
                    etLastName.setError("Porfavor introduce tus apellidos");
                    return;
                }
                /*if(etEmail.getText().toString().trim().length()==0||etEmail.getText().toString().trim().contains("@,.") ){
                    etEmail.setError("Porfavor introduce un correo electronico");
                    return;
                }*/
                if(etPassword.getText().toString().trim().length()<6){
                    etPassword.setError("Porfavor introduce un password de minimo 6 letras");
                    return;
                }
                if (!VerificaPass()) {
                    etRpassword.setError("Las contraseñas no coinciden");
                    return;
                }
                if(etEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]")){
                    etEmail.setError("Introdusca");
                    return;
                }




                /*if(etRpassword.getText()== etPassword){
                    etRpassword.setError("Los password con coinciden");
                    return;
                }*/
                /*if(spTerminos.isChecked()){
                    spTerminos.setError("Porfavor revisa los terminos y concidiones");
                }*/
                if (!VerificaTerminos()) {
                    Toast.makeText(getBaseContext(),
                            "Porfavor revisa los terminos y condiciones", Toast.LENGTH_SHORT)
                            .show();
                }

                else {
                    executeC();
                }
            }
        });
    }

    public  void executeC(){
        Intent i = new Intent(this,CalendarioActivity.class);
        startActivity(i);
    }
    public void executeT(){
        Intent i = new Intent(this,CalendarioActivity.class );
        startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro, menu);
        return true;
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


    public boolean VerificaTerminos(){
        boolean checado = false;
        if(spTerminos.isChecked()){
            checado = true;
        }
        return checado;
    }

    public boolean VerificaPass(){
        boolean verificado = false;
        if(etPassword.getText().toString().equals(etRpassword.getText().toString())){
            verificado = true;
        }
        return verificado;
    }

    public boolean isEmailValid(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public  static boolean isEmailVAlid(String email){
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    //Metodo del loadspinner para cargar cada pais con sus ciudades
    private void loadSpinnerCiudades(){
        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.country_arrays, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        this.spPaises.setAdapter(adapter);

        // This activity implements the AdapterView.OnItemSelectedListener
        this.spCiudades.setOnItemSelectedListener(this);
        this.spPaises.setOnItemSelectedListener(this);

    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id){

        switch(parent.getId()){
            case R.id.paises:

                //Regresa un array
                TypedArray arrayCiudades = getResources().obtainTypedArray(
                        R.array.country_a_ciudades_arrays);
                CharSequence[] localidades = arrayCiudades.getTextArray(pos);
                arrayCiudades.recycle();
                //Crear un ArrayAdapter usando el string array y uno por default
                //spinner layout
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                        this, android.R.layout.simple_spinner_item,
                        android.R.id.text1, localidades);
                //Especificar el layout a usar cuando la lista de opciones aparesca
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Aplica el adapter a el spinner
                this.spCiudades.setAdapter(adapter);
                break;

            case R.id.ciudades:

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){
        //activado cuando el adaptador esta vacio
    }
}
