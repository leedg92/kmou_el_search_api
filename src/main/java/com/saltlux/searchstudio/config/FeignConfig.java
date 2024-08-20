package com.saltlux.searchstudio.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.saltlux.searchstudio")
public class FeignConfig {

}
