package com.shirl.facturacion.xml.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class GReceptor {

    private String dNomRec;
    private String dNumDocRec;

    public GReceptor() {}

    public GReceptor(String dNomRec, String dNumDocRec) {
        this.dNomRec = dNomRec;
        this.dNumDocRec = dNumDocRec;
    }
}