package instruccion;

import entorno.Entorno;
import entorno.tablaSimbolos;
import entorno.Tipo;
import AST.*;


public abstract class Instruccion {
    
    private Tipo tipo;
    private int fila;
    private int columna;

    public Instruccion(Tipo tipo, int fila, int columna){
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
    }

    public abstract NodoAst getNodo();

    public abstract Object interpretar(Entorno ent ,tablaSimbolos ts);

}
