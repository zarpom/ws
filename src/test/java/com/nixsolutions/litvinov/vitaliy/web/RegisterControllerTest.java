package com.nixsolutions.litvinov.vitaliy.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Date;

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
import com.nixsolutions.litvinov.vitaliy.security.CaptchaUtils;
import com.nixsolutions.litvinov.vitaliy.util.validation.dto.Converter;
import com.nixsolutions.litvinov.vitaliy.util.validation.dto.UserAddForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context-mock-config.xml")
@WebAppConfiguration
public class RegisterControllerTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CaptchaUtils captchaUtils;

    @Autowired
    private Converter converter;

    private MockMvc mockMvc;

    private User userValid = new User(2L, "Illia", "Darie", "lon@mail.com",
            "Ron", "Vels", Date.valueOf("1988-11-21"), new Role(2L, "user"));

    @Before
    public void setup() {
        Mockito.reset(userDao);
        Mockito.reset(captchaUtils);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @WithMockUser
    public void register() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));

    }

    @Test
    @WithMockUser(roles = "admin")
    public void registerAdmin() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    public void registerUserNotValid() throws Exception {
        Role role = new Role();
        role.setName("admin");
        User user = new User(2L, "Karl", "Ron", "mail1", "", "",
                Date.valueOf("1812-11-12"), role);
        UserAddForm userAddForm = converter.convertToAddFormUser(user);

        mockMvc.perform(
                post("/registration").param("login", userAddForm.getLogin())
                        .param("email", userAddForm.getEmail())
                        .param("password", userAddForm.getPassword())
                        .param("firstName", userAddForm.getFirstName())
                        .param("lastName", userAddForm.getLastName())
                        .param("birthday", userAddForm.getBirthday().toString())
                        .param("repassword", userAddForm.getRepassword())
                        .param("role", userAddForm.getRole()))
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(model().attributeHasFieldErrors("user", "firstName"))
                .andExpect(model().attributeHasFieldErrors("user", "lastName"))
                .andExpect(model().attributeExists("user"))
                .andExpect(status().isOk()).andExpect(view().name("registr"))
                .andExpect(forwardedUrl("/WEB-INF/pages/registr.jsp"));
    }

    @Test
    public void adminRegisterUserValid() throws Exception {

        UserAddForm userAddForm = converter.convertToAddFormUser(userValid);
        when(captchaUtils.verify(any())).thenReturn(true);
        mockMvc.perform(
                post("/registration").param("login", userAddForm.getLogin())
                        .param("email", userAddForm.getEmail())
                        .param("password", userAddForm.getPassword())
                        .param("firstName", userAddForm.getFirstName())
                        .param("lastName", userAddForm.getLastName())
                        .param("birthday", userAddForm.getBirthday().toString())
                        .param("repassword", userAddForm.getPassword())
                        .param("role", userAddForm.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(captchaUtils).verify(any());
    }

    @Test
    @WithMockUser(roles = "admin")
    public void adminRegisterUserCaptchaError() throws Exception {
        UserAddForm userAddForm = converter.convertToAddFormUser(userValid);

        when(captchaUtils.verify(any())).thenReturn(false);
        mockMvc.perform(post("/register").param("login", userAddForm.getLogin())
                .param("email", userAddForm.getEmail())
                .param("password", userAddForm.getPassword())
                .param("firstName", userAddForm.getFirstName())
                .param("lastName", userAddForm.getLastName())
                .param("birthday", userAddForm.getBirthday().toString())
                .param("repassword", userAddForm.getPassword())
                .param("role", userAddForm.getRole()))
                .andExpect(status().is4xxClientError());
    }

}