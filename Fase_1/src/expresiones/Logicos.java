package expresiones;

import AST.*;
import entorno.*;


public class Logicos extends Expresion{

    private Expresion izquierda;
    private Expresion dererecha;
    private String operador;

    public Logicos(Expresion izquierda, Expresion derecha, String operador, int fila, int columna) {
        super("ERROR", TipoDato.ERROR, fila, columna);
        this.izquierda = izquierda;
        this.dererecha = derecha;
        this.operador = operador;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("LOGICO");
        nodo.agregarHijoAST(this.izquierda.getNodo());
        nodo.agregarHijo(this.operador);
        if(!this.operador.equals("!")){ // si no es operador unario, agrego la derecha
            nodo.agregarHijoAST(this.dererecha.getNodo());
        }
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts){

        Expresion izq = (Expresion) this.izquierda.interpretar(ent, ts);

        //verificar si derecha es nula
        if(this.dererecha == null){
            if(this.operador.equals("!")){
                if(izq.getTipo() == TipoDato.BOOLEAN){
                    this.setValor(!Boolean.parseBoolean(izq.getValor().toString()));
                    this.setTipo(TipoDato.BOOLEAN);
                }else{
                    System.out.println("Error Semántico: Error en la operacion logica NOT.");
                }
                return this;
            }else{
                System.out.println("Error Semántico: Error en la operacion logica.");
                return this;
            }
        }

        Expresion der = (Expresion) this.dererecha.interpretar(ent, ts);

        switch (this.operador) {
            case "&&":
                // Comparar si son iguales
                if (izq.getTipo() == TipoDato.BOOLEAN && der.getTipo() == TipoDato.BOOLEAN) {
                    this.setValor(Boolean.parseBoolean(izq.getValor().toString()) && Boolean.parseBoolean(der.getValor().toString()));
                    this.setTipo(TipoDato.BOOLEAN);
                } else {
                    System.out.println("Error Semántico: Error en la operacion logica AND.");
                }
                break;
            

            case "||":
                // Comparar si son diferentes
                if (izq.getTipo() == TipoDato.BOOLEAN && der.getTipo() == TipoDato.BOOLEAN) {
                    this.setValor(Boolean.parseBoolean(izq.getValor().toString()) || Boolean.parseBoolean(der.getValor().toString()));
                    this.setTipo(TipoDato.BOOLEAN);
                } else {
                    System.out.println("Error Semántico: Error en la operacion logica OR.");
                }
                break;
            
            case "^": //operador XOR
                // Comparar si son diferentes
                if (izq.getTipo() == TipoDato.BOOLEAN && der.getTipo() == TipoDato.BOOLEAN) {
                    this.setValor(Boolean.parseBoolean(izq.getValor().toString()) ^ Boolean.parseBoolean(der.getValor().toString()));
                    this.setTipo(TipoDato.BOOLEAN);
                } else {
                    System.out.println("Error Semántico: Error en la operacion logica XOR.");
                }
                break;
            
            default:
                System.out.println("Error Semántico: Operador logico no reconocido.");
                break;
        }

        return this;
    }
    
    public String toString(){
        return this.getValor().toString();
    }

}
