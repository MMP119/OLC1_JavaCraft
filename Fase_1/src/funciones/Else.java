package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import java.util.LinkedList;

public class Else extends Instruccion{

    private LinkedList<Instruccion> inst_else;

    public Else(LinkedList<Instruccion> inst_else, int fila, int columna) {
        super(new Tipo(TipoInstruccion.ELSE), fila, columna);
        this.inst_else = inst_else;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("ELSE");
        nodo.agregarHijo("else");
        nodo.agregarHijo("{");
        nodo.agregarHijoAST(inst_else.get(0).getNodo());
        nodo.agregarHijo("}");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        
        Entorno EntornoElse = new Entorno(inst_else);
        for (var a: EntornoElse.getInstrucciones()){
            a.interpretar(EntornoElse, ts);
        }
        return this;
    }
    
}
