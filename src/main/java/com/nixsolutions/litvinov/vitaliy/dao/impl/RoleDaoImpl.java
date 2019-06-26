package com.nixsolutions.litvinov.vitaliy.dao.impl;

import com.nixsolutions.litvinov.vitaliy.dao.RoleDao;
import com.nixsolutions.litvinov.vitaliy.entity.Role;
import com.nixsolutions.litvinov.vitaliy.web.LoginController;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;



@Transactional
public class RoleDaoImpl implements RoleDao {


    private static final Logger logger = LoggerFactory
            .getLogger(LoginController.class);

    @Autowired
    
    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public void create(Role role) {
        if (role == null) {
            throw new NullPointerException("role = null");
        }
        try {
            sessionFactory.getCurrentSession().save(role);

        } catch (Exception e) {
            throw new RuntimeException("Cannot create role", e);
        }
    }

    @Override
    public void update(Role role) {
        if (role == null) {
            throw new NullPointerException("role = null");
        }
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(role);
        } catch (Exception e) {
            throw new RuntimeException("Cannot update role", e);
        }
    }

    @Override
    public void remove(Role role) {
        if (role == null) {
            throw new NullPointerException("role = null");
        }
        try {
            sessionFactory.getCurrentSession().delete(role);
        } catch (Exception e) {
            throw new RuntimeException("Cannot remove role", e);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public Role findByName(String name) {
        if (name == null) {
            throw new NullPointerException("name = null");
        }
        try {
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(Role.class);
            return (Role) criteria.add(Restrictions.eq("name", name))
                    .uniqueResult();

        } catch (Exception e) {
            logger.info("Cannot findByName role " + e);
            throw new RuntimeException("Cannot findByName role", e);
        }
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<Role> findAll() {
        try {
            return sessionFactory.getCurrentSession().createCriteria(Role.class)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Cannot find all roles", e);
        }
    } 
}
