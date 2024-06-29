package AST;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ArbolAST {
    private static StringBuilder cuerpo;
    private static int contador;

    public String graficarArbol(NodoAst arbolitos) throws IOException, InterruptedException {
        contador = 1;
        cuerpo = new StringBuilder();
        graphAST("n0", arbolitos);

        String principal = "digraph arbolAST{\n" +
                "node [shape=oval, style=filled, color=lightblue2, fontname=Helvetica, fontsize=10];\n" +
                "edge [fontname=Helvetica, fontsize=10];\n" +
                "n0[label=\"" + arbolitos.getValor().toString().replace("\"", "\\\"") + "\"];\n" +
                cuerpo +
                "}";

        
        // Rutas de los archivos
        String dotRuta = "arbolAST.dot";
        String svgRuta = "arbolAST.svg";

        try (FileWriter writer = new FileWriter(dotRuta)) {
            writer.write(principal);
        }

        ProcessBuilder processBuilder = new ProcessBuilder("dot", "-Tsvg", dotRuta, "-o", svgRuta);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            try (Scanner scanner = new Scanner(process.getErrorStream()).useDelimiter("\\A")) {
                String error = scanner.hasNext() ? scanner.next() : "";
                System.err.println(error);
            }
        }

        return principal;
    }

    public static void graphAST(String texto, NodoAst padre) {
        for (NodoAst hijo : padre.getListaHijos()) {
            String nombreHijo = "n" + contador;
            cuerpo.append(nombreHijo)
                    .append("[label=\"")
                    .append(hijo.getValor().toString().replace("\"", "\\\""))
                    .append("\"];\n")
                    .append(texto)
                    .append(" -> ")
                    .append(nombreHijo)
                    .append(";\n");
            contador++;
            graphAST(nombreHijo, hijo);
        }
    }
}