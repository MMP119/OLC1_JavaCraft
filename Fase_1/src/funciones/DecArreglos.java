package funciones;

import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Simbolo;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.Expresion;
import expresiones.TipoDato;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class DecArreglos extends Instruccion {

    private String id;
    private TipoDato tipo;
    private LinkedList<Expresion> expresion;
    private int fila, columna;

    public DecArreglos(String id, TipoDato tipo, LinkedList<Expresion> expresion,int fila, int columna) {
        super(new Tipo(TipoInstruccion.ARREGLO1D), fila, columna);
        this.id = id;
        this.tipo = tipo;
        this.expresion = expresion;
        this.fila = fila;
        this.columna = columna;
    }


    @Override
    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("Declaracion de Arreglos");
        nodo.agregarHijo(id);
        nodo.agregarHijo(":");
        nodo.agregarHijo(tipo.toString());
        nodo.agregarHijo("=");
        nodo.agregarHijo("[");
        for (Expresion exp : expresion) {
            nodo.agregarHijoAST(exp.getNodo());
        }
        nodo.agregarHijo("]");
        nodo.agregarHijo(";");
        return nodo;
    }
    


    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        //verificar si el arreglo ya existe 
        if(ts.getTablaActual().containsKey(this.id)){
            System.out.println("Error Semantico: El arreglo "+this.id+" ya existe en este ambito. Linea: "+this.fila+" Columna: "+this.columna);
            Errores.errores.add(new Errores("Semantico", "El arreglo "+this.id+" ya existe en este ambito", this.fila, this.columna));
            return new Errores("Semantico", "El arreglo "+this.id+" ya existe en este ambito", this.fila, this.columna);
        }

        //Crear el arreglo con los valores interpretados
        LinkedList<Object> valores = new LinkedList<>();
        for (Expresion exp : expresion) {
            Object valor = exp.interpretar(ent, ts);
            Expresion val = (Expresion)valor;
            if(valor instanceof Errores){
                return new Errores("Semantico", "Error al interpretar el valor del arreglo", this.fila, this.columna);
            }

            //verificar si son del mismo tipo
            if(!this.tipo.equals(val.getTipo())){
                System.out.println("Error Semantico: El tipo de dato del arreglo no coincide con el tipo de dato de la expresion. Linea: "+this.fila+" Columna: "+this.columna);
                Errores.errores.add(new Errores("Semantico", "El tipo de dato del arreglo no coincide con el tipo de dato de la expresion", this.fila, this.columna));
                return new Errores("Semantico", "El tipo de dato del arreglo no coincide con el tipo de dato de la expresion", this.fila, this.columna);
            }

            valores.add(valor);
        }

        //verificar si existe en la tabla de simbolos
        if(ts.getTablaActual().containsKey(this.id)){
            System.out.println("Error Semantico: El arreglo "+this.id+" ya existe en este ambito. Linea: "+this.fila+" Columna: "+this.columna);
            Errores.errores.add(new Errores("Semantico", "El arreglo "+this.id+" ya existe en este ambito", this.fila, this.columna));
            return new Errores("Semantico", "El arreglo "+this.id+" ya existe en este ambito", this.fila, this.columna);
        }


        //crear el simbolo del arreglo y agregarlo a la tabla de simbolos 
        Simbolo simbolo = new Simbolo(new Tipo(TipoInstruccion.ARREGLO1D), this.id, new DatoArreglo(valores, this.tipo));
        ts.setVariable(simbolo);

        System.out.println("Declaracion de arreglo: "+this.id+" = "+valores);
    
        return null;
    }
}
