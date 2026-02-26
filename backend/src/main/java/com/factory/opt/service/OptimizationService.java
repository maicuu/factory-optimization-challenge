package com.factory.opt.service;

import com.factory.opt.dto.ProductionItemDTO;
import com.factory.opt.dto.ProductionPlanDTO;
import com.factory.opt.entity.Product;
import com.factory.opt.entity.ProductComposition;
import com.factory.opt.entity.RawMaterial;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class OptimizationService {

    public ProductionPlanDTO calculateOptimalProduction() {
        return calculateOptimalProduction(Collections.emptyMap());
    }

    public ProductionPlanDTO calculateOptimalProduction(Map<Long, Double> simulatedExtraStock) {
        List<Product> products = Product.listAll();
        List<RawMaterial> materials = RawMaterial.listAll();

        Map<Long, Double> currentStock = new HashMap<>();
        for (RawMaterial rm : materials) {
            double extra = simulatedExtraStock.getOrDefault(rm.id, 0.0);
            currentStock.put(rm.id, rm.stockQuantity + extra);
        }

        List<Product> sortedProducts = products.stream()
                .sorted((p1, p2) -> Double.compare(calculateProfitDensity(p2), calculateProfitDensity(p1)))
                .collect(Collectors.toList());

        List<ProductionItemDTO> planItems = new ArrayList<>();
        double totalValue = 0.0;

        for (Product product : sortedProducts) {
            int canProduce = calculateMaxProductionQty(product, currentStock);
            if (canProduce > 0) {
                deductStock(product, currentStock, canProduce);
                double itemTotal = canProduce * product.price;
                planItems.add(new ProductionItemDTO(product.code, product.name, canProduce, itemTotal));
                totalValue += itemTotal;
            }
        }

        return new ProductionPlanDTO(planItems, totalValue);
    }

    private double calculateProfitDensity(Product p) {
        double totalResourcesNeeded = p.compositions.stream()
                .mapToDouble(comp -> comp.quantityNeeded)
                .sum();
        
        if (totalResourcesNeeded == 0) return 0.0;
        return p.price / totalResourcesNeeded;
    }

    private int calculateMaxProductionQty(Product product, Map<Long, Double> currentStock) {
        if (product.compositions == null || product.compositions.isEmpty()) return 0;
        
        int maxQty = Integer.MAX_VALUE;
        for (ProductComposition comp : product.compositions) {
            double stockAvailable = currentStock.getOrDefault(comp.rawMaterial.id, 0.0);
            int possibleWithThisMaterial = (int) (stockAvailable / comp.quantityNeeded);
            
            if (possibleWithThisMaterial < maxQty) {
                maxQty = possibleWithThisMaterial;
            }
        }
        return maxQty == Integer.MAX_VALUE ? 0 : maxQty;
    }

    private void deductStock(Product product, Map<Long, Double> currentStock, int qtyMultiplier) {
        for (ProductComposition comp : product.compositions) {
            double current = currentStock.get(comp.rawMaterial.id);
            currentStock.put(comp.rawMaterial.id, current - (comp.quantityNeeded * qtyMultiplier));
        }
    }
}