package org.javaschool.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"org.javaschool.service", "org.javaschool.rest"})
@Configuration
public class ConfigWeb {
}
