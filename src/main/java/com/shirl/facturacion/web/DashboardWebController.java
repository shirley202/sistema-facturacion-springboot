package com.shirl.facturacion.web;

import com.shirl.facturacion.service.FacturaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardWebController {

    private final FacturaService facturaService;

    public DashboardWebController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalFacturas", facturaService.contarTodas());
        model.addAttribute("totalEmitidas", facturaService.contarEmitidas());
        model.addAttribute("totalAnuladas", facturaService.contarAnuladas());
        model.addAttribute("totalFacturado", facturaService.totalFacturado());
        model.addAttribute("ultimas", facturaService.ultimasFacturas());

        return "dashboard";
    }
}