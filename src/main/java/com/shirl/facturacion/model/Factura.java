package com.shirl.facturacion.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    private LocalDateTime fechaEmision;
    private String cdc;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    private BigDecimal totalGravado10;
    private BigDecimal totalGravado5;
    private BigDecimal totalExento;
    private BigDecimal totalIva;
    private BigDecimal totalGeneral;
    @Enumerated(EnumType.STRING)
    private EstadoFactura estado;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FacturaDetalle> detalles = new java.util.ArrayList<>();

    public Factura() {}

    public Long getId() { return id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public BigDecimal getTotalGravado10() { return totalGravado10; }
    public void setTotalGravado10(BigDecimal totalGravado10) { this.totalGravado10 = totalGravado10; }

    public BigDecimal getTotalGravado5() { return totalGravado5; }
    public void setTotalGravado5(BigDecimal totalGravado5) { this.totalGravado5 = totalGravado5; }

    public BigDecimal getTotalExento() { return totalExento; }
    public void setTotalExento(BigDecimal totalExento) { this.totalExento = totalExento; }

    public BigDecimal getTotalIva() { return totalIva; }
    public void setTotalIva(BigDecimal totalIva) { this.totalIva = totalIva; }

    public BigDecimal getTotalGeneral() { return totalGeneral; }
    public void setTotalGeneral(BigDecimal totalGeneral) { this.totalGeneral = totalGeneral; }

    public List<FacturaDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<FacturaDetalle> detalles) { this.detalles = detalles; }
    public String getCdc() { return cdc; }
    public void setCdc(String cdc) { this.cdc = cdc; }
    public EstadoFactura getEstado() {
        return estado;
    }

    public void setEstado(EstadoFactura estado) {
        this.estado = estado;
    }
    @Lob
    @Column(columnDefinition = "TEXT")
    private String xmlGenerado;
    public String getXmlGenerado() {
        return xmlGenerado;
    }

    public void setXmlGenerado(String xmlGenerado) {
        this.xmlGenerado = xmlGenerado;
    }

}