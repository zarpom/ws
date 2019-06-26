package com.nixsolutions.litvinov.vitaliy.dao.impl;

import static org.junit.Assert.assertEquals;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.nixsolutions.litvinov.vitaliy.dao.UserDao;
import com.nixsolutions.litvinov.vitaliy.entity.Role;
import com.nixsolutions.litvinov.vitaliy.entity.User;

import java.sql.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@ContextConfiguration(locations = "classpath:database-config.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })

@DatabaseSetup("/user/xml/before-data.xml")
public class UserDaoImplTest {
    private static final String AFTER_REMOVE_XML = "/user/xml/after-remove.xml";
    private static final String AFTER_UPDATE_XML = "/user/xml/after-update.xml";
    private static final String AFTER_CREATE_XML = "/user/xml/after-create.xml";
    private User user;
    private Role role;
    private User userInDB;

    @Autowired
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setId(1L);
        user.setEmail("email2");
        user.setFirstName("firstname2");
        user.setLastName("lastname2");
        user.setLogin("login2");
        user.setPassword("password2");
        user.setBirthday(Date.valueOf("2008-01-01"));
        role = new Role(1L, "admin");
        user.setRole(role);

        userInDB = new User();
        userInDB.setId(1L);
        userInDB.setEmail("email");
        userInDB.setFirstName("firstname");
        userInDB.setLastName("lastname");
        userInDB.setLogin("admin");
        userInDB.setPassword("admin");
        userInDB.setBirthday(Date.valueOf("2008-01-01"));
        role = new Role(1L, "admin");
        userInDB.setRole(role);
    }

    @Test
    @ExpectedDatabase(value = AFTER_CREATE_XML, assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void create() throws Exception {
        userDao.create(user);
    }

    @Test(expected = NullPointerException.class)
    public void createNull() throws Exception {
        userDao.create(null);
    }

    @Test(expected = NullPointerException.class)
    public void updateNull() throws Exception {
        userDao.create(null);
    }

    @Test
    public void findByLogin() throws Exception {
        User user = userDao.findByLogin(userInDB.getLogin());
        assertEquals(userInDB, user);
    }

    @Test
    public void findByEmail() throws Exception {
        User user = userDao.findByEmail(userInDB.getEmail());
        assertEquals(userInDB, user);
    }

    @Test
    @ExpectedDatabase(value = AFTER_UPDATE_XML, assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void update() throws Exception {
        userDao.update(user);
    }

    @Test
    @ExpectedDatabase(value = AFTER_REMOVE_XML, assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void remove() throws Exception {
        userDao.remove(userInDB);
    }

    @Test
    public void testFindAllUsers() throws Exception {
        List<User> users = userDao.findAll();
        assertEquals(users.size(), 1);
    }
}