package funciones;

import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.Expresion;
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

        var newTabla = new tablaSimbolos(ts);
        tablaSimbolos.tablas.add(newTabla);

        try{
            
            while(Boolean.parseBoolean(exp.interpretar(ent, newTabla).toString())){

                var newTabla2 = new tablaSimbolos(newTabla);
                tablaSimbolos.tablas.add(newTabla2);

                for(Instruccion i : inst){
                    Object result = i.interpretar(ent, newTabla2);
                    if(result != null){

                        if(result instanceof Break){
                            Instruccion.cicloProfundida--; //Disminuimos la profundidad del ciclo antes de salir
                            return null;
                        }

                        if(result instanceof Continue){
                            break;
                        }

                    }
                }

            }

        }catch(Exception e){
            Errores.errores.add(new Errores("Error Semantico", "Error en While: "+e.getMessage(), fila, columna));
            System.out.println("Error en While: "+e.getMessage());
            return null;
        }

        Instruccion.cicloProfundida--; //Disminuimos la profundidad del ciclo antes de salir
        return null;
    }  
}
