package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import expresiones.Expresion;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class Match extends Instruccion {

    private Expresion exp;
    private Casos casos;
    private int fila, columna;

    public Match(Expresion exp, Casos casos, int fila, int columna) {
        super(new Tipo(TipoInstruccion.MATCH), fila, columna);
        this.exp = exp;
        this.casos = casos;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("MATCH");
        return nodo;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts) {


        return this;
    }

    
}
