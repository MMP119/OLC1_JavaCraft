package funciones;

import expresiones.TipoDato;

public class Campo {
    private TipoDato tipo;
    private String nombre;
    private int fila, columna;

    public Campo(TipoDato tipo, String nombre, int fila, int columna) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.fila = fila;
        this.columna = columna;
    }

    public TipoDato getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }


    
}
