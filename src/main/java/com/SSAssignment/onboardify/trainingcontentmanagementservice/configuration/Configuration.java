package com.SSAssignment.onboardify.trainingcontentmanagementservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "training-content-service")
public class Configuration {

}
