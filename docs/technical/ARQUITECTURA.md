# Documentacion Tecnica - Arquitectura del Sistema

Esta documentacion describe el diseno interno, la infraestructura de datos y el flujo de ejecucion del proyecto **Gestor**.

---

## 1. Stack Tecnologico

- **Lenguaje:** Java 21 (LTS).
- **Framework Backend:** Spring Boot 3.4.1.
- **Persistencia:** Spring Data JPA / Hibernate 6.x.
- **Base de Datos:** H2 Database Engine (modo embebido persistente en disco).
- **Aplicacion de Escritorio:** Electron y Node.js (con empaquetado standalone).
- **Construccion y Dependencias:** Gradle.

---

## 2. Arquitectura de Capas

El backend sigue un patron en capas estandar de Spring:

```text
com.gestorBackend
├── Config/            # Configuraciones CORS, WebMvc, Seguridad
├── Controller/        # Controladores REST exponiendo endpoints HTTP JSON
├── Dto/               # Data Transfer Objects (Request / Response) con validacion Bean Validation
├── Exception/         # GlobalExceptionHandler centralizado (@ControllerAdvice)
├── Model/             # Entidades JPA (@Entity) mapeadas a tablas relacionales
├── Repository/        # Interfaces Spring Data JPA (JpaRepository)
└── Service/           # Capa logica de negocio y transacciones (@Transactional)
    └── Impl/          # Implementaciones de los servicios
```

---

## 3. Modelo y Persistencia de Datos (H2)

La base de datos actual utiliza el motor **H2** en modo archivo persistente.

### Entidades Principales:
- **Cartera (cartera):** Agrupador principal de activos. Contiene id, nombre, descripcion y fecha de creacion.
- **Activo (activo):** Registro de instrumentos con id, fk id_cartera, nombre, balance (BigDecimal), tipo (TipoActivo) y fecha de obtencion.
- **Movimiento (movimiento):** Historial transaccional entre activos/carteras origen y destino con cantidades transferidas.
- **Historial (historial):** Snapshots periodicos de balances para reconstruccion de graficos historicos.
- **Perfil (perfil):** Informacion del usuario y enlaces guardados (perfil_links).

### Gestion DDL:
Hibernate se encuentra configurado con spring.jpa.hibernate.ddl-auto=update, lo que permite:
- Creacion automatica de tablas y secuencias en el primer arranque.
- Alteracion automatica de columnas al anadir nuevos atributos en las entidades JPA.
- Preservacion total de los datos existentes.

---

## 4. Configuracion y Variables de Entorno

La configuracion se maneja a traves de `application.properties` con soporte de sustitucion mediante `.env`:

| Variable | Descripcion | Valor por Defecto |
|---|---|---|
| APP_NAME | Nombre de la aplicacion Spring Boot | gestor |
| SERVER_PORT | Puerto de escucha HTTP | 8080 |
| DB_URL | Cadena JDBC de conexion H2 | jdbc:h2:file:./data/gestordb;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE |
| DB_DRIVER | Clase del driver JDBC | org.h2.Driver |
| DB_USERNAME | Usuario de base de datos | admin |
| DB_PASSWORD | Contrasena de base de datos | admin |
| JPA_DIALECT | Dialecto Hibernate | org.hibernate.dialect.H2Dialect |
| JPA_DDL_AUTO | Estrategia de actualizacion DDL | update |
| JPA_SHOW_SQL | Loggear sentencias SQL en stdout | false |
| H2_CONSOLE_ENABLED | Habilitar consola web en /h2-console | true |

---

## 5. Ciclo de Vida en Electron (App de Escritorio)

1. Al abrir el ejecutable, `gestorDesktop/main.js` inicializa el proceso y muestra la pantalla de carga (Splash Screen).
2. Electron consulta la carpeta de datos de usuario:
   `app.getPath('userData')/database/gestordb`
3. Electron lanza el backend en un subproceso hijo mediante spawn(javaExe, ['-jar', jarPath]) inyectando la variable DB_URL.
4. Electron sondea http://localhost:8080/ hasta recibir respuesta HTTP afirmativa y despliega la ventana principal, cerrando el splash screen.
5. Al cerrar la ventana, Electron envia una senal de apagado ordenado al proceso de Spring Boot para cerrar correctamente el archivo de base de datos H2.

---

## 6. Proceso de Rebuild de Gestor.exe (Actualizaciones y .env)

Cuando se modifican las variables en el archivo .env, la logica del backend o la interfaz frontend, el ejecutable empaquetado debe regenerarse:

1. **Recompilar Backend (JAR):**
   `powershell
   .\gradlew.bat bootJar
   `
2. **Reempaquetar Desktop:**
   `bash
   cd gestorDesktop
   npm run dist
   `
   El script pack.js copiara automaticamente el nuevo .env y splash.html a la carpeta de distribucion.

**Precedencia de lectura del .env en produccion:**
main.js busca el archivo .env en varios niveles al arrancar:
1. Junto al ejecutable: dist/win-unpacked/.env
2. En recursos del backend: dist/win-unpacked/resources/backend/.env
3. En la raiz del repositorio.

Nota: Si solo necesitas cambiar valores de variables (sin tocar codigo Java o JS), puedes editar directamente el archivo .env ubicado en dist/win-unpacked/resources/backend/.env o poner un .env junto a Gestor.exe sin necesidad de recompilar.
