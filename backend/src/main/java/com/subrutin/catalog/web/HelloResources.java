package com.subrutin.catalog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subrutin.catalog.service.GreetingService;
import com.subrutin.catalog.service.UserService;

@RestController
public class HelloResources {

  private final GreetingService greetingService;
  private final UserService userService;
  private final Logger log = LoggerFactory.getLogger(HelloResources.class);

  @Autowired
  public HelloResources(GreetingService greetingService, UserService userService) {
    this.greetingService = greetingService;
    this.userService = userService;
  }

  @GetMapping("/api/hello")
  public String helloWorld() {
    log.trace("logger TRACE");
    log.debug("logger DEBUG");
    log.info("logger INFO");
    log.warn("logger WARN");
    log.error("logger ERROR");
    return greetingService.sayGreeting();
  }

  @GetMapping("/api/user")
  public String apiUser() {
    return userService.userId();
  }
}
