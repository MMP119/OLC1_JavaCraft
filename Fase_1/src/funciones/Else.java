package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import java.util.LinkedList;
import excepciones.Errores;

public class Else extends Instruccion{

    private LinkedList<Instruccion> inst_else;

    private int fila, columna;

    public Else(LinkedList<Instruccion> inst_else, int fila, int columna) {
        super(new Tipo(TipoInstruccion.ELSE), fila, columna);
        this.inst_else = inst_else;
        this.fila = fila;
        this.columna = columna;
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
        
        try{
            Entorno EntornoElse = new Entorno(inst_else);
            tablaSimbolos tsElse = new tablaSimbolos();
            tsElse.setNombre("Else");
            tsElse.setTablaAnterior(ts);
            EntornoElse.setConsola("");

            for(int i = 0; i< inst_else.size(); i++){
                Instruccion a = inst_else.get(i);
                Object res = a.interpretar(EntornoElse, tsElse);
                ent.setConsola(ent.getConsola() + EntornoElse.getConsola());
                EntornoElse.setConsola("");

                // Break
                if (a instanceof Break || res instanceof Break) {
                    return new Break(fila, columna);
                }

                // Continue
                if (a instanceof Continue || res instanceof Continue) {
                    return new Continue(fila, columna);
                }

            }
            ent.setConsola(ent.getConsola() + EntornoElse.getConsola());

            return this;
            
        }catch(Exception e){
            return new Errores("Semantico", "Error en la instruccion Else", fila, columna);
        }
    }
    
}
