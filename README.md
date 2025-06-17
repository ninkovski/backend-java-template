
# MS Base - Arquetipo para Microservicios - Contract First

## **Descripción**

Esta plantilla está preparada para crear microservicios Java con **Spring Boot** siguiendo el enfoque **Contract-First**. Toda la configuración necesaria ya está integrada para que solo tengas que reemplazar el archivo de contrato OpenAPI (`.yaml`) y generar automáticamente el código base (controladores, modelos, etc.).

Ideal para equipos que buscan mantener **consistencia en sus APIs**, aplicar **desarrollo guiado por contrato** y documentar de forma profesional desde el inicio.

## **Características Principales**
- Contrato Openapi para generar clases del controller
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
 ├   │   ├── openapi/        # Carpeta donde colocar tu contrato YAML
 ├   │   │   └── api-contract.yaml  # Reemplaza este archivo
 ├   │   └── application.yml
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
cd backend-java-template
```

### **2. Reemplaza el contrato OpenAPI**
Ubica tu archivo OpenAPI (por ejemplo `user-service.yaml`) en:
```bash
src/main/resources/openapi/api-contract.yaml
```

### **3. 🧬 Genera el código desde la terminal**
Usa [OpenAPI Generator CLI](https://openapi-generator.tech/) para generar automáticamente el código base:
```bash
openapi-generator-cli generate   -i src/main/resources/openapi/api-contract.yaml   -g spring   -o .   --additional-properties=interfaceOnly=true,useTags=true,skipDefaultInterface=true
```

### 🧠 Alternativa: Importar directamente desde IntelliJ IDEA

También puedes importar el contrato OpenAPI (`.yaml`) directamente desde IntelliJ IDEA:

1. Haz clic derecho en el archivo YAML → `OpenAPI → Generate Server Code`
2. Selecciona `Spring` como framework.
3. Elige la ruta de salida (`src/main/java`, por ejemplo).
4. IntelliJ generará automáticamente los controladores, modelos y las interfaces.

🔒 Asegúrate de que tienes instalado el plugin **OpenAPI Generator** en IntelliJ.

---

### **4. Construcción y ejecución**
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

### **5. Acceder a la API**
Para ambos puedes acceder a la API a través de

- Swagger UI: `http://localhost:8080/backend-service/v1/swagger-ui.html`
- Health Check: `http://localhost:8080/backend-service/v1/actuator/health`

## **Requisitos**
- **Java 17**
- **Maven 3+**
- **Docker (opcional para despliegue con contenedores)**
- **OpenAPI Generator CLI o plugin de IntelliJ IDEA**

### **Licencia**
Este proyecto es de código abierto y puede ser utilizado libremente para aprendizaje y desarrollo.


### **Tips**

- Puedes versionar los contratos YAML para asegurar compatibilidad.
- Ideal para CI/CD contract-testing.
- Puedes conectar esto con herramientas como Microcks o Spring Cloud Contract si quieres validar contratos en producción.