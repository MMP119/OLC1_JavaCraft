package entorno;

public class Simbolo {
    

    private String nombre;
    private Object valor;
    private String tipo;
    private String TipoVar;
    private int fila;
    private int columna;

    public Simbolo(String nombre, Object valor, String tipo, String TipoVar, int fila, int columna){
        this.nombre = nombre;
        this.valor = valor;
        this.tipo = tipo;
        this.TipoVar = TipoVar;
        this.fila = fila;
        this.columna = columna;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public Object getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTipoVar() {
        return TipoVar;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTipoVar(String TipoVar) {
        this.TipoVar = TipoVar;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
