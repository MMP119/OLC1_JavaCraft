package expresiones;

import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.tablaSimbolos;
import excepciones.Errores;
import funciones.DatoArreglo2D;
import funciones.DatoLista;

public class Length extends Expresion{

    private Expresion expresion;
    private int fila, columna;

    public Length(Expresion expresion, int fila, int columna){
        super("ERROR_LENGTH", TipoDato.LENGTH, fila, columna);
        this.expresion = expresion;
        this.fila = fila;
        this.columna = columna;
    }


    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("LENGTH");
        nodo.agregarHijo("LENGTH");
        nodo.agregarHijo("(");
        nodo.agregarHijoAST(expresion.getNodo());
        nodo.agregarHijo(")");
        return nodo;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        try {
            Object valor = expresion.interpretar(ent, ts);
            
            if(valor instanceof Dato && ((Dato)valor).getTipo() == TipoDato.CADENA){
                String cadena = (String) valor.toString();
                int numero = cadena.length();
                Dato nuevo = new Dato(numero, TipoDato.INT, fila, columna);
                return nuevo;
            }
            else if (valor instanceof LinkedList) {
                int numero = (int)((LinkedList<?>)valor).size();
                Dato nuevo = new Dato(numero, TipoDato.INT, fila, columna);
                return nuevo;
            }else if(valor instanceof DatoArreglo2D){
                DatoArreglo2D arreglo = (DatoArreglo2D) valor;
                int numero = (int) arreglo.getValor().size();
                Dato nuevo = new Dato(numero, TipoDato.INT, fila, columna);
                return nuevo;
            } else if (valor instanceof DatoLista) {
                DatoLista lista = (DatoLista) valor;
                int numero = (int) lista.getElementos().size();
                Dato nuevo = new Dato(numero, TipoDato.INT, fila, columna);
                return nuevo;
            } else {
                System.out.println("El parametro de length no es un vector, una lista o una cadena");
                Errores.errores.add(new Errores("Semantico", "El parametro de length no es un vector, una lista o una cadena", fila, columna));
                return new Errores("Semantico", "El parametro de length no es un vector, una lista o una cadena", fila, columna);
            }

        } catch (Exception e) {
            e.printStackTrace();
            StackTraceElement[] stackTrace = e.getStackTrace();
            if (stackTrace.length > 0) {
                StackTraceElement element = stackTrace[0];
                System.out.println("Error en la clase: " + element.getClassName());
                System.out.println("Error en el método: " + element.getMethodName());
                System.out.println("Error en la línea: " + element.getLineNumber());
                } 
            System.out.println("ERROR SEMANTICO, la función LENGTH solo acepta arreglos, listas o cadenas.");
            Errores.errores.add(new Errores("ERROR SEMANTICO", "la función LENGTH solo acepta arreglos, listas o cadenas.", this.fila, this.columna));
            return new Errores("ERROR SEMANTICO", "la función LENGTH solo acepta arreglos, listas o cadenas.", this.fila, this.columna);
        }
    }
    
}
