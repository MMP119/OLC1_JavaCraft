package expresiones;

import AST.*;
import entorno.*;


public class Logicos extends Expresion{

    private Expresion izquierda;
    private Expresion dererecha;
    private String operador;

    public Logicos(Expresion izquierda, Expresion derecha, String operador, int fila, int columna) {
        super("ERROR", TipoDato.ERROR, fila, columna);
        this.izquierda = izquierda;
        this.dererecha = derecha;
        this.operador = operador;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("LOGICO");
        nodo.agregarHijoAST(this.izquierda.getNodo());
        nodo.agregarHijo(this.operador);
        if(!this.operador.equals("!")){ // si no es operador unario, agrego la derecha
            nodo.agregarHijoAST(this.dererecha.getNodo());
        }
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts){

        Expresion izq = (Expresion) this.izquierda.interpretar(ent, ts);
        Expresion der = (Expresion) this.dererecha.interpretar(ent, ts);


        return this;
    }
    
}
