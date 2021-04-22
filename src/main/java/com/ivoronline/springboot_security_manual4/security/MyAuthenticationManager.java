package com.ivoronline.springboot_security_manual4.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyAuthenticationManager implements AuthenticationManager {

  @Autowired UserDetailsService userDetailsService;

  @Override
  public Authentication authenticate(Authentication enteredAuthentication) {

    //GET ENTERED CREDENTIALS
    String enteredUsername = (String) enteredAuthentication.getPrincipal();   //USERNAME
    String enteredPassword = (String) enteredAuthentication.getCredentials(); //PASSWORD

    //GET USER DETAILS
    UserDetails userDetails = userDetailsService.loadUserByUsername(enteredUsername);

    //CHECK USER DETAILS
    if ( userDetails == null                              ) { log.error("Username not found"); return null; }
    if (!enteredPassword.equals(userDetails.getPassword())) { log.error("Incorrect Password"); return null; }

    //CREATE VALIDATED AUTHENTICATION OBJECT
    Authentication authentication = new UsernamePasswordAuthenticationToken(
      enteredUsername,
      enteredPassword ,
      userDetails.getAuthorities()
    );

    //RETURN AUTHENTICATION OBJECT
    return authentication;

  }

}

