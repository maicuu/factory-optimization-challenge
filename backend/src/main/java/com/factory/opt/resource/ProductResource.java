package com.factory.opt.resource;

import com.factory.opt.entity.Product;
import com.factory.opt.entity.ProductComposition; 
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation; 
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @GET
    @Operation(summary = "Listar todos os produtos")
    public List<Product> listAll() {
        return Product.listAll();
    }

    @POST
    @Transactional
    @Operation(summary = "Criar novo produto com receita")
    @APIResponse(responseCode = "201", description = "Criado com sucesso")
    public Response create(@Valid Product product) {
        if (product.compositions != null) {
            
            for (ProductComposition comp : product.compositions) {
                comp.product = product;
            }
        }
        product.persist();
        return Response.status(Response.Status.CREATED).entity(product).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar produto e sua BOM")
    public Response update(@PathParam("id") Long id, @Valid Product product) {
        Product entity = Product.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        entity.code = product.code;
        entity.name = product.name;
        entity.price = product.price;
        
        entity.compositions.clear();
        if (product.compositions != null) {
            for (ProductComposition comp : product.compositions) {
                comp.product = entity;
                entity.compositions.add(comp);
            }
        }
        
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Excluir produto")
    public Response delete(@PathParam("id") Long id) {
        Product entity = Product.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entity.delete();
        return Response.noContent().build();
    }
}