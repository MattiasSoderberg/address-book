package dev.mattiassoderberg.addressbook.controller;

import dev.mattiassoderberg.addressbook.exception.ContactNotFoundException;
import dev.mattiassoderberg.addressbook.model.Contact;
import dev.mattiassoderberg.addressbook.repository.ContactRepository;
import dev.mattiassoderberg.addressbook.util.ImageUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    ContactRepository repository;

    private final Path uploadPath = Paths.get(System.getProperty("user.dir") + "/images");

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
        Contact createdContact = repository.create(contact);
        try {
            BufferedImage croppedImage = ImageUtil.cropImageSquare(image.getBytes(), Integer.parseInt(cropSize), Integer.parseInt(cropX), Integer.parseInt(cropY));
            String extension = ImageUtil.getExtension(image.getOriginalFilename());
            String fileName = createdContact.getId() + "." + extension;
            String filePath = Paths.get(uploadPath + "/" + fileName).toString();

            if (!Files.exists(uploadPath)) {
                Files.createDirectory(uploadPath);
            }

            File output = new File(filePath);
            ImageIO.write(croppedImage, extension, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return createdContact;
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
