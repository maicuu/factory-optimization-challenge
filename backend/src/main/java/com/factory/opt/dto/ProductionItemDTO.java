package com.factory.opt.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.math.BigDecimal;

public class ProductionItemDTO {
    
    @Schema(examples = {"PRD-DESK"})
    public String productCode;

    @Schema(examples = {"Luxury Desk"})
    public String productName;

    @Schema(examples = {"10"})
    public int quantity;

    @Schema(examples = {"8000.00"})
    public BigDecimal totalValue;

    public ProductionItemDTO() {}

    public ProductionItemDTO(String productCode, String productName, int quantity, BigDecimal totalValue) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.totalValue = totalValue;
    }
}