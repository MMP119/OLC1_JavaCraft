package funciones;

import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import expresiones.Expresion;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class Casos extends Instruccion {

    private Expresion exp;
    private LinkedList<Instruccion> inst;
    @SuppressWarnings("unused")
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
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        Entorno EntornoCasos = new Entorno(inst);
        tablaSimbolos tsCasos = new tablaSimbolos();
        tsCasos.setNombre("Casos");
        tsCasos.setTablaAnterior(ts);
        EntornoCasos.setConsola("");

        if(this.exp != null){
            this.exp = (Expresion) this.exp.interpretar(ent, ts);
        }
        
        this.match = (Expresion) this.match.interpretar(ent, ts);

        if(this.exp != null){
            if (this.exp.getValor().toString().equals(this.match.getValor().toString())) {
                for(var a: EntornoCasos.getInstrucciones()){
                    a.interpretar(EntornoCasos, tsCasos);
                    EntornoCasos.getConsola();
                }
                ent.setConsola(ent.getConsola() + EntornoCasos.getConsola());
            }
        }else{
            for (var a: EntornoCasos.getInstrucciones()) {
                a.interpretar(EntornoCasos, tsCasos);
                EntornoCasos.getConsola();
            }
            ent.setConsola(ent.getConsola() + EntornoCasos.getConsola());
        }         
        return this;
    }

    // Getters y Setters
    public Expresion getMatch() {
        return match;
    }

    public void setMatch(Expresion match) {
        this.match = match;
    }
}

