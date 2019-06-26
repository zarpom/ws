package com.nixsolutions.litvinov.vitaliy.soap;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nixsolutions.litvinov.vitaliy.dao.UserDao;
import com.nixsolutions.litvinov.vitaliy.entity.User; 

@WebService( serviceName = "soap")
@Component
public class UserSoapServiceImpl implements UserSoapService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void create(User user) {
        User userExist = userDao.findByLogin(user.getLogin());
        if (userExist != null) {
            throw new RuntimeException(
                    "User with login " + user.getLogin() + " already exist");
        }
        userDao.create(user);
    }

    @Override
    public void update(User user) {
        User userExist = userDao.findByLogin(user.getLogin());
        if (userExist == null) {
            throw new RuntimeException(
                    "User with login " + user.getLogin() + " doesnt exist");
        }
        userDao.update(user);
    }

    @Override
    public void remove(String login) {
        User user = userDao.findByLogin(login);
        if (user == null) {
            throw new RuntimeException(
                    "User with login " + login + " doesnt exist");
        }
        userDao.remove(user);
    }

}
