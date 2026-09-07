# Catalogo de API REST - Endpoints

Base URL local: http://localhost:8080/api/v1

---

## 1. Carteras (/api/v1/cartera)

| Metodo | Endpoint | Descripcion | Payload Request | Respuesta |
|---|---|---|---|---|
| GET | /api/v1/cartera | Listar todas las carteras | Ninguno | List<Cartera> |
| GET | /api/v1/cartera/listado | Listar carteras paginadas con totales y numero de activos | Parametros Pageable | CarteraListadoResponseDto |
| GET | /api/v1/cartera/{id} | Obtener detalle completo de una cartera y sus activos | Ninguno | ObtenerDatosCompletosCarteraRequestDto |
| POST | /api/v1/cartera | Crear una nueva cartera (con o sin activos iniciales) | CrearCarteraRequestDto | Cartera (200 OK) |
| PATCH | /api/v1/cartera/{id}/nombre | Modificar el nombre de una cartera | Query param `nuevoNombreParam` o path variable | 204 No Content |
| PATCH | /api/v1/cartera/{id}/descripcion | Modificar la descripcion de una cartera | Query param `nuevaDescripcion` | 204 No Content |
| DELETE | /api/v1/cartera/{id} | Eliminar una cartera | Ninguno | 204 No Content |

---

## 2. Activos (/api/v1/activo)

| Metodo | Endpoint | Descripcion | Payload Request | Respuesta |
|---|---|---|---|---|
| POST | /api/v1/activo | Crear o guardar un activo individual | Activo (JSON) | Activo (200 OK) |
| DELETE | /api/v1/activo/{id} | Eliminar un activo por su ID | Ninguno | 204 No Content |

---

## 3. Movimientos y Transferencias (/api/v1/movimiento)

| Metodo | Endpoint | Descripcion | Payload Request | Respuesta |
|---|---|---|---|---|
| POST | /api/v1/movimiento/transferencia | Ejecutar transferencia segura entre carteras o activos | MovimientoRequestDto | String ("Transferencia realizada con exito") |
| GET | /api/v1/movimiento/historial/transferencias | Historial paginado de movimientos y transferencias | Parametros Pageable | Page<TransferenciaDto> |

---

## 4. Dashboard (/api/v1/dashboard)

| Metodo | Endpoint | Descripcion | Payload Request | Respuesta |
|---|---|---|---|---|
| GET | /api/v1/dashboard/resumen | Resumen ejecutivo (patrimonio total, top carteras, ultimos movimientos) | Ninguno | DashboardResumenResponseDto |

---

## 5. Historial (/api/v1/historial)

| Metodo | Endpoint | Descripcion | Payload Request | Respuesta |
|---|---|---|---|---|
| POST | /api/v1/historial | Tomar snapshot de saldo actual de todos los activos | Ninguno | 204 No Content |

---

## 6. Perfil (/api/v1/perfil)

| Metodo | Endpoint | Descripcion | Payload Request | Respuesta |
|---|---|---|---|---|
| GET | /api/v1/perfil/{id} | Obtener datos del perfil del usuario | Ninguno | PerfilResponseDto |
| PUT | /api/v1/perfil/{id} | Actualizar nombre y enlaces del perfil | PerfilRequestDto | 204 No Content |

---

## 7. Ajustes (/api/v1/ajustes)

| Metodo | Endpoint | Descripcion | Payload Request | Respuesta |
|---|---|---|---|---|
| POST | /api/v1/ajustes/reset | Resetear completamente la base de datos (carteras, activos, movimientos y perfil) | Ninguno | 204 No Content |

---

## Formato Estandar de Respuestas de Error

Todas las excepciones capturadas por `GlobalExceptionHandler` devuelven el siguiente formato estandar JSON:

```json
{
  "status": 400,
  "mensaje": "Descripcion legible del error o validacion fallida",
  "timestamp": "2026-09-07T10:45:00"
}
```
