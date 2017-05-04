package com.example.guimoye.arturo.Despacho;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;

public class ChangeDatos implements TextWatcher {

    AutoCompleteTextView cantidadtxt;
    AutoCompleteTextView total;
    AutoCompleteTextView umPrecio;
    int cantidadlimite;

    public ChangeDatos(AutoCompleteTextView umPrecio, AutoCompleteTextView cantidadtxt, AutoCompleteTextView total) {
        this.cantidadtxt    =   cantidadtxt;
        this.total          =   total;
        this.umPrecio       =   umPrecio;
        cantidadlimite      =   Integer.parseInt(cantidadtxt.getText().toString());
    }

    public int getCantidadlimite(){
        return cantidadlimite;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        if(cantidadtxt.getText().length()!=0){
            try {
                int n = Integer.parseInt(cantidadtxt.getText().toString());
                if(n>=0 && n<=cantidadlimite){
                    total.setText((n*Double.parseDouble(umPrecio.getText().toString()))+"");
                }else{ total.setText(""); }
            }catch (Exception e){ }
        }else{
            if(cantidadtxt.getText().length()==0){
                total.setText("");
            }
        }
    }


}
