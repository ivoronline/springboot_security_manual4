package com.ivoronline.springboot_security_manual4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

  @Autowired AuthenticationManager authenticationManager;

  //========================================================================
  // AUTHENTICATE
  //========================================================================
  @RequestMapping("/Authenticate")
  public String authenticate(@RequestParam String username, @RequestParam String password) {

    //CREATE AUTHENTICATION OBJECT (with Entered Username & Password)
    Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

    //GET    AUTHENTICATION OBJECT (with Authorities)
    authentication = authenticationManager.authenticate(authentication);

    //CHECK AUTHENTICATION
    if(authentication == null) { return "User NOT Authenticated"; }

    //STORE  AUTHENTICATION OBJECT (into Context)
    SecurityContextHolder.getContext().setAuthentication(authentication);

    //RETURN SOMETHING
    return "User Authenticated";

  }

  //========================================================================
  // HELLO
  //========================================================================
  @Secured("ROLE_USER")
  @RequestMapping("/Hello")
  public String hello() {
    return "Hello from Controller";
  }

}
