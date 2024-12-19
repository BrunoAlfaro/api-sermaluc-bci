# Usuario API

Este proyecto es una API RESTful para la creación y gestión de usuarios, desarrollado en Java con Spring Boot. La API incluye validaciones para correos electrónicos, contraseñas y utiliza tokens JWT para la autenticación.

## Autor

Creado por **Bruno Alfaro**.

## Requisitos

Antes de ejecutar el proyecto, asegúrate de tener instalado lo siguiente:

- [Java 17](https://jdk.java.net/17/)
- [Maven](https://maven.apache.org/)

## Configuración del proyecto

### Instalación

1. Clona este repositorio:
   ```bash
   git clone https://github.com/BrunoAlfaro/api-sermaluc-bci.git
   ```

2. Compila el proyecto:
   ```bash
   mvn clean install
   ```
3. Como opcional puedes solo ejecutar los TEST UNITARIOS:
   ```bash
   mvn test
   ```

### Ejecución

Para ejecutar la aplicación localmente, usa:
```bash
mvn spring-boot:run
```

### Endpoints disponibles

| Método | Endpoint         | Descripción                     |
|--------|------------------|---------------------------------|
| POST   | `/api/usuarios`  | Registrar un nuevo usuario.     |

### Ejemplo de solicitud

**POST `/api/usuarios`**
```json
{
    "name": "GOKU",
    "email": "guerrero@GOOGLE.com",
    "password": "Password123",
    "phones": [
        {
            "number": "123456789",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}

EL RAW SERIA (debe ser content-type: json):
curl --location 'http://localhost:8080/api/usuarios' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "GOKU",
    "email": "guerrero@GOOGLE.com",
    "password": "Password123",
    "phones": [
        {
            "number": "123456789",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}'

```

**Respuesta exitosa**
```json
{
  "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
  "created": "2024-12-16T12:34:56",
  "modified": "2024-12-16T12:34:56",
  "last_login": "2024-12-16T12:34:56",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "is_active": true
}
```

## Base de datos

### Acceso a la consola H2

La base de datos H2 se encuentra en memoria y puede ser accedida desde:
- **URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **Configuración:**
  - **Driver Class:** `org.h2.Driver`
  - **JDBC URL:** `jdbc:h2:mem:testdb`
  - **Username:** `sa`
  - **Password:** `password`

### Consultas útiles
```sql
SELECT * FROM usuarios;
SELECT * FROM telefonos;
```
# Diagrama de la Solución

El Diagrama de solución muestra las diferentes capas del proyecto y sus partes.

![Diagrama de la Solución](/Diagrama_Solucion.jpg)

## Documentación Swagger

Swagger está disponible para explorar y probar la API en:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot 2.6.3**
- **H2 Database**
- **JWT**
- **Maven**

