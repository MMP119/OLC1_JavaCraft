package expresiones;

import entorno.*;
import AST.*;

public class Negativo extends Expresion {

    private Expresion expresion;

    public Negativo(Expresion expresion, int fila, int columna) {
        super("ERROR", TipoDato.ERROR, fila, columna);
        this.expresion = expresion;
    }

    @Override
    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("NEGATIVO");
        nodo.agregarHijo(new NodoAst("-"));
        nodo.agregarHijoAST(expresion.getNodo());
        return nodo;
    }

    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        Expresion valor = (Expresion) this.expresion.interpretar(ent, ts);

        if (valor.getTipo() == TipoDato.DOUBLE) {
            this.setTipo(valor.getTipo());
            this.setValor(-1 * Double.parseDouble(valor.getValor().toString()));
            return this;
        }
        else if(valor.getTipo() == TipoDato.INT){
            this.setTipo(valor.getTipo());
            this.setValor(-1 * Integer.parseInt(valor.getValor().toString()));
            return this;
        }

        System.out.println("Error Semántico: Error en la operacion negativo.");
        return this;
    }

    @Override
    public String toString() {
        return "-" + this.expresion.toString();
    }

}
