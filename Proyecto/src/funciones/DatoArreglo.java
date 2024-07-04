package funciones;

import java.util.LinkedList;
import AST.NodoAst;
import entorno.Entorno;
import entorno.tablaSimbolos;
import expresiones.Expresion;
import expresiones.TipoDato;

public class DatoArreglo extends Expresion {

    private LinkedList<Object> valores;
    private TipoDato tipo;

    public DatoArreglo(LinkedList<Object> valores, TipoDato tipo) {
        super("Error DatoArreglo",tipo, 0, 0);
        this.valores = valores;
        this.tipo = tipo;
    }

    @Override
    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("Arreglo");
        for (Object valor : valores) {
            nodo.agregarHijo(valor.toString());
        }
        return nodo;
    }

    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        return this.valores;
    }

    @Override
    public LinkedList<Object> getValor() {
        return this.valores;
    }

    public String toString(){
        return this.valores.toString();
    }
}