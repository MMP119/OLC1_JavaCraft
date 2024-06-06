package expresiones;

public class Instruccion {
    
    private String tipo;
    private int fila;
    private int columna;

    public Instruccion(String tipo, int fila, int columna){
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
    }


    public Instruccion interpretar(Object entorno){
        return this;
    }

    public String getTipo() {
        return tipo;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    


}
