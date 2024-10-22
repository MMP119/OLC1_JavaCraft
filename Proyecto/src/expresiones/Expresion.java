package expresiones;


import entorno.Entorno;
import entorno.tablaSimbolos;
import AST.*;


// Clase Expresion
public abstract class Expresion {
    private Object valor;
    private String id;
    private TipoDato tipo;
    private String mutabilidad;
    private int fila;
    private int columna;

    // Constructor
    public Expresion(Object valor, TipoDato tipo, int fila, int columna) {
        this.valor = valor;
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
    }

    public abstract NodoAst getNodo();


    public abstract Object interpretar(Entorno ent, tablaSimbolos ts);

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

    public String getMutabilidad() {
        return mutabilidad;
    }

    public void setMutabilidad(String mutabilidad) {
        this.mutabilidad = mutabilidad;
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

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    } 

    public Expresion clone(){
        return new Dato(this.valor, this.tipo, this.fila, this.columna);
    }

}
