package org.acme.customer.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InternalServerErrorExceptionMapper implements ExceptionMapper<Exception> {


    @Override
    public Response toResponse(Exception exception) {
        String errorMessage = "Ocurrió un error inesperado en el servidor. Por favor, intente más tarde. Detalles: " 
        + exception.getMessage();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .build();
    }
}