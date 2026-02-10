package dev.mattiassoderberg.addressbook.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class Contact {

    private final String id;

    @NotBlank(message = "Name is required")
    private String name;

    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    private String phone;

    private String street;

    @Size(min = 5, max = 5, message = "Zip Code must be 5 digits")
    private String zipCode;

    private String city;

    public Contact() {
        this.id = UUID.randomUUID().toString();
    }

    public Contact(String name) {
        this();
        this.name = name;
    }

    public Contact(String name, String phone, String street, String zipCode, String city) {
        this(name);
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

    public String getPhone() {
        return phone;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }
}
