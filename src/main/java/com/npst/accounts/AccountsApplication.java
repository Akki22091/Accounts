package com.npst.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(info = @Info(
        title = "Accounts Microservice REST API Documentation",
        version = "1.0",
        description = "Accounts Microservice",

        contact = @Contact(
                name = "Akash Waghmare",
                email = "K7Kt1@example.com",
                url = "https://www.npst.com"
        ),

        license = @License(
                name = "Apache 2.0",
                url = "https://www.npst.com"
        )
),
        externalDocs = @ExternalDocumentation(
                description = "Accounts Microservice Documentation",
                url = "https://www.npst.com"
        )
)
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
