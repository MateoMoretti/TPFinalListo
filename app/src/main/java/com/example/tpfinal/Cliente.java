package com.example.tpfinal;

public class Cliente {

    private String descripcion;
    private double latitud;
    private double longitud;
    private String domicilio;
    private String telefono;
    private String valor;
    private String detalle;
    private String tipo;
    private double distancia;

    private float distancia_al_usuario;

    public Cliente(String d, int lat, int lon, String dom, String tel, String val, String det, String tip, int dist){
        descripcion = d;
        latitud = lat;
        longitud = lon;
        domicilio = dom;
        telefono = tel;
        valor = val;
        detalle = det;
        tipo = tip;
        distancia = dist;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getValor() {
        return valor;
    }

    public String getDetalle() {
        return detalle;
    }

    public String getTipo() {
        return tipo;
    }

    public double getDistancia() {
        return distancia;
    }

    public float getDistanciaUsuario() {
        return distancia_al_usuario;
    }

    public void setDistanciaUsuario(float d) {
        this.distancia_al_usuario = d;
    }
}
