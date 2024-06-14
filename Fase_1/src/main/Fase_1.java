/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import java.io.StringReader;

import interprete.*;
import entorno.*;
import java.util.LinkedList;
import instruccion.*;
import AST.*;

/**
 *
 * @author mario
 */
public class Fase_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String entrada = """
            const nota: int = 100;
            println("Operaciones aritmeticas");
            var cadena: string = "Vamos " + "por " + "ese " + nota;
            println(cadena);
            var operaciones: double = 1 + 9 - 8 * 2 / 4 % 2 + (5 ** 2);
            println("El resultado de las operaciones es " + operaciones);

            println("");

            println("Operaciones Relacionales");
            var a: bool = 1 < 2;
            println("El valor de a es " + a);

            var b: bool = 1 == 2;
            println("El valor de b es " + b);

            println("");

            println("Operaciones Logicas");
            var c: bool = !!true;
            println("El valor de c es " + c);

            var d: bool = (true && false) || (false || false);
            println("El valor de d es " + d);

            println("");

            println("Operaciones combinadas");
            var e: bool = (1 == 2) || (10 < 5) || (!false);
            println("El valor de e es " + e);

            println("");
            println("Ciclos, condicionales y saltos de control");
            println("");

            var j: int = 0;
            var k: int = 10;
            while (j <= k) {
                match 5 {
                    1 => { println("j es 1"); }
                    2 => { println("j es 2"); }
                    3 => { println("j es 3"); }
                    4 => { println("j es 4"); }
                    5 => { println("j es 5"); }
                    6 => { println("j es 6"); }
                    7 => { println("j es 7"); }
                    8 => { println("j es 8"); }
                    9 => { println("j es 9"); }
                    10 => { println("j es 10"); }
                    _ => { println("j es otro valor"); }
                }
                j++;
            }
                        """;
        
        // Generar Analizadores
        //analizadores("Fase_1/src/interprete/", "Lexer.jflex", "Parser.cup");
        // Analizar
        analizar(entrada);
    }

    public static void analizadores(String ruta, String jflexFile, String cupFile){
        try {
            String opcionesJflex[] =  {ruta+jflexFile,"-d",ruta};
            jflex.Main.generate(opcionesJflex);

            String opcionesCup[] =  {"-destdir", ruta,"-parser","Parser",ruta+cupFile};
            java_cup.Main.main(opcionesCup);
            
        } catch (Exception e) {
            System.out.println("No se ha podido generar los analizadores");
            System.out.println(e);
        }
    }

    // Realizar Analisis
    public static void analizar (String entrada){
        try {
            interprete.Lexer lexer = new interprete.Lexer(new StringReader(entrada)); 
            @SuppressWarnings("deprecation")
            interprete.Parser parser = new Parser(lexer);
            var resultado = parser.parse();
            @SuppressWarnings("unchecked")

            var ast = new Entorno((LinkedList<Instruccion>)resultado.value);
            var ts = new tablaSimbolos();
            ts.setNombre("Global");
            ast.setConsola("");
            var init = new NodoAst("INICIO");
            var instruc = new NodoAst("INSTRUCCIONES");
            for (var a: ast.getInstrucciones()){
                a.interpretar(ast, ts);
                instruc.agregarHijoAST(a.getNodo());
                ast.getConsola();
            }
            System.out.println(ast.getConsola());
            init.agregarHijoAST(instruc);

            ArbolAST arbol = new ArbolAST();
            arbol.graficarArbol(init);

        } catch (Exception e) {
            System.out.println("Error fatal en compilación de entrada.");
            System.out.println(e);
        } 
    } 

    
}
