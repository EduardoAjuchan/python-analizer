%% 
%public
%class PythonLexer
%unicode

%{
  // Puedes agregar importaciones o variables globales aquí si es necesario.
%}

/* Definición de macros para facilitar la lectura */
DIGIT = [0-9]
LETTER = [a-zA-Z_]
IDENTIFIER = {LETTER}({LETTER}|{DIGIT})*

%%

/* Palabras reservadas: Se definen primero para que no sean capturadas por la regla general de identificadores */
"and"                   { return "RESERVED: " + yytext(); }
"as"                    { return "RESERVED: " + yytext(); }
"assert"                { return "RESERVED: " + yytext(); }
"break"                 { return "RESERVED: " + yytext(); }
"class"                 { return "RESERVED: " + yytext(); }
"continue"              { return "RESERVED: " + yytext(); }
"def"                   { return "RESERVED: " + yytext(); }
"del"                   { return "RESERVED: " + yytext(); }
"elif"                  { return "RESERVED: " + yytext(); }
"else"                  { return "RESERVED: " + yytext(); }
"except"                { return "RESERVED: " + yytext(); }
"False"                 { return "RESERVED: " + yytext(); }
"finally"               { return "RESERVED: " + yytext(); }
"for"                   { return "RESERVED: " + yytext(); }
"from"                  { return "RESERVED: " + yytext(); }
"global"                { return "RESERVED: " + yytext(); }
"if"                    { return "RESERVED: " + yytext(); }
"import"                { return "RESERVED: " + yytext(); }
"in"                    { return "RESERVED: " + yytext(); }
"is"                    { return "RESERVED: " + yytext(); }
"lambda"                { return "RESERVED: " + yytext(); }
"None"                  { return "RESERVED: " + yytext(); }
"nonlocal"              { return "RESERVED: " + yytext(); }
"not"                   { return "RESERVED: " + yytext(); }
"or"                    { return "RESERVED: " + yytext(); }
"pass"                  { return "RESERVED: " + yytext(); }
"raise"                 { return "RESERVED: " + yytext(); }
"return"                { return "RESERVED: " + yytext(); }
"True"                  { return "RESERVED: " + yytext(); }
"try"                   { return "RESERVED: " + yytext(); }
"while"                 { return "RESERVED: " + yytext(); }
"with"                  { return "RESERVED: " + yytext(); }
"yield"                 { return "RESERVED: " + yytext(); }

/* Comentarios: todo lo que inicia con '#' hasta el fin de línea */
"#".*                   { return "COMMENT: " + yytext(); }

/* Literales de cadena: soporta comillas simples y dobles (forma simple, sin considerar cadenas multilínea) */
\"([^\\\n]|(\\.))*?\"   { return "STRING: " + yytext(); }
\'([^\\\n]|(\\.))*?\'   { return "STRING: " + yytext(); }

/* Constantes numéricas: reconoce enteros y números con parte decimal */
{DIGIT}+("."{DIGIT}+)?  { 
                           if(yytext().indexOf('.') != -1) 
                              return "FLOAT: " + yytext(); 
                           else 
                              return "INT: " + yytext();
                         }

/* Operadores de dos o más caracteres: se listan primero para evitar conflictos */
"=="                    { return "OPERATOR: " + yytext(); }
"!="                    { return "OPERATOR: " + yytext(); }
"<="                    { return "OPERATOR: " + yytext(); }
">="                    { return "OPERATOR: " + yytext(); }
"//"                    { return "OPERATOR: " + yytext(); }
"\*\*"                  { return "OPERATOR: " + yytext(); }

/* Operadores de un solo carácter */
"="                     { return "OPERATOR: " + yytext(); }
"<"                     { return "OPERATOR: " + yytext(); }
">"                     { return "OPERATOR: " + yytext(); }
"\+"                    { return "OPERATOR: " + yytext(); }
"-"                     { return "OPERATOR: " + yytext(); }
"\*"                    { return "OPERATOR: " + yytext(); }
"/"                     { return "OPERATOR: " + yytext(); }
"%"                     { return "OPERATOR: " + yytext(); }

/* Identificadores: variables, nombres de funciones, etc. */
{IDENTIFIER}            { return "VARIABLE: " + yytext(); }

/* Espacios en blanco: Retorna espacios y tabulaciones */
[ \t]+                  { return "WHITESPACE: " + yytext(); }

/* Saltos de línea: Retorna el salto de línea */
[\r\n]+                { return "NEWLINE: " + yytext(); }

/* Cualquier otro símbolo (puntuación, paréntesis, etc.) */
.                      { return "SYMBOL: " + yytext(); }
