package com.factory.opt.service;

import com.factory.opt.dto.ProductionPlanDTO;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal; // Import necessário
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class OptimizationLogicTest {

    @Inject
    OptimizationService optimizationService;

    @Test
    public void testPriorityEfficiencyOverPrice() {
        ProductionPlanDTO plan = optimizationService.calculateOptimalProduction(); 
        
        assertNotNull(plan);
        
        assertTrue(plan.maxTotalValue.compareTo(BigDecimal.valueOf(2000.0)) >= 0, 
            "O lucro máximo deve ser pelo menos 2000");
    }
}