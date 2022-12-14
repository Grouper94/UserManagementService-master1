package com.example.usermanagementservice.intergrationTest;

import com.example.usermanagementservice.intergrationTest.TestH2Repository;
import com.example.usermanagementservice.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@TestMethodOrder(OrderAnnotation.class)
class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private TestH2Repository h2Repository;

    User user = new User( "Rick", "Morty", 43);
    User user1 = new User( "Rick", "Robinson", 76);
    User user2 = new User( "Gobert", "Jonson", 23);

    @BeforeEach
    void init() {
        h2Repository.save(user);
        h2Repository.save(user1);
        h2Repository.save(user2);
    }

    @AfterEach
    void teardown() {
       h2Repository.deleteAll();
    }
    @Test
    public void  addUser_thenReturnValidResponse() throws Exception {

        MockHttpServletRequestBuilder mockRequest = post("/crud/AddUser")
                .param("name","NewUser")
                .param("surname","Created")
                .param("age","65");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk());
        User actual = h2Repository.findByName("NewUser");
        assertEquals("NewUser", actual.getName());
    }


    @Test
    public void updateUser_whenGivenIdExists_thenReturnValidResponse() throws Exception {
       User actual = h2Repository.save(user);
        actual.setName("Changed");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/crud/Update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(actual));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk());
        User usr = h2Repository.findByName("Changed");
        Assertions.assertEquals(actual.getName(), usr.getName());
    }


    @Test
    public void  findUserById_whenUserExists_thenReturnValidResponseAndUser() throws Exception {

        Matchers Matchers = new Matchers();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/crud/findUserById/{id}", user1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", org.hamcrest.Matchers.is(user1.getName())))
                .andExpect(jsonPath("$.surname", org.hamcrest.Matchers.is(user1.getSurname())))
                .andExpect(jsonPath("$.age", org.hamcrest.Matchers.is(user1.getAge())));
    }


    @Test
    public void findUsersByName_whenUserExists_thenReturnValidResponseAndListOfUsers() throws Exception {
        Matchers Matchers = new Matchers();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/crud/findUserByName/Rick")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", org.hamcrest.Matchers.is("Rick")))
                .andExpect(jsonPath("$[1].name", org.hamcrest.Matchers.is("Rick")))
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)));
    }


    @Test
    public void findAll_whenUsersExists_thenReturnValidResponse() throws Exception {
        Matchers Matchers = new Matchers();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/crud/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(3)));
    }

    @Test
    public void deleteUserById_whenUserExists_thenReturnValidResponse() throws Exception {
        System.out.println(h2Repository.findAll());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/crud/Delete/{id}",user1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
