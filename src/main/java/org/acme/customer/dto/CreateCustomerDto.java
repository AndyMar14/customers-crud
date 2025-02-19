package org.acme.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCustomerDto {
    @NotBlank
    @Size(max = 50)
    public String firstName;
    
    @Size(max = 50)
    public String middleName;
    
    @NotBlank
    @Size(max = 50)
    public String lastName;
    
    @Size(max = 50)
    public String secondLastName;
    
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
    
    @Size(max = 50)
    public String demonym;
}
