package org.acme.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCustomerDto(
    @NotBlank
    @Email
    @Size(max = 100)
    String email,
    
    @NotBlank
    @Size(max = 255)
    String address,
    
    @NotBlank
    @Size(max = 20)
    String phone,
    
    @NotBlank
    @Size(max = 2)
    String country
) {}
