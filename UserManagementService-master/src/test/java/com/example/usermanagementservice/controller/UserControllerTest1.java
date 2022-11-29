package com.example.usermanagementservice.controller;

import org.junit.Before;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class UserControllerTest1 {
    @InjectMocks
    UserController  userController;

    @Autowired
    WebApplicationContext context;
    @Autowired
    private MockMvc mvc;

    @Before
    public void initTests() {
     //  MockitoAnnotations.initMocks(this);
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    //@Test
    public void shouldHaveEmptyDB() throws Exception {
        mvc.perform(get("/example/v1/hotels")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }



}
