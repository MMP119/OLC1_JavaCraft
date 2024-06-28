package funciones;

import instruccion.Instruccion;
import expresiones.Expresion;
import expresiones.TipoDato;
import instruccion.TipoInstruccion;
import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;

public class If extends Instruccion{

    private Expresion condicion;
    private LinkedList<Instruccion> inst_if;
    private Instruccion instr_else;
    private int fila;
    private int columna;


    public If(Expresion condicion, LinkedList<Instruccion> inst_if, Instruccion instr_else, int fila, int columna) {
        super(new Tipo(TipoInstruccion.IF), fila, columna);
        this.condicion = condicion;
        this.inst_if = inst_if;
        this.instr_else = instr_else;
    }
    

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("IF");
        nodo.agregarHijo("if");
        nodo.agregarHijo("(");
        nodo.agregarHijoAST(this.condicion.getNodo());
        nodo.agregarHijo(")");
        nodo.agregarHijo("{");
        NodoAst nodoIf = new NodoAst("IF");
        for (var a: inst_if){
            nodoIf.agregarHijoAST(a.getNodo());
        }
        nodo.agregarHijoAST(nodoIf);
        nodo.agregarHijo("}");
        return nodo;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        try{
            tablaSimbolos tsIf = new tablaSimbolos(ts);
            tsIf.setNombre("if");
            tablaSimbolos.tablas.add(tsIf);
            this.condicion = (Expresion)this.condicion.interpretar(ent, ts);
            

            if(this.condicion.getTipo()!= TipoDato.BOOLEAN){
                System.out.println("ERROR SEMANTICO: Se esperaba una expresion booleana en la condicion del if");
                Errores.errores.add(new Errores("Semantico", "Se esperaba una expresion booleana en la condicion del if", fila, columna));
                return new Errores("Semantico", "Se esperaba una expresion booleana en la condicion del if", fila, columna);
            }

            if(Boolean.parseBoolean(this.condicion.getValor().toString()) == true){

                for (Instruccion inst: inst_if){
                    Object result = inst.interpretar(ent, tsIf);

                    if (result instanceof Errores){
                        return result;
                    }

                    if(inst instanceof Break){
                        return new Break(fila, columna);
                    }

                    if(inst instanceof Continue){
                        return new Continue(fila, columna);
                    }

                    if(inst instanceof Return){
                        return result;
                    }
                
                }


            }else{

                if(instr_else != null){
                    
                    if(instr_else instanceof Else){
                        Object a = instr_else.interpretar(ent, tsIf);
                        
                        if(a instanceof Errores){
                            return a;
                        }

                        if(a instanceof Break){
                            return new Break(fila, columna);
                        }

                        if(a instanceof Continue){
                            return new Continue(fila, columna);
                        }

                        if(a instanceof Return){
                            return a;
                        }

                    }


                    if(instr_else instanceof If){
                        Object a = instr_else.interpretar(ent, tsIf);
                        
                        if(a instanceof Errores){
                            return a;
                        }

                        if(a instanceof Break){
                            return new Break(fila, columna);
                        }

                        if(a instanceof Continue){
                            return new Continue(fila, columna);
                        }

                        if(a instanceof Return){
                            return a;
                        }

                    }


                }

            }

            return null;

        }catch(Exception e){
            System.out.println("ERROR SEMANTICO: Error en la condicion del if try catch: "+e.getMessage());
            Errores.errores.add(new Errores("Semantico", "Error en la condicion del if ERROR", fila, columna));
            return new Errores("Semantico", "Error en la condicion del if ERROR", fila, columna);
        }

    }

}
