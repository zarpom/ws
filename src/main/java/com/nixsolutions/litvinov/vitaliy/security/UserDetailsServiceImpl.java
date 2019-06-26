package com.nixsolutions.litvinov.vitaliy.security;

import com.nixsolutions.litvinov.vitaliy.dao.UserDao;
import com.nixsolutions.litvinov.vitaliy.entity.User;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory
            .getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        try {
            logger.info("Into loadUserByUsername");
            User user = userDao.findByLogin(login);
            logger.info("Into loadUserByUsername findByLogin=" + user);
            if (user == null) {
                throw new UsernameNotFoundException(login);
            }
            return new org.springframework.security.core.userdetails.User(
                    user.getLogin(), user.getPassword(), true, true, true, true,
                    getGrantedAuthorities(user));
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getLocalizedMessage(), e);
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
        return authorities;
    }
}