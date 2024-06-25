package funciones;

import expresiones.Expresion;

public class ValorStruct{
    private String id;
    private Expresion valor;
    private int fila, columna;

    public ValorStruct(String id, Expresion valor, int fila, int columna) {
        this.id = id;
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
    }

    public String getId() {
        return id;
    }

    public Expresion getValor() {
        return valor;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}
