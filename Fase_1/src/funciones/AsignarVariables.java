package funciones;

import AST.NodoAst;
import entorno.*;
import excepciones.Errores;
import expresiones.Expresion;
import expresiones.TipoDato;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;


public class AsignarVariables extends Instruccion{

    private String id;
    private Expresion expresion;
    private TipoDato tipo;
    private int fila;
    private int columna;

    public AsignarVariables(String id,Expresion exp, TipoDato tipo, int fila, int columna) {
        super(new Tipo(TipoInstruccion.ASIGNAR), fila, columna);
        this.id = id;
        this.expresion = exp;
        this.tipo = tipo;
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
        //Expresion variable = (Expresion)new RecVariable(this.id, this.fila, this.columna);
        try{
            Simbolo sim = (Simbolo)ts.getVariable(this.id);
            Expresion variable = (Expresion)sim.getValor();
            this.expresion = (Expresion)this.expresion.interpretar(ent, ts);

            //variable =  (Expresion)variable.interpretar(ent, ts);
            //System.out.println("AQUI Recuperada: "+variable.getId()+" "+ variable.getTipo()+" "+ variable.getValor());
            
    
            if(variable.getValor() != "ERROR"){
            
                if(this.tipo == null){
                    if(variable.getMutabilidad() != null){
    
                        if(variable.getMutabilidad().equals("const")){
                        Errores.errores.add(new Errores("Semantico", "ES UNA CONSTANTE, NO PUEDE CAMBIAR DE VALOR: " + this.id, fila, columna));
                        System.out.println("ERROR SEMANTICO ES UNA CONSTANTE, NO PUEDE CAMBIAR DE VALOR: " + this.id);
                        return new Errores("Semantico", "ES UNA CONSTANTE, NO PUEDE CAMBIAR DE VALOR: " + this.id, fila, columna);
                        
                    }else{
    
                        //verificar si la expresion es del mismo tipo que la variable
                        if(variable.getTipo() == this.expresion.getTipo()){
    
                            variable.setValor(this.expresion.getValor());
    
                            //verificar si existe en la tabla de simbolos, si es así, se actualiza su valor
                            if(ts.getTablaActual().containsKey(this.id)){
                                ts.getTablaActual().replace(this.id, new Simbolo(new Tipo(TipoInstruccion.DECLARAR), this.id, variable));
                            }   
    
                        }else{
                            Errores.errores.add(new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna));
                            System.out.println("ERROR SEMANTICO Tipos de datos diferentes: " + this.id);
                            return new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna);
                            }
                        }
                    }
                
                }
    
            }else{
                Errores.errores.add(new Errores("Semantico", "Variable no declarada: " + this.id, fila, columna));
                System.out.println("ERROR SEMANTICO Variable NO DECLARADA:" + this.id);
                return new Errores("Semantico", "Variable no declarada: " + this.id, fila, columna);
            }
            
    
            return this;
        }
        catch(Exception e){
            Errores.errores.add(new Errores("Semantico", "Variable no declarada: " + this.id, fila, columna));
            System.out.println("ERROR SEMANTICO Variable NO DECLARADA:" + this.id);
            return new Errores("Semantico", "Variable no declarada: " + this.id, fila, columna);
        }

    }
    
}