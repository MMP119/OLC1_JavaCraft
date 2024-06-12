package expresiones;

import AST.NodoAst;
import entorno.*;
import excepciones.Errores;

public class RecVariable extends Expresion {

    // para recuperar el valor de las variables en la tabla de simbolos, si no se enncuentra en la actual, se busca en la anterior y así

    private String id;
    private int fila;
    private int columna;

    public RecVariable(String id, int fila, int columna) {
        super("ERROR", TipoDato.ERROR, fila, columna);
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

        Expresion variable = (Expresion)ts.getTablaActual().get(id);

        if (variable != null) {
            return variable;
        }
        else{
            //si no se encuentra en la tabla actual, se busca en la anterior, y así sucesivamente
            tablaSimbolos tsAux = ts;
            while (tsAux != null) {
                variable = (Expresion)tsAux.getTablaActual().get(id);
                if (variable != null) {
                    return variable;
                }
                tsAux = tsAux.getTablaAnterior();
            }
            System.out.println("ERROR SEMANTICO, Variable " + id+" no ha sido declarada");
            return new Errores("ERROR SEMANTICO","La variable " + id + " no ha sido declarada", this.fila, this.columna);
        }
    }
    
}
