package expresiones;

import AST.*;
import entorno.*;

public class Relacionales extends Expresion{

    private Expresion izquierda;
    private Expresion dererecha;
    private String operador;


    public Relacionales(Expresion izquierda, Expresion derecha, String operador, int fila, int columna) {
        super("ERROR", TipoDato.ERROR, fila, columna);
        this.izquierda = izquierda;
        this.dererecha = derecha;
        this.operador = operador;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("RELACIONAL");
        nodo.agregarHijoAST(this.izquierda.getNodo());
        nodo.agregarHijo(this.operador);
        nodo.agregarHijoAST(this.dererecha.getNodo());
        return nodo;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts){

        Expresion izq = (Expresion) this.izquierda.interpretar(ent, ts);
        Expresion der = (Expresion) this.dererecha.interpretar(ent, ts);

        switch (this.operador) {
            case "==":
                
                break;
            
            case "!=":
                    
                break;
            
            case "<":

                break;

            case "<=":

                break;
            

            case ">":
                
                break;

            case ">=":

                break;
        
            default:
                break;
        }


        return this;
    }



    
}
