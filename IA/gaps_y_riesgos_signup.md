# Informe QA: Análisis de GAPs, Riesgos y Ambigüedades — Funcionalidad Sign Up

**Proyecto:** PetStore Automation (`C:\Users\marya\IdeaProjects\PetStoreAutomation`)  
**Documento Analizado:** [documento_funcional_signup_v4.md](file:///c:/Users/marya/IdeaProjects/PetStoreAutomation/IA/documento_funcional_signup_v4.md) (Versión 4.0)  
**Fuente de Verdad Visual:** Inspección de UI y Formulario de Registro en `https://petstore.octoperf.com/account/new`  
**Rol Evaluador:** QA Senior / Lead QA  
**Fecha de Análisis:** 8 de noviembre de 2026  
**Estado:** **OBSERVADO CON GAPs CRÍTICOS** (Requiere alineación de criterios de aceptación antes de automatizar)

---

## 1. Resumen Ejecutivo de Calidad

Se realizó una revisión técnica y funcional exhaustiva del documento funcional **Sign Up v4.0** contrastándolo directamente contra la interfaz de usuario observada en la aplicación (`https://petstore.octoperf.com/account/new`). 

El documento funcional presenta una excelente evolución respecto a versiones previas (ha alineado nombres de controles, opciones de listas desplegables y ha sincerado el alcance). No obstante, persisten **GAPs significativos** entre la expectativa especificada (validaciones inline, códigos `REG_*`, contrato JSON) y la realidad técnica de la UI/Backend (formulario HTML tradicional con campos ocultos MVC sin validación cliente aparente).

---

## 2. Validación y Respeto Estricto de Alcance

Conforme a las directrices de aseguramiento de calidad, se valida que los límites de alcance se respetan íntegramente:

### 2.1. Alcance Incluyente (§2 § Incluye) — Mantenido
- Alta y validación de credenciales (`User ID`, contraseña y confirmación).
- Captura de información de contacto, dirección y datos personales.
- Configuración de preferencias de perfil (Idioma, Categoría favorita, MyList, MyBanner).
- Validación en servidor y persistencia del formulario tras error.
- Redirección post-registro a Home (`/catalog`).

### 2.2. Alcance Excluyente (§2 § Excluye) — Respetado sin Alteraciones
- **No se exige inicio de sesión automático post-registro** (el usuario debe quedar sin sesión activa).
- **No se exige recuperación de contraseña** ni edición de perfil.
- **No se exige verificación por correo electrónico** (activación diferida; la cuenta nace `ACTIVE`).
- **No se exige aceptación de Términos y Condiciones** ni casilla de consentimiento.
- **No se exige CAPTCHA, Rate Limiting ni SSO / OAuth**.

---

## 3. Matriz de GAPs (Diseño / UI vs. Documento Funcional)

| ID GAP | Componente / Campo | En Diseño / UI (Fuente de Verdad) | En Documento Funcional v4.0 | Clasificación | Severidad | Impacto en QA y Automatización |
| :---: | :--- | :--- | :--- | :---: | :---: | :--- |
| **GAP-01** | **Indicador de Obligatoriedad de Campos** | La UI **no muestra asteriscos (`*`)**, textos de "requerido" ni atributos HTML5 `required` en ningún campo. | §6.1 y §12 (Escenario 5) declaran obligatorios todos los campos excepto *Address 2* y preferencias. | Visual / UX | **Media** | Los tests automatizados no pueden validar obligatoriedad vía atributos del DOM en cliente (`required`); deben probarse obligatoriamente mediante submit HTTP y respuesta de servidor. |
| **GAP-02** | **Estilos y Comportamiento de Errores Inline** | La UI clásica de PetStore no cuenta con contenedores DOM para errores inline ni clases CSS de "borde rojo". | §7.4 y §12 (Escenario 5) exigen "resaltar de manera inline, con borde rojo los campos con error y situar foco en el primero". | Visual / Funcional | **Alta** | Si los scripts de automatización esperan `cssSelector` o bordes de error inline que la app no renderiza, los tests fallarán en falso positivo (*flaky/broken tests*). |
| **GAP-03** | **Control de Visibilidad de Contraseña (Toggle)** | Solo existen inputs nativos `<input type="password">` sin botón ni icono para mostrar/ocultar contraseña. | §7.2 especifica: "Enmascaramiento por defecto con toggle visual ('mostrar/ocultar contraseña')". | Visual / Control UI | **Media** | No se puede automatizar la interacción con el toggle a menos que frontend lo implemente en esta versión. |
| **GAP-04** | **Contrato de Respuesta y Códigos de Error (`REG_*`)** | El envío es un submit tradicional de formulario HTML (`POST /account/new`). No responde con objetos JSON. | §6.2 y §6.3 definen payloads JSON con códigos de error `REG_001` a `REG_500` y HTTP 409/400. | Contrato Técnico | **Crítica** | Si QA diseña pruebas de API esperando respuestas `application/json` con `REG_*`, todas las aserciones fallarán frente al comportamiento real del backend. |
| **GAP-05** | **Campos Ocultos de Formulario MVC (`_sourcePage`, `__fp`)** | El formulario HTML incluye campos ocultos obligatorios generados dinámicamente (`_sourcePage` y `__fp`) para control de flujo MVC. | En §6.1 no se listan como parámetros de entrada; se relegan a consulta abierta en Anexo A (P14). | Técnico / Datos | **Alta** | Cualquier automatización a nivel de servicios/API o pruebas de rendimiento que no capture estos tokens previos fallará con error 400 o rechazo del submit. |
| **GAP-06** | **Restricciones de Longitud y Máscaras en HTML** | Los campos `User ID` y `phone` no tienen atributos `maxlength="10"` ni `pattern` en el DOM; admiten cualquier cantidad de texto en el input. | §6.1 exige "Exactamente 10 caracteres" para User ID y "Exactamente 9 caracteres" para Phone. | Validación / UI | **Media** | El usuario puede teclear cadenas inválidas libremente en UI; la validación recae 100% en el servidor tras hacer clic en guardar. |
| **GAP-07** | **Botón Cancelar / Limpiar** | La UI solo dispone del botón `<input type="submit" name="newAccount" value="Save Account Information">`. | §5 Flujos Alternos y Anexo B confirman que no existe botón de reset/cancelar, pero queda como duda abierta (P8). | Navegación | **Baja** | No debe diseñarse ningún caso de prueba para cancelación de formulario hasta que PO descarte formalmente P8. |

---

## 4. Matriz de Riesgos de Calidad

| ID RKG | Categoría | Descripción del Riesgo | Probabilidad | Impacto | Nivel de Riesgo | Mitigación Sugerida / Acción QA |
| :---: | :---: | :--- | :---: | :---: | :---: | :--- |
| **RKG-01** | **Automatización** | **Aserciones fallidas por expectativa de códigos `REG_*`:** Los Criterios de Aceptación (§12) vinculan el éxito del test a la presencia de `REG_001`, `REG_002`, `REG_005`. Al no estar implementados en la UI/backend actual, los tests automatizados fallarán. | **Alta** | **Crítico** | **Crítico** | Ajustar los criterios de aceptación en Gherkin para validar textos visibles reales en pantalla (o cabeceras de redirección) en lugar de códigos internos no expuestos. |
| **RKG-02** | **Funcional / UX** | **Degradación de experiencia por pérdida de estado (Persistencia):** Si el servidor no conserva los datos ingresados ante un error de validación, el usuario deberá reingresar los 12 campos del formulario. | **Media** | **Alto** | **Alto** | Ejecutar pruebas exploratorias previas de caja negra para verificar qué campos persisten realmente antes de codificar la suite de regresión. |
| **RKG-03** | **Seguridad** | **Inyección de datos masivos y falta de sanitización:** Al excluir deliberadamente CAPTCHA y confirmación por email (§2), la base de datos es vulnerable a inyección masiva de cuentas fantasma durante pruebas de carga o automatización. | **Alta** | **Medio** | **Medio** | Implementar un script de teardown/limpieza periódica de base de datos de pruebas para depurar cuentas creadas con prefijos `test_*`. |
| **RKG-04** | **Técnico / Integración** | **Validación de unicidad insensible a mayúsculas:** Si la base de datos o el motor de persistencia utiliza collation sensible (`case-sensitive`), "UserTest" y "usertest" podrían coexistir, rompiendo la regla §7.1. | **Media** | **Alto** | **Alto** | Crear casos de prueba de borde con combinaciones mayúsculas/minúsculas para verificar el comportamiento de la base de datos real. |
| **RKG-05** | **Pruebas en Ambiente** | **Falta de autorización formal para crear datos en entorno demo (P5):** Si no se aprueba la creación de registros en `petstore.octoperf.com`, no se pueden ejecutar pruebas funcionales de validación de unicidad ni flujos de error. | **Alta** | **Alto** | **Alto** | Obtener confirmación inmediata del PO/Lead para el uso de credenciales y namespaces de prueba dedicados (ej. `qa_auto_*`). |

---

## 5. Registro de Ambigüedades y Preguntas Abiertas Críticas

| ID AMB | Sección / Campo | Ambigüedad o Vacío Detectado | Pregunta Concreta para PO / Negocio | Impacto si no se Define |
| :---: | :--- | :--- | :--- | :--- |
| **AMB-01** | §6.1 / §7.2 (Password) | Se indica que se requiere al menos un carácter especial y que `+` es válido, pero no existe la lista blanca/negra de símbolos permitidos. | **¿Cuál es la lista exacta de caracteres especiales permitidos?** (¿Ej. `!@#$%^&*()_+-=[]{}|;:,.<>?` o cualquier carácter ASCII no alfanumérico?). | Casos de prueba de seguridad incompletos; riesgo de validaciones rechazadas incorrectamente. |
| **AMB-02** | §6.1 (Phone) | Se indica que `Phone` tiene exactamente 9 caracteres con prefijo `+` opcional, indicando en P15 que se asume que el `+` cuenta en los 9. | **¿Si el teléfono incluye `+`, la longitud total es de 9 caracteres (ej: `+12345678`) o es de 10 (`+` más 9 dígitos)?** | Errores en la validación de regex y en los datasets de prueba automatizada. |
| **AMB-03** | §5 / §12 (Escenario 1) | Al completar el registro exitoso, el usuario es redirigido a `/catalog` (Home). No se especifica si debe aparecer un mensaje de confirmación ("Cuenta creada con éxito"). | **¿Al redirigir a `/catalog`, el sistema debe desplegar un banner o mensaje de bienvenida/éxito, o la pantalla queda neutra?** | Criterio de aserción del Escenario 1 incompleto en el framework de pruebas. |
| **AMB-04** | §6.2 / §6.3 (Errores) | Los códigos `REG_*` y respuestas JSON están descritos como propuestos pero no confirmados. | **¿Se implementará una capa de API JSON o se deben homologar los criterios de aceptación a los mensajes de texto HTML reales del servidor?** | Rediseño completo de los escenarios de excepción en Cucumber/Gherkin. |

---

## 6. Dictamen de Readiness para Testing

| Criterio de Entrada | Estado | Observación |
| :--- | :---: | :--- |
| **Diseño de Casos de Prueba Manuales (Test Cases)** | **Apto con Observaciones** | Se pueden diseñar los casos de prueba para el Happy Path y validaciones de datos, ajustando las aserciones a la espera de textos reales en lugar de `REG_*`. |
| **Automatización de Pruebas UI (Selenium / Playwright)** | **Apto con Precauciones** | Se cuenta con los selectores de los campos (`name="username"`, etc.). Las pruebas de errores deben posponerse hasta resolver GAP-02 y GAP-04. |
| **Automatización de Pruebas API / Backend** | **Bloqueado** | No se dispone de un endpoint REST documentado; la petición requiere tokens MVC (`_sourcePage`, `__fp`) que deben ser manejados mediante scraping/sesión. |

### Conclusión Final de QA:
> **Recomendación:** Se aprueba el inicio del diseño de casos de prueba enfocado en el **flujo principal de registro y datos de entrada**, pero **se debe congelar la automatización de escenarios de error (Escenarios 2, 3, 4 y 5)** hasta que el Product Owner responda las preguntas de ambigüedad **AMB-01, AMB-02 y AMB-04**, alineando los Criterios de Aceptación con las respuestas reales de la interfaz.
