package com.nixsolutions.litvinov.vitaliy.dao.impl;

import com.nixsolutions.litvinov.vitaliy.dao.UserDao;
import com.nixsolutions.litvinov.vitaliy.entity.User;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserDaoImpl implements UserDao {

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(User user) {
        if (user == null) {
            throw new NullPointerException("user = null");
        }
        try {
            sessionFactory.getCurrentSession().save(user);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create user ", e);
        }
    }

    @Override
    public void update(User user) {
        if (user == null) {
            throw new NullPointerException("user = null");
        }
        try {
            sessionFactory.getCurrentSession().update(user);
        } catch (Exception e) {
            throw new RuntimeException("Cannot update user", e);
        }
    }

    @Override
    public void remove(User user) {
        if (user == null) {
            throw new NullPointerException("user = null");
        }
        try {
            sessionFactory.getCurrentSession().delete(user);
        } catch (Exception e) {
            throw new RuntimeException("Error while removing user", e);
        }
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        try {
            return sessionFactory.getCurrentSession().createCriteria(User.class)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Error while searching users", e);
        }
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        if (id == null) {
            throw new NullPointerException("id = null");
        }
        try {
            User user = sessionFactory.getCurrentSession().get(User.class, id);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Error while searching user", e);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        if (login == null) {
            throw new NullPointerException("login = null");
        }
        try {
            User user = (User) sessionFactory.getCurrentSession()
                    .createCriteria(User.class)
                    .add(Restrictions.eq("login", login)).uniqueResult();
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Error while searching user", e);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        if (email == null) {
            throw new NullPointerException("email = null");
        }
        try {
            User user = (User) sessionFactory.getCurrentSession()
                    .createCriteria(User.class)
                    .add(Restrictions.eq("email", email)).uniqueResult();
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Error while searching user", e);
        }
    }

}
