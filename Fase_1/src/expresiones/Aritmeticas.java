package expresiones;


public class Aritmeticas extends Expresion{
    
    private Object izq;
    private Object der;
    private int fila;
    private int columna;
    private String operador;

    public Aritmeticas(Object izq, String operador, Object der, int fila, int columna){
        super("ERROR", TipoDato.ERROR, fila, columna);
        this.izq = izq;
        this.der = der;
        this.fila = fila;
        this.columna = columna;
        this.operador = operador;
    }

    @Override
    public Expresion interpretar(Object entorno){

        Expresion izq = (Expresion) this.izq;
        Expresion der = (Expresion) this.der;

        izq.interpretar(entorno);
        der.interpretar(entorno);

        //Sumar
        if(this.operador.equals("+")){

            //INT 
            if(izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT){
                this.setTipo(TipoDato.INT);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                int resultado = valorIzq + valorDer;
                this.setValor(resultado);
                System.out.println("Resultado: " + resultado);
                return this;
            }
            if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                double resultado = valorIzq + valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.INT && der.getTipo()==TipoDato.CHAR){
                this.setTipo(TipoDato.INT);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                char valorDer = der.getValor() != null ? (char) der.getValor() : '0';
                //pasar el char a ascii y sumar
                int valorDerAscii = (int) valorDer;
                int resultado = valorIzq + valorDerAscii;
                this.setValor(resultado);
                return this;                
            }
            if(izq.getTipo()==TipoDato.INT && der.getTipo()==TipoDato.CADENA){
                this.setTipo(TipoDato.CADENA);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                String valorDer = der.getValor() != null ? (String) der.getValor() : "";
                //pasar eel int a string y concatenar
                String valorIzqString = String.valueOf(valorIzq);
                String resultado = valorIzqString + valorDer;
                this.setValor(resultado);
                return this;     
            }

            //DOUBLE
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                double resultado = valorIzq + valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                double resultado = valorIzq + valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.CHAR){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                char valorDer = der.getValor() != null ? (char) der.getValor() : '0';
                //pasar el char a ascii y sumar
                int valorDerAscii = (int) valorDer;
                double resultado = valorIzq + valorDerAscii;
                this.setValor(resultado);
                return this;                
            }
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.CADENA){
                this.setTipo(TipoDato.CADENA);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                String valorDer = der.getValor() != null ? (String) der.getValor() : "";
                //pasar eel int a string y concatenar
                String valorIzqString = String.valueOf(valorIzq);
                String resultado = valorIzqString + valorDer;
                this.setValor(resultado);
                return this;     
            }

            //boolean
            if(izq.getTipo()==TipoDato.BOOLEAN && der.getTipo()==TipoDato.CADENA){
                this.setTipo(TipoDato.CADENA);
                boolean valorIzq = izq.getValor() != null ? (boolean) izq.getValor() : true;
                String valorDer = der.getValor() != null ? (String) der.getValor() : "";
                //pasar el boolean a string y concatenar
                String valorIzqString = String.valueOf(valorIzq);
                String resultado = valorIzqString + valorDer;
                this.setValor(resultado);
                return this;
            }

            //CHAR
            if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.INT){
                this.setTipo(TipoDato.INT);
                char valorIzq = izq.getValor() != null ? (char) izq.getValor() : '0';
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                int valorIzqAscii = (int) valorIzq;
                int resultado = valorIzqAscii + valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                char valorIzq = izq.getValor() != null ? (char) izq.getValor() : '0';
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                int valorIzqAscii = (int) valorIzq;
                double resultado = valorIzqAscii + valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.CHAR){
                this.setTipo(TipoDato.CADENA);
                char valorIzq = izq.getValor() != null ? (char) izq.getValor() : '0';
                char valorDer = der.getValor() != null ? (char) der.getValor() : '0';
                String valorIzqString = String.valueOf(valorIzq);
                String valorDerString = String.valueOf(valorDer);
                String resultado = valorIzqString + valorDerString;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.CADENA){
                this.setTipo(TipoDato.CADENA);
                char valorIzq = izq.getValor() != null ? (char) izq.getValor() : '0';
                String valorDer = der.getValor() != null ? (String) der.getValor() : "";
                String valorIzqString = String.valueOf(valorIzq);
                String resultado = valorIzqString + valorDer;
                this.setValor(resultado);
                return this;
            }

            //CADENA
            if(izq.getTipo()==TipoDato.CADENA && der.getTipo()==TipoDato.INT){
                this.setTipo(TipoDato.CADENA);
                String valorIzq = izq.getValor() != null ? (String) izq.getValor() : "";
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                String valorDerString = String.valueOf(valorDer);
                String resultado = valorIzq + valorDerString;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.CADENA && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.CADENA);
                String valorIzq = izq.getValor() != null ? (String) izq.getValor() : "";
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                String valorDerString = String.valueOf(valorDer);
                String resultado = valorIzq + valorDerString;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.CADENA && der.getTipo()==TipoDato.CHAR){
                this.setTipo(TipoDato.CADENA);
                String valorIzq = izq.getValor() != null ? (String) izq.getValor() : "";
                char valorDer = der.getValor() != null ? (char) der.getValor() : '0';
                String valorDerString = String.valueOf(valorDer);
                String resultado = valorIzq + valorDerString;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.CADENA && der.getTipo()==TipoDato.CADENA){
                this.setTipo(TipoDato.CADENA);
                String valorIzq = izq.getValor() != null ? (String) izq.getValor() : "";
                String valorDer = der.getValor() != null ? (String) der.getValor() : "";
                String resultado = valorIzq + valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.CADENA && der.getTipo()==TipoDato.BOOLEAN){
                this.setTipo(TipoDato.CADENA);
                String valorIzq = izq.getValor() != null ? (String) izq.getValor() : "";
                boolean valorDer = der.getValor() != null ? (boolean) der.getValor() : true;
                String valorDerString = String.valueOf(valorDer);
                String resultado = valorIzq + valorDerString;
                this.setValor(resultado);
                return this;
            }
        }


        //Restar
        if(this.operador.equals("-")){

            //INT 
            if(izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT){
                this.setTipo(TipoDato.INT);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                int resultado = valorIzq - valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                double resultado = valorIzq - valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.INT && der.getTipo()==TipoDato.CHAR){
                this.setTipo(TipoDato.INT);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                char valorDer = der.getValor() != null ? (char) der.getValor() : '0';
                //pasar el char a ascii y restar
                int valorDerAscii = (int) valorDer;
                int resultado = valorIzq - valorDerAscii;
                this.setValor(resultado);
                return this;                
            }

            //DOUBLE
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                double resultado = valorIzq - valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                double resultado = valorIzq - valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.CHAR){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                char valorDer = der.getValor() != null ? (char) der.getValor() : '0';
                //pasar el char a ascii y restar
                int valorDerAscii = (int) valorDer;
                double resultado = valorIzq - valorDerAscii;
                this.setValor(resultado);
                return this;                
            }

            //CHAR
            if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.INT){
                this.setTipo(TipoDato.INT);
                char valorIzq = izq.getValor() != null ? (char) izq.getValor() : '0';
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                int valorIzqAscii = (int) valorIzq;
                int resultado = valorIzqAscii - valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                char valorIzq = izq.getValor() != null ? (char) izq.getValor() : '0';
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                int valorIzqAscii = (int) valorIzq;
                double resultado = valorIzqAscii - valorDer;
                this.setValor(resultado);
                return this;
            }

        }

        
        //Multiplicar
        if(this.operador.equals("*")){

            //INT 
            if(izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT){
                this.setTipo(TipoDato.INT);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                int resultado = valorIzq * valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                double resultado = valorIzq * valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.INT && der.getTipo()==TipoDato.CHAR){
                this.setTipo(TipoDato.INT);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                char valorDer = der.getValor() != null ? (char) der.getValor() : '0';
                //pasar el char a ascii y multiplicar
                int valorDerAscii = (int) valorDer;
                int resultado = valorIzq * valorDerAscii;
                this.setValor(resultado);
                return this;                
            }

            //DOUBLE
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                double resultado = valorIzq * valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                double resultado = valorIzq * valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.CHAR){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                char valorDer = der.getValor() != null ? (char) der.getValor() : '0';
                //pasar el char a ascii y multiplicar
                int valorDerAscii = (int) valorDer;
                double resultado = valorIzq * valorDerAscii;
                this.setValor(resultado);
                return this;                
            }

            //CHAR
            if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.INT){
                this.setTipo(TipoDato.INT);
                char valorIzq = izq.getValor() != null ? (char) izq.getValor() : '0';
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                int valorIzqAscii = (int) valorIzq;
                int resultado = valorIzqAscii * valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                char valorIzq = izq.getValor() != null ? (char) izq.getValor() : '0';
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                int valorIzqAscii = (int) valorIzq;
                double resultado = valorIzqAscii * valorDer;
                this.setValor(resultado);
                return this;
            }        
        }

        //Dividir
        if(this.operador.equals("/")){

            //INT 
            if(izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT){
                this.setTipo(TipoDato.DOUBLE);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                int resultado = valorIzq / valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                double resultado = valorIzq / valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.INT && der.getTipo()==TipoDato.CHAR){
                this.setTipo(TipoDato.DOUBLE);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                char valorDer = der.getValor() != null ? (char) der.getValor() : '0';
                //pasar el char a ascii y dividir
                int valorDerAscii = (int) valorDer;
                int resultado = valorIzq / valorDerAscii;
                this.setValor(resultado);
                return this;                
            }

            //DOUBLE
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                double resultado = valorIzq / valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                double resultado = valorIzq / valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.CHAR){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                char valorDer = der.getValor() != null ? (char) der.getValor() : '0';
                //pasar el char a ascii y dividir
                int valorDerAscii = (int) valorDer;
                double resultado = valorIzq / valorDerAscii;
                this.setValor(resultado);
                return this;                
            }

            //CHAR
            if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.INT){
                this.setTipo(TipoDato.DOUBLE);
                char valorIzq = izq.getValor() != null ? (char) izq.getValor() : '0';
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                int valorIzqAscii = (int) valorIzq;
                double resultado = valorIzqAscii / valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.CHAR && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                char valorIzq = izq.getValor() != null ? (char) izq.getValor() : '0';
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                int valorIzqAscii = (int) valorIzq;
                double resultado = valorIzqAscii / valorDer;
                this.setValor(resultado);
                return this;
            }
        }


        //Potencia
        if(this.operador.equals("**")){

            //INT 
            if(izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT){
                this.setTipo(TipoDato.INT);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                int resultado = (int) Math.pow(valorIzq, valorDer);
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                double resultado = Math.pow(valorIzq, valorDer);
                this.setValor(resultado);
                return this;
            }

            //DOUBLE
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                double resultado = Math.pow(valorIzq, valorDer);
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                double resultado = Math.pow(valorIzq, valorDer);
                this.setValor(resultado);
                return this;
            }
        }

        //Modulo
        if(this.operador.equals("%")){

            //INT 
            if(izq.getTipo() == TipoDato.INT && der.getTipo() == TipoDato.INT){
                this.setTipo(TipoDato.INT);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                int resultado = valorIzq % valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo() == TipoDato.INT && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                int valorIzq = izq.getValor() != null ? (int) izq.getValor() : 0;
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                double resultado = valorIzq % valorDer;
                this.setValor(resultado);
                return this;
            }

            //DOUBLE
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.INT){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                int valorDer = der.getValor() != null ? (int) der.getValor() : 0;
                double resultado = valorIzq % valorDer;
                this.setValor(resultado);
                return this;
            }
            if(izq.getTipo()==TipoDato.DOUBLE && der.getTipo()==TipoDato.DOUBLE){
                this.setTipo(TipoDato.DOUBLE);
                double valorIzq = izq.getValor() != null ? (double) izq.getValor() : 0.0;
                double valorDer = der.getValor() != null ? (double) der.getValor() : 0.0;
                double resultado = valorIzq % valorDer;
                this.setValor(resultado);
                return this;
            }
        }
        
        return this;
    }


}
