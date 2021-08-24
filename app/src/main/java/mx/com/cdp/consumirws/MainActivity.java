package mx.com.cdp.consumirws;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//https://www.youtube.com/watch?v=X41YQJoQGUI
    EditText txtUser, txtTitle, txtBody;
    Button btnEnviar;
    FloatingActionButton btnFloating;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //asigno las variables a los botones de la ventana
        txtUser = findViewById(R.id.txtUser);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnFloating = findViewById(R.id.floatingActionButton);
        linearLayout = findViewById(R.id.linearLayout);



        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // LeerWs();  //necesitamos añadir los permisos de internet
                //enviarWs();   //este metodo inicial sería de una forma estatica

                //enviar con parametros que le paso por lo cumplimentado en el formulario
                //  enviarWs(txtTitle.getText().toString(), txtBody.getText().toString(), txtUser.getText().toString());
             //actualizarWs(txtTitle.getText().toString(), txtBody.getText().toString(), txtUser.getText().toString());
                eliminarWs();
            }
        });

        btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //snackbar needs a view where is placed!!!
                Snackbar.make(linearLayout, "Mensaje Sanckbar", Snackbar.LENGTH_INDEFINITE)
                        .setTextColor(Color.BLACK)
                        .setBackgroundTint(Color.YELLOW)

                        //if you want to add a button to execute an action
                                                .setAction("pulsa AQUI", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "esta es la tostada",Toast.LENGTH_LONG).show();
                            }
                        })

                        .show();
            }
        });

    }

    private void LeerWs() {
//ruta del webService q vamos a consumir
        String url = "https://jsonplaceholder.typicode.com/posts/15";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //cnd nos devuelva una respuesta y capturamos la respuesta
                try {
                    //el try Y catch nos los sugiere y los ponemos automaticamente.
                    JSONObject jsonObject = new JSONObject(response);
                    //traemos los elementos q nos va a traer el webservice
                    txtUser.setText(jsonObject.getString("userId"));
                    txtTitle.setText(jsonObject.getString("title"));
                    txtBody.setText(jsonObject.getString("body"));
        //asi ya traemos los datos

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
            Log.e("dav1", "error al obtener los datos de consulta");
            }
        });
        //enviamos los datos
        Volley.newRequestQueue(this).add(postRequest);
     }

     //tengo q poner las variables como final para que pueda recnocerlos correctamente
  private void enviarWs(final String title, final String body, final String userId) {
  //  private void enviarWs() {
//voy a enviar un registro para guardar
        String url = "https://jsonplaceholder.typicode.com/posts";
//copio el metodo leer y lo modifico
        StringRequest postResquest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    //traemos los elementos q nos va a traer el webservice
             /*
                    txtUser.setText(jsonObject.getString(userId));
                    txtTitle.setText(jsonObject.getString(title));
                    txtBody.setText(jsonObject.getString(body));


              */


                    //el Id lo vamos a mostrar con el Toast
                    Toast.makeText(MainActivity.this, "id enviado POST = " + response, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        }) {
            //para enviar, necesito la clase Map
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", "yo");  //
                params.put("body", "esto es una prueba");
                params.put("userId", "2000");

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postResquest);
    }

    private void actualizarWs(final String title, final String body, final String userId) {

        String url = "https://jsonplaceholder.typicode.com/posts/1";

        StringRequest postResquest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(MainActivity.this, "RESULTADO = " + response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", "1");   //voy a moidificar estaticamente el id 1
                params.put("title", title);
                params.put("body", body);
                params.put("userId", userId);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postResquest);
    }

    private void eliminarWs() {

        String url = "https://jsonplaceholder.typicode.com/posts/1";

        StringRequest postResquest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(MainActivity.this, "RESULTADO = " + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postResquest);
    }
}