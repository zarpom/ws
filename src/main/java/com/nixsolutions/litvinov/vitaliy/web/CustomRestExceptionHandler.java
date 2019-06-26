package com.nixsolutions.litvinov.vitaliy.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView redirectToErrorPage(Exception e) {
        ModelAndView mav = new ModelAndView("error");
        mav.getModelMap().addAttribute("message", e.getMessage());
        return mav;
    }
}