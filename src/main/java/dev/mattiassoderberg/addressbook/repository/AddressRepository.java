package dev.mattiassoderberg.addressbook.repository;

import dev.mattiassoderberg.addressbook.exception.AddressNotFoundException;
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

    public Address findByName(String name) throws AddressNotFoundException {
        return addresses.stream().filter(address -> address.getName().equals(name)).findFirst().orElseThrow(AddressNotFoundException::new);
    }

    public Address create(Address address) {
        addresses.add(address);
        return address;
    }

    public Address update(Address address, String id) throws AddressNotFoundException {
        Address existing = addresses.stream().filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(AddressNotFoundException::new);
        int i = addresses.indexOf(existing);
        addresses.set(i, address);

        return addresses.get(i);
    }

    public void delete(String id) {
        addresses.removeIf(address -> address.getId().equals(id));
    }
}
