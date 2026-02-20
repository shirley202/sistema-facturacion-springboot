package com.shirl.facturacion.xml.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class GDatosGenerales {

    private String dFeEmiDE;

    public GDatosGenerales() {}

    public GDatosGenerales(String dFeEmiDE) {
        this.dFeEmiDE = dFeEmiDE;
    }

    public String getdFeEmiDE() {
        return dFeEmiDE;
    }

    public void setdFeEmiDE(String dFeEmiDE) {
        this.dFeEmiDE = dFeEmiDE;
    }
}