package dev.mattiassoderberg.addressbook.controller;

import dev.mattiassoderberg.addressbook.exception.AddressNotFoundException;
import dev.mattiassoderberg.addressbook.model.Address;
import dev.mattiassoderberg.addressbook.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AddressRepository repository;

    @Autowired
    private ObjectMapper mapper;

    private final Address address = new Address(
            "testId123",
            "Test Tester",
            "0701234567",
            "Test Street",
            "12345",
            "Test City");

    @Test
    void getAddressesReturnAllAddresses() throws Exception {
        final List<Address> addressList = new ArrayList<>();
        addressList.add(address);

        when(repository.findAll()).thenReturn(addressList);
        mockMvc.perform(get("/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("testId123")));
    }

    @Test
    void getAllAddressesReturnEmptyListWhenNoAddress() throws Exception {
        mockMvc.perform(get("/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(jsonPath("$", is(instanceOf(ArrayList.class))));
    }

    @Test
    void getOneAddressByNameReturnAddress() throws Exception {
        when(repository.findByName(address.getName())).thenReturn(address);
        String uri = "/addresses/" + address.getName();

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(address.getName())));
    }


    @Test
    void getOneAddressByNameThrowExceptionIfNotExist() throws Exception {
        String name = "nameThatNotExists";
        when(repository.findByName(name)).thenThrow(new AddressNotFoundException());
        ResultActions result = mockMvc.perform(get("/addresses/" + name))
                .andExpect(status().isNotFound());

        assertEquals(AddressNotFoundException.class,
                Objects.requireNonNull(result.andReturn().getResolvedException()).getClass());
    }

    @Test
    void createAddressReturnAddressAndStatusIsCreated() throws Exception {
        when(repository.create(any(Address.class))).thenReturn(address);

        mockMvc.perform(post("/addresses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(address)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(address.getId())));
    }

    @Test
    void createAddressReturnBadRequestAndThrowsExceptionWhenAddressNotValid() throws Exception {
        String id = UUID.randomUUID().toString();
        String name = "Test";
        String phone = "070123456783754638327";
        String street = "Test street";
        String zipCode = "123 456 789";
        String city = "";

        Address nonValidAddress = new Address(id, name, phone, street, zipCode, city);

        ResultActions result = mockMvc.perform(post("/addresses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(nonValidAddress)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.phone", is("Phone number must be 10 digits")))
                .andExpect(jsonPath("$.zipCode", is("Zip Code must be 5 digits")))
                .andExpect(jsonPath("$.city", is("City is required")));

        assertEquals(MethodArgumentNotValidException.class,
                Objects.requireNonNull(result.andReturn().getResolvedException()).getClass());
    }

    @Test
    void updateAddressReturnStatusOkAndUpdatedAddress() throws Exception {
        Address updatedAddress = new Address(
                address.getId(),
                "updated name",
                "0987654321",
                address.getStreet(),
                address.getZipCode(),
                address.getCity()
        );

        when(repository.update(any(Address.class), any(String.class))).thenReturn(updatedAddress);

        mockMvc.perform(put("/addresses/" + address.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(updatedAddress)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(address.getId())))
                .andExpect(jsonPath("$.name", is(updatedAddress.getName())))
                .andExpect(jsonPath("$.phone", is(updatedAddress.getPhone())));
    }
}