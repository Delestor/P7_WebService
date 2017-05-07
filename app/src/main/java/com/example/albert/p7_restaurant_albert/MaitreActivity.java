package com.example.albert.p7_restaurant_albert;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MaitreActivity extends ListActivity {

    ArrayList<Plato> platos;
    ArrayAdapter<Plato> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maitre);

        platos = new ArrayList<>();
        cargarListaPlatos();

    }

    public void cargarListaPlatos() {

        String newURL = Constantes.ACCEDER_MAITRE_PLATS;
        GestionPeticionesHTTP colaPeticiones = null;

        colaPeticiones.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(Request.Method.POST, newURL, null,

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

        );


    }

    private void procesarRespuesta(JSONObject response) {
/*
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
*/
        try {
            String stringArray = "resultado";
            String primers = "Primers";
            String segons = "Segons";
            String postres = "Postres";

            String actual = primers;
            boolean final_lectura = false;
            int count = 0;

            do{
                String aux = stringArray+actual+count;
                if(response.has(aux)){
                    //JSONArray arrayActual = response.getJSONArray(aux);
                    JSONObject c = response.getJSONObject(aux);
                    System.out.println("Auxiliar: "+aux);
                    System.out.println(c.getString("nom"));
                    Plato plato = new Plato();
                    plato.setNom(c.getString("nom"));
                    plato.setKcal(c.getString("kcal"));
                    platos.add(plato);
                    count++;
                }else{
                    System.out.println("No existe "+aux);
                    count = 0;
                    if(actual.equals(primers)){
                        actual = segons;
                    }else if(actual.equals(segons)){
                        actual = postres;
                    }else if (actual.equals(postres)){
                        //final lectura de Json
                        final_lectura = true;
                    }
                }
            }while(!final_lectura);

            adapter = new AdaptadorPlatosMetre(this, platos);
            setListAdapter(adapter);
        } catch (JSONException e) {

            System.out.println("Aqui hay un error, "+e);

        }
    }

}
