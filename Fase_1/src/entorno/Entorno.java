package entorno;

import java.util.ArrayList;
import java.util.HashMap;


public class Entorno {

    private String nombre;
    private Entorno anterior;
    private HashMap<String, Simbolo> tablaSimbolos = new HashMap<>();


    public Entorno(String nombre, Entorno anterior){
        this.nombre = nombre;
        this.anterior = anterior;
        this.tablaSimbolos = new HashMap<>();
    }

    public void addSimbolo(String nombre, Object valor, String tipo, String TipoVar, int fila, int columna){
        if(tablaSimbolos.containsKey(nombre)){
            System.out.println("Error: La variable " + nombre + " ya existe en el entorno actual");
            return;
        }
        tablaSimbolos.put(nombre, new Simbolo(nombre, valor, tipo, TipoVar, fila, columna));
    }

    public void getSimbolo(String nombre, int fila, int columna){

    }

}
