package expresiones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.*;
import entorno.tablaSimbolos;
import excepciones.Errores;
import funciones.DatoArreglo;
import funciones.DatoArreglo2D;
import funciones.DatoLista;

import java.util.LinkedList;



public class Find extends Expresion{
    
    private String id;
    private Expresion exp;
    private int fila, columna;
    private String valorFinal = "";

    public Find(String id, Expresion exp, int fila, int columna){
        super("ERROR_FIND", TipoDato.ERROR, fila, columna);
        this.id = id;
        this.exp = exp;
        this.fila = fila;
        this.columna = columna;
    }


    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("FIND");
        nodo.agregarHijo("FIND");
        nodo.agregarHijo("(");
        nodo.agregarHijo(id);
        nodo.agregarHijo(",");
        nodo.agregarHijoAST(exp.getNodo());
        nodo.agregarHijo(")");
        return nodo;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        Simbolo sim = ts.getVariable(id);

        if(sim == null){
            System.out.println("No se encontro el vector o lista: " + id);
            Errores.errores.add(new Errores("Semantico", "No se encontro el vector o lista: " + id, fila, columna));
            valorFinal = "false";
            return false;
        }


        Object valor = sim.getValor();

        if(!(valor instanceof LinkedList) && !(valor instanceof DatoArreglo) && !(valor instanceof DatoArreglo2D) && !(valor instanceof DatoLista)){
            System.out.println("Semantico, el simbolo "+id+" no es un vector o lista");
            Errores.errores.add(new Errores("Semantico", "El simbolo "+id+" no es un vector o lista", fila, columna));
            valorFinal = "false";
            return false;
        }

        if(valor instanceof LinkedList){
            LinkedList<?> lista = (LinkedList<?>) valor;
            Object valorBuscado = exp.interpretar(ent, ts);
            Expresion e = (Expresion)valorBuscado;
            for (Object elemento : lista) {
                if(elemento.toString().equals(e.getValor().toString())){
                    valorFinal = "true";
                    return true;
                }
            }
        }

        else if(valor instanceof DatoArreglo){
            DatoArreglo arre = (DatoArreglo) valor;
            LinkedList<Object> arreglo = arre.getValor();
            Object valorBuscado = exp.interpretar(ent, ts);
            Expresion e = (Expresion)valorBuscado;
            for (Object elemento : arreglo) {
                if(elemento.toString().equals(e.getValor().toString())){
                    valorFinal = "true";
                    return true;
                }
            }
        }

        else if(valor instanceof DatoArreglo2D){
            DatoArreglo2D arre = (DatoArreglo2D) valor;
            LinkedList<LinkedList<Object>> arreglo = arre.getValor();
            Object valorBuscado = exp.interpretar(ent, ts);
            Expresion e = (Expresion)valorBuscado;
            for (LinkedList<Object> fila : arreglo) {
                for (Object elemento : fila) {
                    if(elemento.toString().equals(e.getValor().toString())){
                        valorFinal = "true";
                        return true;
                    }
                }
            }
        }

        else if(valor instanceof DatoLista){
            DatoLista lista = (DatoLista) valor;
            LinkedList<Object> arreglo = lista.getElementos();
            Object valorBuscado = exp.interpretar(ent, ts);
            Expresion e = (Expresion)valorBuscado;
            for (Object elemento : arreglo) {
                if(elemento.toString().equals(e.getValor().toString())){
                    valorFinal = "true";
                    return true;
                }
            }
        }
        valorFinal = "false";
        return false;
    }

    public String toString(){
        return valorFinal;
    }

}
