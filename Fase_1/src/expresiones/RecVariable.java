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
            if(ts.getTablaAnterior()!=null){
                variable = (Expresion)ts.getTablaAnterior().getTablaActual().get(id);
            }else{
                System.out.println("ERROR SEMANTICO Variable NO DECLARADA:" + id);
                return new Errores("Semantico", "Variable no declarada: " + id, fila, columna);
            }
            if (variable == null) {
                System.out.println("Variable NO DECLARADA:" + id + " en tabla actual ni en tabla anterior");
                return new Errores("Semantico", "Variable no declarada: " + id, fila, columna);
            }else{
                System.out.println("Variable recuperada anterior: " + variable);
                return variable;
            }
        }
    }
    
}
