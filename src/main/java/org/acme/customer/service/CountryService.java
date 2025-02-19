package org.acme.customer.service;

import java.util.NoSuchElementException;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class CountryService implements ICountryService {
    @Inject
    @RestClient
    ICountryApiClient customerService;

    @Override
    public String getDemonymByCountry(String countryCode) {
        try {
            String countryJson = customerService.getCountryByCode(countryCode);
            if (countryJson == null || countryJson.isBlank()) {
                throw new NoSuchElementException("Código de país no encontrado: " + countryCode);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(countryJson);

            if (rootNode.isEmpty() || rootNode.get("demonyms") == null) {
                throw new NoSuchElementException("No se encontró el gentilicio para el país: " + countryCode);
            }

            JsonNode demonymNode = rootNode.get("demonyms");
            JsonNode engNode = demonymNode.get("eng");
            if (engNode == null || engNode.get("f") == null) {
                throw new NoSuchElementException("No se encontró el gentilicio en inglés para el país: " + countryCode);
            }

            return engNode.get("f").asText();
        } catch (JsonProcessingException e) {
            return null;
        }
    }
    
}