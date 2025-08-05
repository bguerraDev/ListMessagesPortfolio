# 📱 ListMessagesPortfolio

📘 [Read this in English](README.md)

Aplicación Android nativa desarrollada con **Jetpack Compose** para visualizar mensajes almacenados en Firestore. Incluye autenticación con token JWT desde un backend en Django y un diseño moderno con soporte para modo oscuro. USO exclusivo para bguerradev-portfolio.

---

## 🛠️ Tecnologías Usadas

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Retrofit](https://img.shields.io/badge/Retrofit-009688?style=for-the-badge)
![DataStore](https://img.shields.io/badge/DataStore-FF6F00?style=for-the-badge)
![Django REST](https://img.shields.io/badge/Django%20REST-092E20?style=for-the-badge&logo=django&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FC6D26?style=for-the-badge&logo=firebase&logoColor=white)

---

## 🧱 Arquitectura

- **MVVM (Model-View-ViewModel)** 📐t
- **Repository Pattern** para desacoplar lógica de red
- **Jetpack Navigation** para navegación entre pantallas
- **Jetpack Compose** para UI declarativa
- **DataStore Preferences** para manejo del token JWT

---

## 🔐 Flujo de Autenticación

1. El usuario introduce `nombre de usuario` y `contraseña`.
2. Se hace una petición a `/api/login/` del backend (Django).
3. Si es correcto, se guarda el token JWT localmente.
4. Con ese token se consulta `/api/messages/`.

---

## 🧾 Funcionalidades

- 🔐 Login con JWT + Firestore
- 📄 Listado de mensajes
- 🔍 Búsqueda por texto, nombre o fecha
- 🔃 Orden ascendente/descendente
- 🌒 Modo claro/oscuro
- 🎯 Logout con redirección segura
- 🧊 Shimmer Placeholder en carga
- ⬇️ Flecha para desplegar mensaje

---

## 📦 Estructura de Carpetas

```bash
├── data
│   ├── domain.model (Modelos)
│   ├── network (Retrofit + API)
│   └── repository (Lógica de acceso)
├── ui
│   ├── login (Pantalla de login)
│   ├── messages (Listado de mensajes)
│   ├── common (Componentes reutilizables)
│   └── theme (Tema claro/oscuro)
├── utils (Helpers y constantes)
└── MainActivity.kt