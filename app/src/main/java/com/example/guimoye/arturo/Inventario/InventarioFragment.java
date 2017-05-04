package com.example.guimoye.arturo.Inventario;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.guimoye.arturo.R;

import java.util.ArrayList;

public class InventarioFragment extends Fragment {

    ListView lista;

    ArrayList<String> id_despacho   =   new ArrayList<>();
    ArrayList<String> id_inventario =   new ArrayList<>();
    ArrayList<Integer> cantidad     =   new ArrayList<>();
    ArrayList<Double> total         =   new ArrayList<>();
    ArrayList<Integer> estado       =   new ArrayList<>();
    ArrayList<String> fecha         =   new ArrayList<>();

    ListViewAdapterInventario adapter;
    JsonInventario ju;
    LayoutInflater inflater;
    ViewGroup container;
    int positionn;
    View v;
    // Rutas de los Web Services
    String direccion    =   "http://192.168.11.115:8282/arturo/inventario.php?modelo=";
    String GET1         =   direccion+"4";
    String GET2         =   direccion+"3&id_inventario=";
    AlertDialog.Builder builder,builder2;
    AlertDialog ad,ad2;
    AutoCompleteTextView umFechaDsde, umFechaHast, umId_producto,umCantidad,umFecha;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.inflater   =   inflater;
        this.container  =   container;
        v               =   inflater.inflate(R.layout.fragment_inventario,container,false);
        lista           =   (ListView) v.findViewById(R.id.listaInventario);
        ju              =   new JsonInventario(v,inflater,container,getActivity().getBaseContext(),
                            lista, id_despacho, id_inventario, cantidad, fecha);

        /************** ventana de busqueda *****************/
        builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.consultar_fecha_inventario, null);

        umFechaDsde = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtInventario_fechaDesde);
        umFechaHast = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtInventario_fechaHasta);
        final Button btnMoficiarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_BuscarInventario_Fecha);


        btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ju.cambiarFechas(umFechaDsde.getText().toString(),umFechaHast.getText().toString());
                ju.Hilo(direccion+"4","1");
                ad.dismiss();
            }
        });

        builder.setView(dialoglayout);
        ad = builder.create();
        ad.show();


    /*************************** boton flotante *************************/
       FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabInventario);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.register_inventario, null);

                umId_producto   = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtInventario_CodProducto_Register);
                umCantidad      = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtInventario_Cantidad_Register);
                umFecha         = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtInventario_Fecha_Register);

                final Button btnRegistrarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_registrar_inventario);
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
                                ju.Hilo(GET2+ju.getId_Inventario(position),"2");
                                Log.e("el id para eliminar es:","esss: "+ju.getId_Inventario(position));
                                break;
                            case R.id.action_settings1:
                                builder2 = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater2 = getActivity().getLayoutInflater();
                                final View dialoglayout2 = inflater2.inflate(R.layout.modificar_inventario, null);
                                Log.e("el id para eliminar es:","modificandoooo: "+ju.getId_Inventario(position));
                                umId_producto   = (AutoCompleteTextView) dialoglayout2.findViewById(R.id.txtInventario_CodProducto_Modificar);
                                umCantidad      = (AutoCompleteTextView) dialoglayout2.findViewById(R.id.txtInventario_Cantidad_Modificar);
                                umFecha         = (AutoCompleteTextView) dialoglayout2.findViewById(R.id.txtInventario_Fecha_Modificar);

                                umId_producto.setText(ju.getId_Producto(position));
                                umCantidad.setText(ju.getCantidad(position)+"");
                                umFecha.setText(ju.getFecha(position));
                                final Button btnMoficiarUsuario   = (Button) dialoglayout2.findViewById(R.id.btn_modificar_inventario);
                                btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        ju.Hilo(direccion+"2" +
                                                        "&id_inventario="+ju.getId_Inventario(position)+"" +
                                                        "&id_producto="+umId_producto.getText()+"" +
                                                        "&cantidad="+Integer.parseInt(umCantidad.getText().toString())+"" +
                                                        "&fecha="+umFecha.getText()+""
                                                ,"3");

                                        ad2.dismiss();
                                    }
                                });

                                builder2.setView(dialoglayout2);
                                ad2 = builder2.create();
                                ad2.show();

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
        umId_producto.setError(null);
        umCantidad.setError(null);
        umFecha.setError(null);
    }

    private void validacions(){
        limpiarRequired();
        if(umId_producto.getText().length()!=0){
            if(umCantidad.getText().length()!=0){
                if(umFecha.getText().length()!=0){
                    try {
                        //&id_inventario=10&cantidad=300&fecha=2016-11-03
                        String sql=direccion+"1" +
                                "&id_producto="+ umId_producto.getText().toString()+"" +
                                "&cantidad="+ Integer.parseInt(umCantidad.getText().toString())+"" +
                                "&fecha="+umFecha.getText().toString()+"";
                        ju.Hilo(sql,"4");
                        limpiar();
                        ad.dismiss();
                    }catch (Exception e){  Toast.makeText(getActivity(),"disculpe solo puede introducir " +
                            "\nnros en el campo cantidad",Toast.LENGTH_LONG).show(); }
                }else{
                    vali(umFechaHast);
                }
            }else{
                vali(umFechaHast);
            }
        }else{
            vali(umFechaDsde);
        }



    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        v=t;
        v.requestFocus();
    }
    private void limpiar(){
        umId_producto.setText(null);
        umCantidad.setText(null);
        umFecha.setText(null);
    }

}
