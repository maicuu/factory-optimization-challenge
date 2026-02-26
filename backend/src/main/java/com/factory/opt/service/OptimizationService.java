package com.factory.opt.service;

import com.factory.opt.dto.ProductionItemDTO;
import com.factory.opt.dto.ProductionPlanDTO;
import com.factory.opt.entity.Product;
import com.factory.opt.entity.ProductComposition;
import com.factory.opt.entity.RawMaterial;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class OptimizationService {

    public ProductionPlanDTO calculateOptimalProduction() {
        return calculateOptimalProduction(Collections.emptyMap());
    }

    public ProductionPlanDTO calculateOptimalProduction(Map<Long, BigDecimal> simulatedExtraStock) {
        List<Product> products = Product.listAll();
        List<RawMaterial> materials = RawMaterial.listAll();

        Map<Long, BigDecimal> currentStock = new HashMap<>();
        for (RawMaterial rm : materials) {
            BigDecimal extra = simulatedExtraStock.getOrDefault(rm.id, BigDecimal.ZERO);
            currentStock.put(rm.id, rm.stockQuantity.add(extra));
        }

        List<Product> sortedProducts = products.stream()
                .sorted((p1, p2) -> calculateProfitDensity(p2).compareTo(calculateProfitDensity(p1)))
                .collect(Collectors.toList());

        List<ProductionItemDTO> planItems = new ArrayList<>();
        BigDecimal totalValue = BigDecimal.ZERO;

        for (Product product : sortedProducts) {
            int canProduce = calculateMaxProductionQty(product, currentStock);
            if (canProduce > 0) {
                deductStock(product, currentStock, canProduce);
                
                BigDecimal itemTotal = product.price.multiply(BigDecimal.valueOf(canProduce));
                
                
                planItems.add(new ProductionItemDTO(product.code, product.name, canProduce, itemTotal));
                totalValue = totalValue.add(itemTotal);
            }
        }

        
        return new ProductionPlanDTO(planItems, totalValue);
    }

    private BigDecimal calculateProfitDensity(Product p) {
        BigDecimal totalResourcesNeeded = p.compositions.stream()
                .map(comp -> comp.quantityNeeded)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        if (totalResourcesNeeded.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        
        return p.price.divide(totalResourcesNeeded, MathContext.DECIMAL128);
    }

    private int calculateMaxProductionQty(Product product, Map<Long, BigDecimal> currentStock) {
        if (product.compositions == null || product.compositions.isEmpty()) return 0;
        
        int maxQty = Integer.MAX_VALUE;
        for (ProductComposition comp : product.compositions) {
            BigDecimal stockAvailable = currentStock.getOrDefault(comp.rawMaterial.id, BigDecimal.ZERO);
            int possibleWithThisMaterial = stockAvailable.divide(comp.quantityNeeded, 0, RoundingMode.DOWN).intValue();
            
            if (possibleWithThisMaterial < maxQty) {
                maxQty = possibleWithThisMaterial;
            }
        }
        return maxQty == Integer.MAX_VALUE ? 0 : maxQty;
    }

    private void deductStock(Product product, Map<Long, BigDecimal> currentStock, int qtyMultiplier) {
        for (ProductComposition comp : product.compositions) {
            BigDecimal current = currentStock.get(comp.rawMaterial.id);
            BigDecimal consumption = comp.quantityNeeded.multiply(BigDecimal.valueOf(qtyMultiplier));
            currentStock.put(comp.rawMaterial.id, current.subtract(consumption));
        }
    }
}