# Guia de Usuario - Gestor Financiero Personal

Bienvenido al manual de uso de **Gestor**, tu aplicacion de escritorio para el control y seguimiento patrimonial de inversiones, cuentas y activos financieros.

---

## 1. Inicio Rapido

1. **Abrir la Aplicacion:**
   * Haz doble clic en el ejecutable Gestor.exe.
   * La aplicacion arrancara automaticamente el motor interno y abrira la ventana principal. No necesitas configurar servidores ni bases de datos externas.
2. **Ubicacion de tus Datos:**
   * Todos tus datos se almacenan exclusivamente en tu propio ordenador de manera privada y segura.

---

## 2. Gestion de Carteras

Una **Cartera** representa un contenedor de fondos o una entidad donde guardas dinero o inversiones (por ejemplo: *Banco Santander*, *Binance*, *Broker DeGiro*, *Efectivo*).

* **Crear una Cartera:**
  1. Dirigete a la seccion de **Carteras**.
  2. Haz clic en **Nueva Cartera**.
  3. Introduce el nombre (ej. *Cartera Cripto*) y una descripcion opcional.
  4. Puedes anadir inmediatamente los activos iniciales que contiene o guardarla vacia para anadirlos mas tarde.
* **Consultar una Cartera:**
  * Al hacer clic en una cartera podras ver su balance total consolidado, la lista de activos que contiene y el historial de cambios.
* **Eliminar una Cartera:**
  * Puedes eliminar una cartera cuando ya no la uses. Recuerda que al eliminarla tambien se daran de baja sus activos asociados.

---

## 3. Gestion de Activos

Un **Activo** es cualquier bien o moneda con valor economico dentro de una cartera (por ejemplo: *EUR*, *USD*, *Bitcoin*, *Acciones Apple*).

* **Tipos de Activos Disponibles:**
  * Divisas tradicionales (FIAT / Dinero en efectivo o banco).
  * Criptomonedas.
  * Acciones / Fondos indexados.
  * Materias primas u otros activos.
* **Anadir o Editar Activos:**
  1. Entra en la cartera correspondiente.
  2. Pulsa en **Anadir Activo**.
  3. Define el nombre del activo, el tipo y el balance inicial o cantidad actual.
  4. La fecha de adquisicion se registrara automaticamente o podras personalizarla.

---

## 4. Registro de Movimientos y Transferencias

Los **Movimientos** permiten registrar transferencias de dinero entre dos carteras o intercambios entre diferentes activos (por ejemplo: transferir 500 EUR de tu *Cuenta Bancaria* a *Binance* para comprar *BTC*).

* **Como registrar un movimiento:**
  1. Ve a la pestana **Movimientos** o pulsa en **Nueva Transferencia**.
  2. Selecciona la **Cartera Origen** y el **Activo Origen**.
  3. Selecciona la **Cartera Destino** y el **Activo Destino**.
  4. Indica la **Cantidad Origen** enviada y la **Cantidad Destino** recibida.
  5. Confirma la operacion. El sistema actualizara automaticamente los balances de ambas carteras y guardara el registro cronologico.

---

## 5. Dashboard y Metricas

En la pantalla principal (**Dashboard**) encontraras un resumen ejecutivo de tus finanzas:

* **Patrimonio Total:** La suma total calculada de todas tus carteras activas.
* **Mejores Carteras:** Visualizacion rapida de las carteras con mayor peso o mejor balance.
* **Evolucion Cronologica:** Graficos para entender como ha crecido o variado tu patrimonio a lo largo del tiempo.

---

## 6. Copias de Seguridad (Backups)

Dado que la aplicacion guarda todo en un archivo local sin depender de la nube:

* **Hacer una Copia de Seguridad:**
  1. Cierra la aplicacion Gestor.
  2. Dirigete a la carpeta donde estan tus datos:
     * En Windows: %APPDATA%\gestorDesktop\database\ (o en la carpeta data/ del programa).
  3. Copia el archivo gestordb.mv.db a una memoria USB, disco externo o tu servicio en la nube preferido (Google Drive, Dropbox, etc.).
* **Restaurar tus Datos:**
  * Basta con copiar tu archivo gestordb.mv.db de respaldo en la misma ruta y abrir la aplicacion.
