package com.neoteric.fullstackdemo_31._8._4.mapper;

import com.neoteric.fullstackdemo_31._8._4.model.Account;
import com.neoteric.fullstackdemo_31._8._4.model.AccountEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    @Autowired
    ModelMapper modelMapper;
    public Account dtoToEntity(AccountEntity accountEntity){
        Account account = modelMapper.map(accountEntity,Account.class);
        return account;
    }

    public AccountEntity entityToDto(Account account){
        AccountEntity accountEntity = modelMapper.map(account,AccountEntity.class);
        return accountEntity;
    }
}
