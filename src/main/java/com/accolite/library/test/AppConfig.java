package com.accolite.library.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.accolite.library.*"})
public class AppConfig {
}