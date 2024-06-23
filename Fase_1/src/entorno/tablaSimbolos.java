package entorno;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import expresiones.Expresion;
import funciones.DatoArreglo;


public class tablaSimbolos {

    private tablaSimbolos tablaAnterior;
    private HashMap<String, Object> tablaActual;
    private String nombre;

    public static List<tablaSimbolos> tablas = new ArrayList<>();

    public tablaSimbolos() {
        this.tablaActual = new HashMap<>();
        this.nombre = "";
    }

    public tablaSimbolos(tablaSimbolos tablaAnterior) {
        this.tablaAnterior = tablaAnterior;
        this.tablaActual = new HashMap<>();
        this.nombre = "";
    }

    public tablaSimbolos getTablaAnterior() {
        return tablaAnterior;
    }

    public void setTablaAnterior(tablaSimbolos tablaAnterior) {
        this.tablaAnterior = tablaAnterior;
    }

    public HashMap<String, Object> getTablaActual() {
        return tablaActual;
    }

    public void setTablaActual(HashMap<String, Object> tablaActual) {
        this.tablaActual = tablaActual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean setVariable(Simbolo simbolo) {
        Simbolo busqueda = (Simbolo)this.tablaActual.get(simbolo.getId().toLowerCase());
        if (busqueda == null) {
            this.tablaActual.put(simbolo.getId().toLowerCase(),simbolo);
            return true;
        }
        return false;
    }

    public Simbolo getVariable(String id) {
        for (tablaSimbolos i = this; i != null; i = i.getTablaAnterior()) {
            Object valor = i.tablaActual.get(id.toLowerCase());
            if (valor != null) {
                if (valor instanceof Simbolo) {
                    return (Simbolo) valor;
                } else {
                    // Manejar el caso donde el tipo no es el esperado
                    throw new ClassCastException("El valor encontrado para la clave '" + id + "' no es de tipo Simbolo");
                }
            }
        }
        return null;
    }
    
    public static void tablaSimbolosHTML(){
        FileWriter fichero = null;
        PrintWriter pw = null;

        try {
            //String path = "Fase_1/Reports/Fails.html";
            String path = "tabla.html";
            fichero = new FileWriter(path);
            pw = new PrintWriter(fichero);

            pw.println("<!DOCTYPE html>");
            pw.println("<html lang=\"es\">");
            pw.println("<head>");
            pw.println("<meta charset=\"UTF-8\">");
            pw.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            pw.println("<title>Tabla Simbolos</title>");
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
            pw.println("<h1>Tabla de Simbolos</h1>");
            pw.println("<table class=\"table table-dark table-striped mt-3\">");
            pw.println("<thead>");
            pw.println("<tr>");
            pw.println("<th>#</th>");
            pw.println("<th>ID</th>");
            pw.println("<th>Mutabilidad</th>");
            pw.println("<th>Tipo de dato</th>");
            pw.println("<th>Entorno</th>");
            pw.println("<th>Valor</th>");
            pw.println("<th>Fila</th>");
            pw.println("<th>Columna</th>");
            pw.println("</tr>");
            pw.println("</thead>");
            pw.println("<tbody>");

            int numError = 1;

            for (tablaSimbolos ts : tablaSimbolos.tablas) {
                for (Map.Entry<String, Object> entry : ts.getTablaActual().entrySet()) {
                    if (entry.getValue() instanceof Simbolo) {
                        Simbolo simbolo = (Simbolo) entry.getValue();
                        Object valor = simbolo.getValor();
                        String valorStr = "";
                        String TipoD = "";
                        String mutabilidad = "";

                        if (valor instanceof Expresion) {
                            Expresion exp = (Expresion) valor;
                            valorStr = String.valueOf(exp.getValor());
                            TipoD = exp.getTipo().toString();
                            mutabilidad = exp.getMutabilidad();

                        } else if (valor instanceof DatoArreglo) {
                            DatoArreglo arreglo = (DatoArreglo) valor;
                            valorStr = arreglo.toString();
                            TipoD = "Arreglo";
                            mutabilidad = arreglo.getMutabilidad();
                        } else {
                            valorStr = valor.toString();
                            TipoD = simbolo.getTipo().toString();
                            mutabilidad = simbolo.getMutabilidad();
                        }
                        pw.println("<tr>");
                        pw.println("<td>" + numError++ + "</td>");
                        pw.println("<td>" + simbolo.getId() + "</td>");
                        pw.println("<td>" + mutabilidad + "</td>");
                        pw.println("<td>" + TipoD+ "</td>");
                        pw.println("<td>" + ts.getNombre() + "</td>");
                        pw.println("<td>" + valorStr + "</td>");
                        pw.println("<td>" + simbolo.getFila() + "</td>");
                        pw.println("<td>" + simbolo.getColumna() + "</td>");
                        pw.println("</tr>");
                    }
                }
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

    //limpiar list de tablas
    public static void limpiarTablas(){
        tablaSimbolos.tablas.clear();
    }

}
