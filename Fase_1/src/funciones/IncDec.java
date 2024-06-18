package funciones;

import java.util.Arrays;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Simbolo;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.Expresion;
import expresiones.TipoDato;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class IncDec extends Instruccion {

    private String id;
    private String operador;
    private int fila;
    private int columna;

    public IncDec(String id,String operador, int fila, int columna) {
        super(new Tipo(TipoInstruccion.IncDec), fila, columna);
        this.id = id;
        this.operador = operador;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("INC_DEC");
        nodo.agregarHijo(id);
        nodo.agregarHijo(operador);
        nodo.agregarHijo(";");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        try{
            Simbolo variable = ts.getVariable(this.id);

            if (variable == null){
                return new Errores("SEMANTICO", "La variable " + this.id + " no existe", this.fila, this.columna);
            }
            Expresion exp = (Expresion)variable.getValor();
            
            if (TiposCompatibles(exp.getTipo()) == false){
                return new Errores("SEMANTICO", "La variable " + this.id + " no es de tipo entero", this.fila, this.columna);
            }
            
            if(this.operador.equals("++") && exp.getTipo() == TipoDato.INT){
                int valorActual = (int) Integer.parseInt(exp.getValor().toString());
                exp.setValor(valorActual + 1);
            }else if(this.operador.equals("--") && exp.getTipo() == TipoDato.INT){
                int valorActual = (int) Integer.parseInt(exp.getValor().toString());
                exp.setValor(valorActual - 1);
            }else if(this.operador.equals("++") && exp.getTipo() == TipoDato.DOUBLE){
                double valorActual = (double) Double.parseDouble(exp.getValor().toString());
                exp.setValor(valorActual + 1);
            }else if(this.operador.equals("--") && exp.getTipo() == TipoDato.DOUBLE){
                double valorActual = (double) Double.parseDouble(exp.getValor().toString());
                exp.setValor(valorActual - 1);
            }
            
            return null;

        }catch(Exception e){
            e.printStackTrace();
            // Imprimir información específica sobre la línea exacta del error
            StackTraceElement[] stackTrace = e.getStackTrace();
            if (stackTrace.length > 0) {
                StackTraceElement element = stackTrace[0];
                System.out.println("Error en la clase: " + element.getClassName());
                System.out.println("Error en el método: " + element.getMethodName());
                System.out.println("Error en la línea: " + element.getLineNumber());
                } 
            Errores.errores.add(new Errores("Semantico", "NO SE PUEDE INCREMENTRA UNA VARIABLE", this.fila, this.columna));
            return new Errores("Semantico", "NO SE PUEDE INCREMENTAR UNA VARIABLE", this.fila, this.columna);
        }
    }

    public boolean TiposCompatibles(TipoDato op1){
        

        //comprobar las combinaciones permitidas entre enteros, dobles
        TipoDato[] tiposCompatibles = {TipoDato.INT, TipoDato.DOUBLE};

        //verificar si los tipos de datos se encuentran en el arreglo de tipos compatibles
        boolean izqCompatible = Arrays.stream (tiposCompatibles).anyMatch(tipo -> tipo == op1);

        if(izqCompatible == true){
            return true;
        }

        return false;
    }
    
}
