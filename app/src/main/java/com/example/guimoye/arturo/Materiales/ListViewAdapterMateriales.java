package com.example.guimoye.arturo.Materiales;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guimoye.arturo.R;

import java.util.ArrayList;

public class ListViewAdapterMateriales extends BaseAdapter {
    // Declare Variables
    Context context;
    ArrayList<String> id_materiales;
    ArrayList<String> materiales;

    LayoutInflater inflater;

    public ListViewAdapterMateriales(Context context, ArrayList<String> id_materiales, ArrayList<String> materiales) {
        this.context        =   context;
        this.id_materiales  =   id_materiales;
        this.materiales     =   materiales;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return id_materiales.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateResults(ArrayList<String> id_materiales,ArrayList<String> materiales) {
        this.id_materiales =   id_materiales;
        this.materiales =   materiales;
        //Triggers the list update
        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtid_materiales,txtmateriales;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_modelo_materiales, parent, false);

        // Locate the TextViews in listview_item.xml
        txtid_materiales  = (TextView) itemView.findViewById(R.id.row_txt_material_cod);
        txtmateriales     = (TextView) itemView.findViewById(R.id.row_txt_material);

        // Capture position and set to the TextViews
        txtid_materiales.setText(id_materiales.get(position));
        txtmateriales.setText(materiales.get(position));


        return itemView;
    }
}