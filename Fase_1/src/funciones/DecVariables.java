package funciones;


import entorno.*;
import instruccion.*;
import AST.*;
import expresiones.Expresion;
import expresiones.TipoDato;
import excepciones.Errores;
import expresiones.Dato;

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

        //si expresion es null, se declara una variable asignando un valor por defecto
        if(this.expresion == null){
            switch (this.tipo) {
                case INT:
                    this.expresion = new Dato(0, TipoDato.INT, this.fila, this.columna);    
                    break;
                case DOUBLE:
                    this.expresion = new Dato(0.0, TipoDato.DOUBLE, this.fila, this.columna);
                    break;
                case BOOLEAN:
                    this.expresion = new Dato(true, TipoDato.BOOLEAN, this.fila, this.columna);
                    break;
                case CHAR:
                    this.expresion = new Dato('0', TipoDato.CHAR, this.fila, this.columna);
                    break;
                case CADENA:
                    this.expresion = new Dato("", TipoDato.CADENA, this.fila, this.columna);
                    break;            
                default:
                    System.out.println("ERROR SEMANTICO, Tipo de dato no valido");
                    break;
            }
        }

        if(this.expresion != null){

            Expresion valor = (Expresion)this.expresion.interpretar(ent, ts);

            if(ts.getTablaActual().containsKey(id)){
                System.out.println("Variable "+this.id+" ya existe");
                return new Errores("Semantico","Variable "+this.id+" ya existe", this.fila, this.columna);
            }
            
            valor.setMutabilidad(this.mutabilidad);
            ts.getTablaActual().put(id, this.expresion);
        }
        return this;
    }
    
}
