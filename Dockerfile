# Etapa 1: Construcción del proyecto con Maven
FROM maven:3.8.8-eclipse-temurin-17 AS build

# Definir argumentos con valores por defecto
ARG APP_NAME=backend-service
ARG APP_PORT=8080

# Definir el directorio de trabajo
WORKDIR /app

# Copiar el archivo de dependencias y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Copiar README.md para documentación
COPY README.md .

# Compilar sin ejecutar pruebas
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final optimizada con JDK
FROM eclipse-temurin:17-jdk-alpine

# Definir argumentos (se volverán variables de entorno)
ARG APP_NAME=backend-service
ARG APP_PORT=8080
ENV APP_NAME=${APP_NAME}
ENV APP_PORT=${APP_PORT}

# Definir directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR desde la etapa anterior (se asume que el jar tiene el mismo nombre que el APP_NAME)
COPY --from=build /app/target/*.jar "$APP_NAME.jar"
# Copiar README.md desde la etapa anterior
COPY --from=build /app/README.md /app/README.md
# Exponer el puerto de la aplicación
EXPOSE ${APP_PORT}

# Comando de inicio del microservicio
ENTRYPOINT ["sh", "-c", "java -jar $APP_NAME.jar"]
