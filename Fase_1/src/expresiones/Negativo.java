package expresiones;

import entorno.*;
import AST.*;
import excepciones.Errores;

public class Negativo extends Expresion {

    private Expresion expresion;
    private int fila;
    private int columna;

    public Negativo(Expresion expresion, int fila, int columna) {
        super("ERROR", TipoDato.ERROR, fila, columna);
        this.expresion = expresion;
        this.fila = fila;
        this.columna = columna;
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
            this.expresion = (Expresion) this.expresion.interpretar(ent, ts);
        //System.out.println(valor.getValor()+" "+valor.getTipo());

            if (this.expresion.getTipo() == TipoDato.DOUBLE) {
                this.expresion.setValor(-1 * Double.parseDouble(this.expresion.getValor().toString()));
                this.expresion.setTipo(TipoDato.DOUBLE);
                //System.out.println(this.expresion.getValor()+" "+this.expresion.getTipo());
                return this.expresion;
            }
            else if(this.expresion.getTipo() == TipoDato.INT){
                this.expresion.setValor(-1 * Integer.parseInt(this.expresion.getValor().toString()));
                this.expresion.setTipo(TipoDato.INT);
                //System.out.println(this.expresion.getValor()+" "+this.expresion.getTipo());
                return this.expresion;
            }

            System.out.println("Error Semántico: Error en la operacion negativo.");
            Errores.errores.add(new Errores("Semantico", "Error en la operacion negativo.", this.fila,  this.columna));
            return this;

        }catch(Exception e){
            Errores.errores.add(new Errores("Semantico", "Error al interpretar Negativo", this.fila,  this.columna));
            return new Errores("Semantico", "Error al interpretar Negativo", this.fila,  this.columna);
        }
    }

    @Override
    public String toString() {
        return this.expresion.toString();
    }
    

}
