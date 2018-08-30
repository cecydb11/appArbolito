package com.example.cecy_.arbolito;

import java.util.ArrayList;

/**
 * Created by cecy_ on 13/08/2018.
 */
public class DetallePedido {
    int Cant, Imp;
    String Desc;

    DetallePedido(int cant, String desc, int imp){
        this.setCant(cant);
        this.setDesc(desc);
        this.setImp(imp);
    }

    public int getCant() {
        return Cant;
    }

    public void setCant(int cant) {
        Cant = cant;
    }

    public int getImp() {
        return Imp;
    }

    public void setImp(int imp) {
        Imp = imp;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
