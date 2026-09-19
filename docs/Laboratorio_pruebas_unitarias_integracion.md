# Laboratorio: Pruebas Unitarias e Integración

## Objetivo

Entender y afianzar los conceptos de pruebas unitarias y de integración mediante refactorización y análisis estático en Java/Spring Boot.

## Enunciado resumido

Se trabajó con el servicio REST del módulo de préstamos del Banco Pascual Bravo y se validó lo siguiente:

1. Análisis estático y refactorización.
   - Se revisó la clase `PrestamoService`.
   - Se identificaron problemas de:
     - Complejidad cognitiva.
     - Bucles ineficientes.
   - Se refactorizó el código sin afectar la lógica matemática del negocio.

2. Pruebas unitarias.
   - Se crearon pruebas para validar rechazo de créditos mayores a 50.000.
   - Tasa preferencial para clientes `PREMIUM`.
   - Tasa para `REGULAR` y resto de clientes.
   - Excepciones para cliente nulo y monto negativo.

3. Persistencia con H2.
   - Se usó `@DataJpaTest`.
   - Se validaron consultas personalizadas del repositorio.

4. Pruebas de integración.
   - Se utilizó `@SpringBootTest` y `MockMvc`.
   - Se validó el endpoint `/api/prestamos/solicitar` con respuesta HTTP 200 y JSON esperado.

## Entregables

- Código fuente refactorizado.
- Suite de pruebas en `src/test/java`.
- Cobertura superior al 80%.
- Evidencias y análisis estático con SonarQube.

## Cumplimiento

El proyecto quedó compilando y validado con pruebas, además de cumplir con la métrica de cobertura y calidad establecida por el laboratorio.
