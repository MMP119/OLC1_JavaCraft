package expresiones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.tablaSimbolos;
import excepciones.Errores;


public class Round extends Expresion{


    private Expresion expresion;
    private int fila, columna;

    public Round(Expresion expresion, int fila, int columna){
        super("ERROR_ROUND", TipoDato.ROUND, fila, columna);
        this.expresion = expresion;
        this.fila = fila;
        this.columna = columna;
    }


    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("ROUND");
        nodo.agregarHijo("ROUND");
        nodo.agregarHijo("(");
        nodo.agregarHijoAST(expresion.getNodo());
        nodo.agregarHijo(")");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        try {
            Object valor = expresion.interpretar(ent, ts);

            try{
                double numero = Double.parseDouble(valor.toString());
                double numeroRedondeado = Math.round(numero);
                int numeroFinal = (int) numeroRedondeado;
                Dato nuevo = new Dato(numeroFinal, TipoDato.INT, fila, columna);
                return nuevo;  
                
            } catch (Exception e){
                System.out.println("El parametro de round no es un numero");
                Errores.errores.add(new Errores("Semantico", "El parametro de round no es un numero", fila, columna));
                return new Errores("Semantico", "El parametro de round no es un numero", fila, columna);
            }

        } catch (Exception e) {
            System.out.println("ERROR SEMANTICO, la función ROUND solo acepta números decimales.");
            Errores.errores.add(new Errores("ERROR SEMANTICO", "la función ROUND solo acepta números decimales.", this.fila, this.columna));
            return new Errores("ERROR SEMANTICO", "la función ROUND solo acepta números decimales.", this.fila, this.columna);
        }
    }
    
}
