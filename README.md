# 🐾 PetStore Automation Framework

Marco de automatización de pruebas funcional de extremo a extremo (**E2E**) para la aplicación **JPetStore** (`https://petstore.octoperf.com/catalog`), desarrollado en **Java**, **Selenium WebDriver**, **Cucumber (BDD)** y **Gradle**.

Este proyecto implementa el patrón de diseño **Page Object Model (POM)** con contratos de interfaz y ciclo de vida aislado por hilo para garantizar robustez, mantenibilidad y escalabilidad.

---

## 📋 Índice

- [1. Especificación Funcional (Sign Up v4.0)](#1-especificación-funcional-sign-up-v40)
  - [1.1. Alcance y Límites del Sistema](#11-alcance-y-límites-del-sistema)
  - [1.2. Diccionario de Datos y Reglas de Negocio](#12-diccionario-de-datos-y-reglas-de-negocio)
  - [1.3. Flujo E2E y Criterios de Aceptación](#13-flujo-e2e-y-criterios-de-aceptación)
- [2. Arquitectura Técnica del Framework](#2-arquitectura-técnica-del-framework)
  - [2.1. Stack Tecnológico y Dependencias](#21-stack-tecnológico-y-dependencias)
  - [2.2. Estructura de Carpetas y Capas](#22-estructura-de-carpetas-y-capas)
  - [2.3. Patrones de Diseño y Ciclo de Vida](#23-patrones-de-diseño-y-ciclo-de-vida)
- [3. Convenciones y Estándares de Código](#3-convenciones-y-estándares-de-código)
- [4. Riesgos Técnicos y Limitaciones Identificadas](#4-riesgos-técnicos-y-limitaciones-identificadas)
- [5. Requisitos Previos e Instalación](#5-requisitos-previos-e-instalación)
- [6. Guía de Ejecución y Reportes](#6-guía-de-ejecución-y-reportes)
- [7. Artefactos de Calidad y Documentación IA](#7-artefactos-de-calidad-y-documentación-ia)

---

## 1. Especificación Funcional (Sign Up v4.0)

Basado en el documento funcional **[`IA/documento_funcional_signup_v4.md`](file:///c:/Users/marya/IdeaProjects/PetStoreAutomation/IA/documento_funcional_signup_v4.md)**, cuya fuente de verdad es la inspección directa de la aplicación web en `https://petstore.octoperf.com/account/new`.

### 1.1. Alcance y Límites del Sistema

#### ✅ Incluye (§2)
- Alta de credenciales: `User ID` único, contraseña y confirmación.
- Captura y validación de datos personales, contacto y dirección.
- Configuración de preferencias de perfil (Idioma, Categoría favorita, MyList, MyBanner).
- Validación de formulario en backend y persistencia del estado en caso de error.
- Redirección automática post-registro hacia Home (`/catalog`).

#### ❌ Excluye (§2 - Supuestos y Restricciones)
- **Autenticación automática post-registro:** El registro exitoso **no** inicia sesión de manera automática; el usuario permanece sin sesión activa y debe autenticarse manualmente desde *Sign In*.
- **Recuperación de contraseña olvidada** y edición posterior de perfil (administrado en *My Account*).
- **Verificación asíncrona de email:** La cuenta queda disponible en estado `ACTIVE` de forma inmediata.
- **Términos y Condiciones obligatorios:** La interfaz no cuenta con checkbox de aceptación ni política de privacidad.
- **CAPTCHA, Rate Limiting y Single Sign-On (SSO / OAuth).**

---

### 1.2. Diccionario de Datos y Reglas de Negocio

| Campo (UI) | Atributo HTML | Tipo | Obligatorio | Reglas y Restricciones de Negocio | Valor por Defecto |
| :--- | :--- | :---: | :---: | :--- | :--- |
| **User ID** | `username` | String | Sí | Exactamente 10 caracteres alfanuméricos (`^[a-zA-Z0-9]{10}$`). Único en base de datos. | Vacío |
| **New password** | `password` | String | Sí | 8 a 20 caracteres. Al menos 1 mayúscula, 1 minúscula, 1 número y 1 carácter especial. | Vacío |
| **Repeat password** | `repeatedPassword` | String | Sí | Debe coincidir exactamente con *New password*. | Vacío |
| **First name** | `firstName` | String | Sí | 1 a 50 caracteres alfabéticos sanitizados. | Vacío |
| **Last name** | `lastName` | String | Sí | 1 a 50 caracteres alfabéticos sanitizados. | Vacío |
| **Email** | `email` | String | Sí | Formato RFC 5322. Máximo 100 caracteres. Único. | Vacío |
| **Phone** | `phone` | String | Sí | Exactamente 9 caracteres numéricos con prefijo `+` opcional. | Vacío |
| **Address 1** | `address1` | String | Sí | 3 a 100 caracteres alfanuméricos. | Vacío |
| **Address 2** | `address2` | String | No | Hasta 100 caracteres alfanuméricos. | Vacío |
| **City** | `city` | String | Sí | 2 a 50 caracteres. | Vacío |
| **State** | `state` | String | Sí | 2 a 50 caracteres. | Vacío |
| **Zip** | `zip` | String | Sí | 3 a 10 caracteres alfanuméricos (`^[a-zA-Z0-9\s-]{3,10}$`). | Vacío |
| **Country** | `country` | String | Sí | 2 a 50 caracteres. | Vacío |
| **Language Preference** | `languagePreference` | Select | No | Opciones: `english`, `japanese`. | `english` |
| **Favourite Category** | `favouriteCategoryId` | Select | No | Opciones: `FISH`, `DOGS`, `REPTILES`, `CATS`, `BIRDS`. | `FISH` |
| **Enable MyList** | `listOption` | Checkbox | No | Checkbox booleano (`true` / `false`). | `false` (desmarcado) |
| **Enable MyBanner** | `bannerOption` | Checkbox | No | Checkbox booleano (`true` / `false`). | `false` (desmarcado) |

---

### 1.3. Flujo E2E y Criterios de Aceptación

El escenario automatizado actual en [`CreateUser.feature`](file:///c:/Users/marya/IdeaProjects/PetStoreAutomation/src/test/resources/Features/CreateUser/CreateUser.feature) valida el ciclo de vida completo:
1. **Navegación:** Acceso a `/catalog` -> Clic en `Sign In` -> Clic en `Register Now`.
2. **Formulario:** Ingreso dinámico de `User ID` único (10 caracteres generados con timestamp) y `Email` parametrizado para evitar colisiones.
3. **Preferencias:** Verificación activa de que los valores por defecto sean `english`, `FISH` y checkboxes inactivos.
4. **Envío y Redirección:** Clic en `Save Account Information` y verificación de redirección a `/catalog`.
5. **Autenticación:** Clic en `Sign In`, ingreso de las credenciales registradas y verificación de sesión exitosa comprobando la visibilidad de los botones **`My Account`** y **`Sign Out`**.

---

## 2. Arquitectura Técnica del Framework

Conforme a la auditoría técnica detallada en **[`IA/context.md`](file:///c:/Users/marya/IdeaProjects/PetStoreAutomation/IA/context.md)**:

### 2.1. Stack Tecnológico y Dependencias

| Categoría | Tecnología / Librería | Versión | Propósito en el Framework |
| :--- | :--- | :---: | :--- |
| **Plataforma / Build** | Gradle Wrapper | 9.6.0 | Gestión de dependencias y tareas de compilación |
| **Lenguaje** | Java (JDK) | 17+ | Lenguaje base del proyecto |
| **Test Engine** | JUnit Jupiter Platform | 6.0.0 (BOM) / 5.x | Motor principal de pruebas unitarias y de integración |
| **BDD Engine** | Cucumber Java & Core | 7.18.0 | Interpretación y ejecución de especificaciones Gherkin |
| **Cucumber Platform** | `cucumber-junit-platform-engine` | 7.13.0 / 7.18.0 | Integración nativa de Cucumber con JUnit 5 Platform |
| **Automatización Web** | Selenium Java | 4.20.0 | Interacción programática con navegadores web |
| **Aserciones** | AssertJ Core & Hamcrest | 3.23.1 / 2.2 | Aserciones fluidas, legibles y descriptivas |
| **Pruebas de API** | REST Assured & Apache HttpClient | 5.3.1 / 4.5.14 | Automatización y soporte para servicios HTTP/REST |
| **Manejo de Datos** | Jackson Databind & OpenCSV | 2.14.2 / 5.7.1 | Serialización JSON y soporte Data-Driven Testing (DDT) |
| **Reportes** | Allure JUnit 5 | 2.23.0 | Generación de reportes gráficos y métricas QA |
| **Logging** | Logback Classic & Janino | 1.4.5 / 3.1.9 | Sistema centralizado de trazas y logs |

---

### 2.2. Estructura de Carpetas y Capas

```text
PetStoreAutomation/
├── IA/                                         # Especificaciones funcionales y análisis QA
│   ├── context.md                              # Auditoría arquitectónica y técnica
│   ├── documento_funcional_signup_v4.md        # Documento funcional oficial
│   ├── gaps_y_riesgos_signup.md                # Matriz de brechas y riesgos QA
│   ├── plantilla_analisis_gaps_y_riesgos.md    # Plantilla reutilizable para otros módulos
│   └── prompt_analisis_funcional.md            # Especificación de prompts para IA
├── src/
│   ├── main/java/org/example/Main.java         # Módulo autogenerado por IDE
│   └── test/
│       ├── java/
│       │   ├── driver/
│       │   │   └── DriverManager.java          # ThreadLocal WebDriver manager
│       │   ├── hook/
│       │   │   └── Hook.java                   # Hooks @Before y @After de Cucumber
│       │   ├── interfaces/
│       │   │   └── ButtonClick.java            # Contrato funcional para acciones de clic
│       │   ├── pages/                          # Capa de abstracción Page Object Model
│       │   │   ├── BasePage.java               # Métodos base comunes y esperas explícitas
│       │   │   ├── HomePage.java               # Catálogo, banners y menú de sesión
│       │   │   ├── LoginPage.java              # Formulario de inicio de sesión
│       │   │   └── InformationPage.java        # Formulario de registro y validaciones
│       │   └── steps/
│       │       └── CreateUserStep.java         # Step Definitions conectados con Gherkin
│       └── resources/
│           └── Features/
│               └── CreateUser/
│                   └── CreateUser.feature      # Escenarios BDD Gherkin
├── build.gradle                                # Dependencias y configuración de Gradle
└── README.md
```

---

### 2.3. Patrones de Diseño y Ciclo de Vida

1. **Page Object Model (POM):**
   - [`BasePage.java`](file:///c:/Users/marya/IdeaProjects/PetStoreAutomation/src/test/java/pages/BasePage.java): Encapsula la instancia del `WebDriver` y centraliza un `WebDriverWait` explícito de **15 segundos** (`visibilityOfElementLocated`). Expone métodos atómicos: `type`, `click`, `getText`, `isDisplayed`, `navigateTo`.
   - Especialización por vistas: [`HomePage`](file:///c:/Users/marya/IdeaProjects/PetStoreAutomation/src/test/java/pages/HomePage.java), [`LoginPage`](file:///c:/Users/marya/IdeaProjects/PetStoreAutomation/src/test/java/pages/LoginPage.java) e [`InformationPage`](file:///c:/Users/marya/IdeaProjects/PetStoreAutomation/src/test/java/pages/InformationPage.java).

2. **Desacoplamiento por Interfaces:**
   - La interfaz [`ButtonClick.java`](file:///c:/Users/marya/IdeaProjects/PetStoreAutomation/src/test/java/interfaces/ButtonClick.java) (`darClick()`) estandariza el comportamiento de componentes que ejecutan acciones principales de navegación o confirmación.

3. **Ciclo de Vida Concurrente (`ThreadLocal`):**
   - [`DriverManager.java`](file:///c:/Users/marya/IdeaProjects/PetStoreAutomation/src/test/java/driver/DriverManager.java) utiliza `ThreadLocal<WebDriver>` para garantizar aislamiento completo por hilo, habilitando futuras ejecuciones paralelas sin colisión de sesión.
   - [`Hook.java`](file:///c:/Users/marya/IdeaProjects/PetStoreAutomation/src/test/java/hook/Hook.java): Inicializa el navegador (`@Before`) y asegura el cierre limpio con `quitDriver()` (`@After`) liberando recursos en memoria.

---

## 3. Convenciones y Estándares de Código

- **Nomenclatura:** Paquetes en minúsculas (`driver`, `hook`, `pages`, `steps`), clases en `PascalCase` y métodos en `camelCase`.
- **Sensibilidad a Mayúsculas/Minúsculas en Steps:** Manejo de botones mediante normalización (`boton.toLowerCase().trim()`) para evitar errores en `switch-case`.
- **Datos Dinámicos Anti-Colisión:** Sustitución automática de prefijos y `{timestamp}` en `User ID` y `Email` para permitir ejecuciones de pruebas repetitivas sin errores de duplicidad (Primary Key Violation).

---

## 4. Riesgos Técnicos y Limitaciones Identificadas

Derivadas del análisis de arquitectura en `context.md` y la matriz de calidad en `gaps_y_riesgos_signup.md`:

1. **Mezcla de Esperas Implícitas y Explícitas:**  
   `DriverManager` define un `implicitlyWait` de 10s mientras `BasePage` aplica un `WebDriverWait` de 15s. *Recomendación:* Eliminar la espera implícita global para evitar tiempos muertos impredecibles según la documentación de Selenium.
2. **GAPs de Errores e Interfaz (GAP-02 y GAP-04):**  
   El backend de JPetStore procesa formularios con submit tradicional HTML y puede retornar páginas de error 500 no capturadas ante longitudes excedidas en campos. No existe una API REST JSON ni códigos `REG_*` en la versión observada.
3. **Parametrización de Navegadores y Modo Headless:**  
   `DriverManager` tiene cableado `ChromeDriver` en modo gráfico fijo (`--start-maximized`). Para integración con pipelines CI/CD (GitHub Actions / Jenkins) se requerirá parametrizar `--headless=new`.

---

## 5. Requisitos Previos e Instalación

1. **Java Development Kit (JDK):** Versión 17 o superior.
2. **Google Chrome:** Versión estable actualizada (Selenium Manager resolverá el driver automáticamente).
3. **Git:** Instalado con credenciales configuradas.

Clonar el repositorio:
```bash
git clone https://github.com/TU_USUARIO/PetStoreAutomation.git
cd PetStoreAutomation
```

---

## 6. Guía de Ejecución y Reportes

### 6.1. Ejecución desde Terminal (Gradle)

Ejecutar la suite completa de pruebas:
```bash
# Windows
gradlew.bat test

# macOS / Linux
./gradlew test
```

Ejecutar filtrando por etiquetas Cucumber:
```bash
./gradlew test -Dcucumber.filter.tags="@smoke"
```

### 6.2. Ejecución desde IntelliJ IDEA
1. Navega a `src/test/resources/Features/CreateUser/CreateUser.feature`.
2. Haz clic derecho sobre el archivo o sobre el escenario específico y selecciona **Run 'Feature: CreateUser'**.

### 6.3. Reportes de Resultados
Al finalizar la ejecución, los reportes nativos de Gradle quedan disponibles en:
```text
build/reports/tests/test/index.html
```
*(Para reportes Allure, ejecutar `gradlew allureServe` si el plugin está habilitado).*

---

## 7. Artefactos de Calidad y Documentación IA

En el directorio **[`IA/`](file:///c:/Users/marya/IdeaProjects/PetStoreAutomation/IA/)** se encuentran centralizados los documentos de ingeniería y calidad:
- **`context.md`**: Auditoría arquitectónica del proyecto, stack técnico y dependencias.
- **`documento_funcional_signup_v4.md`**: Especificación de requerimientos, alcance y criterios Gherkin.
- **`gaps_y_riesgos_signup.md`**: Matriz detallada de discrepancias UI vs Requerimientos y riesgos de automatización.
- **`plantilla_analisis_gaps_y_riesgos.md`**: Plantilla estandarizada de QA para evaluar nuevas funcionalidades.
