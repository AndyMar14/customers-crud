package org.acme.customer.mapper;

import org.acme.customer.dto.CreateCustomerDto;
import org.acme.customer.dto.UpdateCustomerDto;
import org.acme.customer.entity.CustomerEntity;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class CustomerMapperImpl implements ICustomerMapperImpl{
    @Override
    public CustomerEntity fromCreate(CreateCustomerDto createCustomerDto) {
        var customer = new CustomerEntity();
        customer.setFirstName(createCustomerDto.firstName());
        customer.setMiddleName(createCustomerDto.middleName());
        customer.setLastName(createCustomerDto.lastName());
        customer.setSecondLastName(createCustomerDto.secondLastName());
        customer.setEmail(createCustomerDto.email());
        customer.setAddress(createCustomerDto.address());
        customer.setPhone(createCustomerDto.phone());
        customer.setCountry(createCustomerDto.country());
        customer.setDemonym(createCustomerDto.demonym());

        return customer;
    }

    @Override
    public CustomerEntity fromUpdate(Long id, UpdateCustomerDto updateCustomerDto) {
        var entity = CustomerEntity.findById(id);
        var customer = CustomerEntity.class.cast(entity);
        if(customer == null) {
            return null;
        }
        
        customer.setEmail(updateCustomerDto.email());
        customer.setAddress(updateCustomerDto.address());
        customer.setPhone(updateCustomerDto.phone());
        customer.setCountry(updateCustomerDto.country());

        return customer;
    }
}
