package com.neoteric.fullstackdemo_31._8._4.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "account",schema = "bank")
@Data
public class AccountEntity {
    public AccountEntity(){

    }
    @Column(name = "name", nullable = false)
    private String name;
    @Id
    @Column(name = "accountnumber",nullable = false)
    private String accountnumber;
    @Column(name = "pan", nullable = false)
    private String pan;
    @Column(name = "mobile", nullable = false)
    private String mobileNumber;
    @Column(name = "balance", nullable = false)
    private String balance;


    @OneToMany(mappedBy = "accountEntity",cascade = CascadeType.PERSIST)
    public List<AccountAddressEntity> accountAddressEntityList;

}
