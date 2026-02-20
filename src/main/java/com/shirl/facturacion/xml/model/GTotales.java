package com.shirl.facturacion.xml.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class GTotales {

    private BigDecimal dTotGralOpe;
    private BigDecimal dTotIVA;

    public GTotales() {}

    public GTotales(BigDecimal total, BigDecimal iva) {
        this.dTotGralOpe = total;
        this.dTotIVA = iva;
    }
}