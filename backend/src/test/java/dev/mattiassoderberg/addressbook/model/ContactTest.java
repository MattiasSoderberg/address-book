package dev.mattiassoderberg.addressbook.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    @Test
    void createContactWithNameOnly() {
        String name = "Test";

        Contact contact = new Contact(name);

        assertNotNull(contact);
        assertEquals(name, contact.getName());
        assertNull(contact.getPhone());
    }

    @Test
    void createContactWithAllFields() {
        String name = "Test";
        String phone = "0701234567";
        String street = "Test street";
        String zipCode = "123 45";
        String city = "Test City";

        Contact contact = new Contact(name, phone, street, zipCode, city);

        assertNotNull(contact);
        assertEquals(phone, contact.getPhone());
    }
}
