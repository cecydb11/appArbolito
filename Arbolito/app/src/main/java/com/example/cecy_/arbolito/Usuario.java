package com.example.cecy_.arbolito;

/**
 * Created by cecy_ on 12/08/2018.
 */
public class Usuario {
    String Usuario, nombreUsuario, contrasena;
    int tipoUsuario, Sucursal;

    Usuario(String Usuario, String nombreUsuario, String contrasena, int tipoUsuario, int Sucursal){
        this.setUsuario(Usuario);
        this.setNombreUsuario(nombreUsuario);
        this.setContrasena(contrasena);
        this.setTipoUsuario(tipoUsuario);
        this.setSucursal(Sucursal);

    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public int getSucursal() {
        return Sucursal;
    }

    public void setSucursal(int sucursal) {
        Sucursal = sucursal;
    }
}
