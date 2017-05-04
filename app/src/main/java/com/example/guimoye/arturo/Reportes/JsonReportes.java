package com.example.guimoye.arturo.Reportes;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guimoye.arturo.Perdidas.ListViewAdapterPerdidas;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

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
import java.util.Random;

/**
 * Created by Guimoye on 27/10/2016.
 */

public class JsonReportes {


    ListView lista;
    ArrayList<Double> arrayTotal;
    ArrayList<String> arrayFecha;
    String resultado="";
    ListViewAdapterReportes adapter;
    Context myContext;
    View v;
    String GET = "";
    String nro="";
    String nroSelect="";
    String fecha1,fecha2;

    float[] Stotal;
    String[] Sfecha;
    JSONArray alumnosJSON=null;
    LayoutInflater inflaterr;
    ViewGroup containerr;
    PieChart mChart;
    Random rnd = new Random();

    public float getTotal(int i){ return Stotal[i];}
    public String getFecha(int i){ return Sfecha[i];}
    public ListView getLista (){
        return lista;
    }

    private void reset(){
        Stotal  =   new float[alumnosJSON.length()];
        Sfecha  =   new String[alumnosJSON.length()];

        arrayTotal.clear();
        arrayFecha.clear();
    }

    public JsonReportes(View v, LayoutInflater inflater, ViewGroup container, Context myContext,
                        ListView lista,ArrayList<Double> ArrayTotal, ArrayList<String> ArrayFecha){
        this.myContext          =   myContext;
        this.lista              =   lista;
        this.arrayTotal         =   ArrayTotal;
        this.arrayFecha         =   ArrayFecha;
        this.inflaterr          =   inflater;
        this.containerr         =   container;
        this.v                  =   v;

        this.arrayTotal         =   new ArrayList<>();
        this.arrayFecha         =   new ArrayList<>();
    }

    public void cambiarFechas(String fecha1,String fecha2){
        this.fecha1=fecha1;
        this.fecha2=fecha2;
    }

    public void cambiarGrafica(PieChart mChart){
        this.mChart = mChart;
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


                if(params[1]=="1"){    // Consulta de ganancias
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

                                alumnosJSON = respuestaJSON.getJSONArray("ReportDespacho");   // estado es el nombre del campo en el JSON

                                reset();
                                for(int i=0;i<result.length();i++){
                                    devuelve = devuelve +
                                            alumnosJSON.getJSONObject(i).getDouble("total") + " " +
                                            alumnosJSON.getJSONObject(i).getString("fecha") + "\n";

                                    arrayTotal.add(alumnosJSON.getJSONObject(i).getDouble("total"));
                                    arrayFecha.add(alumnosJSON.getJSONObject(i).getString("fecha"));

                                    Stotal[i]    = (float) alumnosJSON.getJSONObject(i).getDouble("total");
                                    Sfecha[i]    =   alumnosJSON.getJSONObject(i).getString("fecha");

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
                else if(params[1]=="2"){    // consulta perdidas

                    try {
                        url = new URL(cadena+"&fecha1="+fecha1+"&fecha2="+fecha2+"");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                        connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                                " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                        //connection.setHeader("content-type", "application/json");
                        Log.e("sapooo, "," llamando consulta para perdida: ");
                        int respuesta = connection.getResponseCode();
                        StringBuilder result = new StringBuilder();

                        if (respuesta == HttpURLConnection.HTTP_OK){
                            Log.e("sapooo, "," Conecnto para la perdidas: ");
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

                            alumnosJSON = respuestaJSON.getJSONArray("ReportPerdidas");   // estado es el nombre del campo en el JSON
                            Log.e("sapooo, "," Conecnto para la PERDIDAS: ");
                            reset();
                            for(int i=0;i<result.length();i++){
                                devuelve = devuelve +
                                        alumnosJSON.getJSONObject(i).getInt("total") + " " +
                                        alumnosJSON.getJSONObject(i).getString("fecha") + "\n";

                                arrayTotal.add(alumnosJSON.getJSONObject(i).getDouble("total"));
                                arrayFecha.add(alumnosJSON.getJSONObject(i).getString("fecha"));

                                Stotal[i]    = (float) alumnosJSON.getJSONObject(i).getDouble("total");
                                Sfecha[i]    =   alumnosJSON.getJSONObject(i).getString("fecha");

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

                return null;
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }

            @Override
            protected void onPostExecute(String s) {
                resultado   =   s;

                if(resultado.length()!=0){
                    adapter = new ListViewAdapterReportes(myContext, arrayTotal,arrayFecha);
                    lista.setAdapter(adapter);


                    // configure pie chart
                    mChart.setUsePercentValues(true);

                    // enable hole and configure
                    mChart.setDrawHoleEnabled(true);
                    // mChart.setHoleColorTransparent(true);
                    mChart.setHoleRadius(7);
                    mChart.setTransparentCircleRadius(10);

                    // enable rotation of the chart by touch
                    mChart.setRotationAngle(0);
                    mChart.setRotationEnabled(true);

                    // set a chart value selected listener
                    mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

                        @Override
                        public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                            // display msg when value selected
                            if (e == null)
                                return;

                            Toast.makeText(myContext,
                                    Sfecha[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    });


                    addData();

                    // customize legends
                    Legend l = mChart.getLegend();
                    l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
                    l.setXEntrySpace(7);
                    l.setYEntrySpace(5);

                }else{
                    Toast.makeText(myContext,"Disculpe la busqueda realizada no tiene datos",Toast.LENGTH_SHORT);
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



    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < Stotal.length; i++)
            yVals1.add(new Entry(Stotal[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < Sfecha.length; i++)
            xVals.add(Sfecha[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int i =0; i<=Sfecha.length;i++){
            colors.add(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        }

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }

}
