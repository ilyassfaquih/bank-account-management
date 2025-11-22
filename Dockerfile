# Étape 1 : Build Maven
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copier le pom et télécharger les dépendances en cache
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copier le code source
COPY src ./src

# Build du JAR (sans tests pour aller plus vite)
RUN mvn -B clean package -DskipTests

# Étape 2 : Image runtime (plus légère)
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copier le JAR généré depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Port exposé par Spring Boot (par défaut)
EXPOSE 8080

# Lancement de l’app
ENTRYPOINT ["java", "-jar", "app.jar"]
