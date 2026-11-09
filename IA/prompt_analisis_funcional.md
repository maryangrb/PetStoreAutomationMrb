# Plantilla Estándar: Prompt para Análisis de Documentos Funcionales, GAPs y Riesgos

Este documento define el **prompt estandarizado** para que un agente o LLM actúe como **QA Senior** y realice un análisis exhaustivo de documentos funcionales contrastados contra el diseño (mockups/UI).

La plantilla está diseñada para ser **reutilizable** en cualquier funcionalidad del proyecto mediante variables (`[PARAMETRO]`), e incluye al final la versión configurada y lista para ejecutar con el caso de prueba de **Sign Up (`documento_funcional_signup_v4.md`)**.

---

## 1. Plantilla Estándar Reutilizable (Template)

```markdown
# Rol

Actúa como QA Lead / QA Senior especialista en Análisis de Requerimientos, Control de Calidad de Software y Gestión de Riesgos.

# Objetivo

Analizar minuciosamente el documento funcional ubicado en:
`[RUTA_DOCUMENTO_FUNCIONAL]`
correspondiente al proyecto:
`[RUTA_PROYECTO]`

Tu propósito es contrastar el documento funcional contra la fuente de verdad del diseño (mockups, capturas o interfaz de usuario referenciada en: `[REFERENCIA_DISEÑO_O_IMAGEN]`), identificando de forma estructurada:
1. **GAPs (Brechas):** Discrepancias entre lo descrito en el documento funcional y lo reflejado en el diseño/UI.
2. **Riesgos:** Amenazas funcionales, técnicas, de experiencia de usuario (UX) o de cobertura de pruebas.
3. **Ambigüedades:** Reglas de negocio incompletas, validaciones imprecisas o criterios de aceptación ambiguos.

El resultado debe guardarse como un documento Markdown en:
`[RUTA_SALIDA_MD]`

# Reglas de Comportamiento y Directrices Estrictas

1. **Fuente de verdad:** El diseño visual definido (`[REFERENCIA_DISEÑO_O_IMAGEN]`) es la fuente primaria de verdad para componentes, textos de interfaz, etiquetas, controles y disposición en pantalla.
2. **No inventar nada (Cero alucinaciones):** Cada hallazgo, GAP o riesgo debe estar estrictamente justificado en contradicciones reales entre el documento y el diseño, o en vacíos evidentes de especificación.
3. **Respeto estricto del alcance:** Los alcances incluyentes y excluyentes definidos en el documento funcional NO deben ser modificados, alterados ni reinterpretados.
4. **Privacidad y Seguridad:** No solicites ni expongas datos sensibles, credenciales reales, tokens ni PII (Información Personal Identificable).
5. **Resolución de dudas:** Si identificas una contradicción insalvable o una falta de información crítica que te impida clasificar un hallazgo, consúltale puntualmente al usuario antes de asumir cualquier comportamiento.
6. **Tono y formato:** Formal, analítico, técnico y orientado a asegurar la calidad y testabilidad (QA).

# Qué debes analizar

1. **Contraste UI vs. Documento Funcional:**
   - Campos de entrada: nombres reales, etiquetas visibles, placeholders, orden y agrupaciones.
   - Tipos de control: selectores, checkboxes, inputs de texto, botones.
   - Opciones y valores por defecto (ej. idiomas, listas desplegables).
   - Acciones principales, secundarias y enlaces de navegación (Header/Footer/Formulario).
2. **Validaciones y Reglas de Negocio:**
   - Obligatoriedad vs. opcionalidad de campos.
   - Longitudes mínimas, máximas, formatos permitidos y caracteres especiales.
   - Manejo de confirmaciones (ej. contraseñas coincidentes).
3. **Flujo de Usuario y Criterios de Aceptación:**
   - Flujo exitoso (Happy Path) y navegación posterior (redirección).
   - Mensajería de error, alertas y persistencia de datos tras error.
   - Estados de botones (habilitado/deshabilitado/loading).
4. **Matriz de Riesgos y Testabilidad:**
   - Casos borde no cubiertos o comportamientos no especificados.
   - Impacto en automatización de pruebas (localizadores estables, dependencias externas).

# Estructura del Archivo de Salida (`[RUTA_SALIDA_MD]`)

El archivo generado debe estructurarse obligatoriamente con las siguientes secciones:

## 1. Metadatos y Control de Versión
- **Funcionalidad:** [Nombre de la funcionalidad]
- **Documento analizado:** [Nombre y versión del archivo funcional]
- **Fuente de verdad visual:** [Referencia al diseño / URL / imagen]
- **Fecha de análisis:** [Fecha actual]
- **Estado de QA:** [Aprobado con observaciones / Requiere clarificación / Bloqueado]

## 2. Validación de Alcance
- Confirmación de los límites del requerimiento respetando íntegramente:
  - Alcance Incluyente (§ Incluye)
  - Alcance Excluyente (§ Excluye)

## 3. Matriz de GAPs (Diseño vs. Documento Funcional)
Tabla con las siguientes columnas:
| ID GAP | Componente / Campo | En Diseño (Fuente de Verdad) | En Documento Funcional | Clasificación (Visual / Negocio / Navegación) | Severidad (Bloqueante / Mayor / Menor) | Impacto en QA |

## 4. Matriz de Riesgos
Tabla con las siguientes columnas:
| ID RKG | Tipo de Riesgo (Funcional / Técnico / UX / Automatización) | Descripción del Riesgo | Probabilidad (Alta / Media / Baja) | Impacto (Alto / Medio / Bajo) | Estrategia de Mitigación Sugerida |

## 5. Registro de Ambigüedades y Preguntas Abiertas
Tabla con las siguientes columnas:
| ID AMB | Sección / Campo | Ambigüedad o Vacío Detectado | Pregunta Concreta para PO / Diseño | Impacto si no se define |

## 6. Dictamen de Readiness para Testing y Próximos Pasos
- Conclusión ejecutiva sobre si el documento cuenta con la madurez suficiente para generar matriz de casos de prueba y scripts de automatización.
```

