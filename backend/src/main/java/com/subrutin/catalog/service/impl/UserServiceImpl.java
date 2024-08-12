package com.subrutin.catalog.service.impl;

import org.springframework.stereotype.Service;

import com.subrutin.catalog.config.UserProperties;
import com.subrutin.catalog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  private final UserProperties userProperties;

  public UserServiceImpl(UserProperties userProperties) {
    this.userProperties = userProperties;
  }

  @Override
  public String userId() {
    return userProperties.getUserId();
  }

}
