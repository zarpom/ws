package com.nixsolutions.litvinov.vitaliy.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestRole {
    Role role;
    String roleName = "testRole1";
    Long roleId = 1L;

    @Before
    public void before() throws Exception {
        role = new Role();
    }

    @Test(timeout = 1000)
    public void testGetterAndSetters() {
        role.setName(roleName);
        role.setId(roleId);

        assertEquals(role.getName(), roleName);
        assertEquals(role.getId(), roleId);
    }
}
