package com.example.guimoye.arturo.Loggin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.guimoye.arturo.R;
import com.example.guimoye.arturo.Usuarios.JsonUsers;

public class Register extends AppCompatActivity {

    private AutoCompleteTextView txtUsuarioRegister,txtTipoRegister,txtClaveRegister,
            txtCorreoRegister,txtTlfnoRegister,txtPreguntaRegister,txtRespuestaRegister;
    private Button bRegister;
    private String usuario,clave,email,pregunta,respuesta;
    private long tlfno;
    private int tipo;
    private View focusView = null;
    String direccion="http://192.168.11.115:8282/arturo/usuarios.php?modelo=";
    JsonUsers ju;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);
        txtUsuarioRegister      =   (AutoCompleteTextView) findViewById(R.id.txtUsuarioRegister);
        txtTipoRegister         =   (AutoCompleteTextView) findViewById(R.id.txtTipoRegister);
        txtClaveRegister        =   (AutoCompleteTextView) findViewById(R.id.txtClaveRegister);
        txtCorreoRegister       =   (AutoCompleteTextView) findViewById(R.id.txtCorreoRegister);
        txtTlfnoRegister        =   (AutoCompleteTextView) findViewById(R.id.txtTlfnoRegister);
        txtPreguntaRegister     =   (AutoCompleteTextView) findViewById(R.id.txtPreguntaRegister);
        txtRespuestaRegister    =   (AutoCompleteTextView) findViewById(R.id.txtRespuestaRegister);
        bRegister               =   (Button) findViewById(R.id.btn_registrar_users);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validacions();
            }
        });

    }

    private void validacions(){
        limpiarRequired();
        if(txtUsuarioRegister.getText().length()!=0){
            if(txtTipoRegister.getText().length()!=0){
            if(txtClaveRegister.getText().length()!=0){
                if(txtCorreoRegister.getText().toString().contains("@") && txtCorreoRegister.getText().toString().contains(".com") ){
                    if(txtTlfnoRegister.getText().length()!=0){
                        if(txtPreguntaRegister.getText().length()!=0){
                            if(txtRespuestaRegister.getText().length()!=0){

                                usuario     =   txtUsuarioRegister.getText().toString();
                                tipo        =   Integer.parseInt(txtTipoRegister.getText().toString());
                                clave       =   txtClaveRegister.getText().toString();
                                email       =   txtCorreoRegister.getText().toString();
                                tlfno       =   Long.parseLong(txtTlfnoRegister.getText().toString());
                                pregunta    =   txtPreguntaRegister.getText().toString();
                                respuesta   =   txtRespuestaRegister.getText().toString();
                               // Toast.makeText(this.getApplicationContext(),"deberia registrar dsps de esto",Toast.LENGTH_LONG).show();

                                String sql=direccion+"1" +
                                        "&usuario="+usuario+"" +
                                        "&tipo="+tipo+"" +
                                        "&clave="+clave+"" +
                                        "&correo="+email+"" +
                                        "&telefono="+tlfno+"" +
                                        "&pregunta="+pregunta+"" +
                                        "&respuesta="+respuesta+"";
                                ju              =   new JsonUsers(getBaseContext(),sql,"5");
                                limpiar();

                            }else{
                                vali(txtRespuestaRegister);
                            }
                        }else{
                            vali(txtPreguntaRegister);
                        }
                    }else{
                        vali(txtTlfnoRegister);
                    }
                }else{
                    txtCorreoRegister.setError(getString(R.string.error_invalid_email));
                    focusView   =   txtCorreoRegister;
                    focusView.requestFocus();
                }
            }else{
                vali(txtClaveRegister);
            }
            }else{
                vali(txtTipoRegister);
            }
        }else{
            vali(txtUsuarioRegister);
        }



    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        focusView=t;
        focusView.requestFocus();
    }

    private void limpiarRequired(){
        txtUsuarioRegister.setError(null);
        txtTipoRegister.setError(null);
        txtClaveRegister.setError(null);
        txtCorreoRegister.setError(null);
        txtTlfnoRegister.setError(null);
        txtPreguntaRegister.setError(null);
        txtRespuestaRegister.setError(null);
    }

    private void limpiar(){
        txtUsuarioRegister.setText(null);
        txtTipoRegister.setText(null);
        txtClaveRegister.setText(null);
        txtCorreoRegister.setText(null);
        txtTlfnoRegister.setText(null);
        txtPreguntaRegister.setText(null);
        txtRespuestaRegister.setText(null);
    }
}
