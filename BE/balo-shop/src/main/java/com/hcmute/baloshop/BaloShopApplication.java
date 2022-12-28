package com.hcmute.baloshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class BaloShopApplication {

  public static void main(String[] args) {
    SpringApplication.run(BaloShopApplication.class, args);
  }
}
