package com.factory.opt.resource;

import com.factory.opt.dto.ProductionPlanDTO;
import com.factory.opt.service.OptimizationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.Map;

@Path("/api/optimization")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Optimization")
public class OptimizationResource {

    @Inject
    OptimizationService optimizationService;

    @GET
    @Operation(summary = "Get optimal production plan based on current stock")
    public ProductionPlanDTO getOptimalProductionPlan() {
        return optimizationService.calculateOptimalProduction();
    }

    @POST
    @Path("/simulate")
    @Operation(summary = "Simulate production with extra stock")
    public Response simulateProduction(Map<Long, BigDecimal> extraStock) {
        ProductionPlanDTO simulatedPlan = optimizationService.calculateOptimalProduction(extraStock);
        return Response.ok(simulatedPlan).build();
    }
}