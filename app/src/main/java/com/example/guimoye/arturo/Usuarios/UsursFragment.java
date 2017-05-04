package com.example.guimoye.arturo.Usuarios;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.example.guimoye.arturo.R;
import java.util.ArrayList;

public class UsursFragment extends Fragment {

    ListView lista;
    ArrayList<String> personas = new ArrayList<>();
    ArrayList<String> tipo = new ArrayList<>();
    ArrayList<String> correo = new ArrayList<>();
    ArrayList<Long> tlfno = new ArrayList<>();
    ListViewAdapterUsuars adapter;
    JsonUsers ju;
    LayoutInflater inflater;
    ViewGroup container;
     int positionn;
    View v;
    // Rutas de los Web Services
    String direccion="http://192.168.11.115:8282/arturo/usuarios.php?modelo=";
    String GET1 = direccion+"6";
    String GET2 = direccion+"5&usuario=";
    AlertDialog.Builder builder;
    AlertDialog ad;
    AutoCompleteTextView umUsuario,umTipo,umClave,umCorreo,umTlfno,umPregunta,umRespuesta;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.inflater   =   inflater;
        this.container  =   container;
        v               =   inflater.inflate(R.layout.fragment_usuraios,container,false);
        lista           =   (ListView) v.findViewById(R.id.listaUsuarios);
        ju              =   new JsonUsers(v,inflater,container,getActivity().getBaseContext(),lista,personas,tipo,correo,tlfno,GET1,"1");

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.register_user, null);

                    umUsuario    = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtUsuarioRegister);
                    umTipo       = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtTipoRegister);
                    umClave      = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtClaveRegister);
                    umCorreo     = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtCorreoRegister);
                    umTlfno      = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtTlfnoRegister);
                    umPregunta   = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtPreguntaRegister);
                    umRespuesta  = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtRespuestaRegister);

                final Button btnRegistrarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_registrar_users);
                btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        validacions();
                        //ad.dismiss();
                    }
                });

                builder.setView(dialoglayout);
                ad = builder.create();
                ad.show();
/*
                Snackbar.make(v, "presione registrar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/


            }
        });
        ju.getLista().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) {
                positionn=position;
                PopupMenu popup = new PopupMenu(getActivity(), view);
                popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    //MENU FILA
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_settings:
                                ju.Hilo(GET2+ju.getUsuario(position),"2");
                                break;
                            case R.id.action_settings1:
                                builder = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                final View dialoglayout = inflater.inflate(R.layout.modificar_user, null);

                                umUsuario    = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtUsuarioModificar);
                                umTipo       = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtTipoModificar);
                                umClave      = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtClaveModificar);
                                umCorreo     = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtCorreoModificar);
                                umTlfno      = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtTlfnoModificar);
                                umPregunta   = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtPreguntaModificar);
                                umRespuesta  = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtRespuestaModificar);

                                umUsuario.setText(ju.getUsuario(position));
                                umTipo.setText(ju.getTipo(position)+"");
                                umClave.setText(ju.getClave(position));
                                umCorreo.setText(ju.getCorreo(position));
                                umTlfno.setText(ju.getTlfno(position)+"");
                                umPregunta.setText(ju.getPregunta(position));
                                umRespuesta.setText(ju.getRespuesta(position));

                                final Button btnMoficiarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_modificar_usuario);
                                btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ju.Hilo(direccion+"4" +
                                                        "&usuarioid="+ju.getUsuario(position)+"" +
                                                        "&usuario="+umUsuario.getText()+"" +
                                                        "&tipo="+umTipo.getText()+"" +
                                                        "&clave="+umClave.getText()+"" +
                                                        "&correo="+umCorreo.getText()+"" +
                                                        "&telefono="+umTlfno.getText()+"" +
                                                        "&pregunta="+umPregunta.getText()+"" +
                                                        "&respuesta="+umRespuesta.getText()+""
                                                ,"3");
/*
                                        ju.setUsuario(position,umUsuario.getText().toString());
                                        ju.setClave(position,umClave.getText().toString());
                                        ju.setCorreo(position,umCorreo.getText().toString());
                                        ju.setPregunta(position,umPregunta.getText().toString());
                                        ju.setRespuesta(position,umRespuesta.getText().toString());
                                        ju.setTipo(position,Integer.parseInt(umTipo.getText().toString()));
                                        ju.setTlfno(position, Long.parseLong(umTlfno.getText().toString()));*/
                                        ad.dismiss();
                                    }
                                });

                                builder.setView(dialoglayout);
                                ad = builder.create();
                                ad.show();

                                break;
                        }
                        return true;
                    }
                    //FIN MENU FILA
                });
                popup.show();
                return true;
            }
        });


        return v;
    }

    private void limpiarRequired(){
        umUsuario.setError(null);
        umTipo.setError(null);
        umClave.setError(null);
        umCorreo.setError(null);
        umTlfno.setError(null);
        umPregunta.setError(null);
        umRespuesta.setError(null);
    }

    private void validacions(){
        limpiarRequired();
        if(umUsuario.getText().length()!=0){
            if(umTipo.getText().length()!=0){
            if(umClave.getText().length()!=0){
                if(umCorreo.getText().toString().contains("@") && umCorreo.getText().toString().contains(".com") ){
                    if(umTlfno.getText().length()!=0){
                        if(umPregunta.getText().length()!=0){
                            if(umRespuesta.getText().length()!=0){
/*
                                usuario     =   umUsuario.getText().toString();
                                clave       =   umClave.getText().toString();
                                email       =   umCorreo.getText().toString();
                                tlfnoo       =   umTlfno.getText().toString();
                                pregunta    =   umPregunta.getText().toString();
                                respuesta   =   umRespuesta.getText().toString();*/
                                String sql=direccion+"1" +
                                        "&usuario="+umUsuario.getText().toString()+"" +
                                        "&tipo="+umTipo.getText().toString()+"" +
                                        "&clave="+umClave.getText().toString()+"" +
                                        "&correo="+umCorreo.getText().toString()+"" +
                                        "&telefono="+umTlfno.getText().toString()+"" +
                                        "&pregunta="+umPregunta.getText().toString()+"" +
                                        "&respuesta="+umRespuesta.getText().toString()+"";
                                ju.Hilo(sql,"4");

                               // Toast.makeText(getActivity(),"deberia registrar dsps de esto",Toast.LENGTH_LONG).show();
                                limpiar();
                                ad.dismiss();

                            }else{
                                vali(umRespuesta);
                            }
                        }else{
                            vali(umPregunta);
                        }
                    }else{
                        vali(umTlfno);
                    }
                }else{
                    umCorreo.setError(getString(R.string.error_invalid_email));
                    v=umCorreo;
                    v.requestFocus();
                }
            }else{
                vali(umClave);
            }
            }else{
                vali(umTipo);
            }
        }else{
            vali(umUsuario);
        }



    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        v=t;
        v.requestFocus();
    }
    private void limpiar(){
        umUsuario.setText(null);
        umTipo.setText(null);
        umClave.setText(null);
        umCorreo.setText(null);
        umTlfno.setText(null);
        umPregunta.setText(null);
        umRespuesta.setText(null);
    }

}
