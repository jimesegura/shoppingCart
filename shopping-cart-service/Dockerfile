# Usa una imagen base de Java
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado
COPY target/shopping-cart-service-1.0-SNAPSHOT.jar shopping-cart-service.jar

# Expone los puertos necesarios
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "shopping-cart-service.jar"]
