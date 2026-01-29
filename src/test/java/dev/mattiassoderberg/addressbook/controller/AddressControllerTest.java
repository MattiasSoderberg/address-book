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
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
            "Test Street",
            "123 45",
            "Test Area");

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
}