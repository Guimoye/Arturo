package com.example.guimoye.arturo.Ubicacion;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import com.example.guimoye.arturo.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.MapView;



public class GmapFragment extends Fragment {

    MapView m;
    AlertDialog.Builder builder;
    AlertDialog ad;
    View v;
    JsonUbicacion ju;
    FloatingActionButton menu1, menu3;
    FloatingActionsMenu bExpandible;
    LayoutInflater inflater2,inflater3,inflater4;
    AutoCompleteTextView usuario,latitud,longitud;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflater2=inflater;
        v           =   inflater.inflate(R.layout.fragment_gmaps, container, false);
        m           =   (MapView) v.findViewById(R.id.mapView);
        ju          =   new JsonUbicacion(v,inflater,container,getActivity(),m);
        menu1       =   (FloatingActionButton) v.findViewById(R.id.subFloatingMenu1) ;
      //  menu2       =   (FloatingActionButton) v.findViewById(R.id.subFloatingMenu2) ;
        menu3       =   (FloatingActionButton) v.findViewById(R.id.subFloatingMenu3) ;
        bExpandible =   (FloatingActionsMenu) v.findViewById(R.id.multiple_actions) ;
        //bExpandible.setIcon(R.drawable.ic_settings_fb);

        /************** ventana de busqueda *****************/
        builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.consultar_ubicaciones, null);

        final Spinner listaBusqueda                 = (Spinner) dialoglayout.findViewById(R.id.selectBusquedaUbicaciones);
        ArrayAdapter<CharSequence> adapterSpinner   = ArrayAdapter.createFromResource(this.getActivity(),R.array.opcionesUbica,
                android.R.layout.simple_spinner_item);
        listaBusqueda.setAdapter(adapterSpinner);
        final Button btnMoficiarUsuario             = (Button) dialoglayout.findViewById(R.id.btn_ConsultarUbicacion);

        btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("esto es loq selecciono ","seleccione: "+listaBusqueda.getSelectedItemPosition());
                if(listaBusqueda.getSelectedItemPosition()==0){
                  // ju.cambiarTipo(0);
                   ju.Hilo("http://192.168.11.115:8282/arturo/ubicacions.php?modelo=5&tipo="+0+"","1");
                }else{
                    if(listaBusqueda.getSelectedItemPosition()==1){
                        ju.Hilo("http://192.168.11.115:8282/arturo/ubicacions.php?modelo=5&tipo="+1+"","1");
                    }else{
                        if(listaBusqueda.getSelectedItemPosition()==2){
                            ju.Hilo("http://192.168.11.115:8282/arturo/ubicacions.php?modelo=4","1");
                        }
                    }
                }

                ad.dismiss();
            }
        });

        builder.setView(dialoglayout);
        ad = builder.create();
        ad.show();




        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ju.Hilo("http://192.168.11.115:8282/arturo/ubicacions.php?modelo=4","1");
            }
        });

/*
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        */

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /************** ventana de agregar cliente*****************/
                builder     = new AlertDialog.Builder(getActivity());
                inflater2   = getActivity().getLayoutInflater();
                final View dialoglayout = inflater2.inflate(R.layout.register_ubicacion_cliente, null);

                usuario     = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtRegister_Ubicacion_usuario);
                latitud     = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtRegister_Ubicacion_latitud);
                longitud    = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtRegister_Ubicacion_longitud);

                final Button btnRegistrarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_registrar_ubicacion_cliente);
                btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       validacions();
                    }
                });

                builder.setView(dialoglayout);
                ad = builder.create();
                ad.show();
            }
        });

            try {  m.onCreate(savedInstanceState); }catch (Exception e){ }

        return v;
    }

    private void limpiarRequired(){
        usuario.setError(null);
        latitud.setError(null);
        longitud.setError(null);
    }

    private void limpiar(){
        usuario.setText(null);
        latitud.setText(null);
        longitud.setText(null);
    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        v=t;
        v.requestFocus();
    }

    private void validacions(){
        limpiarRequired();
        if(usuario.getText().length()!=0){
            if(latitud.getText().length()!=0){
                if(longitud.getText().length()!=0){

                        String sql="http://192.168.11.115:8282/arturo/ubicacions.php?modelo=1" +
                                "&usuario="+usuario.getText()+"" +
                                "&latitud="+latitud.getText()+"" +
                                "&longitud="+longitud.getText()+"";
                        ju.Hilo(sql,"2");
                        // Toast.makeText(getActivity(),"deberia registrar dsps de esto",Toast.LENGTH_LONG).show();
                        limpiar();
                        ad.dismiss();
                }else{
                    vali(longitud);
                }
                }else{
                    vali(latitud);
                }
        }else{
            vali(usuario);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        m.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        m.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        m.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        m.onLowMemory();
    }

}
