package com.nixsolutions.litvinov.vitaliy.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.nixsolutions.litvinov.vitaliy.entity.User;

@WebService
public interface UserSoapService {

    @WebMethod
    public void create(@WebParam(name = "user") User user);

    @WebMethod
    public void update(@WebParam(name = "user") User user);

    @WebMethod
    public void remove(@WebParam(name = "login") String login);

    @WebMethod
    @WebResult(name = "user")
    public User findByLogin(@WebParam(name = "login") String login);

    @WebMethod
    @WebResult(name = "users")
    public List<User> findAll();

}
