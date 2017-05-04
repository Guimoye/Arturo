package com.example.guimoye.arturo.Usuarios;

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

public class JsonUsers {


    ListView lista;
    ArrayList<String> personas;
    ArrayList<String> tipo;
    ArrayList<String> correo;
    ArrayList<Long> tlfno;
    JsonUsers ju;
    String direccion="http://192.168.11.115:8282/arturo/usuarios.php?modelo=";
    String resultado;
    ListViewAdapterUsuars adapter;
    Context myContext;
    View v;
   // String IP = "http://192.168.11.115:8282/arturo";
    // Rutas de los Web Services
    String GET = "";
    String nro="";
    String nroSelect="";

    String[] Susuario;
    String[] Sclave;
    String[] Scorreo;
    String[] Spregunta;
    String[] Srespuesta;
    Long[] Stlfno;
    int[] Stipo;
    JSONArray alumnosJSON=null;
   // JsonUsersDelete ju;
    LayoutInflater inflaterr;
    ViewGroup containerr;

    public String getUsuario(int i){ return Susuario[i];}
    public String getClave(int i){ return Sclave[i];}
    public String getCorreo(int i) { return Scorreo[i]; }
    public String getPregunta(int i) { return Spregunta[i];}
    public String getRespuesta(int i) { return Srespuesta[i]; }
    public Long getTlfno(int i) {  return Stlfno[i]; }
    public int getTipo(int i) {  return Stipo[i]; }
    public ListView getLista (){
        return lista;
    }

    private void reset(){
        Susuario    = new String[alumnosJSON.length()];
        Sclave      = new String[alumnosJSON.length()];
        Scorreo     = new String[alumnosJSON.length()];
        Spregunta   = new String[alumnosJSON.length()];
        Srespuesta  = new String[alumnosJSON.length()];
        Stlfno      = new Long[alumnosJSON.length()];
        Stipo       = new int[alumnosJSON.length()];
        personas.clear();
        tipo.clear();
        correo.clear();
        tlfno.clear();
    }

    public JsonUsers(View v,LayoutInflater inflater, ViewGroup container, Context myContext,
                     ListView lista, ArrayList<String> personas, ArrayList<String> tipo,
                     ArrayList<String> correo, ArrayList<Long> tlfno, String GET, String nroSelect){
        this.myContext      =   myContext;
        this.lista          =   lista;
        this.personas       =   personas;
        this.correo         =   correo;
        this.tipo           =   tipo;
        this.tlfno          =   tlfno;
        this.GET            =   GET;
        this.nroSelect      =   nroSelect;
        this.inflaterr      =   inflater;
        this.containerr     =   container;
        this.v              =   v;
        this.personas       =   new ArrayList<>();
        this.tipo           =   new ArrayList<>();
        this.correo         =   new ArrayList<>();
        this.tlfno          =   new ArrayList<>();
        Hilo(GET,nroSelect);
    }

    public JsonUsers(Context myContext, String GET, String nroSelect){
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

                            //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                            JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                            //Accedemos al vector de resultados

                            Log.e("epaaaa ","sapooo respuestaaa line "+result.length()+"");

                                alumnosJSON = respuestaJSON.getJSONArray("Users");   // estado es el nombre del campo en el JSON

                                reset();
                                for(int i=0;i<result.length();i++){
                                    devuelve = devuelve +
                                            alumnosJSON.getJSONObject(i).getString("usuario") + " " +
                                            alumnosJSON.getJSONObject(i).getInt("tipo") + " " +
                                            alumnosJSON.getJSONObject(i).getString("clave") + " " +
                                            alumnosJSON.getJSONObject(i).getString("correo") + " " +
                                            alumnosJSON.getJSONObject(i).getLong("telefono") + " " +
                                            alumnosJSON.getJSONObject(i).getString("pregunta") + " " +
                                            alumnosJSON.getJSONObject(i).getString("respuesta") + "\n";

                                    personas.add(alumnosJSON.getJSONObject(i).getString("usuario"));
                                    tipo.add(alumnosJSON.getJSONObject(i).getString("tipo"));
                                    correo.add(alumnosJSON.getJSONObject(i).getString("correo"));
                                    tlfno.add(alumnosJSON.getJSONObject(i).getLong("telefono"));

                                    Susuario[i]     =   alumnosJSON.getJSONObject(i).getString("usuario");
                                    Stipo[i]        =   alumnosJSON.getJSONObject(i).getInt("tipo");
                                    Sclave[i]       =   alumnosJSON.getJSONObject(i).getString("clave");
                                    Scorreo[i]      =   alumnosJSON.getJSONObject(i).getString("correo");
                                    Stlfno[i]       =   alumnosJSON.getJSONObject(i).getLong("telefono");
                                    Spregunta[i]    =   alumnosJSON.getJSONObject(i).getString("pregunta");
                                    Srespuesta[i]   =   alumnosJSON.getJSONObject(i).getString("respuesta");
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
                                devuelve = "usuario modificado satisfactoriamente";
                            }else if (resultJSON.equals("0")){
                                devuelve = "disculpe el usuario introducido ya existe";
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
                                devuelve = "usuario registrado satisfactoriamente";
                            }else if (resultJSON.equals("0")){
                                devuelve = "disculpe el usuario introducido ya existe";
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return devuelve;
                }
                else if(params[1]=="5") {    // register login
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
                               devuelve = "usuario registrado satisfactoriamente";
                           }else if (resultJSON.equals("0")){
                               devuelve = "disculpe el usuario introducido ya existe";
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
                    adapter = new ListViewAdapterUsuars(myContext, personas,tipo,correo,tlfno);
                    lista.setAdapter(adapter);
                }else{
                    if(nro.equals("2") || nro.equals("3") || nro.equals("4")){
                        Toast.makeText(myContext,s,Toast.LENGTH_SHORT).show();
                        lista.setAdapter(null);
                        Hilo(direccion+"6","1");
                    }else{
                        if(nro.equals("5") || nro.equals("6")){
                            Toast.makeText(myContext,s,Toast.LENGTH_SHORT).show();
                        }
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
