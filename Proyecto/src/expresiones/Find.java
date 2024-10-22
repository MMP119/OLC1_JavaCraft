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
    private int linea, columna;
    private String valorFinal = "";

    public Find(String id, Expresion exp, int fila, int columna){
        super("ERROR_FIND", TipoDato.ERROR, fila, columna);
        this.id = id;
        this.exp = exp;
        this.linea = fila;
        this.columna = columna;
    }


    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("FIND");
        nodo.agregarHijo(id);
        nodo.agregarHijo(".FIND");
        nodo.agregarHijo("(");
        nodo.agregarHijoAST(exp.getNodo());
        nodo.agregarHijo(")");
        return nodo;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        try{
            Simbolo sim = ts.getVariable(id);

            if(sim == null){
                System.out.println("No se encontro el vector o lista: " + id);
                Errores.errores.add(new Errores("Semantico", "No se encontro el vector o lista: " + id, linea, columna));
                valorFinal = "false";
                return new Dato(false, TipoDato.BOOLEAN, linea, columna);
            }


            Object valor = sim.getValor();

            if(!(valor instanceof LinkedList) && !(valor instanceof DatoArreglo) && !(valor instanceof DatoArreglo2D) && !(valor instanceof DatoLista)){
                System.out.println("Semantico, el simbolo "+id+" no es un vector o lista");
                Errores.errores.add(new Errores("Semantico", "El simbolo "+id+" no es un vector o lista", linea, columna));
                valorFinal = "false";
                return new Dato(false, TipoDato.BOOLEAN, linea, columna);
            }

            if(valor instanceof LinkedList){
                LinkedList<?> lista = (LinkedList<?>) valor;
                Object valorBuscado = exp.interpretar(ent, ts);
                Expresion e = (Expresion)valorBuscado;
                for (Object elemento : lista) {
                    if(elemento.toString().equals(e.getValor().toString())){
                        valorFinal = "true";
                        return new Dato(true, TipoDato.BOOLEAN, linea, columna);
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
                        return new Dato(true, TipoDato.BOOLEAN, linea, columna);
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
                            return new Dato(true, TipoDato.BOOLEAN, linea, columna);
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
                        return new Dato(true, TipoDato.BOOLEAN, linea, columna);
                    }
                }
            }
            valorFinal = "false";
            return new Dato(false, TipoDato.BOOLEAN, linea, columna);
            
        }catch(Exception e){
            System.out.println("Error al buscar el elemento en la lista o vector: "+id);
            Errores.errores.add(new Errores("Semantico", "Error al buscar el elemento en la lista o vector: "+id, linea, columna));
            valorFinal = "false";
            return new Dato(false, TipoDato.BOOLEAN, linea, columna);
        }
    }

    public String toString(){
        return valorFinal;
    }

}
