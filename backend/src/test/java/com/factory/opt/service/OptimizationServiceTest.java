package com.factory.opt.service;

import com.factory.opt.dto.ProductionPlanDTO;
import com.factory.opt.entity.Product;
import com.factory.opt.entity.ProductComposition;
import com.factory.opt.entity.RawMaterial;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.narayana.jta.QuarkusTransaction;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class OptimizationServiceTest {

    @Inject
    OptimizationService optimizationService;

    @Test
    @DisplayName("Cenário: Priorizar eficiência de recurso sobre preço unitário")
    void testExpensiveProductIsNotAlwaysTheBest() {
        
        QuarkusTransaction.requiringNew().run(() -> {
            ProductComposition.deleteAll();
            Product.deleteAll();
            RawMaterial.deleteAll();

            RawMaterial gold = new RawMaterial();
            gold.code = "RM-GOLD";
            gold.name = "Gold";
            gold.stockQuantity = BigDecimal.valueOf(10.0);
            gold.unit = "g";
            gold.persist();

            
            Product prodA = new Product();
            prodA.code = "PRD-A";
            prodA.name = "Heavy Gold Ring";
            prodA.price = BigDecimal.valueOf(1000.0);
            prodA.persist();

            ProductComposition compA = new ProductComposition();
            compA.product = prodA;
            compA.rawMaterial = gold;
            compA.quantityNeeded = BigDecimal.valueOf(10.0);
            compA.persist();

            
            Product prodB = new Product();
            prodB.code = "PRD-B";
            prodB.name = "Light Gold Earring";
            prodB.price = BigDecimal.valueOf(200.0);
            prodB.persist();

            ProductComposition compB = new ProductComposition();
            compB.product = prodB;
            compB.rawMaterial = gold;
            compB.quantityNeeded = BigDecimal.valueOf(1.0);
            compB.persist();
        });

        
        ProductionPlanDTO plan = optimizationService.calculateOptimalProduction();

        assertNotNull(plan);
        assertTrue(BigDecimal.valueOf(2000.0).compareTo(plan.maxTotalValue) == 0, 
            "Esperado lucro de 2000.0, mas veio: " + plan.maxTotalValue);
        
        assertFalse(plan.items.isEmpty(), "O plano deveria conter itens");
        assertEquals("PRD-B", plan.items.get(0).productCode);
        assertEquals(10, plan.items.get(0).quantity);
    }
}