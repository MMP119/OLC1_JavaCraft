package expresiones;

import AST.*;
import entorno.*;
import java.util.Arrays;

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

                if(this.TiposCompatibles(izq.getTipo(), der.getTipo())){
                    this.setTipo(TipoDato.BOOLEAN);

                    //verificar si el tipo derecha o izquierda es tipo de dato char
                    if(izq.getTipo().equals(TipoDato.CHAR) || der.getTipo().equals(TipoDato.CHAR)){//verificar si el tipo derecha o izquierda es tipo de dato char
                        
                        //verificar si ambos son de tipo char
                        if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.CHAR)){
                            if ((izq.getValor().toString()).equals(der.getValor().toString())) {
                                this.setValor(true);
                                return this;
                            }
                        }

                        //verificar si el tipo izquierda es char y derecha es int
                        if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.INT)){
                            //convertir el valor a ascii
                            int ascii = (int)izq.getValor().toString().charAt(0);
                            if(ascii == (int)Integer.parseInt(der.getValor().toString())){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo derecha es char y izquierda es int
                        else if(der.getTipo().equals(TipoDato.CHAR) && izq.getTipo().equals(TipoDato.INT)){
                            //convertir el valor a ascii
                            int ascii = (int)der.getValor().toString().charAt(0);
                            if(ascii == (int)Integer.parseInt(izq.getValor().toString())){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo izquierda es char y la derecha es double
                        else if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.DOUBLE)){
                            //convertir el valor a ascii
                            int ascii = (int)izq.getValor().toString().charAt(0);
                            if(ascii == (double)Double.parseDouble(der.getValor().toString())){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo derecha es char y la izquierda es double
                        else if(der.getTipo().equals(TipoDato.CHAR) && izq.getTipo().equals(TipoDato.DOUBLE)){
                            //convertir el valor a ascii
                            int ascii = (int)der.getValor().toString().charAt(0);
                            if(ascii == (double)Double.parseDouble(izq.getValor().toString())){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                    }

                    //verificar si izq es int y der es double
                    if(izq.getTipo().equals(TipoDato.INT) && der.getTipo().equals(TipoDato.DOUBLE)){
                        //convertir el valor izq a double
                        double izqDouble = (double)Integer.parseInt(izq.getValor().toString());
                        if(izqDouble == (double)Double.parseDouble(der.getValor().toString())){
                            this.setValor(true);
                            return this;
                        }else{
                            this.setValor(false);
                            return this;
                        }
                    }
                    //verificar si izq es double y der es int
                    else if(izq.getTipo().equals(TipoDato.DOUBLE) && der.getTipo().equals(TipoDato.INT)){
                        //convertir el valor der a double
                        double derDouble = (double)Integer.parseInt(der.getValor().toString());
                        if((double)Double.parseDouble(izq.getValor().toString()) == derDouble){
                            this.setValor(true);
                            return this;
                        }else{
                            this.setValor(false);
                            return this;
                        }
                    }
                    
                    
                    //verificar si los valores son iguales
                    if ((izq.getValor().toString()).equals(der.getValor().toString())) {
                        this.setValor(true);
                        return this;
                    }else{
                        this.setValor(false);
                        return this;
                    }
                }
                break;
            

            case "!=":
                
                if(this.TiposCompatibles(izq.getTipo(), der.getTipo())){
                    this.setTipo(TipoDato.BOOLEAN);

                    //verificar si el tipo derecha o izquierda es tipo de dato char
                    if(izq.getTipo().equals(TipoDato.CHAR) || der.getTipo().equals(TipoDato.CHAR)){//verificar si el tipo derecha o izquierda es tipo de dato char
                        
                        //verificar si ambos son de tipo char
                        if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.CHAR)){
                            if ((izq.getValor().toString()).equals(der.getValor().toString())) {
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo izquierda es char y derecha es int
                        if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.INT)){
                            //convertir el valor a ascii
                            int ascii = (int)izq.getValor().toString().charAt(0);
                            if(ascii == (int)Integer.parseInt(der.getValor().toString())){
                                this.setValor(false);
                                return this;
                            }else{
                                this.setValor(true);
                                return this;
                            }
                        }

                        //verificar si el tipo derecha es char y izquierda es int
                        else if(der.getTipo().equals(TipoDato.CHAR) && izq.getTipo().equals(TipoDato.INT)){
                            //convertir el valor a ascii
                            int ascii = (int)der.getValor().toString().charAt(0);
                            if(ascii == (int)Integer.parseInt(izq.getValor().toString())){
                                this.setValor(false);
                                return this;
                            }else{
                                this.setValor(true);
                                return this;
                            }
                        }

                        //verificar si el tipo izquierda es char y la derecha es double
                        else if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.DOUBLE)){
                            //convertir el valor a ascii
                            int ascii = (int)izq.getValor().toString().charAt(0);
                            if(ascii == (double)Double.parseDouble(der.getValor().toString())){
                                this.setValor(false);
                                return this;
                            }else{
                                this.setValor(true);
                                return this;
                            }
                        }
                    
                        //verificar si el tipo derecha es char y la izquierda es double
                        else if(der.getTipo().equals(TipoDato.CHAR) && izq.getTipo().equals(TipoDato.DOUBLE)){
                            //convertir el valor a ascii
                            int ascii = (int)der.getValor().toString().charAt(0);
                            if(ascii == (double)Double.parseDouble(izq.getValor().toString())){
                                this.setValor(false);
                                return this;
                            }else{
                                this.setValor(true);
                                return this;
                            }
                        }

                    }

                    //verificar si izq es int y der es double
                    if(izq.getTipo().equals(TipoDato.INT) && der.getTipo().equals(TipoDato.DOUBLE)){
                        //convertir el valor izq a double
                        double izqDouble = (double)Integer.parseInt(izq.getValor().toString());
                        if(izqDouble == (double)Double.parseDouble(der.getValor().toString())){
                            this.setValor(false);
                            return this;
                        }else{
                            this.setValor(true);
                            return this;
                        }
                    }

                    //verificar si izq es double y der es int
                    else if(izq.getTipo().equals(TipoDato.DOUBLE) && der.getTipo().equals(TipoDato.INT)){
                        //convertir el valor der a double
                        double derDouble = (double)Integer.parseInt(der.getValor().toString());
                        if((double)Double.parseDouble(izq.getValor().toString()) == derDouble){
                            this.setValor(false);
                            return this;
                        }else{
                            this.setValor(true);
                            return this;
                        }
                    }

                    //verificar si los valores son iguales
                    if ((izq.getValor().toString()).equals(der.getValor().toString())) {
                        this.setValor(false);
                        return this;

                    }else{
                        this.setValor(true);
                        return this;
                    }
                }
                break;


            
            case "<":

                // Comparar si es menor

                if(this.TiposCompatibles(izq.getTipo(), der.getTipo())){
                    this.setTipo(TipoDato.BOOLEAN);

                    //verificar si el tipo derecha o izquierda es tipo de dato char
                    if(izq.getTipo().equals(TipoDato.CHAR) || der.getTipo().equals(TipoDato.CHAR)){//verificar si el tipo derecha o izquierda es tipo de dato char
                        
                        //verificar si ambos son de tipo char
                        if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.CHAR)){
                            if ((int)izq.getValor().toString().charAt(0) < (int)der.getValor().toString().charAt(0)) {
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo izquierda es char y derecha es int
                        if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.INT)){
                            //convertir el valor a ascii
                            int ascii = (int)izq.getValor().toString().charAt(0);
                            if(ascii < (int)Integer.parseInt(der.getValor().toString())){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo derecha es char y izquierda es int
                        else if(der.getTipo().equals(TipoDato.CHAR) && izq.getTipo().equals(TipoDato.INT)){
                            //convertir el valor a ascii
                            int ascii = (int)der.getValor().toString().charAt(0);
                            if((int)Integer.parseInt(izq.getValor().toString()) < ascii){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo izquierda es char y la derecha es double
                        else if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.DOUBLE)){
                            //convertir el valor a ascii
                            int ascii = (int)izq.getValor().toString().charAt(0);
                            if(ascii < (double)Double.parseDouble(der.getValor().toString())){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo derecha es char y la izquierda es double
                        else if(der.getTipo().equals(TipoDato.CHAR) && izq.getTipo().equals(TipoDato.DOUBLE)){
                            //convertir el valor a ascii
                            int ascii = (int)der.getValor().toString().charAt(0);
                            if((double)Double.parseDouble(izq.getValor().toString()) < ascii){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                    }

                    //verificar si izq es int y der es double

                    if(izq.getTipo().equals(TipoDato.INT) && der.getTipo().equals(TipoDato.DOUBLE)){
                        //convertir el valor izq a double
                        double izqDouble = (double)Integer.parseInt(izq.getValor().toString());
                        if(izqDouble < (double)Double.parseDouble(der.getValor().toString())){
                            this.setValor(true);
                            return this;
                        }else{
                            this.setValor(false);
                            return this;
                        }
                    }

                    //verificar si izq es double y der es int
                    else if(izq.getTipo().equals(TipoDato.DOUBLE) && der.getTipo().equals(TipoDato.INT)){
                        //convertir el valor der a double
                        double derDouble = (double)Integer.parseInt(der.getValor().toString());
                        if((double)Double.parseDouble(izq.getValor().toString()) < derDouble){
                            this.setValor(true);
                            return this;
                        }else{
                            this.setValor(false);
                            return this;
                        }
                    }

                    //verificar si los valores son iguales

                    if (izq.getValor().toString().equals(der.getValor().toString())) {
                        this.setValor(false);
                        return this;
                    }else{
                        this.setValor(true);
                        return this;
                    }
                }
                break;


            case "<=":
                
                // Comparar si es menor o igual

                if(this.TiposCompatibles(izq.getTipo(), der.getTipo())){
                    this.setTipo(TipoDato.BOOLEAN);

                    //verificar si el tipo derecha o izquierda es tipo de dato char
                    if(izq.getTipo().equals(TipoDato.CHAR) || der.getTipo().equals(TipoDato.CHAR)){//verificar si el tipo derecha o izquierda es tipo de dato char
                        
                        //verificar si ambos son de tipo char
                        if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.CHAR)){
                            if ((int)izq.getValor().toString().charAt(0) <= (int)der.getValor().toString().charAt(0)) {
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo izquierda es char y derecha es int
                        if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.INT)){
                            //convertir el valor a ascii
                            int ascii = (int)izq.getValor().toString().charAt(0);
                            if(ascii <= (int)Integer.parseInt(der.getValor().toString())){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo derecha es char y izquierda es int
                        else if(der.getTipo().equals(TipoDato.CHAR) && izq.getTipo().equals(TipoDato.INT)){
                            //convertir el valor a ascii
                            int ascii = (int)der.getValor().toString().charAt(0);
                            if((int)Integer.parseInt(izq.getValor().toString()) <= ascii){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo izquierda es char y la derecha es double
                        else if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.DOUBLE)){
                            //convertir el valor a ascii
                            int ascii = (int)izq.getValor().toString().charAt(0);
                            if(ascii <= (double)Double.parseDouble(der.getValor().toString())){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo derecha es char y la izquierda es double
                        else if(der.getTipo().equals(TipoDato.CHAR) && izq.getTipo().equals(TipoDato.DOUBLE)){
                            //convertir el valor a ascii
                            int ascii = (int)der.getValor().toString().charAt(0);
                            if((double)Double.parseDouble(izq.getValor().toString()) <= ascii){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                    }

                    //verificar si izq es int y der es double

                    if(izq.getTipo().equals(TipoDato.INT) && der.getTipo().equals(TipoDato.DOUBLE)){
                        //convertir el valor izq a double
                        double izqDouble = (double)Integer.parseInt(izq.getValor().toString());
                        if(izqDouble <= (double)Double.parseDouble(der.getValor().toString())){
                            this.setValor(true);
                            return this;
                        }else{
                            this.setValor(false);
                            return this;
                        }
                    }

                    //verificar si izq es double y der es int
                    else if(izq.getTipo().equals(TipoDato.DOUBLE) && der.getTipo().equals(TipoDato.INT)){
                        //convertir el valor der a double
                        double derDouble = (double)Integer.parseInt(der.getValor().toString());
                        if((double)Double.parseDouble(izq.getValor().toString()) <= derDouble){
                            this.setValor(true);
                            return this;
                        }else{
                            this.setValor(false);
                            return this;
                        }
                    }

                    //verificar si los valores son iguales

                    if (izq.getValor().toString().equals(der.getValor().toString())) {
                        this.setValor(true);
                        return this;
                    }else{
                        this.setValor(false);
                        return this;
                    }
                }
                break;
            

            case ">":
                // Comparar si es mayor

                if(this.TiposCompatibles(izq.getTipo(), der.getTipo())){
                    this.setTipo(TipoDato.BOOLEAN);

                    //verificar si el tipo derecha o izquierda es tipo de dato char
                    if(izq.getTipo().equals(TipoDato.CHAR) || der.getTipo().equals(TipoDato.CHAR)){//verificar si el tipo derecha o izquierda es tipo de dato char
                        
                        //verificar si ambos son de tipo char
                        if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.CHAR)){
                            if ((int)izq.getValor().toString().charAt(0) > (int)der.getValor().toString().charAt(0)) {
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo izquierda es char y derecha es int
                        if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.INT)){
                            //convertir el valor a ascii
                            int ascii = (int)izq.getValor().toString().charAt(0);
                            if(ascii > (int)Integer.parseInt(der.getValor().toString())){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo derecha es char y izquierda es int
                        else if(der.getTipo().equals(TipoDato.CHAR) && izq.getTipo().equals(TipoDato.INT)){
                            //convertir el valor a ascii
                            int ascii = (int)der.getValor().toString().charAt(0);
                            if((int)Integer.parseInt(izq.getValor().toString()) > ascii){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo izquierda es char y la derecha es double
                        else if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.DOUBLE)){
                            //convertir el valor a ascii
                            int ascii = (int)izq.getValor().toString().charAt(0);
                            if(ascii > (double)Double.parseDouble(der.getValor().toString())){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo derecha es char y la izquierda es double
                        else if(der.getTipo().equals(TipoDato.CHAR) && izq.getTipo().equals(TipoDato.DOUBLE)){
                            //convertir el valor a ascii
                            int ascii = (int)der.getValor().toString().charAt(0);
                            if((double)Double.parseDouble(izq.getValor().toString()) > ascii){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                    }

                    //verificar si izq es int y der es double

                    if(izq.getTipo().equals(TipoDato.INT) && der.getTipo().equals(TipoDato.DOUBLE)){
                        //convertir el valor izq a double
                        double izqDouble = (double)Integer.parseInt(izq.getValor().toString());
                        if(izqDouble > (double)Double.parseDouble(der.getValor().toString())){
                            this.setValor(true);
                            return this;
                        }else{
                            this.setValor(false);
                            return this;
                        }
                    }

                    //verificar si izq es double y der es int
                    else if(izq.getTipo().equals(TipoDato.DOUBLE) && der.getTipo().equals(TipoDato.INT)){
                        //convertir el valor der a double
                        double derDouble = (double)Integer.parseInt(der.getValor().toString());
                        if((double)Double.parseDouble(izq.getValor().toString()) > derDouble){
                            this.setValor(true);
                            return this;
                        }else{
                            this.setValor(false);
                            return this;
                        }
                    }

                    //verificar si los valores son iguales

                    if (izq.getValor().toString().equals(der.getValor().toString())) {
                        this.setValor(false);
                        return this;
                    }else{
                        this.setValor(true);
                        return this;
                    }
                }
                break;


            case ">=":
                // Comparar si es mayor o igual
                
                if(this.TiposCompatibles(izq.getTipo(), der.getTipo())){
                    this.setTipo(TipoDato.BOOLEAN);

                    //verificar si el tipo derecha o izquierda es tipo de dato char
                    if(izq.getTipo().equals(TipoDato.CHAR) || der.getTipo().equals(TipoDato.CHAR)){//verificar si el tipo derecha o izquierda es tipo de dato char
                        
                        //verificar si ambos son de tipo char
                        if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.CHAR)){
                            if ((int)izq.getValor().toString().charAt(0) >= (int)der.getValor().toString().charAt(0)) {
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo izquierda es char y derecha es int
                        if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.INT)){
                            //convertir el valor a ascii
                            int ascii = (int)izq.getValor().toString().charAt(0);
                            if(ascii >= (int)Integer.parseInt(der.getValor().toString())){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo derecha es char y izquierda es int
                        else if(der.getTipo().equals(TipoDato.CHAR) && izq.getTipo().equals(TipoDato.INT)){
                            //convertir el valor a ascii
                            int ascii = (int)der.getValor().toString().charAt(0);
                            if((int)Integer.parseInt(izq.getValor().toString()) >= ascii){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo izquierda es char y la derecha es double
                        else if(izq.getTipo().equals(TipoDato.CHAR) && der.getTipo().equals(TipoDato.DOUBLE)){
                            //convertir el valor a ascii
                            int ascii = (int)izq.getValor().toString().charAt(0);
                            if(ascii >= (double)Double.parseDouble(der.getValor().toString())){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                        //verificar si el tipo derecha es char y la izquierda es double
                        else if(der.getTipo().equals(TipoDato.CHAR) && izq.getTipo().equals(TipoDato.DOUBLE)){
                            //convertir el valor a ascii
                            int ascii = (int)der.getValor().toString().charAt(0);
                            if((double)Double.parseDouble(izq.getValor().toString()) >= ascii){
                                this.setValor(true);
                                return this;
                            }else{
                                this.setValor(false);
                                return this;
                            }
                        }

                    }

                    //verificar si izq es int y der es double

                    if(izq.getTipo().equals(TipoDato.INT) && der.getTipo().equals(TipoDato.DOUBLE)){
                        //convertir el valor izq a double
                        double izqDouble = (double)Integer.parseInt(izq.getValor().toString());
                        if(izqDouble >= (double)Double.parseDouble(der.getValor().toString())){
                            this.setValor(true);
                            return this;
                        }else{
                            this.setValor(false);
                            return this;
                        }
                    }

                    //verificar si izq es double y der es int
                    else if(izq.getTipo().equals(TipoDato.DOUBLE) && der.getTipo().equals(TipoDato.INT)){
                        //convertir el valor der a double
                        double derDouble = (double)Integer.parseInt(der.getValor().toString());
                        if((double)Double.parseDouble(izq.getValor().toString()) >= derDouble){
                            this.setValor(true);
                            return this;
                        }else{
                            this.setValor(false);
                            return this;
                        }
                    }

                    //verificar si los valores son iguales

                    if (izq.getValor().toString().equals(der.getValor().toString())) {
                        this.setValor(true);
                        return this;
                    }else{
                        this.setValor(false);
                        return this;
                    }
                }
                break;
        
            default:
                System.err.println("Error SEMANTICO en operador relacional");
                break;
        }
        System.out.println("ERROR SEMANTICO: Tipos incompatibles en operador relacional");
        return this;
    }


    public boolean TiposCompatibles(TipoDato op1, TipoDato op2){
        
        //comprobar si ambos tipos son idénticos ( int == int, double == double, string == string, boolean == boolean)
        if(op1 == op2){
            return true;
        }

        //comprobar las combinaciones permitidas entre enteros, dobles y chars
        TipoDato[] tiposCompatibles = {TipoDato.INT, TipoDato.DOUBLE, TipoDato.CHAR};

        //verificar si los tipos de datos se encuentran en el arreglo de tipos compatibles
        boolean izqCompatible = Arrays.stream (tiposCompatibles).anyMatch(tipo -> tipo == op1);
        boolean derCompatible = Arrays.stream (tiposCompatibles).anyMatch(tipo -> tipo == op2);

        if(izqCompatible && derCompatible){
            return true;
        }

        return false;
    }
    


    @Override
    public String toString(){
        //boolean valor = (boolean)Boolean.parseBoolean(this.getValor().toString());
        return this.getValor().toString();
    }

}
