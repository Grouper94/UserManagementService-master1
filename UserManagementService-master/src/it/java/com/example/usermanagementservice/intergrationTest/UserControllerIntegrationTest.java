package com.example.usermanagementservice.intergrationTest;

import com.example.usermanagementservice.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private TestH2Repository h2Repository;

    HttpHeaders headers = new HttpHeaders();

    TestRestTemplate restTemplate = new TestRestTemplate();

    private static final String LOCALHOST_PREFIX = "http://localhost:";

    @Test
    public void findAll_whenUsersExists_thenReturnValidResponse() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort("/crud/findAll"),
                HttpMethod.GET, entity, String.class);

        String expected = "[{\"id\":1,\"name\":\"Rick\",\"surname\":\"Morty\",\"age\":43},"
                +"{\"id\":2,\"name\":\"Rick\",\"surname\":\"Robinson\",\"age\":73},"
                +"{\"id\":3,\"name\":\"George\",\"surname\":\"Palson\",\"age\":22}]";

        System.out.println(response2.getBody());
        JSONAssert.assertEquals(expected, response2.getBody(), false);
    }


    @Test
    public void findUserById_whenUserExists_thenReturnValidResponseAndUser() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        String expected = "{\"id\":1,\"name\":\"Rick\",\"surname\":\"Morty\",\"age\":43}";

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort("/crud/findUserById/1"),
                HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(expected, response2.getBody(), false);
    }

    @Test
    public void findUsersByName_whenUserExists_thenReturnValidResponseAndListOfUsers() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        String expected = "[{\"id\":1,\"name\":\"Rick\",\"surname\":\"Morty\",\"age\":43},"
                         +"{\"id\":2,\"name\":\"Rick\",\"surname\":\"Robinson\",\"age\":73}]";

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort("/crud/findUserByName/Rick"),
                HttpMethod.GET, entity, String.class);
        JSONAssert.assertEquals(expected, response2.getBody(), false);
    }

    @Test
    public void  addUser_thenReturnValidResponse() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort("/crud/AddUser?name=Eli&&surname=Gotier&age=41"),
                HttpMethod.POST, entity, String.class);

        assertEquals("200 OK",response2.getStatusCode().toString());

        User actual = h2Repository.findByName("Eli");

        assertEquals("Eli",actual.getName());

        assertEquals("Gotier",actual.getSurname());

        assertEquals(41,actual.getAge());

        h2Repository.deleteById(5);

    }

    @Test
    public void updateUser_whenGivenIdExists_thenReturnValidResponse() throws Exception {

        HttpEntity<User> entity = new HttpEntity<User>(new User(1,"NewName","NewSurname",56));

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort("/crud/Update"),
                HttpMethod.PUT, entity, String.class);

        User user = h2Repository.findByName("NewName") ;

        assertEquals(user.getName(),"NewName");

        assertEquals(user.getSurname(),"NewSurname");

        assertEquals(user.getAge(),56);

        assertEquals("200 OK",response2.getStatusCode().toString());

    }

    @Test
    @Sql({"/RandomUser.sql"})
    public void deleteUserById_whenUserExists_thenReturnValidResponse() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort("/crud/Delete/4"),
                HttpMethod.DELETE, entity, String.class);

      assert( response2.getStatusCode().is2xxSuccessful());

    }

    private String createURLWithPort(String uri) {
        return LOCALHOST_PREFIX + port + uri;
    }
}