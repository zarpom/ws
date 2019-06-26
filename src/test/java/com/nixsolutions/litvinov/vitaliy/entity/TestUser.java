package com.nixsolutions.litvinov.vitaliy.entity;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;



public class TestUser {
    User user;

    String login = "login";
    String password = "password";
    String email = "email";
    String firstName = "firstName";
    String lastName = "lastName";
    Date birthday = Date.valueOf("2008-01-01");
    Role role;

    String roleName = "testRole1";
    Long id = 1L;

    @Before
    public void before() throws Exception {
        user = new User();
        role = new Role();
    }

    @Test(timeout = 1000)
    public void testGetterAndSetters() {

        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthday(birthday);

        role.setId(id);
        role.setName(roleName);
        user.setRole(role);

        assertEquals(user.getId(), id);
        assertEquals(user.getLogin(), login);
        assertEquals(user.getPassword(), password);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getLastName(), lastName);
        assertEquals(user.getBirthday(), birthday);
        assertEquals(user.getRole(), role);
    }

}
