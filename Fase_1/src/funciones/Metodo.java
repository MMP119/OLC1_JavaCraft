package funciones;

import java.util.HashMap;
import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import expresiones.TipoDato;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class Metodo extends Instruccion{
    public String id;
    public LinkedList<Instruccion> instrucciones;
    public LinkedList<HashMap> parametros;


    public Metodo(String id, LinkedList<HashMap> parametros ,LinkedList<Instruccion> instrucciones, TipoDato tipo, int fila, int columna) {
        super(new Tipo(TipoInstruccion.METODO), fila, columna);
        this.id = id;
        this.instrucciones = instrucciones;
        this.parametros = parametros;
    }

    public NodoAst getNodo() {
        return null;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        for(var i: this.instrucciones){
            var resultado = i.interpretar(ent, ts);
            //resultado instancia de errores
            //validar si viene un return
            //si viene un break o continue es un error semantico


        }


        return null;
    }
    


}
