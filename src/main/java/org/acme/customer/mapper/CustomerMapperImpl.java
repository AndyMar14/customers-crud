package org.acme.customer.mapper;

import org.acme.customer.dto.CreateCustomerDto;
import org.acme.customer.dto.UpdateCustomerDto;
import org.acme.customer.entity.CustomerEntity;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class CustomerMapperImpl implements ICustomerMapperImpl{
    @Override
    public CustomerEntity fromCreate(CreateCustomerDto createCustomerDto, String demonym) {
        var CustomerEntity = new CustomerEntity();
        CustomerEntity.setFirstName(createCustomerDto.firstName());
        CustomerEntity.setMiddleName(createCustomerDto.middleName());
        CustomerEntity.setLastName(createCustomerDto.lastName());
        CustomerEntity.setSecondLastName(createCustomerDto.secondLastName());
        CustomerEntity.setEmail(createCustomerDto.email());
        CustomerEntity.setAddress(createCustomerDto.address());
        CustomerEntity.setPhone(createCustomerDto.phone());
        CustomerEntity.setCountry(createCustomerDto.country());
        CustomerEntity.setDemonym(demonym);

        return CustomerEntity;
    }

    @Override
    public CustomerEntity fromUpdate(Long id, UpdateCustomerDto updateCustomerDto, String demonym) {
        var panacheEntity = CustomerEntity.findById(id);
        var Customer = CustomerEntity.class.cast(panacheEntity);
        if(Customer == null) {
            return null;
        }
        
        Customer.setEmail(updateCustomerDto.email());
        Customer.setAddress(updateCustomerDto.address());
        Customer.setPhone(updateCustomerDto.phone());
        Customer.setCountry(updateCustomerDto.country());
        Customer.setDemonym(demonym);

        return Customer;
    }
}
