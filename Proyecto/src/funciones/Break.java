package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class Break extends Instruccion {

    private int fila, columna;

    public Break(int linea, int col) {
        super(new Tipo(TipoInstruccion.BREAK), linea, col);
        this.fila = linea;
        this.columna = col;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("BREAK");
        nodo.agregarHijo("break");
        return nodo;
    }

    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        
        try {
            if(Instruccion.cicloProfundida == 0){
                //System.out.println("ERROR SEMANTICO: Break fuera de ciclo");
                Errores.errores.add(new Errores("Semantico", "Break fuera de ciclo", this.fila, this.columna));
                return new Errores("Semantico", "Break fuera de ciclo", this.fila, this.columna);
            }
            return this;
        } catch (Exception e) {
            Errores.errores.add(new Errores("Semantico", "Error al interpretar Break", this.fila, this.columna));
            System.out.println("ERROR: Break");
            return null;
        }
        
    }

}