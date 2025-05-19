# 🛍️ Inditex Core Platform Pricing API

Proyecto técnico basado en Spring Boot para resolver la prueba de selección de la plataforma Core de Inditex. Expone un servicio REST para consultar el precio aplicable de un producto en una fecha y marca determinada.

---

## ✅ Requisitos técnicos

- Java 21
- Maven 3.8+
- Spring Boot 3.2+
- H2 Database (en memoria)
- Swagger UI / OpenAPI 3
- Docker (opcional, para despliegue)

---

## 📦 Tecnologías

- Spring Boot (Web, Data JPA)
- H2 (base de datos embebida)
- JUnit 5 + Mockito + MockMvc
- Swagger (Springdoc OpenAPI)
- Docker (para empaquetado)
- Arquitectura limpia (domain, application, infrastructure)

---

## 🚀 Cómo levantar la aplicación

```bash
./mvnw clean package -DskipTests
java -jar target/store.jar
```

O si prefieres usar Docker:

```bash
docker build -t inditex-pricing-api .
docker run -p 8080:8080 inditex-pricing-api
```

---

## 🔍 Endpoints REST

### `GET /api/price`

Consulta el precio aplicable en función de la fecha, el producto y la marca.

#### 🔸 Parámetros:

| Nombre      | Tipo           | Formato ISO      | Descripción                      |
|-------------|----------------|------------------|----------------------------------|
| `date`      | `LocalDateTime`| `YYYY-MM-DDTHH:MM:SS` | Fecha de aplicación de precio |
| `productId` | `Long`         | -                | ID del producto                  |
| `brandId`   | `Long`         | -                | ID de la marca                   |

#### 🔸 Ejemplo:

```http
GET /api/price?date=2020-06-14T10:00:00&productId=35455&brandId=1
```

#### 🔸 Respuesta Exitosa:

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

#### 🔸 Código 404:

Si no se encuentra precio aplicable para los parámetros.

---

## 📊 Documentación Swagger

Disponible en:  
👉 [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

---

## 🧪 Tests

Ejecuta los tests con:

```bash
./mvnw test
```

Incluye:

- ✅ 5 pruebas de integración (casos solicitados)
- ✅ Tests unitarios del controller
- ✅ Validaciones y manejo de errores
- ✅ Base de datos precargada con `schema.sql` y `data.sql`

---

## 🧠 Casos de prueba cubiertos

| Test # | Fecha y hora         | Producto | Marca | Tarifa Esperada | Precio |
|--------|----------------------|----------|-------|------------------|--------|
| 1      | 2020-06-14 10:00:00  | 35455    | 1     | 1                | 35.50  |
| 2      | 2020-06-14 16:00:00  | 35455    | 1     | 2                | 25.45  |
| 3      | 2020-06-14 21:00:00  | 35455    | 1     | 1                | 35.50  |
| 4      | 2020-06-15 10:00:00  | 35455    | 1     | 3                | 30.50  |
| 5      | 2020-06-16 21:00:00  | 35455    | 1     | 4                | 38.95  |

---

## 🐳 Docker

### 🧱 Construcción

```bash
./mvnw clean package -DskipTests
docker build -t inditex-pricing-api .
```

### ▶️ Ejecución

```bash
docker run -p 8080:8080 inditex-pricing-api
```

---

## 🚀 Pruebas rápidas vía `curl`

```bash
curl -X GET "http://localhost:8080/api/price?date=2020-06-14T10:00:00&productId=35455&brandId=1" -H "Accept: application/json"
```

---

## 📂 Estructura del proyecto

```
.
├── domain
│   └── model / repository
├── application
│   └── services
├── infrastructure
│   ├── rest
│   ├── dto
│   ├── configuration
│   └── exceptions
├── resources
│   ├── schema.sql
│   ├── data.sql
│   └── application.properties
└── test
    └── unit / integration
```

---

## ✨ Autor

Proyecto desarrollado como parte del proceso técnico para Inditex.  
By [💻 Luis Contreras].

---