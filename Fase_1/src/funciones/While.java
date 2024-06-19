package funciones;

import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.Expresion;
import instruccion.*;

public class While extends Instruccion {

    private Expresion exp;
    private LinkedList<Instruccion> inst;
    private int fila, columna;

    public While(Expresion exp, LinkedList<Instruccion> inst, int fila, int columna){
        super(new Tipo(TipoInstruccion.WHILE), fila, columna);
        this.exp = exp;
        this.inst = inst;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("WHILE");
        nodo.agregarHijo("while");
        nodo.agregarHijo("(");
        nodo.agregarHijoAST(exp.getNodo());
        nodo.agregarHijo(")");
        nodo.agregarHijo("{");
        for(Instruccion i : inst){
            nodo.agregarHijoAST(i.getNodo());
        }
        nodo.agregarHijo("}");
        return nodo;
    }

    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {
            Object res = exp.interpretar(ent, ts);
            while (Boolean.parseBoolean(res.toString())) {
                var newTabla2 = new tablaSimbolos(ts);
                tablaSimbolos.tablas.add(newTabla2);
                for (Instruccion i : inst) {
                    if( i instanceof DecVariables){
                        System.out.println("DecVariables");
                    }
                    Object result = i.interpretar(ent, newTabla2);
                    if (result != null) {
                        if (result instanceof Break) {
                            return null;
                        }
                        if (result instanceof Continue) {
                            break;
                        }
                    }
                }
                res = exp.interpretar(ent, newTabla2);
            }
        return null;
    }  
}

