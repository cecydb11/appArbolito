package com.example.cecy_.arbolito;

/**
 * Created by cecy_ on 02/08/2018.
 */
public class Clientes {
    private String NombreNegocio;
    private String NombrePropietario;
    private String TipoNegocio;
    private String Domicilio;
    private String Telefono;
    private int Estado, NotaCobrar, idCliente;
    private double Bono;
    private long Latitud, Longitud;



    Clientes(String Name, String nombrePropietario, String tipoNegocio, String domicilio, String telefono, int Estado, int notaCobrar, int idCliente, double bono, long latitud, long loingitud){
        this.setNombreNegocio(Name);
        this.setNombrePropietario(nombrePropietario);
        this.setTipoNegocio(tipoNegocio);
        this.setDomicilio(domicilio);
        this.setTelefono(telefono);
        this.setEstado(Estado);
        this.setNotaCobrar(notaCobrar);
        this.setIdCliente(idCliente);
        this.setBono(bono);
        this.setLatitud(latitud);
        this.setLongitud(loingitud);
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getDomicilio() {
        return Domicilio;
    }

    public void setDomicilio(String domicilio) {
        Domicilio = domicilio;
    }

    public String getTipoNegocio() {
        return TipoNegocio;
    }

    public void setTipoNegocio(String tipoNegocio) {
        TipoNegocio = tipoNegocio;
    }

    public String getNombrePropietario() {
        return NombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        NombrePropietario = nombrePropietario;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }

    public String getNombreNegocio() {
        return NombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        NombreNegocio = nombreNegocio;
    }

    public int getNotaCobrar() {
        return NotaCobrar;
    }

    public void setNotaCobrar(int notaCobrar) {
        NotaCobrar = notaCobrar;
    }

    public double getBono() {
        return Bono;
    }

    public void setBono(double bono) {
        Bono = bono;
    }

    public double getLatitud() {
        return Latitud;
    }

    public void setLatitud(long latitud) {
        Latitud = latitud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLongitud(long longitud) {
        Longitud = longitud;
    }
}
