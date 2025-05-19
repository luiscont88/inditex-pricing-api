package com.inditex.store.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI 3 Configuration for Swagger UI.
 * Accessible at /swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

    @Value("${inditex.store.openapi.dev-url:http://localhost:8080}")
    private String devUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        // Server definition
        Server devServer = new Server()
                .url(devUrl)
                .description("Development environment");

        // Contact info
        Contact contact = new Contact()
                .name("Luis Contreras")
                .url("https://www.inditex.com/itxcomweb/es/es/home")
                .email("luis.contreras.20@gmail.com");

        // License info
        License license = new License()
                .name("MIT License")
                .url("https://www.inditex.com/itxcomweb/es/es/informacion/legal");

        // General info
        Info info = new Info()
                .title("Inditex Pricing API")
                .version("1.0")
                .description("""
                        API REST desarrollada como parte del proceso técnico de selección para Inditex.
                        Permite consultar el precio aplicable de productos según fecha, marca y tarifa.
                        """)
                .termsOfService("https://www.inditex.com/itxcomweb/es/es/informacion/politica-de-privacidad")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}
