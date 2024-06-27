package expresiones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.tablaSimbolos;
import excepciones.Errores;

public class Casteo extends Expresion{

    private Expresion exp;
    private TipoDato tipo;
    private int fila, columna;

    public Casteo(Expresion exp, TipoDato tipo, int fila, int columna) {
        super("ERROR CASTEO", TipoDato.ERROR, fila, columna);
        this.exp = exp;
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("CASTEO");
        nodo.agregarHijoAST(this.exp.getNodo());
        nodo.agregarHijo("CASTEO A " + this.tipo);
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts){
        try{
            
            this.exp = (Expresion)this.exp.interpretar(ent, ts);

            //casteo int a double
            if(this.tipo == TipoDato.DOUBLE && this.exp.getTipo() == TipoDato.INT){
                int valor = (int)Integer.parseInt(this.exp.getValor().toString());
                double val = (double)valor;
                this.exp.setValor(val);
                this.exp.setTipo(TipoDato.DOUBLE);
                return this.exp;
            }

            //casteo double a int
            else if(this.tipo == TipoDato.INT && this.exp.getTipo() == TipoDato.DOUBLE){
                double valor = (double)Double.parseDouble(this.exp.getValor().toString());
                int val = (int)valor;
                this.exp.setValor(val);
                this.exp.setTipo(TipoDato.INT);
                return this.exp;
            }

            //casteo int a char
            else if(this.tipo == TipoDato.CHAR && this.exp.getTipo() == TipoDato.INT){
                int valor = (int)Integer.parseInt(this.exp.getValor().toString());
                char val = (char)valor;
                this.exp.setValor(val);
                this.exp.setTipo(TipoDato.CHAR);
                return this.exp;
            }

            //casteo char a int
            else if(this.tipo == TipoDato.INT && this.exp.getTipo() == TipoDato.CHAR){
                char valor = (char)this.exp.getValor().toString().charAt(0);
                int val = (int)valor;
                this.exp.setValor(val);
                this.exp.setTipo(TipoDato.INT);
                return this.exp;
            }

            //casteo char a double
            else if(this.tipo == TipoDato.DOUBLE && this.exp.getTipo() == TipoDato.CHAR){
                char valor = (char)this.exp.getValor().toString().charAt(0);
                double val = (double)valor;
                this.exp.setValor(val);
                this.exp.setTipo(TipoDato.DOUBLE);
                return this.exp;
            }

            else{
                System.out.println("Error Semantico: Error en el casteo. Linea: "+this.fila+" Columna: "+this.columna);
                Errores.errores.add(new Errores("Semantico:", "Error en el casteo", this.columna, this.fila));
                return new Errores("Semantico:", "Error en el casteo", this.columna, this.fila);
            }

        }catch(Exception e){
            System.out.println("Error en el casteo: "+e);
            System.out.println("Error en el casteo: "+e);
            Errores.errores.add(new Errores("Semantico:", "Error en el casteo", this.columna, this.fila));
            return new Errores("Semantico:", "Error en el casteo", this.columna, this.fila);
        }

    }
    
}
