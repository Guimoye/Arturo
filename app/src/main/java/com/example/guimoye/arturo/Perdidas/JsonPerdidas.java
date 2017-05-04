package com.example.guimoye.arturo.Perdidas;

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

public class JsonPerdidas {


    ListView lista;
    ArrayList<String> arrayId_perdida;
    ArrayList<String> arrayId_material;
    ArrayList<Integer> arrayCantidad;
    ArrayList<String> arrayFecha;
    String direccion="http://192.168.11.115:8282/arturo/perdida.php?modelo=";
    String resultado;
    ListViewAdapterPerdidas adapter;
    Context myContext;
    View v;
    String GET = "";
    String nro="";
    String nroSelect="";
    String fecha1,fecha2;

    String[] Sid_Perdida;
    String[] Sid_material;
    int[] Scantidad;
    String[] Sfecha;
    JSONArray alumnosJSON=null;
   // JsonUsersDelete ju;
    LayoutInflater inflaterr;
    ViewGroup containerr;

    public String getId_Perdida(int i){ return Sid_Perdida[i];}
    public String getId_Material(int i){ return Sid_material[i];}
    public int getCantidad(int i){ return Scantidad[i];}
    public String getFecha(int i){ return Sfecha[i];}

    public ListView getLista (){
        return lista;
    }

    private void reset(){
        Sid_Perdida     =   new String[alumnosJSON.length()];
        Sid_material    =   new String[alumnosJSON.length()];
        Scantidad       =   new int[alumnosJSON.length()];
        Sfecha          =   new String[alumnosJSON.length()];

        arrayId_perdida.clear();
        arrayId_material.clear();
        arrayCantidad.clear();
        arrayFecha.clear();
    }

    public JsonPerdidas(View v, LayoutInflater inflater, ViewGroup container, Context myContext,
                        ListView lista, ArrayList<String> ArrayId_perdida, ArrayList<String> ArrayId_material,
                        ArrayList<Integer> ArrayCantidad, ArrayList<String> ArrayFecha,String GET, String nroSelect){
        this.myContext          =   myContext;
        this.lista              =   lista;
        this.arrayId_perdida    =   ArrayId_perdida;
        this.arrayId_material   =   ArrayId_material;
        this.arrayCantidad      =   ArrayCantidad;
        this.arrayFecha         =   ArrayFecha;
        this.GET                =   GET;
        this.nroSelect          =   nroSelect;
        this.inflaterr          =   inflater;
        this.containerr         =   container;
        this.v                  =   v;

        this.arrayId_perdida    =   new ArrayList<>();
        this.arrayId_material   =   new ArrayList<>();
        this.arrayCantidad      =   new ArrayList<>();
        this.arrayFecha         =   new ArrayList<>();
       // Hilo(GET,nroSelect);
    }

    public void cambiarFechas(String fecha1,String fecha2){
        this.fecha1=fecha1;
        this.fecha2=fecha2;
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
                        url = new URL(cadena+"&fecha1="+fecha1+"&fecha2="+fecha2+"");
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

                                alumnosJSON = respuestaJSON.getJSONArray("Perdidas");   // estado es el nombre del campo en el JSON

                                reset();
                                for(int i=0;i<result.length();i++){
                                    devuelve = devuelve +
                                            alumnosJSON.getJSONObject(i).getString("id_perdida") + " " +
                                            alumnosJSON.getJSONObject(i).getString("id_material") + " " +
                                            alumnosJSON.getJSONObject(i).getInt("cantidad") + " " +
                                            alumnosJSON.getJSONObject(i).getString("fecha_perdida") + "\n";

                                    arrayId_perdida.add(alumnosJSON.getJSONObject(i).getString("id_perdida"));
                                    arrayId_material.add(alumnosJSON.getJSONObject(i).getString("id_material"));
                                    arrayCantidad.add(alumnosJSON.getJSONObject(i).getInt("cantidad"));
                                    arrayFecha.add(alumnosJSON.getJSONObject(i).getString("fecha_perdida"));

                                    Sid_Perdida[i]  =   alumnosJSON.getJSONObject(i).getString("id_perdida");
                                    Sid_material[i] =   alumnosJSON.getJSONObject(i).getString("id_material");
                                    Scantidad[i]    =   alumnosJSON.getJSONObject(i).getInt("cantidad");
                                    Sfecha[i]       =   alumnosJSON.getJSONObject(i).getString("fecha_perdida");

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
                            Log.e("sapooo, ","conectado para modificar ");
                            InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                            // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                            // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                            // StringBuilder.

                            String line;
                            while ((line = reader.readLine()) != null) {
                                result.append(line);        // Paso toda la entrada al StringBuilder
                            }
                            Log.e("sapooo, ","para modificar da esto: "+line);
                            //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                            JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                            //Accedemos al vector de resultados

                            String resultJSON = respuestaJSON.getString("Estado");   // estado es el nombre del campo en el JSON
                            Log.e("sapooo, ","siiiii: "+resultJSON);
                            if (resultJSON.equals("1")){      // hay un alumno que mostrar
                                devuelve = "perdida de material modificado satisfactoriamente";
                            }else if (resultJSON.equals("0")){
                                devuelve = "disculpe el material introducido no existe";
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
                                devuelve = "Perdida registrado satisfactoriamente";
                            }else if (resultJSON.equals("0")){
                                devuelve = "disculpe el cod del material introducido no existe";
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
                    adapter = new ListViewAdapterPerdidas(myContext, arrayId_perdida, arrayId_material, arrayCantidad,arrayFecha);
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
