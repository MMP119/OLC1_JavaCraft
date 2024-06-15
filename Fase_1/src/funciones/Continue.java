package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class Continue extends Instruccion{

    private int fila, columna;

    public Continue(int fila, int columna) {
        super(new Tipo(TipoInstruccion.CONTINUE), fila, columna);
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("CONTINUE");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        try{
            if(Instruccion.cicloProfundida == 0){
                //System.out.println("ERROR SEMANTICO: Continue fuera de ciclo");
                return new Errores("Semantico", "Continue fuera de ciclo", this.fila, this.columna);
            }
            return this;
        }catch(Exception e){
            return new Errores("Semantico", "Error al interpretar Continue", this.fila, this.columna);
        }
    }
    
}
