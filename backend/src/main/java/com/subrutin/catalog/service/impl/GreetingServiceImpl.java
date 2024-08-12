package com.subrutin.catalog.service.impl;

import java.util.TimeZone;

import org.springframework.stereotype.Service;

import com.subrutin.catalog.config.ApplicationProperties;
import com.subrutin.catalog.config.CloudProperties;
import com.subrutin.catalog.service.GreetingService;

@Service
public class GreetingServiceImpl implements GreetingService {

  private final ApplicationProperties appProperties;
  private final CloudProperties cloudProperties;

  public GreetingServiceImpl(ApplicationProperties appProperties, CloudProperties cloudProperties) {
    this.appProperties = appProperties;
    this.cloudProperties = cloudProperties;
  }

  @Override
  public String sayGreeting() {
    var currentTimeZone = TimeZone.getTimeZone(appProperties.getTimezone());

    return appProperties.getWelcomeText() + ", our timezone " + currentTimeZone.getDisplayName() + ", our currency "
        + appProperties.getCurrency() + " cloud apikey: " + cloudProperties.getApiKeyCloud();
  }

}
