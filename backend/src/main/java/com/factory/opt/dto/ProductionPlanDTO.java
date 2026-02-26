package com.factory.opt.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;

public class ProductionPlanDTO {
    
    public List<ProductionItemDTO> items;

    @Schema(description = "Total projected revenue", examples = {"12500.00"})
    public BigDecimal maxTotalValue;

    public ProductionPlanDTO() {}

    public ProductionPlanDTO(List<ProductionItemDTO> items, BigDecimal maxTotalValue) {
        this.items = items;
        this.maxTotalValue = maxTotalValue;
    }
}