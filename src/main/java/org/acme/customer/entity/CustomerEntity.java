package org.acme.customer.entity;

import org.hibernate.annotations.processing.Pattern;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="customers")
public class CustomerEntity extends PanacheEntity {

    @Id
    @GeneratedValue
    public String id;

    @NotBlank
    @Column(nullable = false)
    public String firstName;
    
    public String middleName;
    
    @NotBlank
    @Column(nullable = false)
    public String lastName;
    
    public String secondLastName;
    
    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    public String email;
    
    @NotBlank
    @Column(nullable = false)
    public String address;
    
    @NotBlank
    @Column(nullable = false)
    public String phone;
    
    @NotBlank
    @Column(nullable = false)
    public String country;
    
    @Column(nullable = true)
    public String demonym;
}
