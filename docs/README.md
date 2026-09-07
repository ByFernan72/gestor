# Documentacion del Proyecto Gestor

Indice general de documentacion para usuarios y desarrolladores.

---

## 1. Guia de Usuario
* Documento: [docs/user/GUIA_USUARIO.md](user/GUIA_USUARIO.md)
* **Objetivo:** Explicar de forma clara, sencilla y paso a paso como utilizar la aplicacion de escritorio:
  * Como dar de alta carteras y activos.
  * Como registrar movimientos y transferencias entre cuentas.
  * Como interpretar las metricas del Dashboard.
  * Como realizar copias de seguridad de los datos locales (gestordb.mv.db).

---

## 2. Documentacion Tecnica
* **Arquitectura del Sistema:** [docs/technical/ARQUITECTURA.md](technical/ARQUITECTURA.md)
  * Estructura del stack (Java 21, Spring Boot 3.4, H2 local persistente, Electron).
  * Persistencia, modelos JPA y gestion DDL automatica.
  * Variables de entorno (.env / application.properties).
  * Integracion y ciclo de vida en Electron.
* **Catalogo de Endpoints REST:** [docs/technical/API_ENDPOINTS.md](technical/API_ENDPOINTS.md)
  * Especificacion de todos los endpoints HTTP (/api/carteras, /api/activos, /api/movimientos, /api/dashboard, /api/perfil, etc.).
  * Payloads, DTOs de entrada/salida y codigos de estado HTTP.
  * Formato unificado de errores (GlobalExceptionHandler).

---

## 3. Javadoc de Clases (HTML)
* Directorio: [docs/javadoc/index.html](javadoc/index.html)
* **Objetivo:** Documentacion navegable de todas las clases, interfaces, controladores y repositorios Java generada con Gradle.
* Para consultarlo, abre el archivo docs/javadoc/index.html en cualquier navegador web.
