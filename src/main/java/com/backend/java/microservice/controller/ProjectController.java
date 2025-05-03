package com.backend.java.microservice.controller;

import com.backend.java.microservice.model.ProjectInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    @Value("${spring.profiles.active:local}")
    private String environment;

    @Value("${spring.application.name:backend-service}")
    private String appName;

    @Value("${app.version:1.0.0}")
    private String version;

    @Operation(
            summary = "Obtiene la información del proyecto",
            description = "Devuelve el entorno, el nombre de la aplicación y la versión.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información del proyecto obtenida con éxito",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProjectInfoResponse.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/info", produces = "application/json")
    public ResponseEntity<ProjectInfoResponse> getProjectInfo() {
        ProjectInfoResponse response = ProjectInfoResponse.builder()
                .environment(environment)
                .appName(appName)
                .version(version)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)   // Establece el código de estado HTTP 200
                .body(response);         // Devuelve el cuerpo de la respuesta
    }
}
