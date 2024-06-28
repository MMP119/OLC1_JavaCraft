package funciones;

import entorno.Entorno;
import entorno.tablaSimbolos;
import expresiones.Expresion;
import AST.NodoAst;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import excepciones.Errores;
import entorno.Tipo;


public class Return extends Instruccion {
    private Expresion expresion;
    private int fila, columna;

    public Return(Expresion expresion, int fila, int columna) {
        super(new Tipo(TipoInstruccion.RETURN),fila, columna);
        this.expresion = expresion;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("RETURN");
        nodo.agregarHijo("return");
        if (expresion != null) {
            nodo.agregarHijoAST(expresion.getNodo());
        }
        nodo.agregarHijo(";");
        return nodo;
    }


    /*public Object interpretar(Entorno ent, tablaSimbolos ts) {
        System.out.println("Interpretando Return");
        if (expresion != null) {
            System.out.println("Return con expresion");
            Object resultado = expresion.interpretar(ent, ts);
            if (resultado instanceof Errores) {
                return resultado;
            }
            return resultado;
        }
        System.out.println("Return sin expresion");
        return null;
    }*/


    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        System.out.println("Interpretando Return");
        if (expresion != null) {
            System.out.println("Return con expresion");
            Object resultado = expresion.interpretar(ent, ts);
            if (resultado instanceof Errores) {
                return resultado;
            }
            this.expresion = (Expresion) resultado;
            return this; 
        }
        System.out.println("Return sin expresion");
        return this; 
    }



    public Expresion getExpresion() {
        return expresion;
    }
}