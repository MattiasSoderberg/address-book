package dev.mattiassoderberg.addressbook.model;

import jakarta.validation.constraints.*;

import java.util.UUID;

public class Address {

    private final String id;

    @NotBlank(message = "Name is required")
    private String name;

    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    private String phone;

    private String street;

    @Size(min = 5, max = 5, message = "Zip Code must be 5 digits")
    private String zipCode;

    private String city;

    public Address() {
        this.id = UUID.randomUUID().toString();
    }

    public Address(String name, String phone, String street, String zipCode, String city) {
        this();
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
