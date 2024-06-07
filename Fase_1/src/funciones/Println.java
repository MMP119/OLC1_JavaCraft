package funciones;


import entorno.*;
import instruccion.*;
import expresiones.TipoDato;
import excepciones.Errores;
import expresiones.Expresion;

public class Println extends Instruccion{

    private Expresion expresion;

    public Println(Expresion expresion, int fila, int columna){
        super(new Tipo(TipoDato.VOID), fila, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        var valor = expresion.interpretar(ent, ts);
        if(valor instanceof Errores) {
            return valor;
        }
        ent.Print(valor.toString());
        return null;
    }


}
