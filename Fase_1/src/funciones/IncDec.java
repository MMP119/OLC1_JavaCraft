package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Simbolo;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.Expresion;
import expresiones.TipoDato;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class IncDec extends Instruccion {

    private String id;
    private String operador;
    private int fila;
    private int columna;

    public IncDec(String id,String operador, int fila, int columna) {
        super(new Tipo(TipoInstruccion.IncDec), fila, columna);
        this.id = id;
        this.operador = operador;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("INC_DEC");
        nodo.agregarHijo(id);
        nodo.agregarHijo(operador);
        nodo.agregarHijo(";");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        //verificar si la variable existe
        //Expresion variable = (Expresion)new RecVariable(this.id, this.fila, this.columna);
        try{
            Simbolo sim = (Simbolo)ts.getVariable(this.id);
        Expresion variable = (Expresion)sim.getValor();
        variable = (Expresion)variable.interpretar(ent, ts);
        
        if(variable.getValor() != "ERROR"){
            if(variable.getMutabilidad().equals("const")){
                System.out.println("ERROR SEMANTICO ES UNA CONSTANTE, NO PUEDE CAMBIAR DE VALOR: " + this.id);
                Errores.errores.add(new Errores("Semantico", "ES UNA CONSTANTE, NO PUEDE CAMBIAR DE VALOR: " + this.id, fila, columna));
                return new Errores("Semantico", "ES UNA CONSTANTE, NO PUEDE CAMBIAR DE VALOR: " + this.id, fila, columna);
            }else{

                if(this.operador.equals("++")){
                    if(variable.getTipo() == TipoDato.INT || variable.getTipo() == TipoDato.DOUBLE){
                        if(variable.getTipo() == TipoDato.INT){
                            int valor = (int)Integer.parseInt(variable.getValor().toString());
                            variable.setValor(valor + 1);

                            if(ts.getTablaActual().containsKey(this.id)){
                                ts.getTablaActual().replace(this.id, new Simbolo(new Tipo(TipoInstruccion.IncDec), this.id, variable));
                            }  

                        }else{
                            double valor = (double)Double.parseDouble(variable.getValor().toString());
                            variable.setValor(valor + 1.0);

                            if(ts.getTablaActual().containsKey(this.id)){
                                ts.getTablaActual().replace(id, new Simbolo(new Tipo(TipoInstruccion.IncDec), this.id, variable));
                            }  
                        }
                    }else{
                        System.out.println("ERROR SEMANTICO NO SE PUEDE INCREMENTAR UNA VARIABLE DE TIPO: " + variable.getTipo());
                        Errores.errores.add(new Errores("Semantico", "NO SE PUEDE INCREMENTAR UNA VARIABLE DE TIPO: " + variable.getTipo(), fila, columna));
                        return new Errores("Semantico", "NO SE PUEDE INCREMENTAR UNA VARIABLE DE TIPO: " + variable.getTipo(), fila, columna);
                    }
                    
                }else{

                    if(variable.getTipo() == TipoDato.INT || variable.getTipo() == TipoDato.DOUBLE){
                        if(variable.getTipo() == TipoDato.INT){
                            int valor = (int)Integer.parseInt(variable.getValor().toString());
                            variable.setValor(valor - 1);

                            if(ts.getTablaActual().containsKey(this.id)){
                                ts.getTablaActual().replace(id, new Simbolo(new Tipo(TipoInstruccion.IncDec), this.id, variable));
                            }  

                        }else{
                            double valor = (double)Double.parseDouble(variable.getValor().toString());
                            variable.setValor(valor - 1.0);

                            if(ts.getTablaActual().containsKey(this.id)){
                                ts.getTablaActual().replace(id, new Simbolo(new Tipo(TipoInstruccion.IncDec), this.id, variable));
                            }  

                        }
                    }else{
                        System.out.println("ERROR SEMANTICO NO SE PUEDE DECREMENTAR UNA VARIABLE DE TIPO: " + variable.getTipo());
                        Errores.errores.add(new Errores("Semantico", "NO SE PUEDE DECREMENTAR UNA VARIABLE DE TIPO: " + variable.getTipo(), fila, columna));
                        return new Errores("Semantico", "NO SE PUEDE DECREMENTAR UNA VARIABLE DE TIPO: " + variable.getTipo(), fila, columna);
                    }
                }
                
            }
        }
        return null;
        }catch(Exception e){
            Errores.errores.add(new Errores("Semantico", "NO SE PUEDE DECREMENTAR UNA VARIABLE", this.fila, this.columna));
            return new Errores("Semantico", "NO SE PUEDE DECREMENTAR UNA VARIABLE", this.fila, this.columna);
        }
    }
    
}
