# Evidencias de pruebas

## Cobertura

La cobertura generada por JaCoCo fue validada con el comando:

```bash
./mvnw test jacoco:report
```

Resultado obtenido:

- Cobertura de líneas: 88.8%
- Cobertura de clases: 80.0%

Reporte disponible en:

```text
target/site/jacoco/index.html
```

## Tipos de pruebas realizadas

### 1. Unitarias

- Rechazo de créditos superiores a 50.000.
- Tasa preferencial 3.5% para clientes `PREMIUM`.
- Tasa 5.0% para clientes `REGULAR`.
- Tasa 6.0% para clientes restantes.
- Validación de cliente nulo.
- Validación de monto negativo.
- Cálculo de descuentos de cartera.

### 2. Persistencia

- Inserción de 3 préstamos simulados.
- Validación de `findByEstado` con H2.

### 3. Integración

- Simulación de solicitud HTTP a `/api/prestamos/solicitar`.
- Verificación de 200 OK y JSON esperado.

## Resultados

La suite actual fue ejecutada exitosamente con Maven y la aplicación responde adecuadamente a las solicitudes de negocio.
