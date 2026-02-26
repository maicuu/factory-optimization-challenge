package com.factory.opt.resource;

import com.factory.opt.entity.RawMaterial;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;

@Path("/api/materials")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Materials")
public class RawMaterialResource {

    @GET
    @Operation(summary = "List all raw materials")
    public List<RawMaterial> listAll() {
        return RawMaterial.listAll();
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new raw material")
    public Response create(@Valid RawMaterial material) {
        material.persist();
        return Response.status(Response.Status.CREATED).entity(material).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing raw material")
    public Response update(@PathParam("id") Long id, @Valid RawMaterial material) {
        RawMaterial entity = RawMaterial.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        entity.code = material.code;
        entity.name = material.name;
        entity.stockQuantity = material.stockQuantity;
        entity.unit = material.unit;
        
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a raw material")
    public Response delete(@PathParam("id") Long id) {
        RawMaterial entity = RawMaterial.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entity.delete();
        return Response.noContent().build();
    }
}