# Currency Converter - Java Application

// Descripción del Proyecto
Este proyecto es un conversor de monedas desarrollado en Java utilizando el paradigma de Programación Orientada a Objetos (POO). El programa consume datos de una API externa para obtener tasas de cambio actualizadas, permite filtrar entre varias monedas, mostrar los resultados de las conversiones y almacenar un registro de las consultas realizadas por el usuario.
Main ejecutara en una ventana la conversión de Divisa,y UI, hara la ejecución del código en consola.

// Funcionalidades principales:
Conversión de divisas utilizando datos de tasas de cambio en tiempo real.
Interfaz de usuario textual para realizar conversiones entre varias monedas.
Filtrado de monedas específicas (USD, ARS, BOB, BRL, CLP, COP).
Registro de todas las conversiones realizadas.
Consumo de la API de tasas de cambio utilizando la clase HttpClient.
Manipulación de JSON usando la biblioteca Gson.
Requisitos del Proyecto
Para ejecutar este proyecto, necesitas lo siguiente:

// Requisitos de software:
Java 11 o superior
IntelliJ IDEA o cualquier otro IDE compatible con Java
Biblioteca Gson
API utilizada:
API utilizada:
-**Exchange Rate API**: [https://www.exchangerate-api.com/](https://www.exchangerate-api.com/)  
-Necesitarás una **clave de API** para realizar solicitudes a la API. Consulta la documentación para obtener la clave.

// Instalación y Configuración
Descarga y Configura el proyecto:

Clona este repositorio o descarga los archivos en tu máquina local.
Configura las dependencias:

Si no usas Maven o Gradle, descarga el archivo gson-2.8.6.jar y agrégalo a tu proyecto.
En IntelliJ IDEA: ve a File -> Project Structure -> Modules -> Dependencies y añade la biblioteca Gson.
Obtén una clave de API:

Visita Exchange Rate API para obtener una clave de API gratuita.
Reemplaza el valor de la clave en el archivo APIClient.java donde se realiza la solicitud a la API.
Estructura del Proyecto:

CurrencyConverterProject/
├── src/
│   ├── Main.java
│   ├── APIClient.java
│   ├── CurrencyService.java
│   ├── CurrencyRecord.java
│   └── UI.java
├── lib/
│   └── gson-2.8.6.jar
└── README.md
Compila y ejecuta el proyecto:

En IntelliJ IDEA, selecciona Main.java y ejecuta el proyecto.
Uso de la Aplicación
Inicio del programa: Al iniciar, el programa mostrará un menú con varias opciones para realizar conversiones entre monedas.

Conversión de monedas: El usuario deberá ingresar las monedas a convertir (p. ej., USD a COP) y el valor a convertir. El programa mostrará el resultado basado en las tasas de cambio obtenidas de la API.

Historial de consultas: Todas las conversiones realizadas serán almacenadas y podrán ser visualizadas desde el menú de opciones.

Estructura de las Clases
Main.java: Clase principal que inicia la aplicación.
APIClient.java: Clase que se encarga de realizar la solicitud HTTP a la API de tasas de cambio y manejar la respuesta JSON.
CurrencyService.java: Clase que contiene la lógica de conversión entre monedas utilizando los datos de la API.
CurrencyRecord.java: Clase que almacena un registro de las conversiones realizadas.
UI.java: Clase que gestiona la interfaz de usuario textual para interactuar con el programa.
Licencia
Proyecto realizado para ONE en el Challenge de backend G7 -Challenge Conversor de Monedas-.
Este proyecto está licenciado bajo los términos de la licencia MIT.
