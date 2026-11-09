# Contexto Técnico del Proyecto

**Ruta base del proyecto:** `C:\Users\marya\IdeaProjects\PetStoreAutomation`  
**Fecha de generación:** 2026-11-08  
**Rol del análisis:** QA Automation Senior / Software Architect

---

## Resumen

El proyecto `PetStoreAutomation` es un marco de automatización de pruebas (Test Automation Framework) diseñado sobre el ecosistema Java y Gradle. A juzgar por la nomenclatura y las dependencias integradas, su objetivo está orientado a la validación funcional extremo a extremo (E2E) y de servicios sobre flujos de la aplicación de ejemplo PetStore (con un primer flujo identificado para la creación de usuarios).

Actualmente, el proyecto se encuentra en una fase inicial de arquitectura y andamiaje (scaffolding). Cuenta con la infraestructura fundamental de inicialización del navegador web (Selenium WebDriver), hooks de ciclo de vida para BDD (Cucumber), y una abstracción base para el patrón Page Object Model (POM). Los artefactos funcionales específicos (`CreateUser.feature` y `CreateUserStep.java`) se encuentran en estado de definición inicial o vacíos, a la espera del desarrollo de escenarios, localizadores y lógica de negocio.

---

## Arquitectura y módulos

El proyecto sigue una estructura mono-módulo estandarizada bajo Gradle (`src/main` y `src/test`). La arquitectura implementada se compone de las siguientes capas y módulos:

```
PetStoreAutomation/
├── .gradle/
├── .idea/
├── IA/
│   ├── prompt_context.md
│   └── context.md
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/
│   │   │       └── Main.java
│   │   └── resources/
│   └── test/
│       ├── java/
│       │   ├── driver/
│       │   │   └── DriverManager.java
│       │   ├── hook/
│       │   │   └── Hook.java
│       │   ├── pages/
│       │   │   ├── BasePage.java
│       │   │   └── HomePage.java
│       │   └── steps/
│       │       └── CreateUserStep.java
│       └── resources/
│           └── Features/
│               └── CreateUser/
│                   └── CreateUser.feature
├── .gitignore
├── build.gradle
├── gradlew
├── gradlew.bat
└── settings.gradle
```

### Detalle de Módulos y Componentes

1. **Gestión de Driver (`src/test/java/driver/DriverManager.java`)**:
   - Implementa un manejador estático para la instancia de `WebDriver`.
   - Utiliza `ThreadLocal<WebDriver>` para aislar el contexto del driver por hilo, asegurando compatibilidad con ejecuciones concurrentes o paralelas.
   - Configura `ChromeDriver` con el argumento `--start-maximized` y establece un `implicitlyWait` de 10 segundos.
   - Proporciona métodos para obtener la instancia activa (`getDriver()`) y liberarla limpiando el hilo (`quitDriver()`).

2. **Ganchos de Ciclo de Vida (`src/test/java/hook/Hook.java`)**:
   - Controla las fases previas y posteriores a la ejecución de cada escenario Cucumber.
   - `@Before`: Invoca `DriverManager.getDriver()` para inicializar el navegador antes de cada test.
   - `@After`: Invoca `DriverManager.quitDriver()` para cerrar la sesión del navegador y liberar memoria tras finalizar el escenario.

3. **Capa de Abstracción de Páginas / POM (`src/test/java/pages/`)**:
   - `BasePage.java`: Actúa como clase padre para todos los Page Objects. Encapsula las instancias de `WebDriver` y `WebDriverWait` (configurada con un timeout explícito de 15 segundos). Expone métodos reutilizables:
     - `navigateTo(String url)`: Carga de URL.
     - `find(By locator)`: Localización con espera explícita `visibilityOfElementLocated`.
     - `click(By locator)`, `type(By locator, String text)`: Acciones básicas sobre elementos.
     - `getText(By locator)`, `isDisplayed(By locator)`: Verificación y consulta de estado.
   - `HomePage.java`: Especialización de `BasePage` que invoca el constructor padre. Actualmente no cuenta con selectores ni métodos de negocio definidos.

4. **Capa de Definición de Pasos (`src/test/java/steps/CreateUserStep.java`)**:
   - Diseñada como puente entre las especificaciones Gherkin y la lógica de automatización.
   - Se encuentra declarada pero vacía (sin step definitions `@Given`, `@When`, `@Then`).

5. **Especificaciones BDD (`src/test/resources/Features/CreateUser/CreateUser.feature`)**:
   - Directorio de especificaciones de comportamiento en lenguaje Gherkin.
   - Archivo creado en disco pero con contenido vacío (0 bytes).

