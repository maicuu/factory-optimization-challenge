package com.factory.opt.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class ProductionItemDTO {
    
    @Schema(examples = {"PRD-DESK"})
    public String productCode;

    @Schema(examples = {"Luxury Desk"})
    public String productName;

    @Schema(examples = {"10"})
    public int quantity;

    @Schema(examples = {"8000.0"})
    public double totalValue;

    public ProductionItemDTO() {}

    public ProductionItemDTO(String productCode, String productName, int quantity, double totalValue) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.totalValue = totalValue;
    }
}