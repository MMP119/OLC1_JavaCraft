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

entero  [0-9]+;
decimal [0-9]+[\.][0-9]+;
booleano ("true"|"false");
cadena [\"]((\\[\\"'\\tnr])|[^"\\])*[\"];
caracter [\']((\\[\\"'\\tnr])|[^\'\\])[\'];
comentario  [\/][\/]([^\n]*)?;
comentarioMulti [\/][\*][\s\S]*?[\*][\/*];
Id =  (\_)*[a-zA-Z][a-zA-Z0-9\_]*;


%%
// ------------  Reglas Lexicas -------------------

{entero}  { return new Symbol(sym.ENTERO, yycolumn, yyline, yytext()); }




//------> Ingorados 
[ \t\r\n\f]     {/* Espacios en blanco se ignoran */}

//------> Errores Léxicos 
.           	{ System.out.println("Error Lexico: " + yytext() + " | Fila:" + yyline + " | Columna: " + yycolumn); }