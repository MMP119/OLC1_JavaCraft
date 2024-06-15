package funciones;

import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.Expresion;
import expresiones.TipoDato;
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

    public Object interpretar(Entorno ent, tablaSimbolos ts){
        Instruccion.cicloProfundida++;//Aumentamos la profundidad del ciclo

        Entorno EntWhile = new Entorno(inst);
        tablaSimbolos tsWhile = new tablaSimbolos();
        tsWhile.setNombre("While");
        tsWhile.setTablaAnterior(ts);
        EntWhile.setConsola("");
        tablaSimbolos.tablas.add(tsWhile);

        while(true){
            Expresion condicion = (Expresion)this.exp.interpretar(EntWhile, tsWhile);

            if(condicion.getTipo() != TipoDato.BOOLEAN){
                System.out.println("ERROR SEMANTICO: Se esperaba una expresion booleana en la condicion del while");
                Errores.errores.add(new Errores("Semantico", "Se esperaba una expresion booleana en la condicion del while", fila, columna));
                return new Errores("Semantico", "Se esperaba una expresion booleana en la condicion del while", fila, columna);
            }

            if(condicion.getValor().toString().equals("true")){
                for(int i = 0; i< inst.size(); i++){
                    Instruccion a = inst.get(i);
                    Object res = a.interpretar(EntWhile, tsWhile);
                    ent.setConsola(ent.getConsola() + EntWhile.getConsola());
                    EntWhile.setConsola("");

                    // Break
                    if(res instanceof Break || a instanceof Break){
                        Instruccion.cicloProfundida--; //Disminuimos la profundidad del ciclo antes de salir
                        return null;
                    }

                    // Continue
                    if(res instanceof Continue || a instanceof Continue){
                        ent.setConsola(ent.getConsola() + EntWhile.getConsola());
                        EntWhile.setConsola("");
                        break;
                    }

                }
                ent.setConsola(ent.getConsola() + EntWhile.getConsola());
                EntWhile.setConsola("");
            } else {
                break;
            }
        }
        Instruccion.cicloProfundida--; //Disminuimos la profundidad del ciclo antes de salir
        return this;
    }  
}