package com.shirl.facturacion.repository;

import com.shirl.facturacion.model.EstadoFactura;
import com.shirl.facturacion.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

    long countByEstado(EstadoFactura estado);

}