package com.example.guimoye.arturo.Productos;

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

public class ProductosFragment extends Fragment {

    ListView lista;
    ArrayList<String> id_producto = new ArrayList<>();
    ArrayList<String> producto = new ArrayList<>();
    ArrayList<Double> precio_producto = new ArrayList<>();
    ArrayList<Long> tlfno = new ArrayList<>();
    ListViewAdapterProductos adapter;
    JsonProductos ju;
    LayoutInflater inflater;
    ViewGroup container;
     int positionn;
    View v;
    // Rutas de los Web Services
    String direccion="http://192.168.11.115:8282/arturo/productos.php?modelo=";
    String GET1 = direccion+"4";
    String GET2 = direccion+"3&id_producto=";
    AlertDialog.Builder builder;
    AlertDialog ad;
    AutoCompleteTextView umProducto, umPrecio;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.inflater   =   inflater;
        this.container  =   container;
        v               =   inflater.inflate(R.layout.fragment_productos,container,false);
        lista           =   (ListView) v.findViewById(R.id.listaProductos);
        ju              =   new JsonProductos(v,inflater,container,getActivity().getBaseContext(),lista, id_producto, producto, precio_producto,GET1,"1");

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabProductos);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.register_productos, null);

                    umProducto  = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtProductoRegister);
                    umPrecio    = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtProducto_PrecioRegister);

                final Button btnRegistrarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_registrar_producto);
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
                                ju.Hilo(GET2+ju.getId_Producto(position),"3");
                                Log.e("el id para eliminar es:","esss: "+ju.getId_Producto(position));
                                break;
                            case R.id.action_settings1:
                                builder = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                final View dialoglayout = inflater.inflate(R.layout.modificar_productos, null);

                                umProducto = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtProductoModificar);
                                umPrecio = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtProducto_PrecioModificar);

                                umProducto.setText(ju.getProducto(position));
                                umPrecio.setText(ju.getPrecio_producto(position)+"");


                                final Button btnMoficiarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_modificar_producto);
                                btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ju.Hilo(direccion+"2" +
                                                        "&id_producto="+ju.getId_Producto(position)+"" +
                                                        "&nombre_producto="+umProducto.getText()+"" +
                                                        "&precio_producto="+Double.parseDouble(umPrecio.getText().toString())+""
                                                ,"3");

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
        umProducto.setError(null);
        umPrecio.setError(null);
    }

    private void validacions(){
        limpiarRequired();
        if(umProducto.getText().length()!=0){
            if(umPrecio.getText().length()!=0){
                try {
                    String sql=direccion+"1" +
                            "&nombre_producto="+umProducto.getText().toString()+"" +
                            "&precio_producto="+Double.parseDouble(umPrecio.getText().toString())+"";
                    ju.Hilo(sql,"4");
                    limpiar();
                    ad.dismiss();
                }catch (Exception e){  Toast.makeText(getActivity(),"disculpe solo puede introducir " +
                        "\nnros en el campo de precios",Toast.LENGTH_LONG).show(); }

            }else{
                vali(umPrecio);
            }
        }else{
            vali(umProducto);
        }



    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        v=t;
        v.requestFocus();
    }
    private void limpiar(){
        umProducto.setText(null);
        umPrecio.setText(null);
    }

}
