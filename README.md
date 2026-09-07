# Gestor ・ Cuaderno de Finanzas

Durante bastante tiempo llevé mis cuentas, ahorros e inversiones en hojas de cálculo de **Excel**. 

Aunque Excel es una herramienta muy potente, en el día a día se me hacía pesado y poco práctico:
- Fórmulas manuales propensas a romperse si cambiaba una fila o columna.
- Ausencia de una interfaz visual limpia y clara para ver el estado general de un vistazo rápido.
- La pereza constante de tener que memorizar o calcular números entre bancos, brokers y criptomonedas para luego transcribirlos en celdas.

Tenía ganas de aprender **Spring Boot**, explorar cómo estructurar una aplicación completa de principio a fin y profundizar en la persistencia local con **H2**. La unión entre esa necesidad personal y las ganas de cacharrear y aprender fue lo que dio origen a **Gestor**.

Hoy en día es la herramienta que utilizo a diario: me permite registrar y contar mi patrimonio de forma mucho más rápida y visual, sin tener que pelearme con filas ni cálculos manuales.

---

## ¿Qué problemas resuelve para mi día a día?

- **Adiós a las filas y columnas:** Registro las cuentas y movimientos a través de formularios limpios; la aplicación se encarga de calcular balances y agregados.
- **Vista consolidada instantánea:** Un dashboard visual donde veo mi patrimonio global y el peso de cada cartera en segundos.
- **Carteras organizadas a mi gusto:** Bancos tradicionales, brokers de bolsa, fondos, billeteras cripto o dinero en efectivo.
- **Registro de transferencias:** Movimientos entre carteras sin perder el rastro del dinero ni tener que ajustar celdas a mano.
- **100% privado y en local:** Sin suscripciones, sin vincular credenciales bancarias ni enviar mis datos financieros a nubes de terceros. Todo vive en un archivo local sin necesidad de estar conectado a internet.
- **Formato escritorio:** Empaquetado como ejecutable de Windows (Gestor.exe) con Electron para abrirlo como cualquier programa nativo.

---

## ¿Quieres probarlo?

Si también estás cansado de las hojas de cálculo y quieres usarlo:

1. Ve a la carpeta del ejecutable (dist/win-unpacked).
2. Abre **Gestor.exe**.
3. El backend Spring Boot y la base de datos arrancan solos en segundo plano con una pantalla de carga integrada.

Para aprender a usar la interfaz, registrar carteras o hacer copias de seguridad de tus datos, consulta el manual detallado:
[**Guía de Usuario**](docs/user/GUIA_USUARIO.md)

---

## ¿Qué aprendí construyendo este proyecto?

Este proyecto me sirvió como campo de pruebas real para conectar varias piezas del ecosistema de desarrollo:

- **Backend con Spring Boot (Java 21):** Diseño de API REST, separación en capas (Controller, Service, DTOs, Repository) y manejo global de excepciones.
- **Persistencia con H2:** Uno de los puntos que más disfruté explorar; entender el modo archivo embebido, cómo garantizar la persistencia local sin depender de instalar un servidor de base de datos externo y cómo gestionar la evolución de esquemas con JPA/Hibernate.
- **Frontend con React + TypeScript:** Creación de una interfaz con modo oscuro, componentes modulares, estados de carga y tipado estricto.
- **Integración Desktop (Electron):** Coordinar el ciclo de vida de un subproceso Java con la ventana de Electron, reduciendo tiempos de espera y añadiendo un splash screen animado.

Para profundizar en la arquitectura interna, modelos de datos, endpoints o cómo recompilar el ejecutable:
- [**Documentación Técnica y Arquitectura**](docs/technical/ARQUITECTURA.md)
- [**Catálogo de Endpoints REST**](docs/technical/API_ENDPOINTS.md)
- [**Javadoc de Clases**](docs/javadoc/index.html)

---

## Ejecución en local (Desarrollo)

Si quieres clonar el proyecto para trastear con el código o adaptarlo:

```bash
# 1. Backend (Spring Boot en http://localhost:8080)
.\gradlew.bat bootRun   # Windows
./gradlew bootRun      # Linux / macOS

# 2. Frontend (Vite en http://localhost:5173)
cd gestorFrontend
npm install
npm run dev

# 3. Desktop (Electron)
cd gestorDesktop
npm install
npm start
```
