package com.example.guimoye.arturo.Productos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guimoye.arturo.R;

import java.util.ArrayList;

public class ListViewAdapterProductos extends BaseAdapter {
    // Declare Variables
    Context context;
    ArrayList<String> id_producto;
    ArrayList<String> producto;
    ArrayList<Double> precio;
    LayoutInflater inflater;

    public ListViewAdapterProductos(Context context, ArrayList<String> id_producto, ArrayList<String> producto, ArrayList<Double> precio) {
        this.context        =   context;
        this.id_producto    =   id_producto;
        this.producto       =   producto;
        this.precio         =   precio;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return id_producto.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateResults(ArrayList<String> titulos,ArrayList<String> tipo,ArrayList<Double> precio) {
        this.id_producto =   titulos;
        this.producto =   tipo;
        this.precio =   precio;
        //Triggers the list update
        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtId_Producto,txtProducto,txtPrecio;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_modelo_productos, parent, false);

        // Locate the TextViews in listview_item.xml
        txtId_Producto  = (TextView) itemView.findViewById(R.id.row_txt_producto_cod);
        txtProducto     = (TextView) itemView.findViewById(R.id.row_txt_producto);
        txtPrecio       = (TextView) itemView.findViewById(R.id.row_txt_producto_precio);

        // Capture position and set to the TextViews
        txtId_Producto.setText(id_producto.get(position));
        txtProducto.setText(producto.get(position));
        txtPrecio.setText(precio.get(position).toString());

        return itemView;
    }
}