package com.shirl.facturacion.service;

import com.shirl.facturacion.dto.FacturaRequest;
import com.shirl.facturacion.dto.ItemRequest;
import com.shirl.facturacion.model.*;
import com.shirl.facturacion.repository.*;
import com.shirl.facturacion.util.CdcGenerator;
import com.shirl.facturacion.xml.FacturaXmlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final NumeracionService numeracionService;
    private final FacturaXmlService facturaXmlService;

    public FacturaService(FacturaRepository facturaRepository,
                          ClienteRepository clienteRepository,
                          ProductoRepository productoRepository,
                          NumeracionService numeracionService,
                          FacturaXmlService facturaXmlService) {

        this.facturaRepository = facturaRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.numeracionService = numeracionService;
        this.facturaXmlService = facturaXmlService;
    }
    public Factura obtenerFactura(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }
    public List<Factura> listarTodas() {
        return facturaRepository.findAll();
    }
    public long contarTodas() {
        return facturaRepository.count();
    }
    public Factura guardar(Factura factura) {

        factura.setEstado(EstadoFactura.BORRADOR);

        if (factura.getFechaEmision() == null) {
            factura.setFechaEmision(LocalDateTime.now());
        }

        return facturaRepository.save(factura);
    }

    public long contarEmitidas() {
        return facturaRepository.countByEstado(EstadoFactura.EMITIDA);
    }

    public long contarAnuladas() {
        return facturaRepository.countByEstado(EstadoFactura.ANULADA);
    }

    public BigDecimal totalFacturado() {
        return facturaRepository.findAll()
                .stream()
                .filter(f -> f.getEstado() == EstadoFactura.EMITIDA)
                .map(Factura::getTotalGeneral)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Factura> ultimasFacturas() {
        return facturaRepository.findAll()
                .stream()
                .sorted((a,b) -> b.getId().compareTo(a.getId()))
                .limit(5)
                .toList();
    }

    @Transactional
    public Factura crearFactura(FacturaRequest request) {

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Factura factura = new Factura();
        factura.setCliente(cliente);
        factura.setFechaEmision(LocalDateTime.now());

        // Generar número correlativo
        String numeroGenerado = numeracionService.generarNumero("001", "001");
        factura.setNumero(numeroGenerado);

        BigDecimal total10 = BigDecimal.ZERO;
        BigDecimal total5 = BigDecimal.ZERO;
        BigDecimal totalExento = BigDecimal.ZERO;
        BigDecimal totalIva = BigDecimal.ZERO;

        List<FacturaDetalle> detalles = new ArrayList<>();

        for (ItemRequest item : request.getItems()) {

            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            BigDecimal precio = producto.getPrecio();
            BigDecimal cantidad = BigDecimal.valueOf(item.getCantidad());
            BigDecimal subtotal = precio.multiply(cantidad);

            BigDecimal iva = BigDecimal.ZERO;

            if (producto.getPorcentajeIva() == 10) {
                iva = subtotal.divide(BigDecimal.valueOf(11), 2, RoundingMode.HALF_UP);
                total10 = total10.add(subtotal);
            } else if (producto.getPorcentajeIva() == 5) {
                iva = subtotal.divide(BigDecimal.valueOf(21), 2, RoundingMode.HALF_UP);
                total5 = total5.add(subtotal);
            } else {
                totalExento = totalExento.add(subtotal);
            }

            totalIva = totalIva.add(iva);

            FacturaDetalle detalle = new FacturaDetalle();
            detalle.setFactura(factura);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(precio);
            detalle.setSubtotal(subtotal);
            detalle.setIva(iva);

            detalles.add(detalle);
        }

        factura.setDetalles(detalles);
        factura.setTotalGravado10(total10);
        factura.setTotalGravado5(total5);
        factura.setTotalExento(totalExento);
        factura.setTotalIva(totalIva);
        factura.setTotalGeneral(total10.add(total5).add(totalExento));
        factura.setEstado(EstadoFactura.BORRADOR);

        // Generar CDC usando número real
        String numeroSolo = numeroGenerado.split("-")[2];

        String cdc = CdcGenerator.generarCDC(
                "80012345",
                "01",
                "001",
                "001",
                numeroSolo,
                "1",
                "12345678"
        );

        factura.setCdc(cdc);

        return facturaRepository.save(factura);
    }

    @Transactional
    public Factura emitirFactura(Long id) {

        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        if (factura.getEstado() != EstadoFactura.BORRADOR) {
            throw new RuntimeException("Solo se puede emitir factura en BORRADOR");
        }

        factura.setEstado(EstadoFactura.EMITIDA);

        return facturaRepository.save(factura);
    }

    @Transactional
    public Factura anularFactura(Long id) {

        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        if (factura.getEstado() != EstadoFactura.EMITIDA) {
            throw new RuntimeException("Solo se puede anular factura EMITIDA");
        }

        factura.setEstado(EstadoFactura.ANULADA);

        return facturaRepository.save(factura);
    }
    @Transactional
    public String generarXml(Long id) {

        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        if (factura.getEstado() != EstadoFactura.EMITIDA) {
            throw new RuntimeException("Solo se puede generar XML de factura EMITIDA");
        }

        // 🔥 Aquí generamos el XML
        String xml = facturaXmlService.generarXml(factura);
        // 🔥 Aquí lo guardamos en la base de datos
        factura.setXmlGenerado(xml);
        facturaRepository.save(factura);

        return xml;
    }
}