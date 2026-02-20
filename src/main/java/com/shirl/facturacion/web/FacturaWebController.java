package com.shirl.facturacion.web;

import com.shirl.facturacion.model.Factura;
import com.shirl.facturacion.service.ClienteService;
import com.shirl.facturacion.service.FacturaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.shirl.facturacion.dto.FacturaRequest;
import com.shirl.facturacion.service.ProductoService;
import java.util.Map;
import com.shirl.facturacion.util.QrGenerator;
@Controller
@RequestMapping("/facturas")
public class FacturaWebController {

    private final FacturaService facturaService;
    private final ClienteService clienteService;
    private final ProductoService productoService;

    public FacturaWebController(FacturaService facturaService,
                                ClienteService clienteService,
                                ProductoService productoService) {
        this.facturaService = facturaService;
        this.clienteService = clienteService;
        this.productoService = productoService;
    }
    // LISTAR
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("facturas", facturaService.listarTodas());
        return "facturas/lista";
    }

    // NUEVA FACTURA
    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("facturaRequest", new FacturaRequest());
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("productos",
                productoService.listarProductos()
                        .stream()
                        .map(p -> Map.of(
                                "id", p.getId(),
                                "descripcion", p.getDescripcion(),
                                "precio", p.getPrecio(),
                                "iva", p.getPorcentajeIva(),
                                "codigo", p.getCodigo()
                        ))
                        .toList()
        );
        return "facturas/formulario";
    }
    // GUARDAR FACTURA
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute FacturaRequest request) {

        Factura factura = facturaService.crearFactura(request);

        return "redirect:/facturas/ver/" + factura.getId();
    }
// ver factura
@GetMapping("/ver/{id}")
public String verFactura(@PathVariable Long id, Model model) {

    Factura factura = facturaService.obtenerFactura(id);

    String qrBase64 = QrGenerator.generarBase64(factura.getCdc());

    model.addAttribute("factura", factura);
    model.addAttribute("qr", qrBase64);

    return "facturas/imprimir";
}

    // EMITIR
    @GetMapping("/emitir/{id}")
    public String emitir(@PathVariable Long id) {
        facturaService.emitirFactura(id);
        return "redirect:/facturas";
    }

    // ANULAR
    @GetMapping("/anular/{id}")
    public String anular(@PathVariable Long id) {
        facturaService.anularFactura(id);
        return "redirect:/facturas";
    }

    // GENERAR XML
    @GetMapping("/xml/{id}")
    public String generarXml(@PathVariable Long id) {
        facturaService.generarXml(id);
        return "redirect:/facturas";
    }
}