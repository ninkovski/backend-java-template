# MS Base - Arquetipo para Microservicios

## Descripción
Proyecto base para aprender a construir microservicios en Java con Spring Boot. Incluye:
- Documentación automática con Swagger (springdoc OpenAPI).
- Precarga de datos desde un JSON de ejemplo (cv-ejemplo.json) usando JPA/H2.
- Validación de API Key mediante un filtro HTTP (X-API-KEY).
- Ejemplos de DTOs y entidades JPA para un currículum (Profile, Experience, Skill).

## Requisitos
- Java 17
- Maven

## Ejecutar la aplicación
1. Compilar y ejecutar:
   mvn -DskipTests package
   mvn spring-boot:run

2. Base URL de la API:
   http://localhost:8080/backend-service/v1

## Endpoints principales
- GET /api/resume
  - Devuelve el ProfileDTO como JSON (perfil, experiencias, skills).
  - Requiere header HTTP X-API-KEY.

## Swagger UI (documentación)
1. Abrir:
   http://localhost:8080/backend-service/v1/swagger-ui.html
2. Para probar endpoints protegidos por X-API-KEY:
   - Hacer clic en "Authorize" (candado) en la UI.
   - Introducir la API key (valor definido en application.yaml -> app.security.api-key).
   - Después de autorizar, las llamadas desde Swagger incluirán el header X-API-KEY.

## Ejemplos curl
- Petición válida (reemplaza my-secret-key por la configured en application.yaml):
  curl -H "X-API-KEY: my-secret-key" http://localhost:8080/backend-service/v1/api/resume

- Petición sin key (respuesta 401 con JSON de error):
  curl http://localhost:8080/backend-service/v1/api/resume

## Configuración importante
- API key esperada:
  - application.yaml -> app.security.api-key
- CORS (orígenes permitidos para Swagger / frontends):
  - application.yaml -> app.cors.allowed-origins (por ejemplo "http://localhost:3000")

## Datos de ejemplo y carga inicial
- El archivo `src/main/resources/cv-ejemplo.json` contiene un currículum de ejemplo.
- Al iniciar la aplicación, un componente (ResumeDataLoader) parsea el JSON y lo persiste en la base de datos H2 usando JPA.
- Puedes inspeccionar las tablas en H2 Console (si la habilitas) o mediante queries en código.

## Estructura educativa (qué revisar en el código)
- DTOs: `com.backend.java.microservice.model.dto` (ProfileDTO, ExperienceDTO, SkillDTO)
- Entidades JPA: `com.backend.java.microservice.model.entity` (ProfileEntity, ExperienceEntity, SkillEntity, ExperienceHighlightEntity)
- Repositorio: `com.backend.java.microservice.repository.ProfileRepository`
- Servicio: `com.backend.java.microservice.service.ResumeService` (mappea entidades a DTOs)
- Precarga: `com.backend.java.microservice.service.ResumeDataLoader`
- Seguridad: `com.backend.java.microservice.security.ApiKeyFilter`
- Documentación OpenAPI: `com.backend.java.microservice.config.SwaggerConfig`

## Notas para la clase
- Recomendación: reimportar el proyecto Maven en el IDE después de modificar `pom.xml`.
- Para desarrollo rápido puedes activar `spring.jpa.hibernate.ddl-auto: update` en application.yaml.
- Si se usan cambios en seguridad (Spring Security), ajustar el orden y exposición de endpoints de Swagger.

---
Si necesitan que agregue un ejercicio guía (por ejemplo: crear un endpoint POST para agregar skills y una prueba de integración), lo incorporo al README con pasos y tests de ejemplo.
