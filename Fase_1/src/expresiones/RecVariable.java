package expresiones;

import AST.NodoAst;
import entorno.*;
import excepciones.Errores;

public class RecVariable extends Expresion {

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
        try {
            // Buscar la variable en la tabla de símbolos
            Simbolo variable = ts.getVariable(this.id);

            if (variable != null && variable.getValor() != null) {
                Expresion var = (Expresion) variable.getValor(); 
                
                return var;
            } else {
                System.out.println("ERROR SEMANTICO, Variable " + id + " no ha sido declarada");
                Errores.errores.add(new Errores("ERROR SEMANTICO", "La variable " + id + " no ha sido declarada", this.fila, this.columna));
                return new Errores("ERROR SEMANTICO", "La variable " + id + " no ha sido declarada", this.fila, this.columna);
            }

        } catch (Exception e) {
            Errores.errores.add(new Errores("Semantico", "Error al interpretar Recuperar Variable", this.fila, this.columna));
            return new Errores("Semantico", "Error al interpretar Recuperar Variable", this.fila, this.columna);
        }
    }
}

