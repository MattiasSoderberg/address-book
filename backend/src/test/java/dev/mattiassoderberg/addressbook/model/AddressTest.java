package dev.mattiassoderberg.addressbook.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void createAddress() {
        String name = "Test";
        String phone = "0701234567";
        String street = "Test street";
        String zipCode = "123 45";
        String city = "Testville";

        Address address = new Address(name, phone, street, zipCode, city);

        assertNotNull(address);
        assertEquals(street, address.getStreet());
    }
}