package com.hcmute.baloshop.config;

import com.hcmute.baloshop.filter.JwtTokenFilter;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.repositories.UserRepository;
import com.hcmute.baloshop.utils.MapHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfiguration {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private JwtTokenFilter jwtTokenFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests()
            .antMatchers("/api/v1/auth/*", "/docs/**", "/swagger-ui/index.html#/")
            .permitAll()
            .anyRequest()
            .permitAll();
    http.exceptionHandling()
            .accessDeniedHandler(
                    (request, response, ex) -> {
                      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                      response.setStatus(HttpServletResponse.SC_OK);

                      Map<String, Object> map = new HashMap<String, Object>();
                      ResponseObject responseObject =
                              new ResponseObject(HttpStatus.UNAUTHORIZED, ex.getMessage());
                      map = MapHelper.convertObject(responseObject);
                    });
    http.exceptionHandling()
            .authenticationEntryPoint(
                    (request, response, ex) -> {
                      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                      response.setStatus(HttpServletResponse.SC_OK);

                      Map<String, Object> map = new HashMap<String, Object>();
                      ResponseObject responseObject =
                              new ResponseObject(HttpStatus.UNAUTHORIZED, ex.getMessage());
                      map = MapHelper.convertObject(responseObject);

                    });
    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

}
