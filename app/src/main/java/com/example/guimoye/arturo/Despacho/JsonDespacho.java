package com.example.guimoye.arturo.Despacho;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
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

public class JsonDespacho {

   // http://192.168.11.115:8282/arturo/inventario.php?modelo=4&fecha1=2016-11-09&fecha2=2016-11-10
    ListView lista;
    ArrayList<String> arrayId_Despacho;
    ArrayList<String> arrayId_Inventario;
    ArrayList<Integer> arrayCantidad;
    ArrayList<Double> arrayTotal;
    ArrayList<Integer> arrayEstado;
    ArrayList<String> arrayFecha;

    String direccion="http://192.168.11.115:8282/arturo/despacho.php?modelo=";
    String resultado;
    ListViewAdapterDespacho adapter;
    Context myContext;
    View v;
    String GET = "";
    String nro="";
    String fecha1,fecha2;

    String[] Sid_Despacho;
    String[] Sid_Inventario;
    int[] Scantidad;
    double[] Stotal;
    int[] Sestado;
    String[] Sfecha;

    int ScantidadInventario =0;
    double SPrecioProducto=0;

    JSONArray alumnosJSON=null;
   // JsonUsersDelete ju;
    LayoutInflater inflaterr;
    ViewGroup containerr;
    AutoCompleteTextView cantidadInventario,precio,total;
    ChangeDatos ch;

    public String getId_Despacho(int i){ return Sid_Despacho[i];}
    public String getId_Inventario(int i){ return Sid_Inventario[i];}
    public int getCantidad(int i){ return Scantidad[i];}
    public double getTotal(int i){ return Stotal[i];}
    public int getEstado(int i){ return Sestado[i];}
    public String getFecha(int i){ return Sfecha[i];}
    public double getPrecio(){ return SPrecioProducto; }
    public int getCantidadInventario(){ return ScantidadInventario; }
    public int getCantidadLimite(){
        return ch.getCantidadlimite();
    }


    public ListView getLista (){
        return lista;
    }

    private void reset(){
        Sid_Despacho        =   new String[alumnosJSON.length()];
        Sid_Inventario      =   new String[alumnosJSON.length()];
        Scantidad           =   new int[alumnosJSON.length()];
        Stotal              =   new double[alumnosJSON.length()];
        Sestado             =   new int[alumnosJSON.length()];
        Sfecha              =   new String[alumnosJSON.length()];
        SPrecioProducto     =   0;
        ScantidadInventario =   0;

        arrayId_Despacho.clear();
        arrayId_Inventario.clear();
        arrayCantidad.clear();
        arrayTotal.clear();
        arrayEstado.clear();
        arrayFecha.clear();
    }

    public JsonDespacho(View v, LayoutInflater inflater, ViewGroup container, Context myContext, ListView lista,
                        ArrayList<String> Arrayid_despacho, ArrayList<String> Arrayid_inventario,
                        ArrayList<Integer> ArrayCantidad,  ArrayList<Double> ArrayTotal,
                        ArrayList<Integer> ArrayEstado,ArrayList<String> ArrayFecha){

        this.myContext              =   myContext;
        this.lista                  =   lista;
        this.arrayId_Despacho       =   Arrayid_despacho;
        this.arrayId_Inventario     =   Arrayid_inventario;
        this.arrayCantidad          =   ArrayCantidad;
        this.arrayTotal             =   ArrayTotal;
        this.arrayEstado            =   ArrayEstado;
        this.arrayFecha             =   ArrayFecha;

        this.inflaterr              =   inflater;
        this.containerr             =   container;
        this.v                      =   v;
        this.arrayId_Despacho       =   new ArrayList<>();
        this.arrayId_Inventario     =   new ArrayList<>();
        this.arrayCantidad          =   new ArrayList<>();
        this.arrayTotal             =   new ArrayList<>();
        this.arrayEstado            =   new ArrayList<>();
        this.arrayFecha             =   new ArrayList<>();

       // Hilo(GET,nroSelect);
    }

