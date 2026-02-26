package com.factory.opt.service; // 1. Package corrigido

import com.factory.opt.dto.ProductionPlanDTO;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class OptimizationLogicTest {

    @Inject
    OptimizationService optimizationService;

    @Test
public void testPriorityEfficiencyOverPrice() {
    
    ProductionPlanDTO plan = optimizationService.calculateOptimalProduction(); 
    
    assertNotNull(plan);
    assertTrue(plan.maxTotalValue >= 2000.0);
}
}