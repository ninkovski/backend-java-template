
# MS Base - Arquetipo para Microservicios

## **Descripción**
Este proyecto sirve como un arquetipo base para la creación de microservicios en Java con Spring Boot. Está diseñado para proporcionar a los desarrolladores una estructura inicial robusta y optimizada que facilite la construcción de aplicaciones distribuidas de manera rápida y escalable. Con configuraciones predefinidas y buenas prácticas incorporadas, como seguridad integrada, documentación automática de la API mediante Swagger, y monitoreo a través de Health Check, este proyecto base es ideal para cualquier equipo que quiera implementar microservicios con una arquitectura limpia y eficiente.

## **Características Principales**
- Configuración centralizada con **Spring Cloud Config Server**.
- Documentación de APIs con **Springdoc OpenAPI (Swagger UI)**.
- Endpoint para obtener información del proyecto (ambiente, ruta de OpenAPI, etc.).
- Health Check para monitoreo del estado de la aplicación.

## **Estructura del Proyecto**
```plaintext
/src
 ├── main
 │   ├── java/com/bootcamp/api
 │   │   ├── config/       # Configuración del proyecto 
 │   │   ├── controller/   # Controladores REST
 │   │   ├── service/      # Lógica de negocio
 │   │   ├── repository/   # Acceso a datos
 │   │   ├── model/        # Entidades y DTOs
 │   │   ├── util/         # Clases de utilidad
 │   ├── resources
 │   │   ├── application.yml  # Configuración principal
 ├── test/                    # Pruebas unitarias
```

## **Endpoints Iniciales**
| Método | Endpoint            | Descripción |
|--------|---------------------|-------------|
| GET    | `/actuator/health`  | Verifica el estado del servicio. |
| GET    | `/api/info`         | Devuelve información sobre el entorno del proyecto. |
| GET    | `/swagger-ui.html`  | Accede a la documentación de OpenAPI. |

## **Configuración y Ejecución**
### **1. Clonar el repositorio**
```bash
git clone https://github.com/ninkovski/backend-java-template.git
cd bootcamp-api-bank
```

### **2. Construcción y ejecución**
```bash
mvn clean install
mvn spring-boot:run
```
Si prefieres ejecutar la aplicación dentro de un contenedor Docker, puedes seguir estos pasos:

#### **Construir la imagen de Docker**
Ejecuta el siguiente comando en la raíz del proyecto para construir la imagen Docker. Asegúrate de que el Dockerfile esté configurado correctamente:

```bash
docker build --build-arg APP_NAME=backend-service --build-arg APP_PORT=8080 -t backend-service .
docker run -e APP_NAME=backend-service -e APP_PORT=8080 -p 8080:8080 backend-service
```

### **3. Acceder a la API**
Para ambos puedes acceder a la API a través de

- Swagger UI: `http://localhost:8080/backend-service/v1/swagger-ui.html`
- Health Check: `http://localhost:8080/backend-service/v1/actuator/health`

## **Requisitos**
- **Java 17**
- **Maven 3+**
- **Docker (opcional para despliegue con contenedores)**

## **Licencia**
Este proyecto es de código abierto y puede ser utilizado libremente para aprendizaje y desarrollo.
