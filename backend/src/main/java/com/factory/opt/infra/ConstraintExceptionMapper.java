package com.factory.opt.infra;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Map;

@Provider
public class ConstraintExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        
        return Response.status(Response.Status.CONFLICT)
                .entity(Map.of(
                    "error", "Conflito de integridade no banco de dados",
                    "details", "Verifique se o código já existe ou se o item está sendo usado."
                ))
                .build();
    }
}