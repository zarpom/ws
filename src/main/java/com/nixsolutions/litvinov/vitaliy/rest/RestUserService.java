package com.nixsolutions.litvinov.vitaliy.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.nixsolutions.litvinov.vitaliy.dao.UserDao;
import com.nixsolutions.litvinov.vitaliy.entity.User;

@Service
@Path("/users")
@Component
public class RestUserService {

    @Autowired
    private UserDao userDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> findAll() {
        List<User> list = userDao.findAll();
        return list;
    }

    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("login") String login) {
        User user = userDao.findByLogin(login);
        return Response.ok().entity(user).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@RequestBody User user) {
        if (userDao.findByLogin(user.getLogin()) == null) {
            userDao.create(user);
            return Response.status(Response.Status.CREATED)
                    .entity("User has been created").build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("User exists!").build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json;odata=verbose")
    public Response update(@RequestBody User user) {
        User userInDb = userDao.findByLogin(user.getLogin());
        if (userInDb != null) {
            userDao.update(userInDb);
            return Response.status(Response.Status.OK)
                    .entity("User has been updated").build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("User doesn't exists!").build();
        }
    }

    @DELETE
    @Path("/{login}")
    public Response delete(@PathParam("login") String login) {
        User user = userDao.findByLogin(login);
        if (user != null) {
            userDao.remove(user);
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("User doesn't exists!").build();
        }
        return Response.status(202).entity("User deleted successfully !!")
                .build();
    }

}
