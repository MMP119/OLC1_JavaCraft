package expresiones;

import entorno.*;
import excepciones.Errores;
import AST.*;

public class Aritmeticas extends Expresion{
    
    private Expresion izq;
    private Expresion der;
    private int fila;
    private int columna;
    private String operador;

    public Aritmeticas(Expresion izq, String operador, Expresion der, int fila, int columna){
        super("ERROR ARITMETICAS", TipoDato.ERROR, fila, columna);
        this.izq = izq;
        this.der = der;
        this.fila = fila;
        this.columna = columna;
        this.operador = operador;
    }

    //obtener los nodos para el ast
    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("ARITMETICA");
        nodo.agregarHijoAST(this.izq.getNodo());
        nodo.agregarHijo(this.operador);
        if(this.der != null){
            nodo.agregarHijoAST(this.der.getNodo());
        }
        return nodo;
    }


    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts){

        try{
            Expresion izq = (Expresion) this.izq.interpretar(ent, ts);

            //negar un valor
            if(this.der == null){
                if(this.operador.equals("-")){
                    if(izq.getTipo() == TipoDato.INT){
                        this.setTipo(TipoDato.INT);
                        int valorIzq = izq.getValor() != null ? (int) Integer.valueOf(izq.getValor().toString()) : 0;
                        int resultado = -valorIzq;

                        return new Dato(resultado, TipoDato.INT, this.fila, this.columna);
                    }
                    if(izq.getTipo() == TipoDato.DOUBLE){
                        this.setTipo(TipoDato.DOUBLE);
                        double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                        double resultado = -valorIzq;

                        return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                    }
                }
            }


            Expresion der = (Expresion) this.der.interpretar(ent, ts);

            //Sumar
            if(this.operador.equals("+")){
                //System.out.println(izq.getValor() + " + " + der.getValor());

                //INT 
                if(izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT){
                    this.setTipo(TipoDato.INT);
                    int valorIzq = izq.getValor() != null ? (int) Integer.valueOf(izq.getValor().toString()) : 0;
                    int valorDer = der.getValor() != null ? (int) Integer.valueOf(der.getValor().toString()) : 0;
                    int resultado = valorIzq + valorDer;
                    return new Dato(resultado, TipoDato.INT, this.fila, this.columna);
                }
                if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    int valorIzq = izq.getValor() != null ? (int) Integer.valueOf(izq.getValor().toString()) : 0;
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    double resultado = valorIzq + valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.INT && der.getTipo()==TipoDato.CHAR){
                    this.setTipo(TipoDato.INT);
                    int valorIzq = izq.getValor() != null ? (int)Integer.valueOf(izq.getValor().toString()) : 0;
                    String valorDer = der.getValor() != null ? (String)String.valueOf(der.getValor()) : "0";
                    char valorDerChar = valorDer.charAt(0);
                    //pasar el char a ascii y sumar
                    int valorDerAscii = (int)valorDerChar;
                    int resultado = valorIzq + valorDerAscii;
                    return new Dato(resultado, TipoDato.INT, this.fila, this.columna);               
                }
                if(izq.getTipo()==TipoDato.INT && der.getTipo()==TipoDato.CADENA){
                    this.setTipo(TipoDato.CADENA);
                    int valorIzq = izq.getValor() != null ? Integer.valueOf(izq.getValor().toString()) : 0;
                    String valorDer = der.getValor() != null ? (String) String.valueOf(der.getValor()) : "";
                    //pasar eel int a string y concatenar
                    String valorIzqString = String.valueOf(valorIzq);
                    String resultado = valorIzqString + valorDer;
                    return new Dato(resultado, TipoDato.CADENA, this.fila, this.columna);     
                }

                //DOUBLE
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    int valorDer = der.getValor() != null ? Integer.valueOf(der.getValor().toString()) : 0;
                    double resultado = valorIzq + valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    double resultado = valorIzq + valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.CHAR){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    String valorDer = der.getValor() != null ? (String) String.valueOf(der.getValor()) : "0";
                    //pasar el char a ascii y sumar
                    char valorDerChar = valorDer.charAt(0);
                    //verificar si el char es un numero
                    double valorDerAscii = (double) valorDerChar;
                    double resultado = valorIzq + valorDerAscii;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);               
                }
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.CADENA){
                    this.setTipo(TipoDato.CADENA);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    String valorDer = der.getValor() != null ? (String) String.valueOf(der.getValor()) : "";
                    //pasar eel int a string y concatenar
                    String valorIzqString = String.valueOf(valorIzq);
                    String resultado = valorIzqString + valorDer;
                    return new Dato(resultado, TipoDato.CADENA, this.fila, this.columna);   
                }

                //boolean
                if(izq.getTipo()==TipoDato.BOOLEAN && der.getTipo()==TipoDato.CADENA){
                    this.setTipo(TipoDato.CADENA);
                    boolean valorIzq = izq.getValor() != null ? (boolean) Boolean.parseBoolean(izq.getValor().toString()) : true;
                    String valorDer = der.getValor() != null ? (String) String.valueOf(der.getValor()) : "";
                    //pasar el boolean a string y concatenar
                    String valorIzqString = String.valueOf(valorIzq);
                    String resultado = valorIzqString + valorDer;
                    return new Dato(resultado, TipoDato.CADENA, this.fila, this.columna);
                }

                //CHAR
                if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.INT){
                    this.setTipo(TipoDato.INT);
                    String valorIzq = izq.getValor() != null ? (String) izq.getValor().toString() : "0";
                    int valorDer = der.getValor() != null ? (int) Integer.parseInt(der.getValor().toString()) : 0;
                    char valorIzqChar = valorIzq.charAt(0);
                    //verificar si el char es un numero
                    int valorIzqAscii = (int) valorIzqChar;
                    int resultado = valorIzqAscii + valorDer;
                    return new Dato(resultado, TipoDato.INT, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    String valorIzq = izq.getValor() != null ? (String) izq.getValor().toString() : "0";
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    char valorIzqChar = valorIzq.charAt(0);
                    //verificar si el char es un numero
                    int valorIzqAscii = (int) valorIzqChar;
                    double resultado = valorIzqAscii + valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.CHAR){
                    this.setTipo(TipoDato.CADENA);
                    String valorIzq = izq.getValor() != null ? (String) izq.getValor().toString() : "0";
                    String valorDer = der.getValor() != null ? (String) der.getValor().toString() : "0";
                    char valorIzqChar = valorIzq.charAt(0);
                    char valorDerChar = valorDer.charAt(0);
                    //verificar si los char son numeros
                    String valorIzqString = String.valueOf(valorIzqChar);
                    String valorDerString = String.valueOf(valorDerChar);
                    String resultado = valorIzqString + valorDerString;
                    return new Dato(resultado, TipoDato.CADENA, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.CADENA){
                    this.setTipo(TipoDato.CADENA);
                    String valorIzq = izq.getValor() != null ? (String) izq.getValor().toString() : "0";
                    String valorDer = der.getValor() != null ? (String) String.valueOf(der.getValor()) : "";
                    char valorIzqChar = valorIzq.charAt(0);
                    String valorIzqString = String.valueOf(valorIzqChar);
                    String resultado = valorIzqString + valorDer;
                    return new Dato(resultado, TipoDato.CADENA, this.fila, this.columna);
                }

                //CADENA
                if(izq.getTipo()==TipoDato.CADENA && der.getTipo()==TipoDato.INT){
                    this.setTipo(TipoDato.CADENA);
                    String valorIzq = izq.getValor() != null ? (String) String.valueOf(izq.getValor()) : "";
                    int valorDer = der.getValor() != null ? (int) Integer.parseInt(der.getValor().toString()) : 0;
                    String valorDerString = String.valueOf(valorDer);
                    String resultado = valorIzq + valorDerString;
                    return new Dato(resultado, TipoDato.CADENA, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.CADENA && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.CADENA);
                    String valorIzq = izq.getValor() != null ? (String) String.valueOf(izq.getValor()) : "";
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    String valorDerString = String.valueOf(valorDer);
                    String resultado = valorIzq + valorDerString;
                    return new Dato(resultado, TipoDato.CADENA, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.CADENA && der.getTipo()==TipoDato.CHAR){
                    this.setTipo(TipoDato.CADENA);
                    String valorIzq = izq.getValor() != null ? (String) String.valueOf(izq.getValor()) : "";
                    String valorDer = der.getValor() != null ? (String) String.valueOf(der.getValor().toString()) : "0";
                    char valorDerChar = valorDer.charAt(0);
                    String valorDerString = String.valueOf(valorDerChar);
                    String resultado = valorIzq + valorDerString;
                    return new Dato(resultado, TipoDato.CADENA, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.CADENA && der.getTipo()==TipoDato.CADENA){
                    this.setTipo(TipoDato.CADENA);
                    String valorIzq = izq.getValor() != null ? (String) String.valueOf(izq.getValor()) : "";
                    String valorDer = der.getValor() != null ? (String) String.valueOf(der.getValor()) : "";
                    String resultado = valorIzq + valorDer;
                    return new Dato(resultado, TipoDato.CADENA, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.CADENA && der.getTipo()==TipoDato.BOOLEAN){
                    this.setTipo(TipoDato.CADENA);
                    String valorIzq = izq.getValor() != null ? (String) String.valueOf(izq.getValor()) : "";
                    boolean valorDer = der.getValor() != null ? (boolean) Boolean.parseBoolean(der.getValor().toString()) : true;
                    String valorDerString = String.valueOf(valorDer);
                    String resultado = valorIzq + valorDerString;
                    return new Dato(resultado, TipoDato.CADENA, this.fila, this.columna);
                }
            }


            //Restar
            else if(this.operador.equals("-")){

                //INT 
                if(izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT){
                    this.setTipo(TipoDato.INT);
                    //System.out.println("Resta de: "+izq.getValor() + " - " + der.getValor());
                    int valorIzq = izq.getValor() != null ? (int) Integer.parseInt(izq.getValor().toString()) : 0;
                    int valorDer = der.getValor() != null ? (int) Integer.parseInt(der.getValor().toString()) : 0;
                    int resultado = valorIzq - valorDer;
                    return new Dato(resultado, TipoDato.INT, this.fila, this.columna);
                }
                if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    int valorIzq = izq.getValor() != null ? (int) Integer.parseInt(izq.getValor().toString()) : 0;
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    double resultado = valorIzq - valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.INT && der.getTipo()==TipoDato.CHAR){
                    this.setTipo(TipoDato.INT);
                    int valorIzq = izq.getValor() != null ? (int) Integer.parseInt(izq.getValor().toString()) : 0;
                    String valorDer = der.getValor() != null ? (String) der.getValor().toString() : "0";
                    char valorDerChar = valorDer.charAt(0);
                    //pasar el char a ascii y restar
                    int valorDerAscii = (int) valorDerChar;
                    int resultado = valorIzq - valorDerAscii;
                    return new Dato(resultado, TipoDato.INT, this.fila, this.columna);                
                }

                //DOUBLE
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    int valorDer = der.getValor() != null ? (int) Integer.parseInt(der.getValor().toString()) : 0;
                    double resultado = valorIzq - valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    double resultado = valorIzq - valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.CHAR){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    String valorDer = der.getValor() != null ? (String) der.getValor().toString() : "0";
                    char valorDerChar = valorDer.charAt(0);
                    //pasar el char a ascii y restar
                    int valorDerAscii = (int) valorDerChar;
                    double resultado = valorIzq - valorDerAscii;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);               
                }

                //CHAR
                if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.INT){
                    this.setTipo(TipoDato.INT);
                    String valorIzq = izq.getValor() != null ? (String) izq.getValor().toString() : "0";
                    int valorDer = der.getValor() != null ? (int) Integer.parseInt(der.getValor().toString()) : 0;
                    char valorIzqChar = valorIzq.charAt(0);
                    int valorIzqAscii = (int) valorIzqChar;
                    int resultado = valorIzqAscii - valorDer;
                    return new Dato(resultado, TipoDato.INT, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    String valorIzq = izq.getValor() != null ? (String) izq.getValor().toString() : "0";
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    char valorIzqChar = valorIzq.charAt(0);
                    int valorIzqAscii = (int) valorIzqChar;
                    double resultado = valorIzqAscii - valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }

            }

            
            //Multiplicar
            else if(this.operador.equals("*")){

                //INT 
                if(izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT){
                    this.setTipo(TipoDato.INT);
                    int valorIzq = izq.getValor() != null ? (int) Integer.parseInt(izq.getValor().toString()) : 0;
                    int valorDer = der.getValor() != null ? (int) Integer.parseInt(der.getValor().toString()) : 0;
                    int resultado = valorIzq * valorDer;
                    return new Dato(resultado, TipoDato.INT, this.fila, this.columna);
                }
                if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    int valorIzq = izq.getValor() != null ? (int) Integer.parseInt(izq.getValor().toString()) : 0;
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    double resultado = valorIzq * valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.INT && der.getTipo()==TipoDato.CHAR){
                    this.setTipo(TipoDato.INT);
                    int valorIzq = izq.getValor() != null ? (int) Integer.parseInt(izq.getValor().toString()) : 0;
                    String valorDer = der.getValor() != null ? (String) der.getValor().toString() : "0";
                    //pasar el char a ascii y multiplicar
                    char valorDerChar = valorDer.charAt(0);
                    int valorDerAscii = (int) valorDerChar;
                    int resultado = valorIzq * valorDerAscii;
                    return new Dato(resultado, TipoDato.INT, this.fila, this.columna);               
                }

                //DOUBLE
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    int valorDer = der.getValor() != null ? (int) Integer.parseInt(der.getValor().toString()) : 0;
                    double resultado = valorIzq * valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    double resultado = valorIzq * valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.CHAR){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    String valorDer = der.getValor() != null ? (String) der.getValor().toString() : "0";
                    //pasar el char a ascii y multiplicar
                    char valorDerChar = valorDer.charAt(0);
                    int valorDerAscii = (int) valorDerChar;
                    double resultado = valorIzq * valorDerAscii;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);                
                }

                //CHAR
                if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.INT){
                    this.setTipo(TipoDato.INT);
                    String valorIzq = izq.getValor() != null ? (String) izq.getValor().toString() : "0";
                    int valorDer = der.getValor() != null ? (int) Integer.parseInt(der.getValor().toString()) : 0;
                    char valorIzqChar = valorIzq.charAt(0);
                    int valorIzqAscii = (int) valorIzqChar;
                    int resultado = valorIzqAscii * valorDer;
                    return new Dato(resultado, TipoDato.INT, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    String valorIzq = izq.getValor() != null ? (String) izq.getValor().toString() : "0";
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    char valorIzqChar = valorIzq.charAt(0);
                    int valorIzqAscii = (int) valorIzqChar;
                    double resultado = valorIzqAscii * valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }        
            }

            //Dividir
            else if(this.operador.equals("/")){

                //INT 
                if(izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()): 0;
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0;
                    //verificar si el denominador es 0
                    if(valorDer == 0){
                        System.out.println("Error Semantico: Division entre 0");
                        Errores.errores.add(new Errores("Semantico", "Division entre 0", fila, columna));
                        return new Errores("Semantico", "Division entre 0", fila, columna);
                    }
                    double resultado = valorIzq / valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    int valorIzq = izq.getValor() != null ? (int) Integer.parseInt(izq.getValor().toString()) : 0;
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    //verificar si el denominador es 0
                    if(valorDer == 0){
                        System.out.println("Error Semantico: Division entre 0");
                        Errores.errores.add(new Errores("Semantico", "Division entre 0", fila, columna));
                        return new Errores("Semantico", "Division entre 0", fila, columna);
                    }
                    double resultado = valorIzq / valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.INT && der.getTipo()==TipoDato.CHAR){
                    this.setTipo(TipoDato.DOUBLE);
                    int valorIzq = izq.getValor() != null ? (int) Integer.parseInt(izq.getValor().toString()) : 0;
                    String valorDer = der.getValor() != null ? (String) der.getValor().toString() : "0";
                    char valorDerChar = valorDer.charAt(0);
                    //pasar el char a ascii y dividir
                    int valorDerAscii = (int) valorDerChar;
                    //verificar si el denominador es 0
                    if(valorDerAscii == 0){
                        System.out.println("Error Semantico: Division entre 0");
                        Errores.errores.add(new Errores("Semantico", "Division entre 0", fila, columna));
                        return new Errores("Semantico", "Division entre 0", fila, columna);
                    }
                    double resultado = valorIzq / valorDerAscii;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);                
                }

                //DOUBLE
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    int valorDer = der.getValor() != null ? (int) Integer.parseInt(der.getValor().toString()) : 0;
                    //verificar si el denominador es 0
                    if(valorDer == 0){
                        System.out.println("Error Semantico: Division entre 0");
                        Errores.errores.add(new Errores("Semantico", "Division entre 0", fila, columna));
                        return new Errores("Semantico", "Division entre 0", fila, columna);
                    }
                    double resultado = valorIzq / valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    //verificar si el denominador es 0
                    if(valorDer == 0){
                        System.out.println("Error Semantico: Division entre 0");
                        Errores.errores.add(new Errores("Semantico", "Division entre 0", fila, columna));
                        return new Errores("Semantico", "Division entre 0", fila, columna);
                    }
                    double resultado = valorIzq / valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.CHAR){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    String valorDer = der.getValor() != null ? (String) der.getValor().toString() : "0";
                    char valorDerChar = valorDer.charAt(0);
                    //pasar el char a ascii y dividir
                    int valorDerAscii = (int) valorDerChar;
                    //verificar si el denominador es 0
                    if(valorDerAscii == 0){
                        System.out.println("Error Semantico: Division entre 0");
                        Errores.errores.add(new Errores("Semantico", "Division entre 0", fila, columna));
                        return new Errores("Semantico", "Division entre 0", fila, columna);
                    }
                    double resultado = valorIzq / valorDerAscii;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);               
                }

                //CHAR
                if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.INT){
                    this.setTipo(TipoDato.DOUBLE);
                    String valorIzq = izq.getValor() != null ? (String) izq.getValor().toString() : "0";
                    int valorDer = der.getValor() != null ? (int) Integer.parseInt(der.getValor().toString()) : 0;
                    char valorIzqChar = valorIzq.charAt(0);
                    int valorIzqAscii = (int) valorIzqChar;
                    //verificar si el denominador es 0
                    if(valorDer == 0){
                        System.out.println("Error Semantico: Division entre 0");
                        Errores.errores.add(new Errores("Semantico", "Division entre 0", fila, columna));
                        return new Errores("Semantico", "Division entre 0", fila, columna);
                    }
                    double resultado = valorIzqAscii / valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    String valorIzq = izq.getValor() != null ? (String) izq.getValor().toString() : "0";
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    char valorIzqChar = valorIzq.charAt(0);
                    int valorIzqAscii = (int) valorIzqChar;
                    //verificar si el denominador es 0
                    if(valorDer == 0){
                        System.out.println("Error Semantico: Division entre 0");
                        Errores.errores.add(new Errores("Semantico", "Division entre 0", fila, columna));
                        return new Errores("Semantico", "Division entre 0", fila, columna);
                    }
                    double resultado = valorIzqAscii / valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
            }


            //Potencia
            else if(this.operador.equals("**")){

                //INT 
                if(izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT){
                    this.setTipo(TipoDato.INT);
                    int valorIzq = izq.getValor() != null ? (int) Integer.parseInt(izq.getValor().toString()) : 0;
                    int valorDer = der.getValor() != null ? (int) Integer.parseInt(der.getValor().toString()) : 0;
                    int resultado = (int) Math.pow(valorIzq, valorDer);
                    return new Dato(resultado, TipoDato.INT, this.fila, this.columna);
                }
                if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    int valorIzq = izq.getValor() != null ? (int) Integer.parseInt(izq.getValor().toString()) : 0;
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    double resultado = Math.pow(valorIzq, valorDer);
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }

                //DOUBLE
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    int valorDer = der.getValor() != null ? (int) Integer.parseInt(der.getValor().toString()) : 0;
                    double resultado = Math.pow(valorIzq, valorDer);
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    double resultado = Math.pow(valorIzq, valorDer);
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
            }

            //Modulo
            else if(this.operador.equals("%")){

                //INT 
                if(izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT){
                    this.setTipo(TipoDato.INT);
                    int valorIzq = izq.getValor() != null ? (int) Integer.parseInt(izq.getValor().toString()) : 0;
                    int valorDer = der.getValor() != null ? (int) Integer.parseInt(der.getValor().toString()) : 0;
                    int resultado = valorIzq % valorDer;
                    return new Dato(resultado, TipoDato.INT, this.fila, this.columna);
                }
                if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    int valorIzq = izq.getValor() != null ? (int) Integer.parseInt(izq.getValor().toString()) : 0;
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    double resultado = valorIzq % valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }

                //DOUBLE
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    int valorDer = der.getValor() != null ? (int) Integer.parseInt(der.getValor().toString()) : 0;
                    double resultado = valorIzq % valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
                if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                    this.setTipo(TipoDato.DOUBLE);
                    double valorIzq = izq.getValor() != null ? (double) Double.parseDouble(izq.getValor().toString()) : 0.0;
                    double valorDer = der.getValor() != null ? (double) Double.parseDouble(der.getValor().toString()) : 0.0;
                    double resultado = valorIzq % valorDer;
                    return new Dato(resultado, TipoDato.DOUBLE, this.fila, this.columna);
                }
            }

            else{
                System.out.println("Error SEMANTICO: Operacion aritmetica no válida");
                Errores.errores.add(new Errores("Semantico", "Operacion aritmetica no válida", fila, columna));
                return new Errores("Semantico", "Operacion aritmetica no válida", fila, columna);
            }
            System.out.println("Error SEMANTICO: Operacion aritmetica no válida");
            Errores.errores.add(new Errores("Semantico", "Operacion aritmetica no válida", fila, columna));
            return new Errores("Semantico", "Error en la operacion Aritmetica", fila, columna);

        }catch(Exception e){
            Errores.errores.add(new Errores("Semantico", "Error en la operacion Aritmetica", fila, columna));
            return new Errores("Semantico", "Error en la operacion Aritmetica", fila, columna);
        }
        
    }

    // Getters y Setters
    public Object getIzq() {
        return izq;
    }

    public void setIzq(Expresion izq) {
        this.izq = izq;
    }

    public Object getDer() {
        return der;
    }

    public void setDer(Expresion der) {
        this.der = der;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "" + getValor();
    }

}
