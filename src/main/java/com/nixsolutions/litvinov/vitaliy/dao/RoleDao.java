package com.nixsolutions.litvinov.vitaliy.dao;

import com.nixsolutions.litvinov.vitaliy.entity.Role;

import java.util.List;

public interface RoleDao {
    void create(Role role);

    void update(Role role);

    void remove(Role role);

    Role findByName(String name);

    public List<Role> findAll();
}
