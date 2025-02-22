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
    ICountryApiClient countryApiClient;

    @Override
    public String getDemonymByCountry(String countryCode) {
        try {
            // Llamamos a la API para obtener los datos del país
            String countryJson = countryApiClient.getCountryByCode(countryCode);

            if (countryJson == null || countryJson.isBlank()) {
                throw new NoSuchElementException("Código de país no encontrado: " + countryCode);
            }

            // Parseamos la respuesta como un array de JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootArray = mapper.readTree(countryJson);

            // Verificamos que la respuesta no esté vacía
            if (!rootArray.isArray() || rootArray.isEmpty()) {
                throw new NoSuchElementException("No se encontró información para el país: " + countryCode);
            }

            // Accedemos al primer objeto del array
            JsonNode countryData = rootArray.get(0);

            // Validamos que tenga la clave "demonyms"
            if (!countryData.has("demonyms")) {
                throw new NoSuchElementException("No se encontró el gentilicio para el país: " + countryCode);
            }

            // Obtenemos el nodo "demonyms"
            JsonNode demonymNode = countryData.get("demonyms");

            // Buscamos el gentilicio en inglés (femenino)
            if (!demonymNode.has("eng") || !demonymNode.get("eng").has("f")) {
                throw new NoSuchElementException("No se encontró el gentilicio en inglés para el país: " + countryCode);
            }

            String demonym = demonymNode.get("eng").get("f").asText();
            return demonym;

        }catch (NoSuchElementException e) {
            throw e; // Relanzamos la excepción para que el controlador la maneje
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al obtener el gentilicio", e);
        }
    }
}