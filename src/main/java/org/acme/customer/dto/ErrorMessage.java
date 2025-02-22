package org.acme.customer.dto;

public record ErrorMessage(
    String error,
    String message
) {
    public ErrorMessage(String error) {
        this(error, ""); // Default value for message
    }
}
