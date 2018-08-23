package com.example.cecy_.arbolito;

import java.util.ArrayList;

/**
 * Created by cecy_ on 13/08/2018.
 */
public class DetallePedido {
    ArrayList arrayList;
    String Fecha, Cliente, Total;

    DetallePedido(ArrayList arraylist, String fecha, String cliente, String total){
        this.setArrayList(arraylist);
        this.setFecha(fecha);
        this.setCliente(cliente);
        this.setTotal(total);
    }

    public ArrayList getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList arraylist) {
        arrayList = arraylist;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}
