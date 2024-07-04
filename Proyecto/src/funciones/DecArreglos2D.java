package funciones;

import entorno.Entorno;
import entorno.Simbolo;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.Expresion;
import expresiones.TipoDato;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import java.util.LinkedList;

import AST.NodoAst;

public class DecArreglos2D extends Instruccion{

    private String id;
    private TipoDato tipo;
    private LinkedList<LinkedList<Expresion>> expresion;
    private int fila, columna;

    public DecArreglos2D(String id, TipoDato tipo, LinkedList<LinkedList<Expresion>> expresion, int fila, int columna) {
        super(new Tipo(TipoInstruccion.ARREGLO2D), fila, columna);
        this.id = id;
        this.tipo = tipo;
        this.expresion = expresion;
        this.fila = fila;
        this.columna = columna;
    }
    
    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("Declaracion de Arreglos 2D");
        nodo.agregarHijo(id);
        nodo.agregarHijo(":");
        nodo.agregarHijo(tipo.toString());
        nodo.agregarHijo("=");
        nodo.agregarHijo("[");
        for (LinkedList<Expresion> exp : expresion) {
            NodoAst filaNodo = new NodoAst("Fila");
            filaNodo.agregarHijo("[");
            for (Expresion e : exp) {
                filaNodo.agregarHijoAST(e.getNodo());
            }
            filaNodo.agregarHijo("]");
            nodo.agregarHijoAST(filaNodo);
        }
        nodo.agregarHijo("]");
        nodo.agregarHijo(";");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts){

        try{
        
        //verificar si el arreglo ya existe
        if(ts.getTablaActual().containsKey(this.id)){
            System.out.println("Error Semantico: El arreglo "+this.id+" ya existe en este ambito. Linea: "+this.fila+" Columna: "+this.columna);
            Errores.errores.add(new Errores("Semantico", "El arreglo "+this.id+" ya existe en este ambito", this.fila, this.columna));
            return new Errores("Semantico", "El arreglo "+this.id+" ya existe en este ambito", this.fila, this.columna);
        }

        //crear el arreglo con los valores interpretados
        LinkedList<LinkedList<Object>> valores = new LinkedList<>();
        for (LinkedList<Expresion> fila: expresion){
            LinkedList<Object> filaValores = new LinkedList<>();
            for (Expresion exp: fila){
                Object valor = exp.interpretar(ent, ts);
                Expresion tipoValor = (Expresion)valor;
                if(valor instanceof Errores){
                    return new Errores("Semantico", "Error al interpretar el valor de la fila del arreglo "+this.id, this.fila, this.columna);
                }

                //verificar que el tipo de dato sea el mismo
                if(!this.tipo.equals(tipoValor.getTipo())){
                    System.out.println("Error Semantico: El tipo de dato de la fila del arreglo "+this.id+" no coincide con el tipo de dato del arreglo. Linea: "+this.fila+" Columna: "+this.columna);
                    Errores.errores.add(new Errores("Semantico", "El tipo de dato de la fila del arreglo "+this.id+" no coincide con el tipo de dato del arreglo", this.fila, this.columna));
                    return new Errores("Semantico", "El tipo de dato de la fila del arreglo "+this.id+" no coincide con el tipo de dato del arreglo", this.fila, this.columna);
                }

                filaValores.add(valor);
            }
            valores.add(filaValores);
        }

        //verificar si existe en la tabla de simbolos
        if(ts.getTablaActual().containsKey(this.id)){
            System.out.println("Error Semantico: El arreglo "+this.id+" ya existe en este ambito. Linea: "+this.fila+" Columna: "+this.columna);
            Errores.errores.add(new Errores("Semantico", "El arreglo "+this.id+" ya existe en este ambito", this.fila, this.columna));
            return new Errores("Semantico", "El arreglo "+this.id+" ya existe en este ambito", this.fila, this.columna);
        }

        //crear simbolo del arreglo y agregarlo a la tabla de simbolos 
        Simbolo simbolo = new Simbolo(new Tipo(TipoInstruccion.ARREGLO2D), this.id, new DatoArreglo2D(valores, this.tipo));
        ts.setVariable(simbolo);

        //impresion en consola solo para confirmar xd
        //System.out.println("DEclaracion arreglo 2D:"+this.id+" = "+valores);       
        
        return null;
        
    }catch(Exception e){
        System.out.println("Error en la declaracion del arreglo 2D");
        Errores.errores.add(new Errores("Semantico", "Error en la declaracion del arreglo 2D", this.fila, this.columna));
        return new Errores("Semantico", "Error en la declaracion del arreglo 2D", this.fila, this.columna);
    }
    }

}
