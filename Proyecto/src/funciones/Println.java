package funciones;


import entorno.*;
import instruccion.*;
import excepciones.Errores;
import expresiones.Expresion;
import AST.*;
import java.util.*;

public class Println extends Instruccion{

    private Expresion expresion;

    public Println(Expresion expresion, int fila, int columna){
        super(new Tipo(TipoInstruccion.PRINT), fila, columna);
        this.expresion = expresion;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("PRINTLN");
        nodo.agregarHijo("Println");
        nodo.agregarHijo("(");
        nodo.agregarHijoAST(expresion.getNodo());
        nodo.agregarHijo(")");
        nodo.agregarHijo(";");
        return nodo;
    }


    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        var valor = expresion.interpretar(ent, ts);
        if(valor instanceof Errores) {
            return valor;
        }

        String valorStr;

        if(valor instanceof DatoArreglo){
            valorStr = printArreglo((DatoArreglo)valor);
        }
        else if(valor instanceof DatoArreglo2D){
            valorStr = printArreglo2D((DatoArreglo2D)valor);
        }
        else{
            valorStr = valor.toString();
            valorStr = valorStr.replace("\\n", "\n")
                            .replace("\\\\", "\\")
                            .replace("\\\"", "\"")
                            .replace("\\t", "\t")
                            .replace("\\'", "'");
        }

        //datos.add(valorStr);
        ent.Print(valorStr);
        return this;
    }

    private String  printArreglo(DatoArreglo arreglo){
        StringBuilder sb = new StringBuilder("[");
        LinkedList<Object> valores = arreglo.getValor();
        for(int i = 0; i < valores.size(); i++){
            sb.append(valores.get(i).toString());
            if(i < valores.size() - 1){
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private String printArreglo2D(DatoArreglo2D arreglo2D){
        StringBuilder sb = new StringBuilder("[[");
        LinkedList<LinkedList<Object>> valores = arreglo2D.getValor();
        for (int i = 0; i < valores.size(); i++) {
            LinkedList<Object> fila = valores.get(i);
            for (int j = 0; j < fila.size(); j++) {
                sb.append(fila.get(j).toString());
                if (j < fila.size() - 1) {
                    sb.append(", ");
                }
            }
            if (i < valores.size() - 1) {
                sb.append("], [");
            }
        }
        sb.append("]]");
        return sb.toString();
    }


}
