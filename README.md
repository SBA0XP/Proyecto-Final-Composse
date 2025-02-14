Este proyecto es una aplicación Android desarrollada utilizando Jetpack Compose y Firebase para manejar el registro e inicio de sesión de usuarios. También hace uso de la API de PokeAPI para mostrar un Pokémon aleatorio y proporciona funcionalidades para el manejo de accesos de usuarios.

## Descripción

La aplicación permite a los usuarios registrarse y luego iniciar sesión con su correo electrónico. Una vez logueados, se puede acceder a una pantalla principal donde se genera un Pokémon aleatorio utilizando la API pública de PokeAPI.

- **Firebase Firestore**: Se utiliza para almacenar la información de los usuarios, como el correo electrónico, el número de accesos y el último acceso.
- **Retrofit**: Se usa para hacer solicitudes a la API de Pokémon y mostrar información sobre un Pokémon aleatorio.

## Requisitos

- **Android Studio** (con soporte para Jetpack Compose)
- **JDK 17**
- **Dependencias**:
    - Firebase Firestore.
    - Retrofit.
    - Coil.

## Uso

##### 1. Pantalla de Logueo
Los usuarios deben ingresar su correo electrónico para iniciar sesión. Si el correo está registrado, se redirige a la pantalla principal; si no, se redirige a registrarse.

##### 2. Pantalla de Registro
Los usuarios registraran el correo electrónico y el nombre. Una vez registrados, pueden iniciar sesión.

##### 3. Pantalla Principal
Una vez logueados, podran generar un Pokémon aleatorio al hacer clic en el botón correspondiente. Los recordatorios de generación de Pokémon aparecen periódicamente hasta que el usuario haya generado uno.

##### 4. Cerrar Sesión
En la pantalla principal, los usuarios pueden cerrar sesión, lo que los llevará de vuelta a la pantalla de logueo.

##### 5. Cerrar aplicacion
En la pantalla prinsipal, los usuarios podran cerrar la aplicacion completamente si prescionan el boton correspondiente.

## Funcionalidades

- **Login y Registro:** Los usuarios pueden registrarse e iniciar sesión.
- **Pokedex:** Visualización de un Pokémon aleatorio desde la API de PokeAPI.
- **Firestore:** Los datos de los usuarios se almacenan y actualizan en Firebase Firestore.
    - Correo.
    - Nombre.
    - Fecha de ultimo acceso.
    - Numero de registros.

## Dependencias

Este proyecto utiliza las siguientes dependencias:

- **Firebase:** Para manejar el almacenamiento de datos de usuarios y la autenticación.
- **Retrofit:** Para realizar las peticiones a la API de PokeAPI.
- **Coil:** Para cargar imágenes del Pokémon desde la URL proporcionada por la API.

## Referencias

- ChatGPT.
- Documentos aportados en la aula virtual.