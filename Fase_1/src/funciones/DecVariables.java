package funciones;


import entorno.*;
import instruccion.*;
import AST.*;
import expresiones.Expresion;
import expresiones.TipoDato;
import excepciones.Errores;

public class DecVariables extends Instruccion{

    private String mutabilidad;
    private String id;
    private TipoDato tipo;
    private Expresion expresion;
    private int fila;
    private int columna;

    public DecVariables(String mutabilidad, String id, TipoDato tipo, Expresion expresion, int fila, int columna){
        super(new Tipo(TipoInstruccion.DECLARAR), fila, columna);
        this.mutabilidad = mutabilidad;
        this.id = id;
        this.tipo = tipo;
        this.expresion = expresion;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("DECLARACION_VARIABLES");
        nodo.agregarHijo(this.mutabilidad);
        nodo.agregarHijo(id);
        nodo.agregarHijo(":");
        nodo.agregarHijo(tipo);
        if(expresion != null){
            nodo.agregarHijo("=");
            nodo.agregarHijoAST(expresion.getNodo());
        }
        nodo.agregarHijo(";");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        var valor = expresion.interpretar(ent, ts);

        if(valor instanceof Errores) {
            return valor;
        }

        if(ts.getTablaActual().containsKey(id)){
            System.out.println("Variable "+this.id+" ya existe");
            return new Errores("Semantico","Variable "+this.id+" ya existe", this.fila, this.columna);
        }

        if(this.mutabilidad.equals("const")){
            ts.getTablaActual().put(id, valor);
        }else{
            ts.getTablaActual().put(id, this.expresion);
        }
        return this;
    }

    
}
