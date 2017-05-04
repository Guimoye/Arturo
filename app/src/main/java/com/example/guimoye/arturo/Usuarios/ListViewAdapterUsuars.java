package com.example.guimoye.arturo.Usuarios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guimoye.arturo.R;

import java.util.ArrayList;

public class ListViewAdapterUsuars extends BaseAdapter {
    // Declare Variables
    Context context;
    ArrayList<String> usuario;
    ArrayList<String> tipo;
    ArrayList<String> correo;
    ArrayList<Long> tlfno;
    LayoutInflater inflater;

    public ListViewAdapterUsuars(Context context, ArrayList<String> titulos,ArrayList<String> tipo,ArrayList<String> correo,ArrayList<Long> tlfno) {

        this.context    =   context;
        this.usuario    =   titulos;
        this.tipo       =   tipo;
        this.correo     =   correo;
        this.tlfno      =   tlfno;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return usuario.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateResults(ArrayList<String> titulos,ArrayList<String> tipo,ArrayList<String> correo,ArrayList<Long> tlfno) {
        this.usuario    =   titulos;
        this.tipo       =   tipo;
        this.correo     =   correo;
        this.tlfno      =   tlfno;
        //Triggers the list update
        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtUsuario,txtCorreo,txtTlfno,txtTipo;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_modelo_usuarios, parent, false);

        // Locate the TextViews in listview_item.xml
        txtUsuario  = (TextView) itemView.findViewById(R.id.row_txt_usuarios);
        txtTipo     = (TextView) itemView.findViewById(R.id.row_txt_tipo);
        txtCorreo   = (TextView) itemView.findViewById(R.id.row_txt_correo);
        txtTlfno    = (TextView) itemView.findViewById(R.id.row_txt_tlfno);

        // Capture position and set to the TextViews
        txtUsuario.setText(usuario.get(position));
        txtTipo.setText(tipo.get(position));
        txtCorreo.setText(correo.get(position));
        txtTlfno.setText(tlfno.get(position).toString());

        return itemView;
    }
}