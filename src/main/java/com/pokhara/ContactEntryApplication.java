package com.pokhara;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pokhara"})
public class ContactEntryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactEntryApplication.class, args);
    }

}
