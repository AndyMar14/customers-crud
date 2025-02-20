package org.acme.customer.resource;

import java.util.NoSuchElementException;

import org.acme.customer.dto.CreateCustomerDto;
import org.acme.customer.dto.UpdateCustomerDto;
import org.acme.customer.entity.CustomerEntity;
import org.acme.customer.mapper.ICustomerMapper;
import org.acme.customer.service.ICountryService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/customer")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    ICustomerMapper customerMapper;
    
    @Inject
    ICountryService countryService;
 
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid CreateCustomerDto createCustomerDto) {
        if (CustomerEntity.find("email", createCustomerDto.email()).firstResult() != null) {
            throw new BadRequestException("El correo electrónico ya está registrado.");
        }
        
        var demonym = countryService.getDemonymByCountry(createCustomerDto.country());
        var customer = customerMapper.fromCreate(createCustomerDto,demonym);
        customer.persist();
        return Response.ok(customer).build();
    }

    @GET
    public Response getAll() {
        return Response.ok(CustomerEntity.listAll()).build();
    }

    @GET
    @Path("getAllByCountry/{country}")
    public Response getAllByCountry(@PathParam("country") String country) {
        if (country == null || country.isEmpty()) {
            throw new BadRequestException("El parámetro de país no puede estar vacío");
        }

        var customersByCountry = CustomerEntity.list("country", country);
        return Response.ok(customersByCountry).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long customerId) {
        var customer = CustomerEntity.findById(customerId);
        if (customer == null) {
            throw new NoSuchElementException("No fue encontrado el cliente con el siguiente id: " + customerId);
        }
        return Response.ok(customer).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") Long customerId, @Valid UpdateCustomerDto updateCustomerDto) {
        var existingCustomer = CustomerEntity.findById(customerId);
        if (existingCustomer == null) {
            throw new NoSuchElementException("No fue encontrado el cliente con id: " + customerId);
        }

        var demonym = countryService.getDemonymByCountry(updateCustomerDto.country());
        var updatedCustomer = customerMapper.fromUpdate(customerId, updateCustomerDto,demonym);

        CustomerEntity.persist(updatedCustomer);
        return Response.ok(updatedCustomer).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteById(@PathParam("id") Long customerId) {
        boolean deleted = CustomerEntity.deleteById(customerId);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No fue encontrado el cliente con id: " + customerId)
                    .build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
