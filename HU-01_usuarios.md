# 📖 Historia de Usuario – Gestión de Usuarios

## 🎯 Descripción de negocio
Como **administrador/asesor del sistema**,  
quiero contar con un servicio que permita **autenticación y gestión de usuarios** (registro, login, consulta y actualización de contraseña),  
para mantener un control seguro y ordenado de los clientes en la plataforma.

---

## 🛠️ Criterios de aceptación

1. **Login de usuario**
    - El sistema debe permitir que un usuario se autentique con `username` y `password`.
    - Si las credenciales son válidas, se debe devolver un **JWT simulado**.
    - Si las credenciales son inválidas, se debe devolver un error `401 Unauthorized`.

2. **Consultar usuario por ID**
    - El sistema debe permitir obtener la información de un usuario mediante su `id`.
    - Si el usuario existe, se deben devolver sus datos básicos: `id, username, email, created_at`.
    - Si no existe, se debe devolver un error `404 Not Found`.

3. **Registrar un nuevo usuario**
    - El sistema debe permitir registrar un nuevo usuario con `username, email, password`.
    - Si el registro es exitoso, se debe devolver un mensaje confirmando y un `201 Created`.

4. **Actualizar contraseña**
    - El sistema debe permitir que un usuario actualice su contraseña proporcionando su `id` y la nueva contraseña.
    - Si el usuario existe, se debe devolver un `200 OK` confirmando el cambio.
    - Si no existe, se debe devolver un error `404 Not Found`.

---

## 📋 Criterios técnicos

- El servicio debe ser desarrollado en **Java 17 con Spring Boot**.
- Los endpoints deben estar implementados en un **controller REST**.
- Deben exponerse en **Swagger UI** automáticamente usando **springdoc-openapi**.
- Los DTOs de request y response deben estar validados con **Jakarta Validation**.
- Debe implementarse un **flujo feliz** de prueba para cada endpoint.
- Todas las peticiones deben incluir los siguientes headers:

### Headers requeridos
- **X-Transaction-Id**
    - Tipo: `string`
    - Descripción: Identificador único de la transacción para trazabilidad.
    - Ejemplo: `7c55d8fc-3f9a-4b3f-8c1e-922b1f57d2e9`

- **X-Origin-Service**
    - Tipo: `string`
    - Descripción: Nombre del microservicio o cliente que hace la llamada (útil para trazabilidad, métricas y seguridad).
    - Ejemplo: `bootcamp-back-sync-transaction-initiator`

---

## 📦 Esquemas (DTOs)

### 🔑 UserLoginRequest
```yaml
type: object
required: [username, password]
properties:
  username:
    type: string
  password:
    type: string
```
### 🔒 UserPasswordUpdateRequest
```yaml
type: object
required: [user_id, old_password, new_password]
properties:
  user_id:
    type: string
    description: Identificador único del usuario.
    example: "123"
  old_password:
    type: string
    description: Contraseña actual del usuario (se valida antes de actualizar).
    example: "123"
  new_password:
    type: string
    description: Nueva contraseña a establecer.
    example: "456"
```
### 👤 UserResponse
```yaml
type: object
properties:
  id:
    type: string
  username:
    type: string
  email:
    type: string
  created_at:
    type: string
    format: date-time
```
### 🔐 UserLoginResponse
```yaml
type: object
required: [access_token, token_type, expires_in]
properties:
  access_token:
    type: string
    description: JWT firmado que representa la sesión del usuario.
    example: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbmExMjMiLCJpYXQiOjE3MjUzNzkyODIsImV4cCI6MTcyNTM4Mjg4Mn0.cYyXv9qfTqY3mJg4y5mL6WcJ0Q
  token_type:
    type: string
    description: Tipo de token (generalmente "Bearer").
    example: Bearer
  expires_in:
    type: integer
    format: int64
    description: Tiempo de vida del access_token en segundos.
    example: 3600
```

## ✅ Flujo feliz esperado

1. Un usuario se **registra** en el sistema → `201 Created`.
2. El usuario hace **login** con sus credenciales → recibe un `JWT`.
3. El sistema permite **consultar** al usuario recién creado por su `id` → devuelve sus datos.
4. El usuario actualiza su **contraseña** → `200 OK`.

---