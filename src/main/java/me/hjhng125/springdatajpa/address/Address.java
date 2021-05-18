package me.hjhng125.springdatajpa.address;

import javax.persistence.Embeddable;

@Embeddable // Entity에 종속적인 value type 선언
public class Address {

    private String street;

    private String city;

    private String state;

    private String zipCode;
}
