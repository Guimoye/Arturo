package com.example.guimoye.arturo.Reportes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guimoye.arturo.R;

import java.util.ArrayList;

public class ListViewAdapterReportes extends BaseAdapter {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<Double> arrayTotal;
    ArrayList<String> arrayFecha;

    public ListViewAdapterReportes(Context context, ArrayList<Double> arrayTotal, ArrayList<String> ArrayFechas) {
        this.context            =   context;
        this.arrayTotal         =   arrayTotal;
        this.arrayFecha         =   ArrayFechas;
        inflater                =   LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayTotal.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateResults(ArrayList<Double> arrayTotal, ArrayList<String> ArrayFechas) {
        this.arrayTotal         =   arrayTotal;
        this.arrayFecha         =   ArrayFechas;
        //Triggers the list update
        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtTotal,txtFecha;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_modelo_reportes, parent, false);

        // Locate the TextViews in listview_item.xml
        txtTotal    = (TextView) itemView.findViewById(R.id.row_txt_reporte_total);
        txtFecha    = (TextView) itemView.findViewById(R.id.row_txt_reporte_fecha);

        // Capture position and set to the TextViews
        txtTotal.setText(arrayTotal.get(position).toString());
        txtFecha.setText(arrayFecha.get(position).toString());

        return itemView;
    }
}