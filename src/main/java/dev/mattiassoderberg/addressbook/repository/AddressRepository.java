package dev.mattiassoderberg.addressbook.repository;

import dev.mattiassoderberg.addressbook.model.Address;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AddressRepository {

    List<Address> addresses = new ArrayList<>();

    public List<Address> findAll() {
        return addresses;
    }

    public Address findByName(String name) {
        return addresses.stream().filter(address -> address.getName().equals(name)).findFirst().orElseThrow();
    }

    public Address create(Address address) {
        addresses.add(address);
        return address;
    }
}