6. **Módulo Principal de Aplicación (`src/main/java/org/example/Main.java`)**:
   - Clase autogenerada por defecto por el IDE (IntelliJ IDEA) que contiene una plantilla de ejemplo (`main` con bucle de impresión). No forma parte del pipeline de automatización.

---

## Tecnología y versiones

El stack técnico y las dependencias declaradas en `build.gradle` y `gradle-wrapper.properties` son:

| Categoría | Tecnología / Librería | Versión Declarada | Ámbito / Propósito |
| :--- | :--- | :--- | :--- |
| **Plataforma / Build** | Gradle Wrapper | 9.6.0 | Construcción y ejecución del proyecto |
| **Plataforma / Build** | Java Plugin | Estándar (`id("java")`) | Compilación de código fuente Java |
| **Test Runner / Motor** | JUnit BOM | 6.0.0 | Alineación de dependencias JUnit |
| **Test Runner / Motor** | JUnit Jupiter (`junit-jupiter`) | Gestionado vía BOM | Framework base de ejecución unitaria |
| **Test Runner / Motor** | JUnit Platform Launcher | Gestionado vía BOM | Ejecución programática en plataformas de testing |
| **Test Runner / Motor** | JUnit Platform Suite | 1.13.0 / Plataforma | Soporte para agrupamiento y suites de tests |
| **Test Runner Alternativo** | TestNG | 7.10.1 | Framework alternativo (declarado en dependencias) |
| **Framework BDD** | Cucumber Core (`io.cucumber:cucumber-core`) | 7.18.0 | Núcleo de interpretación Gherkin |
| **Framework BDD** | Cucumber Java (`io.cucumber:cucumber-java`) | 7.18.0 | Enlace de pasos con métodos Java |
| **Framework BDD** | Cucumber JUnit (`io.cucumber:cucumber-junit`) | 7.18.0 | Integración histórica con JUnit 4 |
| **Framework BDD** | Cucumber Engine (`cucumber-junit-platform-engine`) | BOM 7.13.0 / 7.18.0 | Motor de ejecución nativo para JUnit 5 Platform |
| **Automatización Web** | Selenium Java (`selenium-java`) | 4.20.0 | Control programático del navegador web |
| **Pruebas de API** | REST Assured (`rest-assured`) | 5.3.1 | Cliente DSL para validación de servicios REST |
| **Pruebas de API / HTTP** | Apache HttpClient | 4.5.14 | Cliente HTTP a bajo nivel |
| **Aserciones** | AssertJ Core (`assertj-core`) | 3.23.1 | Aserciones fluidas para tests |
| **Aserciones** | Hamcrest | 2.2 | Matchers para validaciones de API y respuestas |
| **Serialización / Datos** | Jackson Databind | 2.14.2 | Serialización y deserialización JSON <-> Java POJO |
| **Manipulación de Datos** | OpenCSV | 5.7.1 | Lectura y parseo de archivos CSV para pruebas data-driven |
| **Utilidades de Cadenas** | Apache Commons Lang3 | 3.0 | Utilidades auxiliares (mencionado para random string generation) |
| **Programación Funcional** | Vavr | 1.0.0-alpha-4 | Estructuras inmutables y control funcional |
| **Logging** | Logback Classic / Core | 1.4.5 | Implementación del sistema de logs |
| **Logging** | Janino | 3.1.9 | Compilador en tiempo de ejecución para configuraciones Logback |
| **Reportes** | Allure JUnit 5 (`allure-junit5`) | 2.23.0 | Generación de reportes gráficos y métricas de ejecución |

---

## Contratos (si existen)

No se identifican interfaces formales de contratos (Swagger/OpenAPI schemas, JSON Schemas, contratos Pact ni interfaces Java estrictas de capa de servicio) en el código fuente actual.

A nivel de arquitectura de código, existe un **contrato implícito de diseño**:
- **Contrato de Interacción Web (`BasePage`)**: Define la especificación y comportamiento estándar que deben respetar las páginas del sistema para interactuar con la interfaz gráfica, centralizando el uso obligatorio de esperas explícitas (`WebDriverWait`) y el acceso protegido al `WebDriver`.

---

## Módulos de datos

Actualmente **no existen** clases de modelo de datos (POJOs, DTOs, Java Records ni entidades de dominio) definidas dentro de los paquetes `src/main` o `src/test`.

