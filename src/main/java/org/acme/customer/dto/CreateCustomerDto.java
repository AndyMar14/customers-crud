package org.acme.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record  CreateCustomerDto (
    @NotBlank
    @Size(max = 50)
    String firstName,
    
    @Size(max = 50)
    String middleName,
    
    @NotBlank
    @Size(max = 50)
    String lastName,
    
    @Size(max = 50)
    String secondLastName,
    
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
    String country,
    
    @Size(max = 50)
    String demonym
) {}
