package org.development.springredisassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SpringRedisAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisAssignmentApplication.class, args);

    }

}
