package com.shirl.facturacion.repository;

import com.shirl.facturacion.model.Numeracion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NumeracionRepository extends JpaRepository<Numeracion, Long> {

    Optional<Numeracion> findByEstablecimientoAndPuntoExpedicion(
            String establecimiento,
            String puntoExpedicion
    );
}