Sin embargo, el proyecto cuenta con la infraestructura de dependencias requerida para soportar:
- Mapeo objeto-relacional / JSON mediante **Jackson Databind** (`com.fasterxml.jackson.core:jackson-databind:2.14.2`).
- Soporte para pruebas basadas en datos (Data-Driven Testing) a través de archivos tabulares con **OpenCSV** (`com.opencsv:opencsv:5.7.1`).
- Manipulación de tipos de datos funcionales mediante **Vavr** (`io.vavr:vavr`).

---

## Estándares y convenciones del código

1. **Patrón Page Object Model (POM)**:
   - Separación estricta entre la representación de la vista (`pages`) y los pasos de ejecución (`steps`).
   - Reutilización de comportamiento mediante herencia de clase base (`BasePage`).

2. **Gestión de Sesión y Concurrencia**:
   - Uso de `ThreadLocal<WebDriver>` en `DriverManager` para garantizar aislamiento por hilo, evitando colisiones en caso de ejecución paralela.
   - Ciclo de vida gestionado mediante ganchos (`@Before` y `@After`) de Cucumber.

3. **Convenciones de Nomenclatura**:
   - Paquetes en minúsculas en el nivel raíz de test (`driver`, `hook`, `pages`, `steps`).
   - Clases en PascalCase (`DriverManager`, `BasePage`, `HomePage`, `CreateUserStep`, `Hook`).
   - Métodos en camelCase con verbos de acción claros (`navigateTo`, `find`, `click`, `type`, `getText`, `isDisplayed`, `getDriver`, `quitDriver`, `setup`, `tearDown`).

4. **Gestión de Esperas y Sincronización**:
   - Uso de `WebDriverWait` en conjunción con `ExpectedConditions.visibilityOfElementLocated` dentro de `BasePage` para mitigar la fragilidad de pruebas por asincronía en el DOM.
   - Configuración global de espera implícita (`implicitlyWait(Duration.ofSeconds(10))`) en la inicialización del driver.

5. **Organización BDD**:
   - Rutas de recursos organizadas jerárquicamente por funcionalidad: `src/test/resources/Features/<NombreFeature>/<NombreFeature>.feature`.

---

## Riesgos, supuestos y limitaciones

1. **Riesgo: Mezcla de Esperas Implícitas y Explícitas**:
   - `DriverManager` define una espera implícita global de 10 segundos (`Duration.ofSeconds(10)`), mientras que `BasePage` aplica esperas explícitas de 15 segundos (`WebDriverWait`).
   - *Impacto*: La documentación oficial de Selenium desaconseja expresamente combinar ambas esperas, ya que genera comportamientos indeterminados y penalizaciones severas de tiempo ante elementos no presentes.

2. **Limitación: Ausencia de Runner de Pruebas (Test Runner)**:
   - No existe una clase anotada con `@Suite` o configurada con `Cucumber` para ejecutar los tests desde Gradle o el IDE bajo JUnit Platform. La tarea `tasks.test { useJUnitPlatform() }` no descubrirá escenarios sin un Runner o sin configurar el motor de Cucumber en el platform engine.

3. **Limitación: Artefactos Incompletos / Scaffolding**:
   - El archivo `CreateUser.feature` se encuentra vacío (0 bytes).
   - La clase `CreateUserStep.java` no tiene implementados métodos anotados.
   - `HomePage.java` no contiene localizadores ni acciones.

4. **Inconsistencias y Redundancias en el Gestor de Dependencias (`build.gradle`)**:
   - Coexisten versiones dispares de Cucumber BOM (`io.cucumber:cucumber-bom:7.13.0` junto con dependencias directas de Cucumber en versión `7.18.0`).
   - Se incluye `testng:7.10.1` a pesar de que el proyecto está configurado para JUnit Platform (`tasks.test { useJUnitPlatform() }`).
   - Múltiples dependencias exclusivas de pruebas (`cucumber-core`, `cucumber-java`, `janino`, `logback`) están declaradas bajo la configuración `implementation` en lugar de `testImplementation`.

5. **Riesgo: Acoplamiento de Driver y Falta de Parametrización**:
   - `DriverManager` instancia directamente `ChromeDriver` de manera fija sin permitir selección dinámica de navegador (Edge, Firefox) ni ejecución en modo headless (`--headless=new`), lo que complicará la ejecución en entornos de Integración Continua (CI/CD) desprovistos de interfaz gráfica.

6. **Código Residual en Producción**:
   - `src/main/java/org/example/Main.java` contiene la plantilla autogenerada de IntelliJ con comentarios de tips y código de prueba local no relacionado con la suite de pruebas.
