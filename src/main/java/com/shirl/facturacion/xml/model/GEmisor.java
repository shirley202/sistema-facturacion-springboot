package com.shirl.facturacion.xml.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class GEmisor {

    private String dRucEm;
    private String dNomEmi;

    public GEmisor() {}

    public GEmisor(String dRucEm, String dNomEmi) {
        this.dRucEm = dRucEm;
        this.dNomEmi = dNomEmi;
    }
}