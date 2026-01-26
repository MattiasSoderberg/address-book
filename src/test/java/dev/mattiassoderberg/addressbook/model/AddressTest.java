package dev.mattiassoderberg.addressbook.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void createAddress() {
        String id = UUID.randomUUID().toString();
        String name = "Test";
        String street = "Test street";
        String zipCode = "123 45";
        String area = "Test area";
        String city = "Testville";

        Address address = new Address(id, name, street, zipCode, area, city);

        assertNotNull(address);
        assertEquals(street, address.getStreet());
    }
}