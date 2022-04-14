package org.javaschool.data.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class DataBaseProperties {

    @Value("${DB_URL}")
    private String url;
    @Value("${DB_NAME}")
    private String name;
    @Value("${DB_USERNAME}")
    private String user;
    @Value("${DB_PASSWORD}")
    private String pass;
    @Value("${DB_DRIVER_CLASS}")
    private String driver;

}
