package com.shirl.facturacion.dto;

import java.util.List;

public class FacturaRequest {
    private Long clienteId;
    private List<ItemRequest> items;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public List<ItemRequest> getItems() { return items; }
    public void setItems(List<ItemRequest> items) { this.items = items; }
}