package funciones;

import AST.NodoAst;
import entorno.*;
import excepciones.Errores;
import expresiones.Dato;
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
        
        try{
            Simbolo sim = (Simbolo)ts.getVariable(this.id);
            Expresion simbol = (Expresion)sim.getValor();
            Expresion variable = (Expresion)simbol.interpretar(ent, ts);

            Expresion nuevoValor = (Expresion)this.expresion.interpretar(ent, ts);
            //this.expresion = (Expresion)this.expresion.interpretar(ent, ts);
            
    
            if(variable.getValor() != "ERROR"){
            
                if(this.tipo == null){
                    if(variable.getMutabilidad() != null){
    
                        if(variable.getMutabilidad().equals("const")){
                        Errores.errores.add(new Errores("Semantico", "ES UNA CONSTANTE, NO PUEDE CAMBIAR DE VALOR: " + this.id, fila, columna));
                        System.out.println("ERROR SEMANTICO ES UNA CONSTANTE, NO PUEDE CAMBIAR DE VALOR: " + this.id);
                        return new Errores("Semantico", "ES UNA CONSTANTE, NO PUEDE CAMBIAR DE VALOR: " + this.id, fila, columna);
                        
                    }else{
    
                        //verificar si la expresion es del mismo tipo que la variable
                        if(variable.getTipo() == nuevoValor.getTipo()){
    
                            Object valor = nuevoValor.getValor();
                            TipoDato tipo = nuevoValor.getTipo();
                            int fila = nuevoValor.getFila();
                            int columna = nuevoValor.getColumna();
                            variable.setValor (new Dato(valor, tipo, fila, columna));
                            
                            return null;

                        }else{
                            Errores.errores.add(new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna));
                            System.out.println("ERROR SEMANTICO Tipos de datos diferentes: " + this.id);
                            return new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna);
                            }
                        }
                    }
                
                }else{
                    if(variable.getMutabilidad() != null){
                        if(variable.getMutabilidad().equals("const")){
                            Errores.errores.add(new Errores("Semantico", "ES UNA CONSTANTE, NO PUEDE CAMBIAR DE VALOR: " + this.id, fila, columna));
                            System.out.println("ERROR SEMANTICO ES UNA CONSTANTE, NO PUEDE CAMBIAR DE VALOR: " + this.id);
                            return new Errores("Semantico", "ES UNA CONSTANTE, NO PUEDE CAMBIAR DE VALOR: " + this.id, fila, columna);
                        
                        }else{
        
                            //casteo de int a double
                            if(variable.getTipo() == TipoDato.DOUBLE && this.tipo == TipoDato.DOUBLE && this.expresion.getTipo() == TipoDato.INT){
                                int valor = (int)Integer.parseInt(this.expresion.getValor().toString());
                                double val = (double)valor;
                                this.expresion.setValor(val);
                                this.expresion.setTipo(TipoDato.DOUBLE);
        
                                if(variable.getTipo() == this.expresion.getTipo()){
        
                                    //verificar si existe en la tabla de simbolos, si es así, se actualiza su valor
                                    if(ts.getVariable(this.id) != null){
                                        variable.setValor(new Dato(this.expresion.getValor(), this.expresion.getTipo(), this.expresion.getFila(), this.expresion.getColumna()));
                                    }
        
                                }else{
                                    Errores.errores.add(new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna));
                                    System.out.println("ERROR SEMANTICO Tipos de datos diferentes: " + this.id);
                                    return new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna);
                                }
                            }
        
                            //casteo de double a int
                            else if(variable.getTipo() == TipoDato.INT && this.tipo == TipoDato.INT && this.expresion.getTipo() == TipoDato.DOUBLE){
                                double valor = (double)Double.parseDouble(this.expresion.getValor().toString());
                                int val = (int)valor;
                                this.expresion.setValor(val);
                                this.expresion.setTipo(TipoDato.INT);
        
                                if(variable.getTipo() == this.expresion.getTipo()){
        
                                    //verificar si existe en la tabla de simbolos, si es así, se actualiza su valor
                                    if(ts.getVariable(id) != null){
                                        variable.setValor(new Dato(this.expresion.getValor(), this.expresion.getTipo(), this.expresion.getFila(), this.expresion.getColumna()));
                                    }
        
                                }else{
                                    Errores.errores.add(new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna));
                                    System.out.println("ERROR SEMANTICO Tipos de datos diferentes: " + this.id);
                                    return new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna);
                                }
                            }
        
                            //casteo de int a char
                            else if(variable.getTipo() == TipoDato.CHAR && this.tipo == TipoDato.CHAR && this.expresion.getTipo() == TipoDato.INT){
                                int valor = (int)Integer.parseInt(this.expresion.getValor().toString());
                                char val = (char)valor;
                                this.expresion.setValor(val);
                                this.expresion.setTipo(TipoDato.CHAR);
        
                                if(variable.getTipo() == this.expresion.getTipo()){
        
                                    //verificar si existe en la tabla de simbolos, si es así, se actualiza su valor
                                    if(ts.getVariable(id) != null){
                                        variable.setValor(new Dato(this.expresion.getValor(), this.expresion.getTipo(), this.expresion.getFila(), this.expresion.getColumna()));
                                    }
        
                                }else{
                                    Errores.errores.add(new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna));
                                    System.out.println("ERROR SEMANTICO Tipos de datos diferentes: " + this.id);
                                    return new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna);
                                }
                            }
        
                            //casteo de char a int
                            else if(variable.getTipo() == TipoDato.INT && this.tipo == TipoDato.INT && this.expresion.getTipo() == TipoDato.CHAR){
                                char valor = this.expresion.getValor().toString().charAt(0);
                                int val = (int)valor;
                                this.expresion.setValor(val);
                                this.expresion.setTipo(TipoDato.INT);
        
                                if(variable.getTipo() == this.expresion.getTipo()){
        
                                    //verificar si existe en la tabla de simbolos, si es así, se actualiza su valor
                                    if(ts.getVariable(id) != null){
                                        variable.setValor(new Dato(this.expresion.getValor(), this.expresion.getTipo(), this.expresion.getFila(), this.expresion.getColumna()));
                                    } 
        
                                }else{
                                    Errores.errores.add(new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna));
                                    System.out.println("ERROR SEMANTICO Tipos de datos diferentes: " + this.id);
                                    return new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna);
                                }
                            }
        
                            //casteo de char a double
                            else if(variable.getTipo() == TipoDato.DOUBLE && this.tipo == TipoDato.DOUBLE && this.expresion.getTipo() == TipoDato.CHAR){
                                char valor = this.expresion.getValor().toString().charAt(0);
                                double val = (double)valor;
                                this.expresion.setValor(val);
                                this.expresion.setTipo(TipoDato.DOUBLE);
        
                                if(variable.getTipo() == this.expresion.getTipo()){
        
                                    //verificar si existe en la tabla de simbolos, si es así, se actualiza su valor
                                    if(ts.getVariable(id) != null){
                                        variable.setValor(new Dato(this.expresion.getValor(), this.expresion.getTipo(), this.expresion.getFila(), this.expresion.getColumna()));
                                    } 
        
                                }else{
                                    Errores.errores.add(new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna));
                                    System.out.println("ERROR SEMANTICO Tipos de datos diferentes: " + this.id);
                                    return new Errores("Semantico", "Tipos de datos diferentes: " + this.id, fila, columna);
                                }
                            }
        
                            else{
                                Errores.errores.add(new Errores("Semantico", "No se puede castear de "+this.expresion.getTipo()+" a "+this.tipo, this.fila, this.columna));
                                System.out.println("ERROR SEMANTICO, No se puede castear de "+this.expresion.getTipo()+" a "+this.tipo);
                                return new Errores("Semantico","No se puede castear de "+this.expresion.getTipo()+" a "+this.tipo, this.fila, this.columna);
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