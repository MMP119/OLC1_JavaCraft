package expresiones;

import AST.*;
import entorno.*;

public class Relacionales extends Expresion{

    private Expresion izquierda;
    private Expresion dererecha;
    private String operador;

    public Relacionales(Expresion izquierda, Expresion derecha, String operador, int fila, int columna) {
        super("ERROR", TipoDato.ERROR, fila, columna);
        this.izquierda = izquierda;
        this.dererecha = derecha;
        this.operador = operador;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("RELACIONAL");
        nodo.agregarHijoAST(this.izquierda.getNodo());
        nodo.agregarHijo(this.operador);
        nodo.agregarHijoAST(this.dererecha.getNodo());
        return nodo;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts){

        Expresion izq = (Expresion) this.izquierda.interpretar(ent, ts);
        Expresion der = (Expresion) this.dererecha.interpretar(ent, ts);

        switch (this.operador) {
            case "==":
                System.out.println("Comparando: "+izq.getValor().toString()+" == "+der.getValor().toString());
                System.out.println("Tipo izq: "+izq.getTipo().toString()+" Tipo der: "+der.getTipo().toString());
                // Comparar si son iguales
                if (izq.getTipo() == der.getTipo()) {
                    if ((izq.getValor().toString()).equals(der.getValor().toString())) {
                        this.setValor(true);
                    } else {
                        this.setValor(false);
                    }
                } else {
                    this.setValor(false);
                }
                break;
            

            case "!=":
                // Comparar si son diferentes
                if (izq.getTipo() == der.getTipo()) {
                    if (!(izq.getValor().toString()).equals(der.getValor().toString())) {
                        this.setValor(true);
                    } else {
                        this.setValor(false);
                    }
                } else {
                    this.setValor(true);//es verdadero si son de diferente tipo de dato
                }
                break;

            
            case "<":
                // Comparar si es menor
                if (izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT) {
                    if ((int)Integer.parseInt(izq.getValor().toString()) < (int)Integer.parseInt(der.getValor().toString())) {
                        this.setValor(true);
                    } else {
                        this.setValor(false);
                    }
                }
                else if(izq.getTipo() == TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                    if((double)Double.parseDouble(izq.getValor().toString()) < (double)Double.parseDouble(der.getValor().toString())){
                        this.setValor(true);
                    }else{
                        this.setValor(false);
                    }
                }
                else if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                    if((int)Integer.parseInt(izq.getValor().toString()) < (double)Double.parseDouble(der.getValor().toString())){
                        this.setValor(true);
                    }else{
                        this.setValor(false);
                    }
                }
                else if(izq.getTipo() == TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                    if((double)Double.parseDouble(izq.getValor().toString()) < (int)Integer.parseInt(der.getValor().toString())){
                        this.setValor(true);
                    }else{
                        this.setValor(false);
                    }
                }
                else {
                    this.setValor(false);
                }

                break;


            case "<=":
                // Comparar si es menor o igual
                if (izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT) {
                    if ((int)Integer.parseInt(izq.getValor().toString()) <= (int)Integer.parseInt(der.getValor().toString())) {
                        this.setValor(true);
                    } else {
                        this.setValor(false);
                    }
                }
                else if(izq.getTipo() == TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                    if((double)Double.parseDouble(izq.getValor().toString()) <= (double)Double.parseDouble(der.getValor().toString())){
                        this.setValor(true);
                    }else{
                        this.setValor(false);
                    }
                }
                else if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                    if((int)Integer.parseInt(izq.getValor().toString()) <= (double)Double.parseDouble(der.getValor().toString())){
                        this.setValor(true);
                    }else{
                        this.setValor(false);
                    }
                }
                else if(izq.getTipo() == TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                    if((double)Double.parseDouble(izq.getValor().toString()) <= (int)Integer.parseInt(der.getValor().toString())){
                        this.setValor(true);
                    }else{
                        this.setValor(false);
                    }
                }
                else {
                    this.setValor(false);
                }

                break;
            

            case ">":
                // Comparar si es mayor

                if (izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT) {
                    if ((int)Integer.parseInt(izq.getValor().toString()) > (int)Integer.parseInt(der.getValor().toString())) {
                        this.setValor(true);
                    } else {
                        this.setValor(false);
                    }
                }
                else if(izq.getTipo() == TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                    if((double)Double.parseDouble(izq.getValor().toString()) > (double)Double.parseDouble(der.getValor().toString())){
                        this.setValor(true);
                    }else{
                        this.setValor(false);
                    }
                }
                else if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                    if((int)Integer.parseInt(izq.getValor().toString()) > (double)Double.parseDouble(der.getValor().toString())){
                        this.setValor(true);
                    }else{
                        this.setValor(false);
                    }
                }
                else if(izq.getTipo() == TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                    if((double)Double.parseDouble(izq.getValor().toString()) > (int)Integer.parseInt(der.getValor().toString())){
                        this.setValor(true);
                    }else{
                        this.setValor(false);
                    }
                }
                else {
                    this.setValor(false);
                }
                
                break;


            case ">=":
                // Comparar si es mayor o igual
                if (izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT) {
                    if ((int)Integer.parseInt(izq.getValor().toString()) >= (int)Integer.parseInt(der.getValor().toString())){
                        this.setValor(true);
                    } else {
                        this.setValor(false);
                    }
                }
                else if(izq.getTipo() == TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                    if((double)Double.parseDouble(izq.getValor().toString()) >= (double)Double.parseDouble(der.getValor().toString())){
                        this.setValor(true);
                    }else{
                        this.setValor(false);
                    }
                }
                else if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                    if((int)Integer.parseInt(izq.getValor().toString()) >= (double)Double.parseDouble(der.getValor().toString())){
                        this.setValor(true);
                    }else{
                        this.setValor(false);
                    }
                }
                else if(izq.getTipo() == TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                    if((double)Double.parseDouble(izq.getValor().toString()) >= (int)Integer.parseInt(der.getValor().toString())){
                        this.setValor(true);
                    }else{
                        this.setValor(false);
                    }
                }
                else {
                    this.setValor(false);
                }
                break;
        
            default:
                System.err.println("Error SEMANTICO en operador relacional");
                break;
        }

        return this;
    }

    @Override
    public String toString(){
        //boolean valor = (boolean)Boolean.parseBoolean(this.getValor().toString());
        return this.getValor().toString();
    }

}
