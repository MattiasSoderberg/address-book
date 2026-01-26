package dev.mattiassoderberg.addressbook.controller;

import dev.mattiassoderberg.addressbook.model.Address;
import dev.mattiassoderberg.addressbook.repository.AddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    AddressRepository repository;

    public AddressController(AddressRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Address> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{name}")
    public Address findByName(@PathVariable String name) {
        return repository.findByName(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Address create(@RequestBody Address address) {
        return repository.create(address);
    }
}
