package funciones;

import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class Casos extends Instruccion{

    private Exception exp;
    private LinkedList<Instruccion> inst;
    private Casos casos;
    private int fila, columna;
    
    public Casos(Exception exp, LinkedList<Instruccion> inst, Casos casos, int fila, int columna) {
        super(new Tipo(TipoInstruccion.CASOS), fila, columna);
        this.exp = exp;
        this.inst = inst;
        this.casos = casos;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("CASOS");
        return nodo;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        return this;
    }


}
