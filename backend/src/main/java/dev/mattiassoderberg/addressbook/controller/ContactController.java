package dev.mattiassoderberg.addressbook.controller;

import dev.mattiassoderberg.addressbook.exception.ContactNotFoundException;
import dev.mattiassoderberg.addressbook.model.Contact;
import dev.mattiassoderberg.addressbook.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    ContactRepository repository;

    public ContactController(ContactRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Contact> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Contact findById(@PathVariable String id) throws ContactNotFoundException {
        return repository.findById(id);
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public Contact create(@Valid @RequestPart Contact contact,
                          @RequestPart(required = false) MultipartFile image,
                          @RequestParam(required = false) String cropSize,
                          @RequestParam(required = false) String cropX,
                          @RequestParam(required = false) String cropY) {
        return repository.create(contact);
    }

    @PutMapping("/{id}")
    public Contact update(@PathVariable String id, @Valid @RequestBody Contact contact) throws ContactNotFoundException {
        return repository.update(contact, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        repository.delete(id);
    }
}
