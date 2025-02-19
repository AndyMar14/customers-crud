package org.acme.customer.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "rest-countries-api")
@ApplicationScoped
public interface ICountryApiClient {
    @Path("/alpha/{countryCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    String getCountryByCode(@PathParam("countryCode") String countryCode);
}
