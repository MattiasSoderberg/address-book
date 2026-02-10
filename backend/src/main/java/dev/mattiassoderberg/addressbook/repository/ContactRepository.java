package dev.mattiassoderberg.addressbook.repository;

import dev.mattiassoderberg.addressbook.exception.ContactNotFoundException;
import dev.mattiassoderberg.addressbook.model.Contact;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ContactRepository {

    List<Contact> contactList = new ArrayList<>();

    public List<Contact> findAll() {
        return contactList;
    }

    public Contact findById(String id) throws ContactNotFoundException {
        return contactList.stream()
                .filter(contact -> contact.getId().equals(id))
                .findFirst().orElseThrow(ContactNotFoundException::new);
    }

    public Contact create(Contact contact) {
        contactList.add(contact);
        return contact;
    }

    public Contact update(Contact updatedContact, String id) throws ContactNotFoundException {
        Contact existing = contactList.stream()
                .filter(contact -> contact.getId().equals(id))
                .findFirst().orElseThrow(ContactNotFoundException::new);

        int index = contactList.indexOf(existing);

        contactList.set(index, updatedContact);

        return existing;
    }

    public void delete(String id) {
        contactList.removeIf(contact -> contact.getId().equals(id));
    }
}
