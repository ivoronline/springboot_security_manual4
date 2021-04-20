package com.ivoronline.springboot_security_manual4.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MyAuthenticationManager implements AuthenticationManager {

  @Autowired UserDetailsService userDetailsService;

  @Override
  public Authentication authenticate(Authentication enteredAuthentication) {

    //GET ENTERED CREDENTIALS
    String enteredUsername = (String) enteredAuthentication.getPrincipal();   //ENTERED USERNAME
    String enteredPassword = (String) enteredAuthentication.getCredentials(); //ENTERED PASSWORD

    //GET USER DETAILS
    UserDetails           userDetails    = userDetailsService.loadUserByUsername(enteredUsername);
    String                storedPassword = userDetails.getPassword();
    Set<GrantedAuthority> authorities    = (Set<GrantedAuthority>) userDetails.getAuthorities();

    System.out.println("enteredPassword = " + enteredPassword);
    System.out.println("storedPassword = " + storedPassword);

    //AUTHENTICATE USER (Compare Entered and Stored Password)
    if (!enteredPassword.equals(storedPassword)) { System.out.println("Incorrect Password"); return null; }

    //CREATE VALIDATED AUTHENTICATION
    Authentication authentication = new UsernamePasswordAuthenticationToken(enteredUsername, null, authorities);

    //RETURN VALIDATED AUTHENTICATION
    return authentication;

  }

}

