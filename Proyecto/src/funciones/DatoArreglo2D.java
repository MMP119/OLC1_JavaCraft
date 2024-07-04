package funciones;

import java.util.LinkedList;
import AST.NodoAst;
import entorno.Entorno;
import entorno.tablaSimbolos;
import expresiones.Expresion;
import expresiones.TipoDato;

public class DatoArreglo2D extends Expresion {

    private LinkedList<LinkedList<Object>> valores;
    private TipoDato tipo;

    public DatoArreglo2D(LinkedList<LinkedList<Object>> valores, TipoDato tipo) {
        super("Error DatoArreglo 2D",tipo, 0, 0);
        this.valores = valores;
        this.tipo = tipo;
    }

    @Override
    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("Arreglo 2D");
        for (LinkedList<Object> fila : valores) {
            NodoAst filaNodo = new NodoAst("Fila");
            for (Object valor : fila) {
                filaNodo.agregarHijo(valor.toString());
            }
            nodo.agregarHijoAST(filaNodo);
        }
        return nodo;
    }

    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        return this.valores;
    }

    @Override
    public LinkedList<LinkedList<Object>> getValor() {
        return this.valores;
    }
    
}
