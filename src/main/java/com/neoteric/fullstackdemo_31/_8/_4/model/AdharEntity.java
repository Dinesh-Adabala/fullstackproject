package com.neoteric.fullstackdemo_31._8._4.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "adhar",schema = "bank")
public class AdharEntity {
    public AdharEntity(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adharnumber")
    public Integer aharnumber;


    @Column(name = "name")
    public String name;

    @OneToMany(mappedBy = "adharEntity",cascade = CascadeType.PERSIST)
    public List<AddressEntity> addressEntitiesList;
}
