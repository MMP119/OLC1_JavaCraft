// ------------  Paquete e importaciones ------------
package interprete; 

import java_cup.runtime.*;

%%	
//-------> Directivas (No tocar)

%public 
%class Lexer
%cup
%char
%column
%line
%unicode
%ignorecase

%{ 
%} 

// ------> Expresiones Regulares 

entero = [0-9]+
decimal = [0-9]+[\.][0-9]+
booleano = (true|false)
cadena = [\"]((\\[\"'\\tnr])|[^\"\\])*[\"]
caracter = [\']((\\[\"'\\tnr])|[^\'\\])[\']
comentario = [\/][\/]([^\n]*)?
comentarioMulti = [\/][\*][\s\S]*?[\*][\/*]
Id = (\_)*[a-zA-Z][a-zA-Z0-9\_]*


%%
// ------------  Reglas Lexicas -------------------


//Tipos de datos
"int"               {return new Symbol(sym.INT, yycolumn, yyline, yytext());}
"double"            {return new Symbol(sym.DOUBLE, yycolumn, yyline, yytext());}
"bool"              {return new Symbol(sym.BOOL, yycolumn, yyline, yytext());}
"char"              {return new Symbol(sym.CHAR, yycolumn, yyline, yytext());}
"string"            {return new Symbol(sym.STRING, yycolumn, yyline, yytext());}


//simbolos
";"                 {return new Symbol(sym.PYC, yycolumn, yyline, yytext());}
":"                 {return new Symbol(sym.DOS_P, yycolumn, yyline, yytext());}
"+"                 {return new Symbol(sym.MAS, yycolumn, yyline, yytext());}
"-"                 {return new Symbol(sym.MENOS, yycolumn, yyline, yytext());}
"**"                {return new Symbol(sym.POTENCIA, yycolumn, yyline, yytext());}
"*"                 {return new Symbol(sym.POR, yycolumn, yyline, yytext());}
"/"                 {return new Symbol(sym.DIVIDIR, yycolumn, yyline, yytext());}
"%"                 {return new Symbol(sym.MODULO, yycolumn, yyline, yytext());}
"!"                 {return new Symbol(sym.NOT, yycolumn, yyline, yytext());}
"=="                {return new Symbol(sym.IGUALACION, yycolumn, yyline, yytext());}
"!="                {return new Symbol(sym.DIFERENCIACION, yycolumn, yyline, yytext());}
"<="                {return new Symbol(sym.MENOR_IGUAL, yycolumn, yyline, yytext());}
">="                {return new Symbol(sym.MAYOR_IGUAL, yycolumn, yyline, yytext());}
"="                 {return new Symbol(sym.IGUAL, yycolumn, yyline, yytext());}
"<"                 {return new Symbol(sym.MENOR_Q, yycolumn, yyline, yytext());}
">"                 {return new Symbol(sym.MAYOR_Q, yycolumn, yyline, yytext());}
"||"                {return new Symbol(sym.OR, yycolumn, yyline, yytext());}
"&&"                {return new Symbol(sym.AND, yycolumn, yyline, yytext());}
"^"                 {return new Symbol(sym.XOR, yycolumn, yyline, yytext());}
"("                 {return new Symbol(sym.A_PARENTESIS, yycolumn, yyline, yytext());}
")"                 {return new Symbol(sym.C_PARENTESIS, yycolumn, yyline, yytext());}
"{"                 {return new Symbol(sym.A_LLAVE, yycolumn, yyline, yytext());}
"}"                 {return new Symbol(sym.C_LLAVE, yycolumn, yyline, yytext());}
"["                 {return new Symbol(sym.A_CORCHETE, yycolumn, yyline, yytext());}
"]"                 {return new Symbol(sym.C_CORCHETE, yycolumn, yyline, yytext());}
","                 {return new Symbol(sym.COMA, yycolumn, yyline, yytext());}
"."                 {return new Symbol(sym.PUNTO, yycolumn, yyline, yytext());}
"_"                 {return new Symbol(sym.GUION_BAJO, yycolumn, yyline, yytext());}


//palabras reservadas
"const"             {return new Symbol(sym.CONSTANTE, yycolumn, yyline, yytext());}
"var"               {return new Symbol(sym.VARIABLE, yycolumn, yyline, yytext());}
"if"                {return new Symbol(sym.IF, yycolumn, yyline, yytext());}
"else"              {return new Symbol(sym.ELSE, yycolumn, yyline, yytext());}
"match"             {return new Symbol(sym.MATCH, yycolumn, yyline, yytext());}
"default"           {return new Symbol(sym.DEFAULT, yycolumn, yyline, yytext());}
"while"             {return new Symbol(sym.WHILE, yycolumn, yyline, yytext());}
"for"               {return new Symbol(sym.FOR, yycolumn, yyline, yytext());}
"do"                {return new Symbol(sym.DO, yycolumn, yyline, yytext());}
"break"             {return new Symbol(sym.BREAK, yycolumn, yyline, yytext());}
"continue"          {return new Symbol(sym.CONTINUE, yycolumn, yyline, yytext());}
"return"            {return new Symbol(sym.RETURN, yycolumn, yyline, yytext());}
"list"              {return new Symbol(sym.LIST, yycolumn, yyline, yytext());}
"new"               {return new Symbol(sym.NEW, yycolumn, yyline, yytext());}
"append"            {return new Symbol(sym.APPEND, yycolumn, yyline, yytext());}
"remove"            {return new Symbol(sym.REMOVE, yycolumn, yyline, yytext());}
"struct"            {return new Symbol(sym.STRUCT, yycolumn, yyline, yytext());}
"void"              {return new Symbol(sym.VOID, yycolumn, yyline, yytext());}


//funciones
"println"           {return new Symbol(sym.PRINTLN, yycolumn, yyline, yytext());}
"round"             {return new Symbol(sym.ROUND, yycolumn, yyline, yytext());}
"length"            {return new Symbol(sym.LENGTH, yycolumn, yyline, yytext());}
"toString"          {return new Symbol(sym.TOSTRING, yycolumn, yyline, yytext());}
"find"              {return new Symbol(sym.FIND, yycolumn, yyline, yytext());}
"start_with"        {return new Symbol(sym.START_WITH, yycolumn, yyline, yytext());}


//expresiones regulares
{entero}            {return new Symbol(sym.ENTERO, yycolumn, yyline, yytext());}
{decimal}           {return new Symbol(sym.DECIMAL, yycolumn, yyline, yytext());}
{booleano}          {return new Symbol(sym.BOOLEANO, yycolumn, yyline, yytext());}
{comentario}        {}
{comentarioMulti}   {}
{cadena}            {return new Symbol(sym.CADENA, yycolumn, yyline, yytext().replace("\"", ""));}
{caracter}          {return new Symbol(sym.CARACTER, yycolumn, yyline, yytext().replace("\'", ""));}
{Id}                {return new Symbol(sym.ID, yycolumn, yyline, yytext());}



//------> Ingorados 
[ \t\r\n\f]     {/* Espacios en blanco se ignoran */}

//------> Errores Léxicos 
.           	{ System.out.println("Error Lexico: " + yytext() + " | Fila:" + yyline + " | Columna: " + yycolumn); }