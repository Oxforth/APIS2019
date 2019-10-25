package upeu.edu.pe.rest2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private EditText edtn,edtap,edtuser;
    private Button PHP,java,node,python;
    String ip = "192.168.43.189";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtn = (EditText)findViewById(R.id.nombres);
        edtap = (EditText)findViewById(R.id.apellidos);
        edtuser = (EditText)findViewById(R.id.user);
        PHP = (Button)findViewById(R.id.btncalcular);
        java = (Button)findViewById(R.id.btnjava);
        node = (Button)findViewById(R.id.btnnode);
        python = (Button)findViewById(R.id.btnpython);
        edtn.requestFocus();
    }

    public void reset(View view){
        limpiar();
    }

    public void listar(View view){
        Intent intent = new Intent(getApplicationContext(), ListarActivity.class);
        startActivity(intent);
    }

    public void save(View view){
        String nombres = edtn.getText().toString();
        String apellidos = edtap.getText().toString();
        String usuario = edtuser.getText().toString();
        String url = "http://" + ip + "/rest/registro.php?";
        datosUsuarios(nombres,apellidos,usuario,url);
    }
    public void java(View view){
        String nombres = edtn.getText().toString();
        String apellidos = edtap.getText().toString();
        String usuario = edtuser.getText().toString();
        String url = "http://" + ip + ":8080/usuarios/insert?";
        datosUsuarios(nombres,apellidos,usuario,url);
    }
    public void node(View view){
        String nombres = edtn.getText().toString();
        String apellidos = edtap.getText().toString();
        String usuario = edtuser.getText().toString();
        String url = "http://" + ip + ":3002/users/insert?";
        datosUsuarios(nombres,apellidos,usuario,url);
    }
    public void python(View view){
        String nombres = edtn.getText().toString();
        String apellidos = edtap.getText().toString();
        String usuario = edtuser.getText().toString();
        String url = "http://" + ip + ":5000/add?";
        datosUsuarios(nombres,apellidos,usuario,url);
    }

    public void datosUsuarios(String nombres, String apellidos, String usuario, String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        String parametros = "name=" + nombres + "&correo=" + apellidos + "&pass=" + usuario;
        String urlParameter = parametros.replace(" ","%20");
        client.post(url + urlParameter, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String resultado = new String(responseBody);
                    Toast.makeText(MainActivity.this, "Registro Guardado correctamente...! " + resultado, Toast.LENGTH_LONG).show();
                    limpiar();
                    Intent intent = new Intent(getApplicationContext(), ListarActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "No se Registro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("ERROR AQUI" + error);
                Toast.makeText(MainActivity.this, "Mal: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }


    public void limpiar(){
        edtn.setText("");
        edtap.setText("");
        edtuser.setText("");
        edtn.requestFocus();

    }
}
