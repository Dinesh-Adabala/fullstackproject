package com.neoteric.fullstackdemo_31._8._4.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "account_address",schema = "bank")
@Data
public class AccountAddressEntity {
    public AccountAddressEntity(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "address1")
    private String address1;
    @Column(name = "address2")
    private String address2;
    @Column(name = "pincode")
    private String pincode;
    @Column(name = "state")
    private String state;
    @Column(name = "city")
    private String city;
    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "accountnumber",referencedColumnName = "accountnumber")
    private AccountEntity accountEntity;
}
