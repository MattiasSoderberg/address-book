package dev.mattiassoderberg.addressbook.controller;

import dev.mattiassoderberg.addressbook.exception.ContactNotFoundException;
import dev.mattiassoderberg.addressbook.model.Contact;
import dev.mattiassoderberg.addressbook.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
public class ContactControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private ContactRepository repository;

        @Autowired
        private ObjectMapper mapper;

        private final Contact contact = new Contact(
                "Test Tester",
                "0701234567",
                "Test Street",
                "12345",
                "Test City");

        @Test
        void getContactsReturnAllContacts() throws Exception {
            final List<Contact> contactList = new ArrayList<>();
            contactList.add(contact);

            when(repository.findAll()).thenReturn(contactList);
            mockMvc.perform(get("/contacts"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)));
        }

        @Test
        void getAllContactsReturnEmptyListIsEmpty() throws Exception {
            mockMvc.perform(get("/contacts"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)))
                    .andExpect(jsonPath("$", is(instanceOf(ArrayList.class))));
        }

        @Test
        void getOneContactByIdReturnContact() throws Exception {
            when(repository.findById(contact.getId())).thenReturn(contact);

            mockMvc.perform(get("/contacts/" + contact.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(contact.getName())));
        }


        @Test
        void getOneContactByIdThrowExceptionIfNotExist() throws Exception {
            String name = "nameThatNotExists";
            when(repository.findById(name)).thenThrow(new ContactNotFoundException());
            ResultActions result = mockMvc.perform(get("/contacts/" + name))
                    .andExpect(status().isNotFound());

            assertEquals(ContactNotFoundException.class,
                    Objects.requireNonNull(result.andReturn().getResolvedException()).getClass());
        }

        @Test
        void createContactWithoutImageReturnContactAndStatusIsCreated() throws Exception {
            MockMultipartFile mockContact = new MockMultipartFile("contact", null, "application/json",
                    mapper.writeValueAsBytes(contact));

            when(repository.create(any(Contact.class))).thenReturn(contact);

            mockMvc.perform(multipart("/contacts")
                            .file(mockContact))
                    .andExpect(status().isCreated());
        }

        @Test
        void createContactWithImageReturnStatusIsCreated() throws Exception {
            MockMultipartFile mockContact = new MockMultipartFile("contact", null, "application/json",
                    mapper.writeValueAsBytes(contact));
            MockMultipartFile testImage = new MockMultipartFile("image", "image.png", "file", "a test image".getBytes());
            when(repository.create(any(Contact.class))).thenReturn(contact);

            mockMvc.perform(multipart("/contacts")
                            .file(mockContact)
                            .file(testImage))
                    .andExpect(status().isCreated());
        }

        @Test
        void createContactReturnBadRequestAndThrowsExceptionWhenContactNotValid() throws Exception {
            String name = "";
            String phone = "070123456783754638327";
            String street = "Test street";
            String zipCode = "123 456 789";
            String city = "";

            Contact nonValidContact = new Contact(name, phone, street, zipCode, city);

            MockMultipartFile mockContact = new MockMultipartFile("contact", null, "application/json",
                    mapper.writeValueAsBytes(nonValidContact));

            ResultActions result = mockMvc.perform(multipart("/contacts")
                            .file(mockContact))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.phone", is("Phone number must be 10 digits")))
                    .andExpect(jsonPath("$.zipCode", is("Zip Code must be 5 digits")))
                    .andExpect(jsonPath("$.name", is("Name is required")));

            assertEquals(MethodArgumentNotValidException.class,
                    Objects.requireNonNull(result.andReturn().getResolvedException()).getClass());
        }

        @Test
        void updateContactReturnStatusOkAndUpdatedContact() throws Exception {
            Contact updatedContact = new Contact(
                    "updated name",
                    "0987654321",
                    contact.getStreet(),
                    contact.getZipCode(),
                    contact.getCity()
            );

            when(repository.update(any(Contact.class), any(String.class))).thenReturn(updatedContact);

            mockMvc.perform(put("/contacts/" + contact.getId())
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(mapper.writeValueAsBytes(updatedContact)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(updatedContact.getName())))
                    .andExpect(jsonPath("$.phone", is(updatedContact.getPhone())));
        }

        @Test
        void updateContactThrowExceptionWhenNotFound() throws Exception {
            Contact updatedContact = new Contact(
                    "updated name",
                    "0987654321",
                    contact.getStreet(),
                    contact.getZipCode(),
                    contact.getCity()
            );

            when(repository.update(any(Contact.class), any(String.class))).thenThrow(new ContactNotFoundException());

            ResultActions result = mockMvc.perform(put("/contacts/nonValidId")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(mapper.writeValueAsBytes(updatedContact)))
                    .andExpect(status().isNotFound());

            assertEquals(ContactNotFoundException.class,
                    Objects.requireNonNull(result.andReturn().getResolvedException()).getClass());
        }

        @Test
        void updateContactReturnBadRequestAndThrowsExceptionWithInvalidData() throws Exception {
            Contact updatedContact = new Contact(
                    "",
                    "098765432187654",
                    contact.getStreet(),
                    contact.getZipCode(),
                    contact.getCity()
            );

            ResultActions result = mockMvc.perform(put("/contacts/" + contact.getId())
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(mapper.writeValueAsBytes(updatedContact)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.name", is("Name is required")))
                    .andExpect(jsonPath("$.phone", is("Phone number must be 10 digits")));

            assertEquals(MethodArgumentNotValidException.class,
                    Objects.requireNonNull(result.andReturn().getResolvedException()).getClass());
        }
    
}
