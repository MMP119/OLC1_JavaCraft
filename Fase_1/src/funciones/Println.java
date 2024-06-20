package funciones;


import entorno.*;
import instruccion.*;
import excepciones.Errores;
import expresiones.Expresion;
import AST.*;

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
        //System.out.println(valor.toString());
        if(valor instanceof Errores) {
            return valor;
        }
        String valorStr = valor.toString();
        valorStr = valorStr.replace("\\n", "\n")
                            .replace("\\\\", "\\")
                            .replace("\\\"", "\"")
                            .replace("\\t", "\t")
                            .replace("\\'", "'");
        datos.add(valorStr);
        ent.Print(valorStr);
        return this;
    }


}
