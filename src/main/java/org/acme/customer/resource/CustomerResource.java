package org.acme.customer.resource;

import org.acme.customer.mapper.ICustomerMapperImpl;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("api/customer")
@Produces(MediaType.TEXT_PLAIN)
@Transactional
public class CustomerResource {

    private ICustomerMapperImpl customerMapper;

    @Inject
    public CustomerResource(ICustomerMapperImpl customerMapper) {
        this.customerMapper = customerMapper;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String create() {
        return "Create";
    }

    @GET
    public String getAll() {
        return "GetAllCustomers";
    }

    @GET
    @Path("getAllByCountry/{id}")
    public String getAllByCountry(@PathParam("id") String country) {
        return "GetAllCustomersByCountry";
    }

    @GET
    @Path("{id}")
    public String getById(@PathParam("id") Long customerId) {
        return "GetCustomerById";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String update() {
        return "UpdateCustomer";
    }

    @DELETE
    public String deleteById() {
        return "DeleteCustomer";
    }
}
