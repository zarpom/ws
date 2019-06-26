package com.nixsolutions.litvinov.vitaliy.web;

import com.nixsolutions.litvinov.vitaliy.util.validation.dto.Converter;
import com.nixsolutions.litvinov.vitaliy.util.validation.dto.Login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LoginController {

    @Autowired
    Converter converter;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginGet(Model model, Boolean error) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        error = error == null ? false : true;

        if (error) {
            model.addAttribute("error", "Invalid username or password!");
        }
        model.addAttribute("login", new Login());
        model.addAttribute("command", new Login());

        return "index";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String role;
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                role = authority.getAuthority();
                if (role.equals("ROLE_admin")) {
                    return "redirect:/admin";
                }
                if (role.equals("ROLE_user")) {
                    modelMap.addAttribute("userName", authentication.getName());
                    return "user";
                }
            }
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request,
            HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            new SecurityContextLogoutHandler().logout(request, response,
                    authentication);
        }
        return "redirect:/";
    }

}
