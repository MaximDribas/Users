package com.dribas.service;

import com.dribas.Application;
import com.dribas.entity.User;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void testRetrieveEmptyUsersArray() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users"),
                HttpMethod.GET, entity, String.class);

        String expected = "[]";

        JSONAssert.assertEquals(expected, response.getBody(), true);
    }

    @Test
    public void addUserAndDeleteUserById() throws JSONException {
        //add user
        User user = new User("login6char", "password6char", "First", "Last");

        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users"),
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        //retrieve all users
        HttpEntity<String> all = new HttpEntity<>(null, headers);

        ResponseEntity<String> allResponse = restTemplate.exchange(
                createURLWithPort("/users"),
                HttpMethod.GET, all, String.class);

        String expected = "[{\"userId\":1,\"login\":\"login6char\",\"password\":\"password6char\",\"firstName\":\"First\",\"lastName\":\"Last\"}]";

        JSONAssert.assertEquals(expected, allResponse.getBody(), true);

        //retrieve user by id
        HttpEntity<String> byId = new HttpEntity<>(null, headers);

        ResponseEntity<String> byIdResponse = restTemplate.exchange(
                createURLWithPort("/users/1"),
                HttpMethod.GET, byId, String.class);

        String expectedById = "{\"userId\":1,\"login\":\"login6char\",\"password\":\"password6char\",\"firstName\":\"First\",\"lastName\":\"Last\"}";

        JSONAssert.assertEquals(expectedById, byIdResponse.getBody(), true);

        //delete user by id
        HttpEntity<String> del = new HttpEntity<>(null, headers);

        restTemplate.exchange(
                createURLWithPort("/users/1"),
                HttpMethod.DELETE, del, String.class);

        //get all users
        HttpEntity<String> entity2 = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort("/users"),
                HttpMethod.GET, entity2, String.class);

        String emptyExpected = "[]";

        JSONAssert.assertEquals(emptyExpected, response2.getBody(), true);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
