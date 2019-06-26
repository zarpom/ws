package com.nixsolutions.litvinov.vitaliy.web;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Date;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nixsolutions.litvinov.vitaliy.dao.UserDao;
import com.nixsolutions.litvinov.vitaliy.entity.Role;
import com.nixsolutions.litvinov.vitaliy.entity.User;
import com.nixsolutions.litvinov.vitaliy.util.validation.dto.Converter;
import com.nixsolutions.litvinov.vitaliy.util.validation.dto.UserAddForm;
import com.nixsolutions.litvinov.vitaliy.util.validation.dto.UserEditForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context-mock-config.xml")
@WebAppConfiguration
public class AdminControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private Converter converter;

    @Autowired
    private UserDao userDao;

    private MockMvc mockMvc;

    private Role roleAdmin = new Role(1L, "admin");
    private Role roleUser = new Role(2L, "user");

    private User user1 = new User(1L, "admin", "Admin1", "admin@mail.com",
            "John", "Smith", Date.valueOf("1486-11-21"), roleAdmin);

    private User user2 = new User(2L, "user", "user", "user@mail.com", "User",
            "User", Date.valueOf("1487-12-12"), roleUser);

    private User user3 = new User(3L, "moder", "moder", "moder.com", "", "",
            Date.valueOf("1488-03-12"), roleUser);

    @Before
    public void setup() {
        Mockito.reset(userDao);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @WithMockUser(roles = "admin")
    public void admin() throws Exception {
        when(userDao.findAll()).thenReturn(Arrays.asList(user2, user1));

        mockMvc.perform(get("/admin")).andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(forwardedUrl("/WEB-INF/pages/admin.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(user1)))
                .andExpect(model().attribute("users", hasItem(user2)));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void adminCreateUser() throws Exception {
        UserAddForm userAddForm = converter.convertToAddFormUser(user2);

        mockMvc.perform(
                post("/admin/add").param("login", userAddForm.getLogin())
                        .param("email", userAddForm.getEmail())
                        .param("password", userAddForm.getPassword())
                        .param("firstName", userAddForm.getFirstName())
                        .param("lastName", userAddForm.getLastName())
                        .param("birthday", userAddForm.getBirthday().toString())
                        .param("repassword", userAddForm.getPassword())
                        .param("role", userAddForm.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void adminCreateUserWithErrors() throws Exception {
        UserAddForm userAddForm = converter.convertToAddFormUser(user3);

        mockMvc.perform(
                post("/admin/add").param("login", userAddForm.getLogin())
                        .param("email", userAddForm.getEmail())
                        .param("password", userAddForm.getPassword())
                        .param("firstName", userAddForm.getFirstName())
                        .param("lastName", userAddForm.getLastName())
                        .param("birthday", userAddForm.getBirthday().toString())
                        .param("repassword", userAddForm.getPassword())
                        .param("role", userAddForm.getRole()))
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(model().attributeHasFieldErrors("user", "lastName"))
                .andExpect(model().attributeExists("user"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("/WEB-INF/pages/add.jsp"));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void adminEditUser() throws Exception {
        UserEditForm userEditForm = converter.convertToEditFormUser(user2);

        mockMvc.perform(post("/admin/edit")
                .param("id", userEditForm.getId().toString())
                .param("login", userEditForm.getLogin())
                .param("email", userEditForm.getEmail())
                .param("password", userEditForm.getPassword())
                .param("firstName", userEditForm.getFirstName())
                .param("lastName", userEditForm.getLastName())
                .param("birthday", userEditForm.getBirthday().toString())
                .param("repassword", userEditForm.getPassword())
                .param("role", userEditForm.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void adminEditUserWithErrors() throws Exception {
        when(userDao.findById(3L)).thenReturn(user3);

        UserEditForm userForm = converter.convertToEditFormUser(user3);

        mockMvc.perform(
                post("/admin/edit").param("id", userForm.getId().toString())
                        .param("login", userForm.getLogin())
                        .param("email", userForm.getEmail())
                        .param("password", userForm.getPassword())
                        .param("firstName", userForm.getFirstName())
                        .param("lastName", userForm.getLastName())
                        .param("birthday", userForm.getBirthday().toString())
                        .param("repassword", userForm.getPassword())
                        .param("role", userForm.getRole()))
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(model().attributeHasFieldErrors("user", "lastName"))
                .andExpect(model().attributeExists("user"))
                .andExpect(status().isOk()).andExpect(view().name("edit"))
                .andExpect(forwardedUrl("/WEB-INF/pages/edit.jsp"));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void adminCreate() throws Exception {
        mockMvc.perform(get("/admin/add")).andExpect(status().isOk())
                .andExpect(view().name("add"))
                .andExpect(forwardedUrl("/WEB-INF/pages/add.jsp"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void adminEdit() throws Exception {
        when(userDao.findById(2L)).thenReturn(user2);
        mockMvc.perform(get("/admin/edit?id=2")).andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(forwardedUrl("/WEB-INF/pages/edit.jsp"));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void adminEditWrongId() throws Exception {
        when(userDao.findById(1000L)).thenReturn(null);
        mockMvc.perform(get("/admin/edit?id=1000"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("/WEB-INF/pages/error.jsp"));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void adminDelete() throws Exception {
        when(userDao.findById(2L)).thenReturn(user2);
        mockMvc.perform(get("/admin/delete").param("id", "2"));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void adminDeleteWrongId() throws Exception {
        when(userDao.findById(1000L)).thenReturn(null);
        mockMvc.perform(get("/admin/delete").param("id", "1000"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("/WEB-INF/pages/error.jsp"));
    }
}