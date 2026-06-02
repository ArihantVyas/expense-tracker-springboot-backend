package com.arihant.expense_tracker.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig{

    @Bean
    public OpenAPI expenseTrackerOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Expense tracker API")
                        .description("Expense tracker backend")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Arihant Vyas")
                                .email("arihantvyas.new.github@gmail.com")
                        )
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("Bearer Authentication")
                )
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                );
    }
}
