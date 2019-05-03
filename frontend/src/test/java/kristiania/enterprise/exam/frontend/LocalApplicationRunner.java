package kristiania.enterprise.exam.frontend;

import kristiania.enterprise.exam.Application;
import org.springframework.boot.SpringApplication;

class LocalApplicationRunner {

    public static void main(String[] args) {
        // specifies to use test-configuration
        SpringApplication.run(Application.class, "--spring.profiles.active=test");

    }
}