package com.example.guimoye.arturo.Materiales;

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

public class MaterialesFragment extends Fragment {

    ListView lista;
    ArrayList<String> arrayId = new ArrayList<>();
    ArrayList<String> arrayMateriales = new ArrayList<>();
    ListViewAdapterMateriales adapter;
    JsonMateriales ju;
    LayoutInflater inflater;
    ViewGroup container;
     int positionn;
    View v;
    // Rutas de los Web Services
    String direccion="http://192.168.11.115:8282/arturo/materiales.php?modelo=";
    String GET1 = direccion+"4";
    String GET2 = direccion+"3&id_materiales=";
    AlertDialog.Builder builder;
    AlertDialog ad;
    AutoCompleteTextView umMaterial;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.inflater   =   inflater;
        this.container  =   container;
        v               =   inflater.inflate(R.layout.fragment_materiales,container,false);
        lista           =   (ListView) v.findViewById(R.id.listaMateriales);
        ju              =   new JsonMateriales(v,inflater,container,getActivity().getBaseContext(),lista, arrayId, arrayMateriales,GET1,"1");

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabMateriales);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.register_materiales, null);

                    umMaterial = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtMaterialRegister);

                final Button btnRegistrarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_registrar_material);
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
                                ju.Hilo(GET2+ju.getId_materiales(position),"3");
                                break;
                            case R.id.action_settings1:
                                builder = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                final View dialoglayout = inflater.inflate(R.layout.modificar_materiales, null);

                                umMaterial = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtMaterialesModificar);
                                umMaterial.setText(ju.getMateriales(position));

                                final Button btnMoficiarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_modificar_materiales);
                                btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ju.Hilo(direccion+"2" +
                                                        "&id_materiales="+ju.getId_materiales(position)+"" +
                                                        "&materiales="+ umMaterial.getText()+""
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
        umMaterial.setError(null);
    }

    private void validacions(){
        limpiarRequired();
        if(umMaterial.getText().length()!=0){
            String sql=direccion+"1" +
            "&materiales="+umMaterial.getText().toString()+"";
            ju.Hilo(sql,"4");
            // Toast.makeText(getActivity(),"deberia registrar dsps de esto",Toast.LENGTH_LONG).show();
            limpiar();
            ad.dismiss();
        }else{
            vali(umMaterial);
        }
    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        v=t;
        v.requestFocus();
    }
    private void limpiar(){
        umMaterial.setText(null);
    }

}
