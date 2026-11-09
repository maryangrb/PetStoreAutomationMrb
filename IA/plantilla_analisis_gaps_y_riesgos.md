# Plantilla Estándar: Análisis de GAPs, Riesgos y Ambigüedades

> **Propósito:** Esta plantilla sirve como estándar corporativo de QA para registrar el análisis crítico de especificaciones funcionales contrastadas contra su diseño de interfaz (mockups, wireframes o UI desplegada).

---

## 1. Metadatos y Control de Versión

| Parámetro | Detalle |
| :--- | :--- |
| **Funcionalidad / Módulo:** | `[NOMBRE_FUNCIONALIDAD]` |
| **Documento Funcional Analizado:** | `[NOMBRE_ARCHIVO_FUNCIONAL]` (Versión `[VERSION]`) |
| **Fuente de Verdad Visual (Diseño / UI):** | `[URL_O_ARCHIVO_DE_DISEÑO]` |
| **Fecha de Análisis:** | `[FECHA_ANALISIS]` |
| **Analista QA Responsable:** | `[NOMBRE_O_ROL_QA]` |
| **Estado de Madurez Funcional:** | `[ ] APROBADO / [ ] OBSERVADO / [ ] BLOQUEADO` |

---

## 2. Validación y Respeto de Alcance

### 2.1. Alcance Incluyente (§ Incluye)
*Listado explícito de capacidades que forman parte del análisis actual:*
- `[Elemento dentro de alcance 1]`
- `[Elemento dentro de alcance 2]`

### 2.2. Alcance Excluyente (§ Excluye)
*Listado explícito de capacidades fuera de alcance (no deben asumirse ni exigirse):*
- `[Elemento fuera de alcance 1]`
- `[Elemento fuera de alcance 2]`

---

## 3. Matriz de GAPs (Diseño vs. Documento Funcional)

*Un GAP representa una discrepancia entre lo que muestra el diseño/UI (fuente de verdad) y lo que describe la especificación funcional.*

| ID GAP | Componente / Campo | En Diseño / UI (Fuente de Verdad) | En Documento Funcional | Clasificación | Severidad | Impacto en QA / Testing |
| :---: | :--- | :--- | :--- | :---: | :---: | :--- |
| **GAP-01** | `[Campo/Control]` | `[Comportamiento observado/diseñado]` | `[Texto del requerimiento]` | `[Visual / Negocio / Navegación / Datos]` | `[Crítica / Mayor / Menor]` | `[Impacto en casos de prueba o scripts]` |
| **GAP-02** | `[Campo/Control]` | `[Comportamiento observado/diseñado]` | `[Texto del requerimiento]` | `[Visual / Negocio / Navegación / Datos]` | `[Crítica / Mayor / Menor]` | `[Impacto en casos de prueba o scripts]` |

---

## 4. Matriz de Riesgos de Calidad

*Riesgos técnicos, funcionales, de experiencia de usuario o de automatización derivados de las discrepancias o ausencias de definición.*

| ID RKG | Tipo de Riesgo | Descripción del Evento de Riesgo | Probabilidad | Impacto | Nivel de Riesgo | Estrategia de Mitigación / Acción QA |
| :---: | :--- | :--- | :---: | :---: | :---: | :--- |
| **RKG-01** | `[Funcional / Técnico / UX / Automatización]` | `[Descripción clara del riesgo]` | `[Alta / Media / Baja]` | `[Alto / Medio / Bajo]` | `[Crítico / Alto / Medio / Bajo]` | `[Acción preventiva o diseño de prueba especial]` |
| **RKG-02** | `[Funcional / Técnico / UX / Automatización]` | `[Descripción clara del riesgo]` | `[Alta / Media / Baja]` | `[Alto / Medio / Bajo]` | `[Crítico / Alto / Medio / Bajo]` | `[Acción preventiva o diseño de prueba especial]` |

---

## 5. Registro de Ambigüedades y Preguntas Abiertas

*Vacíos conceptuales, comportamientos imprecisos o criterios de aceptación incompletos que requieren pronunciamiento del Product Owner o Arquitectura.*

| ID AMB | Sección / Campo | Ambigüedad o Vacío Detectado | Pregunta Concreta para PO / Negocio | Impacto si no se Resuelve |
| :---: | :--- | :--- | :--- | :--- |
| **AMB-01** | `[Sección/Campo]` | `[Detalle de la ambigüedad]` | `[Pregunta directa y cerrada/concreta]` | `[Riesgo de retrabajo o bloqueo]` |
| **AMB-02** | `[Sección/Campo]` | `[Detalle de la ambigüedad]` | `[Pregunta directa y cerrada/concreta]` | `[Riesgo de retrabajo o bloqueo]` |

---

## 6. Dictamen de Readiness para Testing y Próximos Pasos

### 6.1. Criterios de Entrada a Pruebas
- [ ] **Diseño de Casos de Prueba (Test Design):** ¿Los flujos principales y alternos están suficientemente claros para diseñar casos?
- [ ] **Automatización de Pruebas (Test Automation):** ¿Existen selectores e identificadores estables y contratos definidos?
- [ ] **Ejecución en Ambiente:** ¿Los endpoints y datos de prueba están disponibles y autorizados?

### 6.2. Recomendación Final de QA
`[Conclusión técnica del evaluador QA: Apto para testing / Apto con salvedades / No apto hasta resolver bloqueantes]`