    public void setEditCantidad(AutoCompleteTextView precio,AutoCompleteTextView cantidadInventario,
                                AutoCompleteTextView total){
        this.precio             =   precio;
        this.cantidadInventario = cantidadInventario;
        this.total = total;
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

                Log.e("esta es la cadena","estoo: "+cadena);

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

                            Log.e("epaaaa ","sapooo consulta linea "+result.length()+"");

                                alumnosJSON = respuestaJSON.getJSONArray("Despacho");   // estado es el nombre del campo en el JSON

                                reset();
                                for(int i=0;i<result.length();i++){
                                    devuelve = devuelve +
                                            alumnosJSON.getJSONObject(i).getString("id_despacho") + " " +
                                            alumnosJSON.getJSONObject(i).getString("id_inventario") + " " +
                                            alumnosJSON.getJSONObject(i).getInt("cantidad") + " " +
                                            alumnosJSON.getJSONObject(i).getDouble("total") + " " +
                                            alumnosJSON.getJSONObject(i).getInt("estado") + " " +
                                            alumnosJSON.getJSONObject(i).getString("fecha_salida") + "\n";

                                    arrayId_Despacho.add(alumnosJSON.getJSONObject(i).getString("id_despacho"));
                                    arrayId_Inventario.add(alumnosJSON.getJSONObject(i).getString("id_inventario"));
                                    arrayCantidad.add(alumnosJSON.getJSONObject(i).getInt("cantidad"));
                                    arrayTotal.add(alumnosJSON.getJSONObject(i).getDouble("total"));
                                    arrayEstado.add(alumnosJSON.getJSONObject(i).getInt("estado"));
                                    arrayFecha.add(alumnosJSON.getJSONObject(i).getString("fecha_salida"));

                                    Sid_Despacho[i]     =   alumnosJSON.getJSONObject(i).getString("id_despacho");
                                    Sid_Inventario[i]   =   alumnosJSON.getJSONObject(i).getString("id_inventario");
                                    Scantidad[i]        =   alumnosJSON.getJSONObject(i).getInt("cantidad");
                                    Stotal[i]           =   alumnosJSON.getJSONObject(i).getDouble("total");
                                    Sestado[i]          =   alumnosJSON.getJSONObject(i).getInt("estado");
                                    Sfecha[i]           =   alumnosJSON.getJSONObject(i).getString("fecha_salida");
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
                                devuelve = "inventario modificado satisfactoriamente";
                            }else if (resultJSON.equals("0")){
                                devuelve = "disculpe el id_producto introducido no ya existe";
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
                                devuelve = "despacho registrado satisfactoriamente";

                            }else if (resultJSON.equals("0")){
                                devuelve = "disculpe el id_inventario introducido no existe";
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return devuelve;
                }else if(params[1]=="5"){    // Consulta de precio de producto y cantidad
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
                            Log.e("epaaaa ","longitud de resultadoooos: "+result.length()+"");

                            if(result.length()>12){
                                alumnosJSON = respuestaJSON.getJSONArray("DespachoPrecioProducto");   // estado es el nombre del campo en el JSON

                                Log.e("epaaaa ","alumnos es esto: "+alumnosJSON+"");
                                reset();

                                devuelve = devuelve +
                                        alumnosJSON.getJSONObject(0).getDouble("precio_producto") + " " +
                                        alumnosJSON.getJSONObject(0).getInt("cantidad_inventario") + " " + "\n";

                                SPrecioProducto      =   alumnosJSON.getJSONObject(0).getDouble("precio_producto");
                                ScantidadInventario  =   alumnosJSON.getJSONObject(0).getInt("cantidad_inventario");
                                Log.e("epaaaa ","precio : "+ SPrecioProducto+"");
                                Log.e("epaaaa ","cantidad: "+ScantidadInventario+"");
                            }else{
                                SPrecioProducto = 0.0;
                                ScantidadInventario =0;
                            }

                        }

                    } catch (Exception e) { e.printStackTrace();  }

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
                    adapter = new ListViewAdapterDespacho(myContext, arrayId_Despacho,
                            arrayId_Inventario, arrayCantidad, arrayTotal, arrayEstado, arrayFecha);
                    lista.setAdapter(adapter);
                }else{
                    if(nro.equals("2") || nro.equals("3") || nro.equals("4")){
                        Toast.makeText(myContext,s,Toast.LENGTH_SHORT).show();
                        lista.setAdapter(null);
                        Hilo(direccion+"5","1");
                    }else{
                        if(nro.equals("5")){
                            cantidadInventario.setText(ScantidadInventario+"");
                            precio.setText(SPrecioProducto+"");
                            cantidadInventario.addTextChangedListener(ch=new ChangeDatos(precio,cantidadInventario,total));
                        }
                    }
                }

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
