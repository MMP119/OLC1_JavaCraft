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
    private Instruccion casos; // Anidado
    @SuppressWarnings("unused")
    private int fila, columna;
    private Expresion match;

    public Casos(Expresion exp, LinkedList<Instruccion> inst, Instruccion casos, int fila, int columna) {
        super(new Tipo(TipoInstruccion.CASOS), fila, columna);
        this.exp = exp;
        this.inst = inst;
        this.casos = casos;
        this.fila = fila;
        this.columna = columna;
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

        if (this.exp != null) {
            this.exp = (Expresion) this.exp.interpretar(EntornoCasos, tsCasos);
        }

        // Caso por defecto
        if (this.exp == null && this.match != null && this.casos == null) {
            for (Instruccion inst : inst) {
                inst.interpretar(EntornoCasos, tsCasos);
                EntornoCasos.getConsola();
            }
            ent.setConsola(ent.getConsola() + EntornoCasos.getConsola());
        } 
        // Un solo caso
        else if (this.exp != null && this.match != null && this.casos == null) {
            if (this.exp.getValor().equals(this.match.getValor())) {
                for (Instruccion inst : inst) {
                    inst.interpretar(EntornoCasos, tsCasos);
                    EntornoCasos.getConsola();
                }
                ent.setConsola(ent.getConsola() + EntornoCasos.getConsola());
            }
        } 
        // Casos anidados
        else if (this.exp != null && this.match != null && this.casos != null) {
            if (this.exp.getValor().equals(this.match.getValor())) {
                for (Instruccion inst : inst) {
                    inst.interpretar(EntornoCasos, tsCasos);
                    EntornoCasos.getConsola();
                }
                ent.setConsola(ent.getConsola() + EntornoCasos.getConsola());
            } else {
                this.casos.interpretar(ent, ts);
            }
        }

        return this;
    }

    // Getters y Setters
    public Expresion getMatch() {
        return match;
    }

    public void setMatch(Expresion match) {
        this.match = match;
        if (this.casos != null && this.casos instanceof Casos) {
            ((Casos) this.casos).setMatch(match);
        }
    }
}

