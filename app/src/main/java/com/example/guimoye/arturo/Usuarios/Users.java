package com.example.guimoye.arturo.Usuarios;

/**
 * Created by Guimoye on 25/10/2016.
 */

public class Users {

    private String usuario,clave,correo,pregunta,respuesta;
    private long tlfno;

    public Users(){
        usuario     =   "";
        clave       =   "";
        correo      =   "";
        pregunta    =   "";
        respuesta   =   "";
        tlfno       =   0;
    }

    public Users(String usuario, String clave, String correo, String pregunta, String respuesta, long tlfno) {
        this.usuario = usuario;
        this.clave = clave;
        this.correo = correo;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.tlfno = tlfno;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public long getTlfno() {
        return tlfno;
    }

    public void setTlfno(long tlfno) {
        this.tlfno = tlfno;
    }
}
