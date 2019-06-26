package com.nixsolutions.litvinov.vitaliy.dao.rest;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import com.nixsolutions.litvinov.vitaliy.entity.Role;
import com.nixsolutions.litvinov.vitaliy.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context-web-services-config.xml")
@WebAppConfiguration
public class RestApiTestClient {

    private User userTest;
    private Client client;
    private final static String APP_PATH = "http://10.10.35.155:8080/litvinov/rest";
    private static RequestSpecification spec;

    @BeforeClass
    public static void initSpec() {
        spec = new RequestSpecBuilder().setContentType(ContentType.JSON)
                .setBaseUri(APP_PATH).addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter()).build();
    }

    @Before
    public void setUp() {
        client = ClientBuilder.newClient();
        initUsers();
        given().spec(spec).body(userTest).when().post("users").then().extract();
    }

    @After
    public void tearDown() {
        initUsers();
        given().spec(spec).when().delete("users/" + userTest.getLogin()).then()
                .extract();
    }

    private void initUsers() {
        Role role = new Role();
        role.setId(1L);
        role.setName("admin");

        userTest = new User();
        userTest.setLogin("admin");
        userTest.setPassword("test");
        userTest.setBirthday(new Date());
        userTest.setEmail("admin");
        userTest.setFirstName("test");
        userTest.setLastName("test");
        userTest.setRole(role);

    }

    @Test
    public void getUsers() {
        given().spec(spec).when().get("users").then().statusCode(200);
    }

    @Test
    public void getUserByLogin() {
        User user = given().spec(spec).when()
                .get("users/" + userTest.getLogin()).then().statusCode(200)
                .extract().as(User.class);

        assertEquals(userTest.getLogin(), user.getLogin());

    }

    @Test
    public void putUser() {
        given().spec(spec).body(userTest).when().post("users").then().extract();
        given().spec(spec).body(userTest).when().put("users").then()
                .statusCode(200).extract();
    }

    @Test
    public void postUser() {
        tearDown();
        given().spec(spec).body(userTest).when().post("users").then()
                .statusCode(201).extract();
    }

    @Test
    public void deleteUser() {
        given().spec(spec).when().delete("users/" + userTest.getLogin()).then()
                .statusCode(202).extract();
    }
}
