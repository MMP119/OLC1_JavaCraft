package funciones;

import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.Expresion;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class Casos extends Instruccion {

    private Expresion exp;
    private LinkedList<Instruccion> inst;
    private int fila, columna;
    private Expresion match;

    public Casos(Expresion exp, LinkedList<Instruccion> inst, int fila, int columna) {
        super(new Tipo(TipoInstruccion.CASOS), fila, columna);
        this.exp = exp;
        this.inst = inst;
        this.fila = fila;
        this.columna = columna;
        this.match = match;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("CASOS");
        if(this.exp != null){
            nodo.agregarHijo(this.exp.getNodo());
        }
        if(this.match != null){
            nodo.agregarHijo(this.match.getNodo());
        }
        for (int i = 0; i < inst.size(); i++) {
            Instruccion a = inst.get(i);
            nodo.agregarHijoAST(a.getNodo());
        }
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        try{
            tablaSimbolos tsCasos = new tablaSimbolos(ts);
            tsCasos.setNombre("casos");
            tablaSimbolos.tablas.add(tsCasos);

            if(this.exp != null){
                this.exp = (Expresion) this.exp.interpretar(ent, ts);
            }
            
            if(this.exp != null && this.exp.getValor().toString().equals("_")){
                this.match = (Expresion) this.match.interpretar(ent, ts);
            }

            if(this.exp != null){
                if (this.exp.getValor().toString().equals(this.match.getValor().toString()) || this.exp.getValor().toString().equals("_")) {
                    
                    for (int i = 0; i < inst.size(); i++) {
                        Instruccion a = inst.get(i);
                        Object res = a.interpretar(ent, tsCasos);

                        // Break
                        if (a instanceof Break || res instanceof Break) {
                            return new Break(fila, columna);
                        }

                        // Continue
                        if (a instanceof Continue || res instanceof Continue) {
                            return new Continue(fila, columna);
                        }

                    }
                    return this;
                }
            }else{
                Errores.errores.add(new Errores("Semantico", "expresion nula", fila, columna));
                return new Errores("Semantico", "expresion nula", fila, columna);
            }
            return null;

        }catch(Exception e){
            Errores.errores.add(new Errores("Semantico", "Error en la interpretacion de los casos", this.fila, this.columna));
            return new Errores("Semantico", "Error en la interpretacion de los casos", this.fila, this.columna);
        }
    }

    public Expresion getMatch() {
        return match;
    }

    public void setMatch(Expresion match) {
        this.match = match;
    }
}