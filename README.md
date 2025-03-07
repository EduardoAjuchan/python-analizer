# Proyecto Analizador Léxico Python

Este proyecto implementa un analizador léxico para código Python utilizando Java y JFlex, junto con una interfaz gráfica basada en Swing que permite al usuario cargar un archivo de texto con código Python y visualizarlo con resaltado de sintaxis.

---

## Estructura del Proyecto

- **/src**  
  Contiene todos los archivos fuente:
  - **PythonLexer.flex**: Especificación de JFlex con las reglas léxicas para identificar elementos del lenguaje Python.
  - **PythonLexer.java**: Archivo generado automáticamente por JFlex a partir de `PythonLexer.flex`.
  - **Main.java**: Clase principal que integra el lexer y la interfaz gráfica para cargar, analizar y mostrar el código.

- **/LIBRARIES**  
  Contiene el archivo JAR de JFlex (por ejemplo, `jflex-full-1.9.1.jar`).

---

## Archivo PythonLexer.flex

El archivo `PythonLexer.flex` define la gramática léxica para el lenguaje Python. Sus aspectos principales son:

- **Macros**:  
  Se definen expresiones regulares reutilizables, como:
  - `DIGIT = [0-9]`
  - `LETTER = [a-zA-Z_]`
  - `IDENTIFIER = {LETTER}({LETTER}|{DIGIT})*`

- **Reglas Léxicas**:  
  Las reglas están organizadas para reconocer:
  - **Palabras reservadas**: Por ejemplo, `def`, `if`, `else`, etc.  
    Estas reglas se definen al inicio para que tengan prioridad sobre las reglas generales de identificadores.
  - **Comentarios**:  
    Captura todo el texto que sigue al símbolo `#` hasta el final de la línea.
  - **Literales de cadena**:  
    Reconoce cadenas delimitadas por comillas simples o dobles, considerando caracteres escapados.
  - **Constantes numéricas**:  
    Detecta tanto enteros como números flotantes.
  - **Operadores**:  
    Se incluyen operadores compuestos (como `==`, `!=`, `<=`, `>=`, `//`, `**`) y operadores simples.
  - **Identificadores**:  
    Captura nombres de variables, funciones, etc.
  - **Espacios y saltos de línea**:  
    Se definen reglas separadas para espacios/tabulaciones (`WHITESPACE`) y para saltos de línea (`NEWLINE`), permitiendo preservar la estructura original del código.
  - **Otros símbolos**:  
    Se capturan símbolos restantes (puntuación, paréntesis, etc.) como `SYMBOL`.

Cada regla retorna un token en el formato `"TIPO: lexema"`, facilitando su identificación en la etapa de resaltado.

---

## Integración del Lexer

La integración del lexer se realiza en dos pasos:

1. **Generación del Lexer**:  
   Se ejecuta JFlex con el comando:  
   `java -jar [ruta_a_jflex]/jflex-full-1.9.1.jar PythonLexer.flex`  
   Esto genera el archivo `PythonLexer.java` basado en la especificación definida.

2. **Uso en el Código Java**:  
   En la clase `Main.java`, se instancia `PythonLexer` utilizando un `Reader` que carga el archivo de texto. El lexer procesa el contenido y retorna tokens conforme a las reglas definidas en el archivo `.flex`.

---

## Funcionamiento del Main

La clase `Main.java` es el punto de entrada de la aplicación y se encarga de lo siguiente:

- **Interfaz Gráfica**:  
  Se crea una ventana con Swing que contiene:
  - Un botón para cargar archivos mediante un `JFileChooser`.
  - Un componente `JTextPane` (dentro de un `JScrollPane`) para mostrar el código con resaltado de sintaxis.

- **Carga del Archivo**:  
  Al pulsar el botón "Cargar Archivo", el usuario selecciona un archivo de texto que contiene código Python.

- **Análisis Léxico y Resaltado**:  
  El contenido del archivo se analiza mediante `PythonLexer`, generando tokens que indican el tipo y el lexema.  
  Cada token se inserta en un `StyledDocument` del `JTextPane` aplicando un estilo específico (color) según su categoría.  
  La separación de tokens para espacios y saltos de línea permite que se preserve la estructura original del código.

- **Visualización**:  
  El código coloreado se muestra en el `JTextPane`, permitiendo al usuario observar el resaltado de sintaxis conforme a las reglas definidas.

---

## Uso del Proyecto

1. **Generar el Lexer**:  
   Ejecuta JFlex sobre el archivo `PythonLexer.flex` para generar `PythonLexer.java`.

2. **Compilar el Proyecto**:  
   Compila todos los archivos Java ubicados en el directorio `src`, incluyendo el archivo generado.

3. **Ejecutar la Aplicación**:  
   Inicia la aplicación ejecutando la clase `Main.java`. Se abrirá una ventana con la interfaz gráfica, desde donde podrás cargar un archivo de texto y ver el código resaltado.

---

## Conclusión

Este proyecto ofrece una base para el desarrollo de herramientas de análisis de código y resaltado de sintaxis. La modularidad de la especificación léxica y la integración con una interfaz gráfica en Java permiten futuras ampliaciones o adaptaciones a otros lenguajes o funcionalidades.


