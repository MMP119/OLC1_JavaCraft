package expresiones;

import entorno.*;
import AST.*;

public class Negativo extends Expresion{
    
    private Expresion expresion;

    public Negativo(Expresion expresion,int fila, int columna){
        super(expresion,TipoDato.ERROR, fila, columna);
        this.expresion = expresion;
    }

    @Override
    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("NEGATIVO");
        nodo.agregarHijo(new NodoAst("-"));
        nodo.agregarHijoAST(expresion.getNodo());
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        
        Expresion valor = (Expresion)this.expresion;
        System.out.println("Valor: "+valor.getValor());

        valor.interpretar(ent, ts);

        if(valor.getTipo() == TipoDato.INT){
            valor.setTipo(TipoDato.INT);
            valor.setValor((int)Integer.parseInt(valor.getValor().toString()) * -1);
            return this;
        }
        if(valor.getTipo() == TipoDato.DOUBLE){
            valor.setTipo(TipoDato.DOUBLE);
            valor.setValor((double)Double.parseDouble(valor.getValor().toString()) * -1);
            return this;
        }

        return this;
    }
}
