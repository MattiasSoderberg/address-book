package dev.mattiassoderberg.addressbook.model;

public class Address {

    private String id;
    private String name;
    private String street;
    private String zipCode;
    private String area;
    private String city;

    public Address(String id, String name, String street, String zipCode, String area, String city) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.zipCode = zipCode;
        this.area = area;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
