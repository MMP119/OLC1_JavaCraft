package expresiones;

import entorno.*;
import AST.*;
import excepciones.Errores;

public class Negativo extends Expresion {

    private Expresion expresion;

    public Negativo(Expresion expresion, int fila, int columna) {
        super("ERROR", TipoDato.ERROR, fila, columna);
        this.expresion = expresion;
    }

    @Override
    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("NEGACION");
        nodo.agregarHijo(new NodoAst("-"));
        nodo.agregarHijoAST(expresion.getNodo());
        return nodo;
    }

    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        try{
            Expresion valor = (Expresion) this.expresion.interpretar(ent, ts);
        //System.out.println(valor.getValor()+" "+valor.getTipo());

            if (valor.getTipo() == TipoDato.DOUBLE) {
                valor.setValor(-1 * Double.parseDouble(valor.getValor().toString()));
                valor.setTipo(TipoDato.DOUBLE);
                //System.out.println(this.expresion.getValor()+" "+this.expresion.getTipo());
                return valor;
            }
            else if(valor.getTipo() == TipoDato.INT){
                valor.setValor(-1 * Integer.parseInt(valor.getValor().toString()));
                valor.setTipo(TipoDato.INT);
                //System.out.println(this.expresion.getValor()+" "+this.expresion.getTipo());
                return valor;
            }

            System.out.println("Error Semántico: Error en la operacion negativo.");
            Errores.errores.add(new Errores("Semantico", "Error en la operacion negativo.", this.getFila(), this.getColumna()));
            return this;

        }catch(Exception e){
            Errores.errores.add(new Errores("Semantico", "Error al interpretar Negativo", this.getFila(), this.getColumna()));
            return new Errores("Semantico", "Error al interpretar Negativo", this.getFila(), this.getColumna());
        }
    }

    @Override
    public String toString() {
        return "-" + this.expresion.toString();
    }

    // Getters y Setters
    public Expresion getExpresion() {
        return expresion;
    }

    public void setExpresion(Expresion expresion) {
        this.expresion = expresion;
    }

    

}
