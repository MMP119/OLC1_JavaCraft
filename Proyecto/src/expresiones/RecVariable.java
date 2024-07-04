package expresiones;

import AST.NodoAst;
import entorno.*;
import excepciones.Errores;
import funciones.DatoArreglo2D;
import funciones.DatoArreglo;

public class RecVariable extends Expresion {

    private String id;
    private int fila;
    private int columna;

    public RecVariable(String id, int fila, int columna) {
        super("ERROR RECUPERAR VARIABLE", TipoDato.ERROR, fila, columna);
        this.id = id;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("RECUPERAR_VARIABLE");
        nodo.agregarHijo(id);
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        try {
            // Buscar la variable en la tabla de símbolos
            Simbolo variable = ts.getVariable(this.id);

            if (variable != null && variable.getValor() != null) {

                Object valor = variable.getValor();

                if(valor instanceof Expresion){
                    return((Expresion)valor).interpretar(ent, ts);

                }else if(valor instanceof DatoArreglo){
                    return valor;

                }else if(valor instanceof DatoArreglo2D){
                    return valor;

                }else{
                    return valor;
                }

                // Expresion var = (Expresion) variable.getValor();
                // Object var1 = variable.getValor();
                // Expresion valor = (Expresion) var.interpretar(ent, ts);
                // return valor;

            } else {
                System.out.println("ERROR SEMANTICO, Variable " + id + " no ha sido declarada");
                Errores.errores.add(new Errores("ERROR SEMANTICO", "La variable " + id + " no ha sido declarada", this.fila, this.columna));
                return new Errores("ERROR SEMANTICO", "La variable " + id + " no ha sido declarada", this.fila, this.columna);
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
            Errores.errores.add(new Errores("Semantico", "Error al interpretar Recuperar Variable", this.fila, this.columna));
            return new Errores("Semantico", "Error al interpretar Recuperar Variable", this.fila, this.columna);
        }
    }
}

