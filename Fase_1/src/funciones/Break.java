package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class Break extends Instruccion {

    public Break(int linea, int col) {
        super(new Tipo(TipoInstruccion.BREAK), linea, col);
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("BREAK");
        return nodo;
    }

    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        //System.out.println("Break");
        return null;
    }

}