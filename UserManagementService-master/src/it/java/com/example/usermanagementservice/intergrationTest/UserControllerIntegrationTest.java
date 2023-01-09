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
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

//    User user = new User("Rick", "Morty", 43);
//    User user1 = new User( "Rick", "Robinson", 76);
//    User user2 = new User( "Gobert", "Jonson", 23);

//    @BeforeEach
//    void init() {
////        h2Repository.save(user);
////        h2Repository.save(user1);
////        h2Repository.save(user2);
//    }

//    @AfterEach
//    void teardown() {
//        h2Repository.deleteAll();
//    }

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
        System.out.println(response2.getStatusCode());
        JSONAssert.assertEquals(expected, response2.getBody(), false);

    }

    @Test
    public void  addUser_thenReturnValidResponse() throws Exception {

        String expected = "400 BAD_REQUEST" ;
      //  "{\"id\":1,\"name\":\"Rick\",\"surname\":\"Morty\",\"age\":43}"
        User user = new User();
        user.setName("Sample User");
        user.setSurname("user1");
        user.setAge(23);
        HttpEntity<User> entity = new HttpEntity<User> (new User("Peter","Teper",18)) ;

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort("/crud/AddUser"),
                HttpMethod.POST, entity, String.class);

//        JSONObject jsonObject = new JSONObject() ;
//        jsonObject = response2.getBody() ;
//        ObjectMapper mapper = new ObjectMapper();
//        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject));
        System.out.println(response2.getStatusCode());

        // JSONAssert.assertEquals(expected, String.valueOf(response2), false);


    }




    private String createURLWithPort(String uri) {
        return LOCALHOST_PREFIX + port + uri;
    }
}