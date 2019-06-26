package com.nixsolutions.litvinov.vitaliy.web;

import com.nixsolutions.litvinov.vitaliy.dao.RoleDao;
import com.nixsolutions.litvinov.vitaliy.dao.UserDao;
import com.nixsolutions.litvinov.vitaliy.security.CaptchaUtils;
import com.nixsolutions.litvinov.vitaliy.util.validation.dto.Converter;
import com.nixsolutions.litvinov.vitaliy.util.validation.dto.UserAddForm;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RegistrController {

    @Autowired
    private Converter converter;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CaptchaUtils captchaUtils;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView addPageGet(ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return new ModelAndView("redirect:/home");
        }
        modelMap.addAttribute("user", new UserAddForm());
        return new ModelAndView("registr", modelMap);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView addPagePost(ModelMap modelMap,
            @ModelAttribute("user") @Valid UserAddForm user,
            BindingResult result, HttpServletRequest request) {

        String googleRecaptchaResponse = request
                .getParameter("g-recaptcha-response");
        if (!captchaUtils.verify(googleRecaptchaResponse)) {
            modelMap.put("captchaError", "Captcha invalid!");
            return new ModelAndView("registr");
        }

        if (result.hasErrors()) {
            return new ModelAndView("registr");
        }

        converter.setRoleDao(roleDao);
        converter.convertAddFormUserToDbUser(user);
        userDao.create(converter.convertAddFormUserToDbUser(user));

        return new ModelAndView("redirect:/", modelMap);
    } 
} 
