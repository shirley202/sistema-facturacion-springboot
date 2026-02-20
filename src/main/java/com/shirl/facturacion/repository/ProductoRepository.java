package com.shirl.facturacion.repository;

import com.shirl.facturacion.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}