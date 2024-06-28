package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import java.util.LinkedList;
import excepciones.Errores;

public class Else extends Instruccion{

    private LinkedList<Instruccion> inst_else;

    private int fila, columna;

    public Else(LinkedList<Instruccion> inst_else, int fila, int columna) {
        super(new Tipo(TipoInstruccion.ELSE), fila, columna);
        this.inst_else = inst_else;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("ELSE");
        nodo.agregarHijo("else");
        nodo.agregarHijo("{");
        nodo.agregarHijoAST(inst_else.get(0).getNodo());
        nodo.agregarHijo("}");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        
        try{
            tablaSimbolos tsElse = new tablaSimbolos(ts);
            tsElse.setNombre("else");
            tablaSimbolos.tablas.add(tsElse);

            for(Instruccion i : inst_else){
                Object resultado = i.interpretar(ent, tsElse);

                if(resultado instanceof Errores){
                    return resultado;
                }

                if(resultado instanceof Break){
                    return new Break(this.fila, this.columna);
                }

                if(resultado instanceof Continue){
                    return new Continue(this.fila, this.columna);
                }

                if(resultado instanceof Return){
                    return resultado;
                }
            }
            
            return null;
            
        }catch(Exception e){
            Errores.errores.add(new Errores("Semantico", "Error en la instruccion Else", fila, columna));
            return new Errores("Semantico", "Error en la instruccion Else", fila, columna);
        }
    }
    
}