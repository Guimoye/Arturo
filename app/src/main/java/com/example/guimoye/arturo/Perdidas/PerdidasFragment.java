package com.example.guimoye.arturo.Perdidas;

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

public class PerdidasFragment extends Fragment {

    ListView lista;
    ArrayList<String> id_perdida = new ArrayList<>();
    ArrayList<String> id_material = new ArrayList<>();
    ArrayList<Integer> cantidad = new ArrayList<>();
    ArrayList<String> fecha         =   new ArrayList<>();
    JsonPerdidas ju;
    LayoutInflater inflater;
    ViewGroup container;
    View v;
    int positionn;

    // Rutas de los Web Services
    String direccion="http://192.168.11.115:8282/arturo/perdida.php?modelo=";
    String GET1 = direccion+"4";
    String GET2 = direccion+"3&id_perdida=";
    AlertDialog.Builder builder;
    AlertDialog ad;
    AutoCompleteTextView umId_material, umCantidad,umFechaDsde, umFechaHast,umFecha;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.inflater   =   inflater;
        this.container  =   container;
        v               =   inflater.inflate(R.layout.fragment_perdidas,container,false);
        lista           =   (ListView) v.findViewById(R.id.listaPerdidas);
        ju              =   new JsonPerdidas(v,inflater,container,getActivity().getBaseContext(),
                            lista, id_perdida, id_material, cantidad,fecha,GET1,"1");


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
                ju.Hilo(GET1,"1");
                ad.dismiss();
            }
        });

        builder.setView(dialoglayout);
        ad = builder.create();
        ad.show();

        /************** boton flotante ****************/
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabPerdidas);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.register_perdida, null);

                    umId_material   =   (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtPerdida_CodMaterial_Register);
                    umCantidad      =   (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtPerdida_CantidadRegister);
                    umFecha         =   (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtPerdida_FechaRegister);

                final Button btnRegistrarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_registrar_perdida);
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
                                ju.Hilo(GET2+ju.getId_Perdida(position),"2");
                                Log.e("el id para eliminar es:","esss: "+ju.getId_Perdida(position));
                                break;
                            case R.id.action_settings1:
                                builder = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                final View dialoglayout = inflater.inflate(R.layout.modificar_perdidas, null);

                                umId_material   = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtPerdida_CodMaterial_Modificar);
                                umCantidad      = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtPerdida_CantidadModificar);
                                umFecha         = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtPerdida_FechaModificar);

                                umId_material.setText(ju.getId_Material(position));
                                umCantidad.setText(ju.getCantidad(position)+"");
                                umFecha.setText(ju.getFecha(position)+"");


                                final Button btnMoficiarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_modificar_perdida);
                                btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ju.Hilo(direccion+"2" +
                                                        "&id_perdida="+ju.getId_Perdida(position)+"" +
                                                        "&id_material="+ umId_material.getText()+"" +
                                                        "&cantidad="+Double.parseDouble(umCantidad.getText().toString())+""+
                                                        "&fecha="+umFecha.getText()+""
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
        umId_material.setError(null);
        umCantidad.setError(null);
    }

    private void validacions(){
        limpiarRequired();
        if(umId_material.getText().length()!=0){
            if(umCantidad.getText().length()!=0){
                if(umFecha.getText().length()!=0){
                try {
                    String sql  =   direccion+"1" +
                            "&id_material="+ umId_material.getText().toString()+"" +
                            "&cantidad="+Double.parseDouble(umCantidad.getText().toString())+""+
                            "&fecha="+ umFecha.getText().toString()+"";
                    ju.Hilo(sql,"4");
                    limpiar();
                    ad.dismiss();
                }catch (Exception e){  Toast.makeText(getActivity(),"disculpe solo puede introducir " +
                        "\nnros en el campo cantidad",Toast.LENGTH_LONG).show(); }

                }else{
                    vali(umFecha);
                }
            }else{
                vali(umCantidad);
            }
        }else{
            vali(umId_material);
        }



    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        v=t;
        v.requestFocus();
    }
    private void limpiar(){
        umId_material.setText(null);
        umCantidad.setText(null);
    }

}
