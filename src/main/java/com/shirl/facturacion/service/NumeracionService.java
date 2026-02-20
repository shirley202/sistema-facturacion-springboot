package com.shirl.facturacion.service;

import com.shirl.facturacion.model.Numeracion;
import com.shirl.facturacion.repository.NumeracionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NumeracionService {

    private final NumeracionRepository numeracionRepository;

    public NumeracionService(NumeracionRepository numeracionRepository) {
        this.numeracionRepository = numeracionRepository;
    }



    @Transactional
    public String generarNumero(String establecimiento, String punto) {

        Numeracion numeracion = numeracionRepository
                .findByEstablecimientoAndPuntoExpedicion(establecimiento, punto)
                .orElseGet(() -> {
                    Numeracion n = new Numeracion();
                    n.setEstablecimiento(establecimiento);
                    n.setPuntoExpedicion(punto);
                    n.setUltimoNumero(0L);
                    return numeracionRepository.save(n);
                });

        Long nuevoNumero = numeracion.getUltimoNumero() + 1;
        numeracion.setUltimoNumero(nuevoNumero);

        numeracionRepository.save(numeracion);

        String numeroFormateado = String.format("%09d", nuevoNumero);

        return establecimiento + "-" + punto + "-" + numeroFormateado;
    }

}