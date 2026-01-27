package dev.mattiassoderberg.addressbook.controller;

import dev.mattiassoderberg.addressbook.model.Address;
import dev.mattiassoderberg.addressbook.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AddressRepository repository;

    private final Address address = new Address(
            "testId123",
            "Test Tester",
            "Test Street",
            "123 45",
            "Test Area",
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
}