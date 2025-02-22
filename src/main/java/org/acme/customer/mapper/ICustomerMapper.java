package org.acme.customer.mapper;

import org.acme.customer.dto.CreateCustomerDto;
import org.acme.customer.dto.UpdateCustomerDto;
import org.acme.customer.entity.CustomerEntity;

public interface ICustomerMapper {
    CustomerEntity fromCreate(CreateCustomerDto createClientDto,String demonym);
    CustomerEntity fromUpdate(Long id, UpdateCustomerDto updateClientDto,String demonym);
}
