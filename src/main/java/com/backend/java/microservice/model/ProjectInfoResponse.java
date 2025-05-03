package com.backend.java.microservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectInfoResponse {

    @Schema(description = "Entorno en el que se está ejecutando la aplicación", example = "dev")
    private String environment;

    @Schema(description = "Nombre de la aplicación", example = "backend-service")
    private String appName;

    @Schema(description = "Versión del servicio", example = "1.0")
    private String version;
}