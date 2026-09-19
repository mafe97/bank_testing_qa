# Banco Pascual Bravo - Préstamos Service

Microservicio de préstamos desarrollado con Java 17 y Spring Boot para el laboratorio de pruebas unitarias e integración del Banco Pascual Bravo.

## Objetivo

Aplicar buenas prácticas de refactorización, pruebas unitarias, pruebas de persistencia con H2 y pruebas de integración con MockMvc, siguiendo el enunciado del taller.

## Stack

- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- H2 Database
- JUnit 5
- Mockito
- JaCoCo
- SonarQube + SonarLint

## Estructura del proyecto

```text
src/
├── main/
│   ├── java/co/edu/pascualbravo/banco/
│   │   ├── controller/PrestamoController.java
│   │   ├── model/Prestamo.java
│   │   ├── repository/PrestamoRepository.java
│   │   ├── service/PrestamoService.java
│   │   └── exception/
│   └── resources/application.properties
├── test/
│   └── java/co/edu/pascualbravo/banco/
│       ├── controller/PrestamoControllerTest.java
│       ├── repository/PrestamoRepositoryTest.java
│       ├── service/PrestamoServiceTest.java
│       └── BancoApplicationTests.java
```

## Requisitos

- Java 17+
- Maven Wrapper incluido (`./mvnw`)
- Docker (para ejecutar SonarQube localmente)

## Ejecutar la aplicación

```bash
cd /home/gtic/Documents/banco
export JAVA_HOME=/ruta/al/jdk-17
export PATH="$JAVA_HOME/bin:$PATH"
./mvnw spring-boot:run
```

## Ejecutar pruebas

```bash
./mvnw test
```

## Generar cobertura

```bash
./mvnw test jacoco:report
```

Reporte generado en:

```text
target/site/jacoco/index.html
```

## Analizar calidad con SonarQube

1. Levantar SonarQube local con Docker:

```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:lts-community
```

2. Abrir:

```text
http://localhost:9000
```

3. Ejecutar análisis:

```bash
./mvnw org.sonarsource.scanner.maven:sonar-maven-plugin:3.10.0.2594:sonar \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=admin \
  -Dsonar.password=admin \
  -Dsonar.projectKey=prestamos-service \
  -Dsonar.projectName=PrestamosService
```

## Cobertura lograda

La cobertura actual validada por JaCoCo alcanza aproximadamente 88.8% de líneas, cumpliendo con el objetivo del laboratorio de al menos 80%.

## Documentación adicional

La documentación del laboratorio y sus evidencias se encuentra en la carpeta `docs/`.
