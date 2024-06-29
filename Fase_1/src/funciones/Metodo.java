package funciones;

import java.util.HashMap;
import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Simbolo;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.TipoDato;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class Metodo extends Instruccion{
    public String id;
    public LinkedList<Instruccion> instrucciones;
    public LinkedList<HashMap> parametros;
    private int fila, columna;


    public Metodo(String id, LinkedList<HashMap> parametros ,LinkedList<Instruccion> instrucciones, TipoDato tipo, int fila, int columna) {
        super(new Tipo(TipoInstruccion.METODO), fila, columna);
        this.id = id;
        this.instrucciones = instrucciones;
        this.parametros = parametros;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("METODO");
        nodo.agregarHijo(id);
        nodo.agregarHijo("(");
        for (HashMap parametro : parametros) {
            nodo.agregarHijo((String)parametro.get("id"));
            nodo.agregarHijo(":");
            nodo.agregarHijo((TipoDato)parametro.get("tipo"));
        }
        nodo.agregarHijo(")");
        nodo.agregarHijo("{");
        for (Instruccion instruccion : instrucciones) {
            nodo.agregarHijoAST(instruccion.getNodo());
        }
        nodo.agregarHijo("}");
        return nodo;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        try{
            for(var i: this.instrucciones){
                var resultado = i.interpretar(ent, ts);
                if(resultado != null){
                    if(resultado instanceof Errores){
                        return resultado;
                    }

                    if (resultado instanceof Return) {
                        return resultado;
                    }

                    //si es instancia de un break o continue es un error, ya que no se puede usar fuera de un ciclo
                    if(resultado instanceof Break || resultado instanceof Continue){
                        System.out.println("Error: break o continue fuera de un ciclo");
                        Errores.errores.add(new Errores("Semantico", "break o continue fuera de un ciclo", fila, columna));
                        return new Errores("Semantico", "break o continue fuera de un ciclo", fila, columna);
                    }
                }
            }
            return null;
        }catch(Exception e){
            System.out.println("Error en metodo: "+e);
            Errores.errores.add(new Errores("Semantico", "Error en metodo: "+e, fila, columna));
            return new Errores("Semantico", "Error en metodo: "+e, fila, columna);
        }
    }
    
}
