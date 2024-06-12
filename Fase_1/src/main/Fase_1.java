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
                        //esto es un comentario
                        var edad : double = (double) 16;

                        println(edad);
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
