package expresiones;

import AST.NodoAst;
import entorno.*;
import excepciones.Errores;

public class RecVariable extends Expresion {

    // para recuperar el valor de las variables en la tabla de simbolos, si no se enncuentra en la actual, se busca en la anterior y así

    private String id;
    private int fila;
    private int columna;

    public RecVariable(String id, int fila, int columna) {
        super("ERROR", TipoDato.ERROR, fila, columna);
        this.id = id;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("RECUPERAR_VARIABLE");
        nodo.agregarHijo(id);
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        try{
            //Expresion variable = (Expresion)ts.getTablaActual().get(id);
            Simbolo variable = (Simbolo)ts.getVariable(this.id);

            if (variable != null && variable.getValor() != "ERROR"){
                Expresion var = (Expresion)variable.getValor();
                //System.out.println("Variable Recuperada: "+var.getId()+" "+ var.getTipo()+" "+ var.getValor());
                return var;
            }
            else{
                System.out.println("ERROR SEMANTICO, Variable " + id+" no ha sido declarada");
                return new Errores("ERROR SEMANTICO","La variable " + id + " no ha sido declarada", this.fila, this.columna);
            }

        }catch(Exception e){
            return new Errores("Semantico", "Error al interpretar Recuperar Variable", this.fila, this.columna);
        }
    }


    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
}