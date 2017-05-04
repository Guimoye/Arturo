package com.example.guimoye.arturo.Inventario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guimoye.arturo.R;

import java.util.ArrayList;

public class ListViewAdapterInventario extends BaseAdapter {
    // Declare Variables
    Context context;
    ArrayList<String> arrayId_Inventario;
    ArrayList<String> arrayId_producto;
    ArrayList<Integer> arrayCantidad;
    ArrayList<String> arrayFecha;
    LayoutInflater inflater;

    public ListViewAdapterInventario(Context context, ArrayList<String> Arrayid_inventario, ArrayList<String> Arrayid_producto,
                                     ArrayList<Integer> ArrayCantidad, ArrayList<String> ArrayFecha) {
        this.context            =   context;
        this.arrayId_Inventario =   Arrayid_inventario;
        this.arrayId_producto   =   Arrayid_producto;
        this.arrayCantidad      =   ArrayCantidad;
        this.arrayFecha         =   ArrayFecha;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayId_Inventario.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateResults(ArrayList<String> Arrayid_inventario, ArrayList<String> Arrayid_producto,
                              ArrayList<Integer> ArrayCantidad, ArrayList<String> ArrayFecha) {
        this.arrayId_Inventario =   Arrayid_inventario;
        this.arrayId_producto   =   Arrayid_producto;
        this.arrayCantidad      =   ArrayCantidad;
        this.arrayFecha         =   ArrayFecha;
        //Triggers the list update
        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtId_Inventario,txtId_Producto,txtCantidad,txtFecha;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_modelo_inventario, parent, false);

        // Locate the TextViews in listview_item.xml
        txtId_Inventario    = (TextView) itemView.findViewById(R.id.row_txt_inventario_cod);
        txtId_Producto      = (TextView) itemView.findViewById(R.id.row_txt_inventario_cod_producto);
        txtCantidad         = (TextView) itemView.findViewById(R.id.row_txt_inventario_cantidad);
        txtFecha            = (TextView) itemView.findViewById(R.id.row_txt_inventario_fecha);

        // Capture position and set to the TextViews
        txtId_Inventario.setText(arrayId_Inventario.get(position));
        txtId_Producto.setText(arrayId_producto.get(position));
        txtCantidad.setText(arrayCantidad.get(position).toString());
        txtFecha.setText(arrayFecha.get(position));

        return itemView;
    }
}