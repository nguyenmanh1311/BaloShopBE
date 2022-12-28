package com.hcmute.baloshop.config;

import com.hcmute.baloshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationConfig {
  @Autowired
  private UserRepository userRepo;

  @Bean
  UserDetailsService userDetailsService() {
    return username -> userRepo
            .findByPhone(username)
            .orElseThrow(() -> new UsernameNotFoundException("User using phone number " + username + " does not exist"));
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
          throws Exception {
    return authConfig.getAuthenticationManager();
  }
}
