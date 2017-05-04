package com.example.albert.p7_restaurant_albert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private boolean cargarMaitreActivity = false;
    private boolean cargarCuinerActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
    }

    public void logInAction(View view){

        String user = etUsername.getText().toString();
        String pass = etPassword.getText().toString();

        HashMap<String, String> map = new HashMap<>();// Mapeo previo
        map.put("login","true");
        map.put("username",user);
        map.put("password",pass);

        //consultaJSONPost(Constantes.POST_LOG_IN, map);

        consultasJSONGet(Constantes.POST_LOG_IN, map);

        if(cargarMaitreActivity){
            Intent intent = new Intent(getApplicationContext(), MaitreActivity.class);
            startActivity(intent);
            cargarMaitreActivity = false;
        }else if(cargarCuinerActivity){
            Intent intent = new Intent(getApplicationContext(), CuinerActivity.class);
            startActivity(intent);
            cargarCuinerActivity = false;
        }else{
            System.out.println("Fallo al loggear");
        }


    }

    public void consultasJSONGet(String enlace, Map map){
        String newURL = enlace + "?";

        int count = map.size();
        if(map.containsKey("login")){
            newURL +="login="+map.get("login")+"&username="+map.get("username")+"&password="+map.get("password");
        }

        GestionPeticionesHTTP colaPeticiones = null;

        colaPeticiones.getInstance(this).addToRequestQueue(

                new JsonObjectRequest(Request.Method.GET, newURL, null,

                        new Response.Listener<JSONObject>() {

                            @Override

                            public void onResponse(JSONObject response) {
                                //procesarRespuesta(response); // Procesar respuesta Json
                                try { // Procesar respuesta Json
                                    String estado = response.getString("estado");
                                    String mensaje = response.getString("mensaje");
                                    Toast.makeText(getApplicationContext(), mensaje,
                                            Toast.LENGTH_LONG).show();
                                    if(response.getString("tipus").equalsIgnoreCase("cuiner")){
                                        cargarCuinerActivity = true;
                                    }else if(response.getString("tipus").equalsIgnoreCase("maitre")){
                                        cargarMaitreActivity = true;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },

                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Log.d("--Consultar DB--", "Error Volley: " + error.getMessage());

                            }

                        }

                )

        );
    }

    public void consultaJSONPost(String enlace, Map map){
        JSONObject jobject = new JSONObject(map);
        GestionPeticionesHTTP colaPeticiones = null;

        colaPeticiones.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(Request.Method.POST, enlace, jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try { // Procesar respuesta Json
                                    String estado = response.getString("estado");
                                    String mensaje = response.getString("mensaje");
                                    Toast.makeText(getApplicationContext(), mensaje,
                                            Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("--Actualizar BD--", "Error Volley: " +
                                        error.getMessage());
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8" + getParamsEncoding();
                    }
                }
        );
    }

    /***Mostrar datos del item seleccionado ***/
    public void cargarDatosModif(int id) {

        String newURL = Constantes.ACCEDER_CUINER_PLATS + "?idCita=" + id;
        GestionPeticionesHTTP colaPeticiones = null;

        colaPeticiones.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(Request.Method.GET, newURL, null,

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuesta(response); // Procesar respuesta Json
                            }
                        },

                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("--CargarDatosModif--", "Error Volley: " + error.getMessage());
                            }

                        }

                )

        );}

    /*Procesa cada uno de los estados posibles de la respuesta enviada desde el servidor*/
    private void procesarRespuesta(JSONObject response) {

        try {

            String mensaje = response.getString("estado");

            switch (mensaje) {

                case "1": //Respuesta correcta: Obtener objeto "citas"

                    //JSONObject object = response.getJSONObject("mensaje");
                    String msg = response.getString("mensaje");
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                    //parseJson(response);
                    break;

                case "2": //No se obtuvo el registro

                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(this, mensaje2, Toast.LENGTH_LONG).show();
                    break;

                case "3": //Se necesita identificador

                    String mensaje3 = response.getString("mensaje");
                    Toast.makeText(this, mensaje3, Toast.LENGTH_LONG).show();
                    break;

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

    }

    /*** Parsear objeto y ponerlo en la vista ***/

    public void parseJson(JSONObject jsonObject) {

        try {

            JSONObject objeto = jsonObject.getJSONObject("cita");
            //Cita cita = new Cita(objeto.getInt("id"), objeto.getString("horacita"), objeto.getString("diacita"),objeto.getString("asuntocita"));
            //horaCita.setText(cita.getHora());
            //descripcionCita.setText(cita.getAsunto());

        } catch (JSONException e) {

            Log.e("ERROR JSON", "Error de parsing: " + e.getMessage());

        }

    }

}
