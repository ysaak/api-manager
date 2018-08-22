package ysaak.apimanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootApplication
public class ApiManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiManagerApplication.class, args);
    }
}
