package funciones;

import expresiones.Expresion;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;

import java.util.LinkedList;

import AST.NodoAst;

public class DoWhile extends Instruccion{

    private Expresion exp;
    private LinkedList<Instruccion> inst;
    private int fila, columna;
    

    public DoWhile(Expresion exp, LinkedList<Instruccion> inst, int fila, int columna) {
        super(new Tipo(TipoInstruccion.DOWHILE), fila, columna);
        this.exp = exp;
        this.inst = inst;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("DOWHILE");
        return nodo;
    }

    public Object interpretar(Entorno ent, entorno.tablaSimbolos ts) {
        try{
            Instruccion.cicloProfundida++;

            Entorno entornoDoWhile = new Entorno(inst);
            tablaSimbolos tsDoWhile = new tablaSimbolos();
            tsDoWhile.setNombre("DoWhile");
            tsDoWhile.setTablaAnterior(ts);
            entornoDoWhile.setConsola("");
            tablaSimbolos.tablas.add(tsDoWhile);

            do {

                for(int i = 0; i < inst.size(); i++){
                    Instruccion a = inst.get(i);
                    Object res = a.interpretar(entornoDoWhile, tsDoWhile);
                    ent.setConsola(ent.getConsola() + entornoDoWhile.getConsola());
                    entornoDoWhile.setConsola("");

                    // Break
                    if (a instanceof Break || res instanceof Break) {
                        Instruccion.cicloProfundida--;
                        return null;
                    }

                    // Continue
                    if (a instanceof Continue || res instanceof Continue) {
                        break;
                    }


                }
                ent.setConsola(ent.getConsola() + entornoDoWhile.getConsola());
                entornoDoWhile.setConsola("");
                this.exp = (Expresion) this.exp.interpretar(entornoDoWhile, tsDoWhile);

            } while (Boolean.parseBoolean(this.exp.getValor().toString()));
            
            Instruccion.cicloProfundida--;
            return this;
            
        }catch(Exception e){
            Errores.errores.add(new Errores("Semantico", "Error en el DoWhile", fila, columna));
            return new Errores("Semantico", "Error en el DoWhile", fila, columna);
        }
    }
    
}
