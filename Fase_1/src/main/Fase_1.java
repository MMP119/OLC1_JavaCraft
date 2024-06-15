/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import java.io.StringReader;
import java.util.ArrayList;
import java.io.*;

import interprete.*;
import entorno.*;
import excepciones.Errores;

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
                    var z : int = -5;
                    #####
                    println(z<5);
                    println("hola mundo");
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

        ArrayList<Errores> errores = new ArrayList<>();


        try {

            

            interprete.Lexer lexer = new interprete.Lexer(new StringReader(entrada)); 
            @SuppressWarnings("deprecation")
            interprete.Parser parser = new Parser(lexer);
            var resultado = parser.parse();
            @SuppressWarnings("unchecked")
            
            var erroresLexicos = lexer.getErrores();
            errores.addAll(erroresLexicos);

            var erroresSintacticos = parser.getErrores();
            errores.addAll(erroresSintacticos);

            var ast = new Entorno((LinkedList<Instruccion>)resultado.value);
            var ts = new tablaSimbolos();
            ts.setNombre("Global");
            ast.setConsola("");
            var init = new NodoAst("INICIO");
            var instruc = new NodoAst("INSTRUCCIONES");
            for (var a: ast.getInstrucciones()){
                try{
                    a.interpretar(ast, ts);
                    instruc.agregarHijoAST(a.getNodo());
                    ast.getConsola();
                    var erroresSemanticos = Errores.getErrores();
                    errores.addAll(erroresSemanticos);
                }catch(Exception e){

                    System.out.println("Error en la instruccion: "+a);
                    e.printStackTrace();
                }

                
            }
            System.out.println(ast.getConsola());

            //insertar ast.getConsola en la consola de la interfaz
            //consola.appendConsola(ast.getConsola());
            
            FailsGenerateHTML(errores);
            
            init.agregarHijoAST(instruc);

            ArbolAST arbol = new ArbolAST();
            arbol.graficarArbol(init);

        } catch (Exception e) {
            System.out.println("Error fatal en compilación de entrada.");
            e.printStackTrace();
            // Imprimir información específica sobre la línea exacta del error
            StackTraceElement[] stackTrace = e.getStackTrace();
            if (stackTrace.length > 0) {
                StackTraceElement element = stackTrace[0];
                System.out.println("Error en la clase: " + element.getClassName());
                System.out.println("Error en el método: " + element.getMethodName());
                System.out.println("Error en la línea: " + element.getLineNumber());
                } 
            }
    } 


    public static void FailsGenerateHTML(ArrayList<Errores> errores) {
        FileWriter fichero = null;
        PrintWriter pw = null;

        try {
            String path = "Fase_1/Reports/Fails.html";
            fichero = new FileWriter(path);
            pw = new PrintWriter(fichero);

            pw.println("<!DOCTYPE html>");
            pw.println("<html lang=\"es\">");
            pw.println("<head>");
            pw.println("<meta charset=\"UTF-8\">");
            pw.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            pw.println("<title>Errores</title>");
            pw.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
            pw.println("<style>");
            pw.println("body { background-color: #343a40; color: white; }");
            pw.println("h1 { text-align: center; color: white; }");
            pw.println("table { background-color: #343a40; }");
            pw.println("th, td { border: 1px solid #dee2e6; }");
            pw.println("th { background-color: #6c757d; }");
            pw.println("tr:nth-child(even) { background-color: #495057; }");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<div class=\"container mt-5\">");
            pw.println("<h1>Reporte de Errores</h1>");
            pw.println("<table class=\"table table-dark table-striped mt-3\">");
            pw.println("<thead>");
            pw.println("<tr>");
            pw.println("<th>#</th>");
            pw.println("<th>Tipo</th>");
            pw.println("<th>Descripción</th>");
            pw.println("<th>Fila</th>");
            pw.println("<th>Columna</th>");
            pw.println("</tr>");
            pw.println("</thead>");
            pw.println("<tbody>");

            int numError = 1;
            for (Errores err : errores) {
                pw.println("<tr>");
                pw.println("<td>" + numError++ + "</td>");
                pw.println("<td>" + err.getNombre() + "</td>");
                pw.println("<td>" + err.getDesc() + "</td>");
                pw.println("<td>" + err.getLinea() + "</td>");
                pw.println("<td>" + err.getColumna() + "</td>");
                pw.println("</tr>");
            }

            pw.println("</tbody>");
            pw.println("</table>");
            pw.println("</div>");
            pw.println("</body>");
            pw.println("</html>");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
