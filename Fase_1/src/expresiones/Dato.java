package expresiones;

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

    @Override
    public Expresion interpretar(Object entorno) {
        return this;
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
        return "Dato{" +
                "valor=" + valor +
                ", tipo=" + tipo +
                ", fila=" + fila +
                ", columna=" + columna +
                '}';
    }
}
