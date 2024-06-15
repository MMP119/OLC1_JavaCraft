package funciones;

import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import expresiones.Expresion;
import expresiones.TipoDato;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import excepciones.*;

public class For extends Instruccion{

    private Instruccion asigVariable;
    private Expresion condicion;
    private Instruccion update;
    private LinkedList<Instruccion> local;
    private int fila, columna;

    public For(Instruccion asigVariable, Expresion condicion, Instruccion update, LinkedList<Instruccion> local, int fila, int columna ){
        super(new Tipo(TipoInstruccion.FOR), fila, columna);
        this.asigVariable = asigVariable;
        this.condicion = condicion;
        this.update = update;
        this.local = local;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("FOR");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        Entorno entFor = new Entorno(local);
        tablaSimbolos tsFor = new tablaSimbolos();
        tsFor.setNombre("For");
        tsFor.setTablaAnterior(ts);
        entFor.setConsola("");

        var asig = asigVariable.interpretar(entFor, tsFor);
        if(asig instanceof Break){
            return null;
        }
        while(true){
            Expresion condicion = (Expresion)this.condicion.interpretar(entFor, tsFor);

            if(condicion.getTipo() != TipoDato.BOOLEAN){
                System.out.println("ERROR SEMANTICO: Se esperaba una expresion booleana en la condicion del for");
                return new Errores("Semantico", "Se esperaba una expresion booleana en la condicion del for", fila, columna);
            }

            if(condicion.getValor().toString().equals("true")){
                for(int i = 0; i< local.size(); i++){
                    Instruccion a = local.get(i);
                    Object res = a.interpretar(entFor, tsFor);
                    ent.setConsola(ent.getConsola()+entFor.getConsola());
                    entFor.setConsola("");
                    if(res instanceof Break || a instanceof Break){
                        return null;
                    }
                }
                update.interpretar(entFor, tsFor);
                ent.setConsola(ent.getConsola() + entFor.getConsola());
                entFor.setConsola("");
            }else{
                break;
            }
        }
        
        return this;
    }
    
}
