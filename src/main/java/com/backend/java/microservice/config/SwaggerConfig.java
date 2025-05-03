package com.backend.java.microservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@Slf4j
public class SwaggerConfig {

    @Value("${spring.application.name:Backend Service}")
    private String applicationName;
    @Value("${app.version:1.0.0}")
    private String version;
    @Bean
    public OpenAPI customOpenAPI() {
        // Leer el contenido del archivo README.md
        String readmeContent = "Descripción no disponible"; // Valor por defecto
        Path path = Paths.get("README.md");

        if (Files.exists(path)) {
            try {
                readmeContent = new String(Files.readAllBytes(path));
            } catch (IOException e) {
                log.error("No se pudo leer el archivo README.md: " + e.getMessage());
            }
        } else {
            log.error("No se encontró README.md, usando descripción por defecto.");
        }

        return new OpenAPI()
                .info(new Info()
                        .title(applicationName)  // Obtiene el nombre de la aplicación desde application.yml
                        .version(version)
                        .description(readmeContent)  // Insertar el contenido del README aquí
                        .license(new License().name("By Ninkovski").url("https://github.com/ninkovski")));
    }
}
