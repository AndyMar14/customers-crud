package org.acme.customer.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface ICountryService {
    public String getDemonymByCountry(String country);
}
