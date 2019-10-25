package upeu.edu.pe.rest2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

public class EditarActivity extends AppCompatActivity {

    private EditText edtn,edtap,edtuser;
    private Button PHP,java,node,python;
    private Usuario usuario = new Usuario();
    private String ip = "192.168.43.189";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usuario = new Usuario(Integer.parseInt(getIntent().getStringExtra("ID")),getIntent().getStringExtra("NAME"),getIntent().getStringExtra("CORREO"),getIntent().getStringExtra("PASS"),getIntent().getStringExtra("ORIGEN"));
        Toast.makeText(this,usuario.toString(),Toast.LENGTH_LONG).show();

        setContentView(R.layout.activity_editar);

        edtn = (EditText)findViewById(R.id.nombres);
        edtap = (EditText)findViewById(R.id.apellidos);
        edtuser = (EditText)findViewById(R.id.user);

        PHP = (Button)findViewById(R.id.btncalcular);
        java = (Button)findViewById(R.id.btnjava);
        node = (Button)findViewById(R.id.btnnode);
        python = (Button)findViewById(R.id.btnpython);
        edtn.requestFocus();

        edtn.setText(usuario.getName());
        edtap.setText(usuario.getCorreo());
        edtuser.setText(usuario.getPass());
    }

    public void reset(View view){
        limpiar();
    }

    public void php(View view){
        String nombres = edtn.getText().toString();
        String apellidos = edtap.getText().toString();
        String usuario = edtuser.getText().toString();
        String url = "http://" + ip + "/rest/actualizar.php?";
        datosUsuarios(nombres,apellidos,usuario,url);
    }
    public void java(View view){
        String nombres = edtn.getText().toString();
        String apellidos = edtap.getText().toString();
        String usuario = edtuser.getText().toString();
        String url = "http://" + ip + ":8080/usuarios/update?";
        datosUsuarios(nombres,apellidos,usuario,url);
    }
    public void node(View view){
        String nombres = edtn.getText().toString();
        String apellidos = edtap.getText().toString();
        String usuario = edtuser.getText().toString();
        String url = "http://" + ip + ":3002/users/update?";
        datosUsuarios(nombres,apellidos,usuario,url);
    }
    public void python(View view){
        String nombres = edtn.getText().toString();
        String apellidos = edtap.getText().toString();
        String usuario = edtuser.getText().toString();
        String url = "http://" + ip + ":5000/update?";
        datosUsuarios(nombres,apellidos,usuario,url);
    }

    public void phpA(View view){
        String nombres = "";
        String apellidos = "";
        String usuario = "";
        String url = "http://" + ip + "/rest/delete.php?";
        datosUsuarios(nombres,apellidos,usuario,url);
    }
    public void javaA(View view){
        String nombres = "";
        String apellidos = "";
        String usuario = "";
        String url = "http://" + ip + ":8080/usuarios/delete?";
        datosUsuarios(nombres,apellidos,usuario,url);
    }
    public void nodeA(View view){
        String nombres = "";
        String apellidos = "";
        String usuario = "";
        String url = "http://" + ip + ":3002/users/delete?";
        datosUsuarios(nombres,apellidos,usuario,url);
    }
    public void pythonA(View view){
        String nombres = "";
        String apellidos = "";
        String usuario = "";
        String url = "http://" + ip + ":5000/delete?";
        datosUsuarios(nombres,apellidos,usuario,url);
    }

    public void listar(View view){
        Intent intent = new Intent(this, ListarActivity.class);
        startActivity(intent);
    }

    public void datosUsuarios(String nombres, String apellidos, String usuario, String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        String parametros = "id=" + this.usuario.getId() + "&name=" + nombres + "&correo=" + apellidos + "&pass=" + usuario;
        String Parameter = parametros.replace(" ","%20");
        String urlParameter = Parameter.replace("\n","%0A");
        if (nombres.isEmpty() && apellidos.isEmpty() && usuario.isEmpty()) {
            client.delete(url + "id=" + this.usuario.getId(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200) {
                        String resultado = new String(responseBody);
                        Toast.makeText(EditarActivity.this, "Registro ELIMINADO correctamente...! " + resultado, Toast.LENGTH_LONG).show();
                        limpiar();
                        Intent intent = new Intent(getApplicationContext(), ListarActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EditarActivity.this, "No se Registro", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    System.out.println("ERROR AQUI" + error);
                    Toast.makeText(EditarActivity.this, "Mal: " + error, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            System.out.println(parametros);
            client.put(url + urlParameter, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200) {
                        String resultado = new String(responseBody);
                        Toast.makeText(EditarActivity.this, "Registro Actualizado correctamente...! " + resultado, Toast.LENGTH_LONG).show();
                        limpiar();
                        Intent intent = new Intent(getApplicationContext(), ListarActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EditarActivity.this, "No se Registro", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    System.out.println("ERROR AQUI" + error);
                    Toast.makeText(EditarActivity.this, "Mal: " + error, Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    public void limpiar(){
        edtn.setText("");
        edtap.setText("");
        edtuser.setText("");
        edtn.requestFocus();
    }
}