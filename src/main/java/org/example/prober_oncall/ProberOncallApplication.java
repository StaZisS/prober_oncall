package org.example.prober_oncall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProberOncallApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProberOncallApplication.class, args);
    }

}
