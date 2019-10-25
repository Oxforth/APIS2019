package upeu.edu.pe.rest2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListarActivity extends AppCompatActivity {

    private ListView lvdatos;
    private String ip = "192.168.43.189";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        lvdatos = (ListView) findViewById(R.id.lista);
        lvdatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuario = (Usuario) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(),EditarActivity.class);
                intent.putExtra("ID", String.valueOf(usuario.getId()));
                intent.putExtra("NAME", usuario.getName());
                intent.putExtra("CORREO", usuario.getCorreo());
                intent.putExtra("PASS", usuario.getPass());
                intent.putExtra("ORIGEN", usuario.getOrigen());
                startActivity(intent);
            }
        });
    }

    public void php(View view){
        String url = "http://" + ip + "/rest/listaPHP.php";
        listarUsuarios(url);
    }

    public void java(View view){
        String url = "http://" + ip + ":8080/usuarios/listJava";
        listarUsuarios(url);
    }
    public void python(View view){
        String url = "http://" + ip + ":5000/userpython";
        listarUsuarios(url);
    }
    public void node(View view){
        String url = "http://" + ip + ":3002/users/node";
        listarUsuarios(url);
    }

    public void phpA(View view){
        Toast.makeText(this,"NUEVA PETICION PHP",Toast.LENGTH_LONG).show();
        String url = "http://" + ip + "/rest/ListaUsuarios.php";
        listarUsuarios(url);
    }

    public void javaA(View view){
        Toast.makeText(this,"NUEVA PETICION JAVA",Toast.LENGTH_LONG).show();
        String url = "http://" + ip + ":8080/usuarios/list";
        listarUsuarios(url);
    }
    public void pythonA(View view){
        Toast.makeText(this,"NUEVA PETICION PYTHON",Toast.LENGTH_LONG).show();
        String url = "http://" + ip + ":5000/users";
        listarUsuarios(url);
    }
    public void nodeA(View view){
        Toast.makeText(this,"NUEVA PETICION NODE JS",Toast.LENGTH_LONG).show();
        String url = "http://" + ip + ":3002/users";
        listarUsuarios(url);
    }

    public void CREAR(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void listarUsuarios(String url){
        AsyncHttpClient client = new AsyncHttpClient();
        Toast.makeText(this,"ESPERANDO RESPUESTA",Toast.LENGTH_LONG).show();

        client.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarLisview(getJson(new String(responseBody)));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        Toast.makeText(this,"RECIBIENDO RESPUESTA",Toast.LENGTH_LONG).show();
    }

    public void cargarLisview(ArrayList<Usuario> datos){
        lvdatos.setAdapter(null);
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(this,android.R.layout.simple_list_item_1,datos);
        lvdatos.setAdapter(adapter);
    }

    public ArrayList<Usuario> getJson(String response){
        ArrayList<Usuario> listaS = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(response);
            for(int i=0; i<array.length();i++){
                Usuario usuario = new Usuario();
                usuario.setId(array.getJSONObject(i).getInt("idusuario"));
                usuario.setName(array.getJSONObject(i).getString("name"));
                usuario.setCorreo(array.getJSONObject(i).getString("correo"));
                usuario.setPass(array.getJSONObject(i).getString("pass"));
                usuario.setOrigen(array.getJSONObject(i).getString("origen"));
                listaS.add(usuario);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaS;
    }
}
