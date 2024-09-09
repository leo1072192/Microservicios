# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR del microservicio al contenedor
COPY target/clientes-personas-0.0.1-SNAPSHOT.jar /app/clientes-personas-0.0.1-SNAPSHOT.jar

# Exponer el puerto del contenedor (cambiar si es necesario)
EXPOSE 8083

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/clientes-personas.jar"]
