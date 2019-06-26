package com.nixsolutions.litvinov.vitaliy.dao.impl;

import static org.junit.Assert.assertEquals;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.nixsolutions.litvinov.vitaliy.dao.RoleDao;
import com.nixsolutions.litvinov.vitaliy.entity.Role;

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

@DatabaseSetup("/role/xml/before-data.xml")
public class RoleDaoImplTest {
    private static final String AFTER_REMOVE_XML = "/role/xml/after-remove.xml";
    private static final String AFTER_UPDATE_XML = "/role/xml/after-update.xml";
    private static final String AFTER_CREATE_XML = "/role/xml/after-create.xml";

    @Autowired
    private RoleDao roleDao;
    private Role[] roles;

    @Before
    public void setUp() throws Exception {

        roles = new Role[3];
        roles[0] = new Role(1L, "admin");
        roles[1] = new Role(2L, "user");
        roles[2] = new Role(3L, "moder");
    }

    @Test
    public void findByName() throws Exception {
        assertEquals(roles[0], roleDao.findByName(roles[0].getName()));
    }

    @Test
    @ExpectedDatabase(value = AFTER_CREATE_XML, assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void create() throws Exception {
        roleDao.create(roles[1]);
    }

    @Test(expected = NullPointerException.class)
    public void createNull() throws Exception {
        roleDao.create(null);
    }

    @Test(expected = NullPointerException.class)
    public void updateNull() throws Exception {
        roleDao.create(null);
    }

    @Test
    @ExpectedDatabase(value = AFTER_UPDATE_XML, assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void update() throws Exception {
        roles[0].setName("superAdmin");
        roleDao.update(roles[0]);
    }

    @Test
    @ExpectedDatabase(value = AFTER_REMOVE_XML, assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testRemoveRole() throws Exception {
        roleDao.remove(roles[0]);
    }
}