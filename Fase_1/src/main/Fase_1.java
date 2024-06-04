/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import java.io.StringReader;

import interprete.*;

/**
 *
 * @author mario
 */
public class Fase_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        @SuppressWarnings("unused")
        String entrada = """
                        2
                        """;
        
        // Generar Analizadores
        analizadores("src/interprete/", "Lexer.jflex", "Parser.cup");
        // Analizar
        //analizar(entrada);
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
            parser.parse();
        } catch (Exception e) {
            System.out.println("Error fatal en compilación de entrada.");
            System.out.println(e);
        } 
    } 

    
}
