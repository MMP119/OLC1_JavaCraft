package expresiones;

import AST.NodoAst;
import entorno.*;
import excepciones.Errores;
import funciones.DatoArreglo2D;
import java.util.LinkedList;

public class AccesoArreglo2D extends Expresion {

    private String id;
    private Expresion indice1;
    private Expresion indice2;
    private int fila;
    private int columna;

    public AccesoArreglo2D(String id, Expresion indice1, Expresion indice2, int fila, int columna) {
        super("ACCESO_ARREGLO_2D", TipoDato.ERROR, fila, columna);
        this.id = id;
        this.indice1 = indice1;
        this.indice2 = indice2;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("ACCESO_ARREGLO_2D");
        nodo.agregarHijo(id);
        nodo.agregarHijo("[");
        nodo.agregarHijoAST(indice1.getNodo());
        nodo.agregarHijo("][");
        nodo.agregarHijoAST(indice2.getNodo());
        nodo.agregarHijo("]");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        try {
            // Buscar el arreglo en la tabla de símbolos
            Simbolo simbolo = ts.getVariable(this.id);

            if (simbolo != null && simbolo.getValor() instanceof DatoArreglo2D) {
                DatoArreglo2D arreglo2D = (DatoArreglo2D) simbolo.getValor();
                LinkedList<LinkedList<Object>> valores = arreglo2D.getValor();

                // Interpretar el primer índice
                Object indice1Valor = indice1.interpretar(ent, ts);

                int index1;
                try {
                    index1 = Integer.parseInt(indice1Valor.toString());
                } catch (Exception e) {
                    System.out.println("ERROR SEMANTICO, El primer índice debe ser un número entero.");
                    Errores.errores.add(new Errores("ERROR SEMANTICO", "El primer índice debe ser un número entero", this.fila, this.columna));
                    return new Errores("ERROR SEMANTICO", "El primer índice debe ser un número entero", this.fila, this.columna);
                }

                if (index1 >= 0 && index1 < valores.size()) {

                    // Interpretar el segundo índice
                    Object indice2Valor = indice2.interpretar(ent, ts);
                    int index2;

                    try {
                        index2 = Integer.parseInt(indice2Valor.toString());
                    } catch (Exception e) {
                        System.out.println("ERROR SEMANTICO, El segundo índice debe ser un número entero.");
                        Errores.errores.add(new Errores("ERROR SEMANTICO", "El segundo índice debe ser un número entero", this.fila, this.columna));
                        return new Errores("ERROR SEMANTICO", "El segundo índice debe ser un número entero", this.fila, this.columna);
                    }

                    LinkedList<Object> fila = valores.get(index1);
                    if (index2 >= 0 && index2 < fila.size()) {
                        return fila.get(index2);
                    } else {
                        System.out.println("ERROR SEMANTICO, Índice fuera de rango: " + index2);
                        Errores.errores.add(new Errores("ERROR SEMANTICO", "Índice fuera de rango", this.fila, this.columna));
                        return new Errores("ERROR SEMANTICO", "Índice fuera de rango", this.fila, this.columna);
                    }

                } else {
                    System.out.println("ERROR SEMANTICO, Índice fuera de rango: " + index1);
                    Errores.errores.add(new Errores("ERROR SEMANTICO", "Índice fuera de rango", this.fila, this.columna));
                    return new Errores("ERROR SEMANTICO", "Índice fuera de rango", this.fila, this.columna);
                }
            } else {
                System.out.println("ERROR SEMANTICO, El arreglo " + id + " no ha sido declarado o no es un arreglo 2D.");
                Errores.errores.add(new Errores("ERROR SEMANTICO", "El arreglo " + id + " no ha sido declarado o no es un arreglo 2D", this.fila, this.columna));
                return new Errores("ERROR SEMANTICO", "El arreglo " + id + " no ha sido declarado o no es un arreglo 2D", this.fila, this.columna);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Imprimir información específica sobre la línea exacta del error
            StackTraceElement[] stackTrace = e.getStackTrace();
            if (stackTrace.length > 0) {
                StackTraceElement element = stackTrace[0];
                System.out.println("Error en la clase: " + element.getClassName());
                System.out.println("Error en el método: " + element.getMethodName());
                System.out.println("Error en la línea: " + element.getLineNumber());
            }
            Errores.errores.add(new Errores("Semantico", "Error al interpretar Acceso a Arreglo 2D", this.fila, this.columna));
            return new Errores("Semantico", "Error al interpretar Acceso a Arreglo 2D", this.fila, this.columna);
        }
    }
}
