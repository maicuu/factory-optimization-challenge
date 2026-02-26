package com.factory.opt.service;

import com.factory.opt.dto.ProductionPlanDTO;
import com.factory.opt.entity.Product;
import com.factory.opt.entity.ProductComposition;
import com.factory.opt.entity.RawMaterial;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class OptimizationServiceTest {

    @Inject
    OptimizationService optimizationService;

    @BeforeEach
    @Transactional
    void setup() {
        
        ProductComposition.deleteAll();
        Product.deleteAll();
        RawMaterial.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("Cenário: O produto mais caro NÃO é o mais lucrativo (Conflito de Insumo)")
    void testExpensiveProductIsNotAlwaysTheBest() {
        
        RawMaterial gold = new RawMaterial();
        gold.code = "RM-GOLD";
        gold.name = "Gold";
        gold.stockQuantity = 10.0;
        gold.persist();

        
        Product prodA = new Product();
        prodA.code = "PRD-A";
        prodA.name = "Heavy Gold Ring";
        prodA.price = 1000.0;
        prodA.persist();

        ProductComposition compA = new ProductComposition();
        compA.product = prodA;
        compA.rawMaterial = gold;
        compA.quantityNeeded = 10.0;
        compA.persist();

        
        Product prodB = new Product();
        prodB.code = "PRD-B";
        prodB.name = "Light Gold Earring";
        prodB.price = 200.0;
        prodB.persist();

        ProductComposition compB = new ProductComposition();
        compB.product = prodB;
        compB.rawMaterial = gold;
        compB.quantityNeeded = 1.0;
        compB.persist();

        
        ProductionPlanDTO plan = optimizationService.calculateOptimalProduction();

        
        assertEquals(2000.0, plan.maxTotalValue, "O lucro máximo deve ser 2000 (10x Produto B)");
        assertEquals(1, plan.items.size(), "Deve fabricar apenas 1 tipo de produto");
        assertEquals("PRD-B", plan.items.get(0).productCode, "Deve priorizar o produto B");
        assertEquals(10, plan.items.get(0).quantity, "Deve fabricar 10 unidades do produto B");
    }

    @Test
    @Transactional
    @DisplayName("Cenário: Estoque insuficiente (Edge case)")
    void testInsufficientStock() {
        RawMaterial iron = new RawMaterial();
        iron.code = "RM-IRON";
        iron.name = "Iron";
        iron.stockQuantity = 2.0;
        iron.persist();

        Product prodC = new Product();
        prodC.code = "PRD-C";
        prodC.name = "Iron Gate";
        prodC.price = 500.0;
        prodC.persist();

        ProductComposition compC = new ProductComposition();
        compC.product = prodC;
        compC.rawMaterial = iron;
        compC.quantityNeeded = 10.0; 
        compC.persist();

        ProductionPlanDTO plan = optimizationService.calculateOptimalProduction();

        assertEquals(0.0, plan.maxTotalValue);
        assertTrue(plan.items.isEmpty(), "Não deve fabricar nada se não tem estoque suficiente");
    }
}