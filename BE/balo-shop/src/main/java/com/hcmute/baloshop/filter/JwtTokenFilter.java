package com.hcmute.baloshop.filter;

import com.hcmute.baloshop.entities.User;
import com.hcmute.baloshop.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.io.IOException;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtTokenUtil jwtUtil;

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    //If the Authorization header of the request doesn’t contain a Bearer token,
    //it continues the filter chain without updating authentication context.
    if (!hasAuthorizationBearer(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    //Else, if the token is not verified, continue the filter chain without updating authentication context.
    String token = getAccessToken(request);
    if (!jwtUtil.validateAccessToken(token)) {
      filterChain.doFilter(request, response);
      return;
    }

    //If the token is verified, update the authentication context with the user details ID and email.
    //In other words, it tells Spring that the user is authenticated, and continue the downstream filters.
    setAuthenticationContext(token, request);
    filterChain.doFilter(request, response);
  }

  private boolean hasAuthorizationBearer(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
      return false;
    }
    return true;
  }

  private String getAccessToken(HttpServletRequest request) {
    String token = null;
    try {
      String header = request.getHeader("Authorization");
      token = header.split(" ")[1].trim();
    } catch (ArrayIndexOutOfBoundsException ex) {
      log.error("Bearer is null", ex.getMessage());
    }
    return token;
  }

  private void setAuthenticationContext(String token, HttpServletRequest request) {
    UserDetails userDetails = getUserDetails(token);
    UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private UserDetails getUserDetails(String token) {
    User userDetails = new User();
    String[] jwtSubject = jwtUtil.getSubject(token).split(",");
    userDetails = (User) userDetailsService.loadUserByUsername(jwtSubject[0]);
    return userDetails;
  }
}
