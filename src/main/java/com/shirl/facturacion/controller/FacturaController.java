package com.shirl.facturacion.controller;

import com.shirl.facturacion.dto.FacturaRequest;
import com.shirl.facturacion.model.Factura;
import com.shirl.facturacion.service.FacturaService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @PostMapping
    public Factura crear(@RequestBody FacturaRequest request) {
        return facturaService.crearFactura(request);
    }

    @PutMapping("/{id}/emitir")
    public Factura emitir(@PathVariable Long id) {
        return facturaService.emitirFactura(id);
    }

    @PutMapping("/{id}/anular")
    public Factura anular(@PathVariable Long id) {
        return facturaService.anularFactura(id);
    }

    @GetMapping(value = "/{id}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String generarXml(@PathVariable Long id) {
        return facturaService.generarXml(id);
    }
    @GetMapping("/{id}/descargar")
    public ResponseEntity<byte[]> descargarXml(@PathVariable Long id) {

        Factura factura = facturaService.obtenerFactura(id);

        if (factura.getXmlGenerado() == null) {
            throw new RuntimeException("Primero debe generar el XML");
        }

        String nombreArchivo = "factura-" + factura.getNumero() + ".xml";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + nombreArchivo)
                .header(HttpHeaders.CONTENT_TYPE, "application/xml")
                .body(factura.getXmlGenerado().getBytes(StandardCharsets.UTF_8));
    }
}