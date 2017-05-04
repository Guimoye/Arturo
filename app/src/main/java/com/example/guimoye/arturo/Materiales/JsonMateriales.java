package com.example.guimoye.arturo.Materiales;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.ArrayList;

/**
 * Created by Guimoye on 27/10/2016.
 */

public class JsonMateriales {


    ListView lista;
    ArrayList<String> arrayId;
    ArrayList<String> arrayMateriales;

    String direccion="http://192.168.11.115:8282/arturo/materiales.php?modelo=";
    String resultado;
    ListViewAdapterMateriales adapter;
    Context myContext;
    View v;
   // String IP = "http://192.168.11.115:8282/arturo";
    // Rutas de los Web Services
    String GET = "";
    String nro="";
    String nroSelect="";

    String[] id_materiales;
    String[] materiales;

    JSONArray alumnosJSON=null;
   // JsonUsersDelete ju;
    LayoutInflater inflaterr;
    ViewGroup containerr;

    public String getId_materiales(int i){ return id_materiales[i];}
    public String getMateriales(int i){ return materiales[i];}
    public ListView getLista (){
        return lista;
    }

    private void reset(){
        id_materiales = new String[alumnosJSON.length()];
        materiales = new String[alumnosJSON.length()];
        arrayId.clear();
        arrayMateriales.clear();
    }

    public JsonMateriales(View v, LayoutInflater inflater, ViewGroup container, Context myContext,
                          ListView lista, ArrayList<String> personas, ArrayList<String> tipo,String GET, String nroSelect){
        this.myContext          =   myContext;
        this.lista              =   lista;
        this.arrayId            =   personas;
        this.arrayMateriales    =   tipo;
        this.GET                =   GET;
        this.nroSelect          =   nroSelect;
        this.inflaterr          =   inflater;
        this.containerr         =   container;
        this.v                  =   v;

        this.arrayId            =   new ArrayList<>();
        this.arrayMateriales    =   new ArrayList<>();

        Hilo(GET,nroSelect);
    }

    public JsonMateriales(Context myContext, String GET, String nroSelect){
        this.myContext      =   myContext;
        this.GET            =   GET;
        this.nroSelect      =   nroSelect;
        Hilo(GET,nroSelect);
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



                if(params[1]=="1"){    // Consulta de todos los alumnos
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
                            Log.e("este es materiales","este es materialessss1");
                            //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                            JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                            //Accedemos al vector de resultados
                            Log.e("este es materiales","este es materialessss33 "+respuestaJSON);
                            Log.e("epaaaa ","sapooo respuestaaa line "+result.length()+"");

                                alumnosJSON = respuestaJSON.getJSONArray("Materiales");   // estado es el nombre del campo en el JSON

                                reset();
                                for(int i=0;i<result.length();i++){
                                    devuelve = devuelve +
                                            alumnosJSON.getJSONObject(i).getString("id_material") + " " +
                                            alumnosJSON.getJSONObject(i).getString("nombre_material") + "\n";

                                    arrayId.add(alumnosJSON.getJSONObject(i).getString("id_material"));
                                    arrayMateriales.add(alumnosJSON.getJSONObject(i).getString("nombre_material"));

                                    id_materiales[i]     =   alumnosJSON.getJSONObject(i).getString("id_material");
                                    materiales[i]       =   alumnosJSON.getJSONObject(i).getString("nombre_material");

                                    Log.e("paso ","veces ");
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
                else if(params[1]=="2"){    // eliminar alumnos

                    try {
                        url = new URL(cadena);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                        connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                                " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                        //connection.setHeader("content-type", "application/json");

                        int respuesta = connection.getResponseCode();

                        if (respuesta == HttpURLConnection.HTTP_OK){
                            devuelve = "eliminado con exito";
                            Log.e("errorrr","errrorrr: "+devuelve);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return devuelve;


                }
                else if(params[1]=="3"){    // update

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

                            String resultJSON = respuestaJSON.getString("Estado");   // estado es el nombre del campo en el JSON
                            Log.e("sapooo, ","siiiii: "+resultJSON);
                            if (resultJSON.equals("1")){      // hay un alumno que mostrar
                                devuelve = "material modificado satisfactoriamente";
                            }else if (resultJSON.equals("0")){
                                devuelve = "disculpe el material introducido ya existe";
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return devuelve;
                }
                else if(params[1]=="4"){    // registrar
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

                            String resultJSON = respuestaJSON.getString("Estado");   // estado es el nombre del campo en el JSON
                            Log.e("sapooo, ","siiiii: "+resultJSON);
                            if (resultJSON.equals("1")){      // hay un alumno que mostrar
                                devuelve = "material registrado satisfactoriamente";
                            }else if (resultJSON.equals("0")){
                                devuelve = "disculpe el material introducido ya existe";
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return devuelve;
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
                    adapter = new ListViewAdapterMateriales(myContext, arrayId,arrayMateriales);
                    lista.setAdapter(adapter);
                }else{
                    if(nro.equals("2") || nro.equals("3") || nro.equals("4")){
                        Toast.makeText(myContext,s,Toast.LENGTH_SHORT).show();
                        lista.setAdapter(null);
                        Hilo(direccion+"4","1");
                    }
                }

                //super.onPostExecute(s);
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
