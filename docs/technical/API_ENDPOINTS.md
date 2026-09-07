# Catalogo de API REST - Endpoints

Base URL local: http://localhost:8080/api

---

## 1. Carteras (/api/carteras)

| Metodo | Endpoint | Descripcion | Payload Request | Respuesta |
|---|---|---|---|---|
| GET | /api/carteras | Listar todas las carteras con su balance total | Ninguno | List<CarteraListadoResponseDto> |
| POST | /api/carteras | Crear una nueva cartera con o sin activos | CrearCarteraRequestDto | Cartera (201 Created) |
| GET | /api/carteras/{id} | Obtener detalle completo de una cartera | Ninguno | ObtenerDatosCompletosCarteraRequestDto |
| DELETE | /api/carteras/{id} | Eliminar una cartera y sus activos | Ninguno | 204 No Content |
| GET | /api/carteras/top | Obtener las mejores carteras segun rendimiento | Ninguno | List<CarteraTopRequestDto> |

---

## 2. Activos (/api/activos)

| Metodo | Endpoint | Descripcion | Payload Request | Respuesta |
|---|---|---|---|---|
| POST | /api/activos | Crear un activo individual en una cartera | Activo | Activo (201 Created) |
| GET | /api/activos/cartera/{idCartera} | Listar los activos pertenecientes a una cartera | Ninguno | List<Activo> |
| PUT | /api/activos/{id} | Actualizar datos o balance de un activo | Activo | Activo (200 OK) |
| DELETE | /api/activos/{id} | Eliminar un activo | Ninguno | 204 No Content |

---

## 3. Movimientos y Transferencias (/api/movimientos)

| Metodo | Endpoint | Descripcion | Payload Request | Respuesta |
|---|---|---|---|---|
| GET | /api/movimientos | Listar historial completo de movimientos | Ninguno | List<Movimiento> |
| POST | /api/movimientos | Registrar un nuevo movimiento/transferencia | MovimientoRequestDto | Movimiento (201 Created) |
| DELETE | /api/movimientos/{id} | Revertir o eliminar un movimiento | Ninguno | 204 No Content |

---

## 4. Dashboard (/api/dashboard)

| Metodo | Endpoint | Descripcion | Payload Request | Respuesta |
|---|---|---|---|---|
| GET | /api/dashboard/resumen | Estadisticas globales consolidadas (patrimonio total, activos, carteras) | Ninguno | DashboardResumenResponseDto |

---

## 5. Historial (/api/historial)

| Metodo | Endpoint | Descripcion | Payload Request | Respuesta |
|---|---|---|---|---|
| GET | /api/historial | Obtener snapshots temporales de balance | Ninguno | List<Historial> |
| POST | /api/historial/snapshot | Registrar snapshot forzado de balance | Ninguno | 200 OK |

---

## 6. Perfil y Enlaces (/api/perfil)

| Metodo | Endpoint | Descripcion | Payload Request | Respuesta |
|---|---|---|---|---|
| GET | /api/perfil | Obtener perfil del usuario y links guardados | Ninguno | PerfilResponseDto |
| PUT | /api/perfil | Actualizar nombre y enlaces de perfil | PerfilRequestDto | PerfilResponseDto |

---

## Manejo de Excepciones

Todas las respuestas de error retornan el siguiente formato estandar JSON mediante GlobalExceptionHandler:

`json
{
  codigo: 400,
  mensaje: Descripcion legible del error o validacion fallida,
  fecha: 2026-09-06T09:30:00
}
`
