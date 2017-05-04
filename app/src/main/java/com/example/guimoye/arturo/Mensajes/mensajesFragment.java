package com.example.guimoye.arturo.Mensajes;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.arturo.R;

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

public class mensajesFragment extends Fragment {

    View v;
    EditText txt_enviar_mensaje;
    Button btn_enviar_msj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v                   =   inflater.inflate(R.layout.fragment_mensaje, container, false);
        txt_enviar_mensaje  =   (EditText) v.findViewById(R.id.txt_enviar_mensaje);
        btn_enviar_msj      =   (Button) v.findViewById(R.id.btn_enviar_msj);

        btn_enviar_msj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // Log.e("sapooooo","sapoooooo "+txt_enviar_mensaje.getText().toString().replaceAll(" ","%20").replaceAll("\n","%20"));
                final String llego=txt_enviar_mensaje.getText().toString().replaceAll(" ","%20").replaceAll("\n","%20");
                String ho="http://192.168.11.115:8282/arturo/mensaje.php?modelo=3&msj="+llego+"&status=1";

                final String ssss= txt_enviar_mensaje.getText().toString();

                RequestQueue queue= Volley.newRequestQueue(getActivity());
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, ho,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Toast.makeText(getActivity(), "mensaje enviado :)", Toast.LENGTH_SHORT).show();
                              //  txt_msj_recibido.setText("Ultimo mensaje recibido: "+ssss);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Disculpe no hay conexion", Toast.LENGTH_SHORT).show();
                    }
                });
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

                txt_enviar_mensaje.setText("");
            }
        });


        return v;
    }



}
