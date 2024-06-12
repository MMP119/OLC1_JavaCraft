package funciones;

import AST.NodoAst;
import entorno.*;
import excepciones.Errores;
import expresiones.Expresion;
import expresiones.RecVariable;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class AsignarVariables extends Instruccion{

    private String id;
    private Expresion expresion;
    private int fila;
    private int columna;

    public AsignarVariables(String id,Expresion exp, int fila, int columna) {
        super(new Tipo(TipoInstruccion.ASIGNAR), fila, columna);
        this.id = id;
        this.expresion = exp;
        this.fila = fila;
        this.columna = columna;
    }


    @Override
    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("ASIGNAR_VARIABLE");
        nodo.agregarHijo(id);
        nodo.agregarHijo("=");
        nodo.agregarHijoAST(expresion.getNodo());
        nodo.agregarHijo(";");
        return nodo;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        
        //verificar si la variable existe
        Expresion variable = (Expresion)new RecVariable(this.id, this.fila, this.columna);

        //interpretar la variable y la expresion 
        variable = (Expresion)variable.interpretar(ent, ts);
        this.expresion = (Expresion)this.expresion.interpretar(ent, ts);
        //System.out.println("Variable: "+ variable.getValor()+" "+variable.getTipo()+" "+variable.getFila()+" "+variable.getColumna());

        if(variable.getValor() != "ERROR"){
            //System.out.println("La variable a asignar existe: " + this.id);

            if(variable.getMutabilidad().equals("const")){
                System.out.println("ERROR SEMANTICO ES UNA CONSTANTE, NO PUEDE CAMBIAR DE VALOR: " + this.id);
                return new Errores("Semantico", "ES UNA CONSTANTE, NO PUEDE CAMBIAR DE VALOR: " + this.id, fila, columna);
            }else{

                //verificar si la expresion es del mismo tipo que la variable
                if(variable.getTipo() == this.expresion.getTipo()){

                    //verificar si existe en la tabla de simbolos, si es así, se actualiza su valor
                    if(ts.getTablaActual().containsKey(this.id)){
                        ts.getTablaActual().replace(id, this.expresion);
                        //System.out.println("Variable actualizada: " + this.id + " = " + this.expresion.getValor());
                    }   

                }else{
                    System.out.println("ERROR SEMANTICO Tipos de datos diferentes: " + this.id);
                    return new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna);

                }
            }


        }else{
            System.out.println("ERROR SEMANTICO Variable NO DECLARADA:" + this.id);
            return new Errores("Semantico", "Variable no declarada: " + this.id, fila, columna);
        }
        

        return this;

    }
    
}
