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

entero = -?[0-9]+
decimal = -?[0-9]+[\.][0-9]+
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
"("                 {return new Symbol(sym.A_PARENTESIS, yycolumn, yyline, yytext());}
")"                 {return new Symbol(sym.C_PARENTESIS, yycolumn, yyline, yytext());}
";"                 {return new Symbol(sym.PYC, yycolumn, yyline, yytext());}


//funciones
"println"           {return new Symbol(sym.PRINTLN, yycolumn, yyline, yytext());}


//expresiones regulares
{entero}            {return new Symbol(sym.ENTERO, yycolumn, yyline, yytext());}
{decimal}           {return new Symbol(sym.DECIMAL, yycolumn, yyline, yytext());}
{booleano}          {return new Symbol(sym.BOOLEANO, yycolumn, yyline, yytext());}
{comentario}        {}
{comentarioMulti}   {}
{cadena}            {return new Symbol(sym.CADENA, yycolumn, yyline, yytext().replace("\"", ""));}
{caracter}          {return new Symbol(sym.CARACTER, yycolumn, yyline, yytext().replace("\"", ""));}
{Id}                {return new Symbol(sym.ID, yycolumn, yyline, yytext());}




//------> Ingorados 
[ \t\r\n\f]     {/* Espacios en blanco se ignoran */}

//------> Errores Léxicos 
.           	{ System.out.println("Error Lexico: " + yytext() + " | Fila:" + yyline + " | Columna: " + yycolumn); }