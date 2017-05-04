package com.example.guimoye.arturo.Despacho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guimoye.arturo.R;

import java.util.ArrayList;

public class ListViewAdapterDespacho extends BaseAdapter {
    // Declare Variables
    Context context;
    ArrayList<String> arrayId_Despacho;
    ArrayList<String> arrayId_Inventario;
    ArrayList<Integer> arrayCantidad;
    ArrayList<Double> arrayTotal;
    ArrayList<Integer> arrayEstado;
    ArrayList<String> arrayFecha;
    LayoutInflater inflater;

    public ListViewAdapterDespacho(Context context, ArrayList<String> Arrayid_despacho, ArrayList<String> Arrayid_inventario,
                                                    ArrayList<Integer> ArrayCantidad, ArrayList<Double> ArrayTotal,
                                                    ArrayList<Integer> ArrayEstado, ArrayList<String> ArrayFecha) {
        this.context            =   context;
        this.arrayId_Despacho   =   Arrayid_despacho;
        this.arrayId_Inventario =   Arrayid_inventario;
        this.arrayCantidad      =   ArrayCantidad;
        this.arrayTotal         =   ArrayTotal;
        this.arrayEstado        =   ArrayEstado;
        this.arrayFecha         =   ArrayFecha;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayId_Despacho.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateResults(ArrayList<String> Arrayid_despacho, ArrayList<String> Arrayid_inventario,
                              ArrayList<Integer> ArrayCantidad, ArrayList<Double> ArrayTotal,
                              ArrayList<Integer> ArrayEstado, ArrayList<String> ArrayFecha) {
        this.arrayId_Despacho   =   Arrayid_despacho;
        this.arrayId_Inventario =   Arrayid_inventario;
        this.arrayCantidad      =   ArrayCantidad;
        this.arrayTotal         =   ArrayTotal;
        this.arrayEstado        =   ArrayEstado;
        this.arrayFecha         =   ArrayFecha;
        //Triggers the list update
        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtId_despacho,txtId_Inventario,txtCantidad,txtTotal,txtEstado,txtFecha;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_modelo_despacho, parent, false);

        // Locate the TextViews in listview_item.xml
        txtId_despacho      = (TextView) itemView.findViewById(R.id.row_txt_despacho_cod);
        txtId_Inventario    = (TextView) itemView.findViewById(R.id.row_txt_despacho_cod_inventario);
        txtCantidad         = (TextView) itemView.findViewById(R.id.row_txt_despacho_cantidad);
        txtTotal            = (TextView) itemView.findViewById(R.id.row_txt_despacho_total);
        txtEstado           = (TextView) itemView.findViewById(R.id.row_txt_despacho_estado);
        txtFecha            = (TextView) itemView.findViewById(R.id.row_txt_despacho_fecha);

        // Capture position and set to the TextViews
        txtId_despacho.setText(arrayId_Despacho.get(position));
        txtId_Inventario.setText(arrayId_Inventario.get(position));
        txtCantidad.setText(arrayCantidad.get(position).toString());
        txtTotal.setText(arrayTotal.get(position).toString());
        txtEstado.setText(arrayEstado.get(position).toString());
        txtFecha.setText(arrayFecha.get(position));

        return itemView;
    }
}