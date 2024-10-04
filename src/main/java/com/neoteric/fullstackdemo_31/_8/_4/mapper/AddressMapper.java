package com.neoteric.fullstackdemo_31._8._4.mapper;

import com.neoteric.fullstackdemo_31._8._4.model.AccountAddressEntity;
import com.neoteric.fullstackdemo_31._8._4.model.Address;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    @Autowired
    ModelMapper modelMapper;

    public AccountAddressEntity dtoToEntity(Address address){
        AccountAddressEntity accountAddressEntity = modelMapper.map(address,AccountAddressEntity.class);
        return accountAddressEntity;
    }
    public Address EntityToDto(AccountAddressEntity accountAddressEntity){
        Address address = modelMapper.map(accountAddressEntity,Address.class);
        return address;
    }
}
