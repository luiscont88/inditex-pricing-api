# Imagen base con JDK 21
FROM eclipse-temurin:21-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR construido al contenedor
COPY target/store.jar app.jar

# Exponer el puerto de la app
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
