package dev.mattiassoderberg.addressbook.controller;

import dev.mattiassoderberg.addressbook.exception.AddressNotFoundException;
import dev.mattiassoderberg.addressbook.model.Address;
import dev.mattiassoderberg.addressbook.repository.AddressRepository;
import jakarta.validation.Valid;
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
    public Address findByName(@PathVariable String name) throws AddressNotFoundException {
        return repository.findByName(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Address create(@Valid @RequestBody Address address) {
        return repository.create(address);
    }

    @PutMapping("/{id}")
    public Address update(@Valid @RequestBody Address address, @PathVariable String id) throws AddressNotFoundException {
        return repository.update(address, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        repository.delete(id);
    }
}
