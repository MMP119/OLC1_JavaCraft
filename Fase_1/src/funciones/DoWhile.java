package funciones;

import expresiones.Expresion;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;

import java.util.LinkedList;

import AST.NodoAst;

public class DoWhile extends Instruccion{

    private Expresion exp;
    private LinkedList<Instruccion> inst;
    private int fila, columna;
    

    public DoWhile(Expresion exp, LinkedList<Instruccion> inst, int fila, int columna) {
        super(new Tipo(TipoInstruccion.DOWHILE), fila, columna);
        this.exp = exp;
        this.inst = inst;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("DOWHILE");
        return nodo;
    }

    public Object interpretar(Entorno ent, entorno.tablaSimbolos ts) {

        Entorno entornoDoWhile = new Entorno(inst);
        tablaSimbolos tsDoWhile = new tablaSimbolos();
        tsDoWhile.setNombre("DoWhile");
        tsDoWhile.setTablaAnterior(ts);
        entornoDoWhile.setConsola("");

        do {

            for (var a: entornoDoWhile.getInstrucciones()) {
                a.interpretar(entornoDoWhile, tsDoWhile);
                entornoDoWhile.getConsola();
            }

            ent.setConsola(ent.getConsola() + entornoDoWhile.getConsola());
            this.exp = (Expresion) this.exp.interpretar(entornoDoWhile, tsDoWhile);

        } while (Boolean.parseBoolean(this.exp.getValor().toString()));
        
        return this;
    }
    
}
