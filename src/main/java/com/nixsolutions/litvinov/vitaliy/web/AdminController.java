package com.nixsolutions.litvinov.vitaliy.web;

import com.nixsolutions.litvinov.vitaliy.dao.RoleDao;
import com.nixsolutions.litvinov.vitaliy.dao.UserDao;
import com.nixsolutions.litvinov.vitaliy.entity.User;
import com.nixsolutions.litvinov.vitaliy.util.validation.dto.Converter;
import com.nixsolutions.litvinov.vitaliy.util.validation.dto.UserAddForm;
import com.nixsolutions.litvinov.vitaliy.util.validation.dto.UserEditForm;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
    @Autowired
    private UserDao userDao;

    @Autowired
    Converter converter;
    @Autowired
    private RoleDao roleDao;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin(ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        modelMap.addAttribute("currentUser",
                userDao.findByLogin(authentication.getName()));
        modelMap.addAttribute("users", userDao.findAll());

        return new ModelAndView("admin", modelMap);
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.GET)
    public ModelAndView addPageGet(ModelMap modelMap) {

        modelMap.addAttribute("roles", roleDao.findAll());
        modelMap.addAttribute("user", new UserAddForm());
        return new ModelAndView("add", modelMap);
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public String addPagePost(ModelMap modelMap,
            @ModelAttribute("user") @Valid UserAddForm user,
            BindingResult result) {

        if (result.hasErrors()) {
            return "add";
        }

        converter.setRoleDao(roleDao);
        converter.convertAddFormUserToDbUser(user);
        userDao.create(converter.convertAddFormUserToDbUser(user));

        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/delete", method = RequestMethod.GET)
    public String delete(Model model, Long id) {
        User user = userDao.findById(id);

        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        String login = auth.getName();
        User currentUser = userDao.findByLogin(login);

        if (currentUser.getId() == id) {
            throw new RuntimeException("You tried delete yourself!");
        }
        userDao.remove(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
    public ModelAndView editGet(ModelMap modelMap, Long id) {
        User user = userDao.findById(id);
        modelMap.addAttribute("user", converter.convertToEditFormUser(user));
        return new ModelAndView("edit", modelMap);
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    public String editPagePost(ModelMap modelMap,
            @ModelAttribute("user") @Valid UserEditForm user,
            BindingResult result, Long id) {

        if (result.hasErrors()) {
            return "edit";
        }

        converter.setRoleDao(roleDao);
        converter.convertEditFormUserToDbUser(user);
        System.out.println(converter.convertEditFormUserToDbUser(user));
        userDao.update(converter.convertEditFormUserToDbUser(user));

        return "redirect:/admin";
    }
}
