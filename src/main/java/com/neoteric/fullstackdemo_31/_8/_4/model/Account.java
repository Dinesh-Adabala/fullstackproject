package com.neoteric.fullstackdemo_31._8._4.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Account {
    private String name;
    private String accountnumber;
    private String pan;
    private String mobileNumber;
    private String balance;
    private Address address;

}
