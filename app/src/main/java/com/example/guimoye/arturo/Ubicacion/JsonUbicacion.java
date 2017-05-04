package com.example.guimoye.arturo.Ubicacion;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.arturo.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Guimoye on 27/10/2016.
 */

public class JsonUbicacion {

    String resultado="";
    Context myContext;
    View v;
    String GET = "";
    String nro="";
    MapView m;

    String[]Susuario;
    String[]Slatitud;
    String[] Slongitud;
    int STipo[];

    JSONArray alumnosJSON=null;
    LayoutInflater inflaterr;
    ViewGroup containerr;

    public String getUsuario(int i){ return Susuario[i];}
    public String getLatitud(int i){ return Slatitud[i];}
    public String getLongitud(int i){ return Slongitud[i];}
    public int getTipo(int i){ return STipo[i];}

    private void reset(){
        Susuario    =   new String[alumnosJSON.length()];
        Slatitud    =   new String[alumnosJSON.length()];
        Slongitud   =   new String[alumnosJSON.length()];
        STipo       =   new int[alumnosJSON.length()];
    }

    public JsonUbicacion(View v, LayoutInflater inflater, ViewGroup container, final Context myContext, MapView m){
        this.myContext          =   myContext;
        this.inflaterr          =   inflater;
        this.containerr         =   container;
        this.v                  =   v;
        this.m                  =   m;
    }


    public void Hilo(String GET, final String nro){
        this.GET    =   GET;
        this.nro    =   nro;

       class ObtenerWebService extends AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String... params) {

                String cadena = params[0];
                URL url = null; // Url de donde queremos obtener información
                String devuelve ="";


                if(params[1]=="1"){    // Consulta de ubicacions
                    try {
                        url = new URL(cadena);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                        connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                                " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                        //connection.setHeader("content-type", "application/json");

                        int respuesta = connection.getResponseCode();
                        StringBuilder result = new StringBuilder();

                        if (respuesta == HttpURLConnection.HTTP_OK){

                            InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                            // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                            // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                            // StringBuilder.

                            String line;
                            while ((line = reader.readLine()) != null) {
                                result.append(line);        // Paso toda la entrada al StringBuilder
                            }

                            //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                            JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                            //Accedemos al vector de resultados

                                alumnosJSON = respuestaJSON.getJSONArray("Ubicaciones");   // estado es el nombre del campo en el JSON


                                reset();
                                for(int i=0;i<result.length();i++){
                                    devuelve = devuelve +
                                            alumnosJSON.getJSONObject(i).getString("usuario") + " " +
                                            alumnosJSON.getJSONObject(i).getInt("tipo") + " " +
                                            alumnosJSON.getJSONObject(i).getString("latitud") + " " +
                                            alumnosJSON.getJSONObject(i).getString("longitud") + "\n";

                                    Susuario[i]     =   alumnosJSON.getJSONObject(i).getString("usuario");
                                    STipo[i]        =   alumnosJSON.getJSONObject(i).getInt("tipo");
                                    Slatitud[i]     =   alumnosJSON.getJSONObject(i).getString("latitud");
                                    Slongitud[i]    =   alumnosJSON.getJSONObject(i).getString("longitud");

                                }

                        }


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return devuelve;


                }
                else if(params[1]=="2"){    // insertar cliente


                    RequestQueue  queue = Volley.newRequestQueue(myContext);
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, cadena,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.

                                    try {
                                        JSONObject respuestaJSON = new JSONObject(response.toString());
                                      String resultado = respuestaJSON.getString("Estado");
                                       if(resultado.equals("1")){
                                          Toast.makeText(myContext, "Ubicacion de cliente registrada con exito", Toast.LENGTH_SHORT).show();
                                       }else{
                                           if(resultado.equals("2")){
                                               Toast.makeText(myContext, "disculpe el cliente insertado ya esta registrado", Toast.LENGTH_SHORT).show();
                                           }else{
                                               if(resultado.equals("0")){
                                                   Toast.makeText(myContext, "disculpe el cliente insertado no existe", Toast.LENGTH_SHORT).show();
                                               }
                                           }
                                       }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }



                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(myContext, "Disculpe no hay conexion", Toast.LENGTH_SHORT).show();
                        }
                    });
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }

                return null;
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }

            @Override
            protected void onPostExecute(String s) {
                resultado   =   s;

               if(nro.equals("1")){

                   m.getMapAsync(new OnMapReadyCallback() {

                       @Override
                       public void onMapReady(GoogleMap googleMap) {

                           //  googleMap.setMapType(googleMap.MAP_TYPE_HYBRID);
                           // googleMap.getUiSettings().setZoomControlsEnabled(true);
                           googleMap.getUiSettings().setCompassEnabled(true);
                           googleMap.getUiSettings().setMapToolbarEnabled(true);
                           if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION)
                                   != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                                   != PackageManager.PERMISSION_GRANTED) {
                               // TODO: Consider calling

                               return;
                           }
                           googleMap.setMyLocationEnabled(true);
                           googleMap.getUiSettings().setMyLocationButtonEnabled(true);

                           for (int i = 0; i<alumnosJSON.length(); i++){
                               LatLng sydney = new LatLng(Double.parseDouble(getLatitud(i)), Double.parseDouble(getLongitud(i)));

                               if(getTipo(i)==0){
                                   googleMap.addMarker(new MarkerOptions().position(sydney).title("Información")
                                           .snippet("Usuario: "+getUsuario(i)
                                                   +"\nTipo: "+getTipo(i)
                                                   +"\nLatitud: "+getLatitud(i)
                                                   +"\nLongitud: "+getLongitud(i))//DameDireccion(getLatitud(i),getLongitud(i)))
                                           //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                           .icon(BitmapDescriptorFactory.fromResource(R.drawable.negocio)));
                                   googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12));
                               }else{
                                   if(getTipo(i)==1){
                                       googleMap.addMarker(new MarkerOptions().position(sydney).title("Información")
                                               .snippet("Usuario: "+getUsuario(i)
                                                       +"\nTipo: "+getTipo(i)
                                                       +"\nLatitud: "+getLatitud(i)
                                                       +"\nLongitud: "+getLongitud(i))//DameDireccion(getLatitud(i),getLongitud(i)))
                                               //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                               .icon(BitmapDescriptorFactory.fromResource(R.drawable.camion)));
                                       googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12));
                                   }
                               }



                               googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                                   @Override
                                   public View getInfoWindow(Marker marker) {
                                       return null;
                                   }

                                   @Override
                                   public View getInfoContents(Marker marker) {
                                       Context context = myContext;

                                       LinearLayout info = new LinearLayout(context);
                                       info.setOrientation(LinearLayout.VERTICAL);

                                       TextView title = new TextView(context);
                                       title.setTextColor(Color.BLACK);
                                       title.setGravity(Gravity.CENTER);
                                       title.setTypeface(null, Typeface.BOLD);
                                       title.setText(marker.getTitle());

                                       TextView snippet = new TextView(context);
                                       snippet.setTextColor(Color.GRAY);
                                       snippet.setText(marker.getSnippet());

                                       info.addView(title);
                                       info.addView(snippet);

                                       return info;


                                   }
                               });


                           }

                       }

                   });
               }else{
                   if(nro.equals("3")){

                   }
               }


                super.onPostExecute(s);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }

        ObtenerWebService g = new ObtenerWebService();
        g.execute(GET,nro);


    }

}
