package org.acme.customer.exception;

import org.acme.customer.dto.ErrorMessage;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InternalServerErrorExceptionMapper implements ExceptionMapper<Exception> {


    @Override
    public Response toResponse(Exception exception) {
        String errorMessage = "Ocurrió un error inesperado en el servidor. Por favor, intente más tarde. Detalles: " 
        + exception.getMessage();

        ErrorMessage error = new ErrorMessage(errorMessage);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}