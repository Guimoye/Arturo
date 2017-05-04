package com.example.guimoye.arturo.Perdidas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guimoye.arturo.R;

import java.util.ArrayList;

public class ListViewAdapterPerdidas extends BaseAdapter {
    // Declare Variables
    Context context;
    ArrayList<String> arrayId_perdida;
    ArrayList<String> arrayId_material;
    ArrayList<Integer> arrayCantidad;
    LayoutInflater inflater;
    ArrayList<String> arrayFecha;

    public ListViewAdapterPerdidas(Context context, ArrayList<String> id_perdida,
                                   ArrayList<String> id_material, ArrayList<Integer> arrayCantidad, ArrayList<String> ArrayFechas) {
        this.context            =   context;
        this.arrayId_perdida    =   id_perdida;
        this.arrayId_material   =   id_material;
        this.arrayCantidad      =   arrayCantidad;
        this.arrayFecha         =   ArrayFechas;
        inflater                =   LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayId_perdida.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateResults(ArrayList<String> titulos,ArrayList<String> tipo,ArrayList<Integer> precio,ArrayList<String> ArrayFechas) {
        this.arrayId_perdida    =   titulos;
        this.arrayId_material   =   tipo;
        this.arrayCantidad      =   precio;
        this.arrayFecha         =   ArrayFechas;
        //Triggers the list update
        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtId_Perdida,txtId_Material,txtCantidad,txtFecha;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_modelo_perdidas, parent, false);

        // Locate the TextViews in listview_item.xml
        txtId_Perdida   = (TextView) itemView.findViewById(R.id.row_txt_perdida_cod);
        txtId_Material  = (TextView) itemView.findViewById(R.id.row_txt_perdida_material_cod);
        txtCantidad     = (TextView) itemView.findViewById(R.id.row_txt_perdida_cantidad);
        txtFecha        = (TextView) itemView.findViewById(R.id.row_txt_perdida_fecha);

        // Capture position and set to the TextViews
        txtId_Perdida.setText(arrayId_perdida.get(position));
        txtId_Material.setText(arrayId_material.get(position));
        txtCantidad.setText(arrayCantidad.get(position).toString());
        txtFecha.setText(arrayFecha.get(position).toString());

        return itemView;
    }
}