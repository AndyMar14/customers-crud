package org.acme.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateCustomerDto {
    @NotBlank
    @Email
    @Size(max = 100)
    public String email;
    
    @NotBlank
    @Size(max = 255)
    public String address;
    
    @NotBlank
    @Size(max = 20)
    public String phone;
    
    @NotBlank
    @Size(max = 2)
    public String country;
}
