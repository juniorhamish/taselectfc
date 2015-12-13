package com.taselectfc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    Application() {
        // Hide the implicit constructor.
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
