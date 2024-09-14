package com.neoteric.fullstackdemo_31._8._4.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String add1;
    private String add2;
    private String pincode;
    private String state;
    private String city;
}
