package dev.mattiassoderberg.addressbook.model;

import jakarta.validation.constraints.*;

public class Address {

    @NotBlank(message = "ID is required")
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone is required")
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "Zip Code is required")
    @Size(min = 5, max = 5, message = "Zip Code must be 5 digits")
    private String zipCode;

    @NotBlank(message = "City is required")
    private String city;

    public Address(String id, String name, String phone, String street, String zipCode, String city) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
