package expresiones;

public class Dato extends Expresion{
    
    public Dato(Object valor, TipoDato tipo, int fila, int columna){
        super(valor, tipo, fila, columna);
    }

    @Override
    public Expresion interpretar(Object entorno) {
        return this;
    }

}