---

## 2. Variables de Configuración para Otras Funcionalidades

Para reutilizar esta plantilla en futuras funcionalidades, solo debes reemplazar los siguientes parámetros:

| Variable | Descripción | Ejemplo |
| :--- | :--- | :--- |
| `[NOMBRE_FUNCIONALIDAD]` | Nombre del módulo o feature a analizar | `Sign In`, `Checkout`, `Cart` |
| `[RUTA_DOCUMENTO_FUNCIONAL]` | Ruta relativa o absoluta del documento funcional | `IA/documento_funcional_checkout_v1.md` |
| `[RUTA_PROYECTO]` | Ruta raíz del proyecto | `C:\Users\marya\IdeaProjects\PetStoreAutomation` |
| `[REFERENCIA_DISEÑO_O_IMAGEN]` | Mockup, captura de pantalla o URL de UI de referencia | `IA/diseno_signup.png` o `https://petstore.octoperf.com/account/new` |
| `[RUTA_SALIDA_MD]` | Ruta donde se guardará el informe de GAPs | `IA/gaps_y_riesgos_signup.md` |

---

## 3. Prompt Listo para Ejecutar (Caso Específico: Sign Up)

A continuación se presenta el prompt ya parametrizado para la solicitud específica requerida en `Solicitud1.md`:

```markdown
Actúa como QA Lead Senior especialista en Análisis de Requerimientos y Control de Riesgos.

Analiza el documento funcional que se encuentra en la ruta:
`IA/documento_funcional_signup_v4.md`
del proyecto:
`C:\Users\marya\IdeaProjects\PetStoreAutomation`

Define los GAPs, los riesgos y las ambigüedades técnicas y funcionales. Para esto:
1. Toma como fuente de verdad el diseño definido e inspección de UI (`https://petstore.octoperf.com/account/new` e imágenes de referencia del formulario de registro).
2. No inventes nada; cada hallazgo debe fundamentarse en inconsistencias reales o vacíos documentados.
3. No solicites datos sensibles ni credenciales.
4. Respeta estrictamente los alcances incluyentes y excluyentes del documento funcional (no los modifiques ni asumas funcionalidades excluidas como sesión inmediata, recuperación de contraseña o token).
5. Si tienes alguna duda crítica, pregúntame antes de asumir un comportamiento.

Genera y guarda el resultado estructurado en el archivo Markdown:
`IA/gaps_y_riesgos_signup.md`
siguiendo las secciones:
- 1. Metadatos y Control de Versión
- 2. Validación de Alcance (Inclusiones y Exclusiones)
- 3. Matriz de GAPs (Diseño vs. Documento Funcional)
- 4. Matriz de Riesgos
- 5. Registro de Ambigüedades y Preguntas Abiertas
- 6. Dictamen de Readiness para Testing y Próximos Pasos
```
