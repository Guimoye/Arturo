package com.example.guimoye.arturo.Despacho;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.guimoye.arturo.Inventario.JsonInventario;
import com.example.guimoye.arturo.R;

import java.util.ArrayList;

/*
Snackbar.make(v, "presione registrar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
*/

public class DespachoFragment extends Fragment {

    ListView lista;
    ArrayList<String> id_despacho   =   new ArrayList<>();
    ArrayList<String> id_inventario =   new ArrayList<>();
    ArrayList<Integer> cantidad     =   new ArrayList<>();
    ArrayList<Double> total         =   new ArrayList<>();
    ArrayList<Integer> estado       =   new ArrayList<>();
    ArrayList<String> fecha         =   new ArrayList<>();
    
    JsonDespacho ju;
    LayoutInflater inflater;
    ViewGroup container;
    int positionn;
    View v;
    // Rutas de los Web Services
    String direccion    =   "http://192.168.11.115:8282/arturo/despacho.php?modelo=";
    String GET1         =   direccion+"5";
    String GET2         =   direccion+"4&id_despacho=";
    AlertDialog.Builder builder;
    AlertDialog ad,ad2;
    AutoCompleteTextView umFechaDsde, umFechaHast, umId_inventario,
                                                    umCantidad,umPrecio,umTotal,umEstado,umFecha;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.inflater   =   inflater;
        this.container  =   container;
        v               =   inflater.inflate(R.layout.fragment_despacho,container,false);
        lista           =   (ListView) v.findViewById(R.id.listaDespacho);
        ju              =   new JsonDespacho(v,inflater,container,getActivity().getBaseContext(),
                lista, id_despacho,id_inventario, cantidad,total,estado, fecha);

        /************** ventana de busqueda *****************/
        builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.consultar_fecha_inventario, null);
        umFechaDsde                 = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtInventario_fechaDesde);
        umFechaHast                 = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtInventario_fechaHasta);
        final Button btnBuscarFecha = (Button) dialoglayout.findViewById(R.id.btn_BuscarInventario_Fecha);
        btnBuscarFecha.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ju.cambiarFechas(umFechaDsde.getText().toString(),umFechaHast.getText().toString());
                ju.Hilo(direccion+"5","1");
                ad.dismiss();
            }
        });

        builder.setView(dialoglayout);
        ad = builder.create();
        ad.show();


        /*************************** boton flotante *************************/
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabDespacho);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.register_despacho, null);

                umId_inventario = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtDespacho_CodInventario_Register);
                umCantidad      = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtDespacho_Cantidad_Register);
                umPrecio        = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtDespacho_Precio_Register);
                umTotal         = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtDespacho_Total_Register);
                umEstado        = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtDespacho_Estado_Register);
                umFecha         = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtDespacho_Fecha_Register);

                umTotal.setEnabled(false);
                umPrecio.setEnabled(false);

                umId_inventario.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(umId_inventario.getText().length()!=0){
                            ju.setEditCantidad(umPrecio,umCantidad,umTotal);
                            ju.Hilo(direccion+"1&id_inventario="+umId_inventario.getText()+"","5");
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) { }
                });
                final Button btnRegistrarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_registrar_despacho);
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

    /************************ lista desplegable****************************/
        ju.getLista().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) {
                positionn=position;
                PopupMenu popup = new PopupMenu(getActivity(), view);
                popup.getMenuInflater().inflate(R.menu.main2, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    //MENU FILA
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_eliminar:
                                ju.Hilo(GET2+ju.getId_Despacho(position),"2");
                                Log.e("el id para eliminar es:","esss: "+ju.getId_Despacho(position));
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
        umId_inventario.setError(null);
        umCantidad.setError(null);
        umTotal.setError(null);
        umEstado.setError(null);
        umFecha.setError(null);
        umPrecio.setError(null);
    }

    private void validacions(){
        limpiarRequired();
        
        if(umId_inventario.getText().length()!=0){
            if(umCantidad.getText().length()!=0){
                if(umTotal.getText().length()!=0){
                    if(umEstado.getText().length()!=0){
                        if(umFecha.getText().length()!=0){
                            try {

                                final String sql    =   direccion+"2" +
                                        "&id_inventario="+umId_inventario.getText()+"" +
                                        "&cantidad="+Integer.parseInt(umCantidad.getText().toString())+"" +
                                        "&total="+Double.parseDouble(umTotal.getText().toString())+"" +
                                        "&estado="+Integer.parseInt(umEstado.getText().toString())+"" +
                                        "&fecha_salida="+umFecha.getText()+"";

                                int cantidadLimite  =   ju.getCantidadLimite();
                                int cantidadIns     =   Integer.parseInt(umCantidad.getText().toString());
                                final int result    =   cantidadLimite-cantidadIns;
                                final String inn          = String.valueOf(umId_inventario.getText());

                                AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                                ab.setTitle("¿Seguro que desea hacer este despacho?");
                                ab.setMessage("le recuerdo que despues que este registrado no lo podra modificar");

                                ab.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                            ju.Hilo(sql,"4");
                                            new JsonInventario().Hilo("http://192.168.11.115:8282/arturo/inventario.php?modelo=5" +
                                                    "&id_inventario="+inn+"" +
                                                    "&cantidad="+result+"","5");
                                    }
                                });

                                ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                ad2 = ab.create();
                                ad2.show();



                                /*
                                 */

                                limpiar();
                                ad.dismiss();
                            }catch (Exception e){  Toast.makeText(getActivity(),"disculpe solo puede introducir " +
                                    "\nnros los campos cantidad y estado",Toast.LENGTH_LONG).show(); }
                        }else{ vali(umFecha); }
                    }else{ vali(umEstado);}
                }else{
                    vali(umTotal);
                    //Toast.makeText(th)
                }
            }else{  vali(umCantidad); }
        }else{ vali(umId_inventario); }

    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        v=t;
        v.requestFocus();
    }
    private void limpiar(){
        umId_inventario.setText(null);
        umCantidad.setText(null);
        umTotal.setText(null);
        umEstado.setText(null);
        umFecha.setText(null);
        umPrecio.setText(null);
    }


}


