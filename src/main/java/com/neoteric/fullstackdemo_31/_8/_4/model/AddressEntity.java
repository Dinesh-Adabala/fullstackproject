package com.neoteric.fullstackdemo_31._8._4.model;

import jakarta.persistence.*;

@Entity
@Table(name = "address",schema = "bank")
public class AddressEntity {
    public AddressEntity(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer Id;

    @Column(name = "state")
    public String state;

    @ManyToOne
    @JoinColumn(name = "adharnumber",referencedColumnName = "adharnumber")
    public AdharEntity adharEntity;

}
