package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.Expresion;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class Match extends Instruccion {

    private Expresion exp;
    private Instruccion casos; // Cambiamos a un solo objeto Instruccion que representa todos los casos
    private int fila, columna;

    public Match(Expresion exp, Instruccion casos, int fila, int columna) {
        super(new Tipo(TipoInstruccion.MATCH), fila, columna);
        this.exp = exp;
        this.casos = casos;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("MATCH");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        Expresion valorExp = (Expresion) this.exp.interpretar(ent, ts);

        // if (valorExp == null || valorExp.getClass() != expresiones.Expresion.class) {
        //     System.out.println("Error Semantico: la expresion no tiene valor o no está asignada");
        //     return new Errores("Semantico", "la expresion no tiene valor", this.fila, this.columna);
        // }

        if (casos instanceof Casos) {
            ((Casos) casos).setMatch(valorExp);
            Object resultado = casos.interpretar(ent, ts);
            if (resultado instanceof Errores) {
                return resultado;
            }
        } else {
            System.out.println("Error: La instrucción de casos no es una instancia de Casos");
            return new Errores("Semantico", "La instrucción de casos no es válida", this.fila, this.columna);
        }

        ent.setConsola(ent.getConsola());
        return this;
    }
}