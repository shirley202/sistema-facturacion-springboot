package com.shirl.facturacion.model;

import jakarta.persistence.*;

@Entity
@Table(name = "numeracion")
public class Numeracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String establecimiento;
    private String puntoExpedicion;

    private Long ultimoNumero;

    public Numeracion() {}

    public Long getId() { return id; }

    public String getEstablecimiento() { return establecimiento; }
    public void setEstablecimiento(String establecimiento) { this.establecimiento = establecimiento; }

    public String getPuntoExpedicion() { return puntoExpedicion; }
    public void setPuntoExpedicion(String puntoExpedicion) { this.puntoExpedicion = puntoExpedicion; }

    public Long getUltimoNumero() { return ultimoNumero; }
    public void setUltimoNumero(Long ultimoNumero) { this.ultimoNumero = ultimoNumero; }
}