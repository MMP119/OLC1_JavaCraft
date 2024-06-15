package expresiones;

import AST.*;
import entorno.*;
import excepciones.Errores;


public class Logicos extends Expresion{

    private Expresion izquierda;
    private Expresion dererecha;
    private String operador;
    private int fila, columna;

    public Logicos(Expresion izquierda, Expresion derecha, String operador, int fila, int columna) {
        super("ERROR", TipoDato.ERROR, fila, columna);
        this.izquierda = izquierda;
        this.dererecha = derecha;
        this.operador = operador;
        this.fila = fila;
        this.columna = columna;
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
        try{
            Expresion izq = (Expresion) this.izquierda.interpretar(ent, ts);

            //verificar si derecha es nula
            if(this.dererecha == null){
                if(this.operador.equals("!")){
                    if(izq.getTipo() == TipoDato.BOOLEAN){
                        this.setValor(!Boolean.parseBoolean(izq.getValor().toString()));
                        this.setTipo(TipoDato.BOOLEAN);
                    }else{
                        System.out.println("Error Semántico: Error en la operacion logica NOT.");
                        Errores.errores.add(new Errores("Semantico", "Error en la operacion logica NOT.", fila, columna));
                    }
                    return this;
                }else{
                    System.out.println("Error Semántico: Error en la operacion logica.");
                    Errores.errores.add(new Errores("Semantico", "Error en la operacion logica NOT.", fila, columna));
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
                        Errores.errores.add(new Errores("Semantico", "Error en la operacion logica AND.", fila, columna));
                    }
                    break;
                

                case "||":
                    // Comparar si son diferentes
                    if (izq.getTipo() == TipoDato.BOOLEAN && der.getTipo() == TipoDato.BOOLEAN) {
                        this.setValor(Boolean.parseBoolean(izq.getValor().toString()) || Boolean.parseBoolean(der.getValor().toString()));
                        this.setTipo(TipoDato.BOOLEAN);
                    } else {
                        System.out.println("Error Semántico: Error en la operacion logica OR.");
                        Errores.errores.add(new Errores("Semantico", "Error en la operacion logica OR.", fila, columna));
                    }
                    break;
                
                case "^": //operador XOR
                    // Comparar si son diferentes
                    if (izq.getTipo() == TipoDato.BOOLEAN && der.getTipo() == TipoDato.BOOLEAN) {
                        this.setValor(Boolean.parseBoolean(izq.getValor().toString()) ^ Boolean.parseBoolean(der.getValor().toString()));
                        this.setTipo(TipoDato.BOOLEAN);
                    } else {
                        System.out.println("Error Semántico: Error en la operacion logica XOR.");
                        Errores.errores.add(new Errores("Semantico", "Error en la operacion logica XOR.", fila, columna));
                    }
                    break;
                
                default:
                    System.out.println("Error Semántico: Operador logico no reconocido.");
                    Errores.errores.add(new Errores("Semantico", "Operador logico no reconocido.", fila, columna));
                    break;
            }

            return this;
        }catch(Exception e){
            Errores.errores.add(new Errores("Semantico", "Error en la operacion logica"+e.getMessage(), fila, columna));
            return new Errores("Semantico", "Error en la operacion logica"+e.getMessage(), fila, columna);
            
        }
        
    }
    
    public String toString(){
        return this.getValor().toString();
    }

}
