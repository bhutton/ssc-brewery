package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public class BaseIT {
    @Autowired
    WebApplicationContext wac;

    public MockMvc mockMvc;

    //    @MockBean
    //    BeerRepository beerRepository;
    //
    //    @MockBean
    //    BreweryRepository breweryRepository;
    //
    //    @MockBean
    //    BeerInventoryRepository beerInventoryRepository;
    //
    //    @MockBean
    //    BreweryService breweryService;
    //
    //    @MockBean
    //    CustomerRepository customerRepository;
    //
    //    @MockBean
    //    BeerService beerService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }
}
