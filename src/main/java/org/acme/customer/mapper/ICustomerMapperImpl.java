package org.acme.customer.mapper;

import org.acme.customer.dto.CreateCustomerDto;
import org.acme.customer.dto.UpdateCustomerDto;
import org.acme.customer.entity.CustomerEntity;

public interface ICustomerMapperImpl {
    CustomerEntity fromCreate(CreateCustomerDto createClientDto);
    CustomerEntity fromUpdate(Long id, UpdateCustomerDto updateClientDto);
}
