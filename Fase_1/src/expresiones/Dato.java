package expresiones;

import entorno.*;
import AST.*;

public class Dato extends Expresion{

    private Object valor;
    private TipoDato tipo;
    private int fila;
    private int columna;

    
    public Dato(Object valor, TipoDato tipo, int fila, int columna){
        super(valor, tipo, fila, columna);
        this.valor = valor;
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst(this.tipo.toString());
        nodo.agregarHijo(this.valor.toString());
        return nodo;
    }


    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        return this.valor;
    }


    // Getters y Setters
    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public TipoDato getTipo() {
        return tipo;
    }

    public void setTipo(TipoDato tipo) {
        this.tipo = tipo;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }


    @Override
    public String toString() {
        return valor.toString();
    }
}
