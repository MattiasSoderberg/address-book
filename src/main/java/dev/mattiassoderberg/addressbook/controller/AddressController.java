package dev.mattiassoderberg.addressbook.controller;

import dev.mattiassoderberg.addressbook.model.Address;
import dev.mattiassoderberg.addressbook.repository.AddressRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
