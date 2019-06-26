package com.nixsolutions.litvinov.vitaliy.util.validation.dto;

import com.nixsolutions.litvinov.vitaliy.dao.RoleDao;
import com.nixsolutions.litvinov.vitaliy.entity.Role;
import com.nixsolutions.litvinov.vitaliy.entity.User;

import org.springframework.stereotype.Component;

@Component
public class Converter {

    private RoleDao roleDao;

    public User convertAddFormUserToDbUser(UserAddForm user) {
        if (user == null) {
            throw new NullPointerException("user == null");
        }
        Role role = roleDao.findByName(user.getRole());

        return new User(user.getLogin(), user.getPassword(), user.getEmail(),
                user.getFirstName(), user.getLastName(), user.getBirthday(),
                role);
    }

    public User convertEditFormUserToDbUser(UserEditForm user) {
        if (user == null) {
            throw new NullPointerException("user == null");
        }
        Role role = roleDao.findByName(user.getRole());
        User resultUser = new User(user.getLogin(), user.getPassword(),
                user.getEmail(), user.getFirstName(), user.getLastName(),
                user.getBirthday(), role);

        resultUser.setId(user.getId());

        return resultUser;
    }

    public UserAddForm convertToAddFormUser(User user) {
        if (user.getRole() == null) {
            throw new NullPointerException("role == null");
        }
        return new UserAddForm(user.getLogin(), user.getPassword(),
                user.getPassword(), user.getEmail(), user.getFirstName(),
                user.getLastName(), user.getBirthday(),
                user.getRole().getName());
    }

    public UserEditForm convertToEditFormUser(User user) {
        if (user.getRole() == null) {
            throw new NullPointerException("role == null");
        }
        return new UserEditForm(user.getId(), user.getLogin(),
                user.getPassword(), user.getPassword(), user.getEmail(),
                user.getFirstName(), user.getLastName(), user.getBirthday(),
                user.getRole().getName());

    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
}
