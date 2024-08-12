package com.subrutin.catalog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cloud")
public class CloudProperties {
  private String apiKeyCloud;

  public String getApiKeyCloud() {
    return apiKeyCloud;
  }

  public void setApiKeyCloud(String apiKeyCloud) {
    this.apiKeyCloud = apiKeyCloud;
  }

}
