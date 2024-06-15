package funciones;


import entorno.*;
import instruccion.*;
import AST.*;
import expresiones.Expresion;
import expresiones.TipoDato;
import excepciones.Errores;
import expresiones.Dato;

public class DecVariables extends Instruccion{

    private String mutabilidad;
    private String id;
    private TipoDato tipo;
    private TipoDato tipo2;
    private Expresion expresion;
    private int fila;
    private int columna;

    public DecVariables(String mutabilidad, String id, TipoDato tipo, Expresion expresion, TipoDato tipo2 ,int fila, int columna){
        super(new Tipo(TipoInstruccion.DECLARAR), fila, columna);
        this.mutabilidad = mutabilidad;
        this.id = id;
        this.tipo = tipo;
        this.expresion = expresion;
        this.tipo2 = tipo2;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("DECLARACION_VARIABLES");
        nodo.agregarHijo(this.mutabilidad);
        nodo.agregarHijo(id);
        nodo.agregarHijo(":");
        nodo.agregarHijo(tipo);
        if(expresion != null){
            nodo.agregarHijo("=");
            nodo.agregarHijoAST(expresion.getNodo());
        }
        nodo.agregarHijo(";");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        
        try{
            if(this.tipo2 == null){
                //si expresion es null, se declara una variable asignando un valor por defecto
                if(this.expresion == null){
                    switch (this.tipo) {
                        case INT:
                            this.expresion = new Dato(0, TipoDato.INT, this.fila, this.columna);    
                            break;
                        case DOUBLE:
                            this.expresion = new Dato(0.0, TipoDato.DOUBLE, this.fila, this.columna);
                            break;
                        case BOOLEAN:
                            this.expresion = new Dato(true, TipoDato.BOOLEAN, this.fila, this.columna);
                            break;
                        case CHAR:
                            this.expresion = new Dato('0', TipoDato.CHAR, this.fila, this.columna);
                            break;
                        case CADENA:
                            this.expresion = new Dato("", TipoDato.CADENA, this.fila, this.columna);
                            break;            
                        default:
                            Errores.errores.add(new Errores("Semantico","Tipo de dato no valido", this.fila, this.columna));
                            System.out.println("ERROR SEMANTICO, Tipo de dato no valido");
                            break;
                    }
                }
    
                if(this.expresion != null){
    
                    Expresion valor = (Expresion)this.expresion.interpretar(ent, ts);
    
                    if(ts.getTablaActual().containsKey(id)){
                        //System.out.println("Variable "+this.id+" ya existe");
                        Errores.errores.add(new Errores("Semantico","Variable "+this.id+" ya existe", this.fila, this.columna));
                        return new Errores("Semantico","Variable "+this.id+" ya existe", this.fila, this.columna);
                    }else{
                        valor.setId(this.id);
                        valor.setMutabilidad(this.mutabilidad);
                        ts.setVariable(new Simbolo(new Tipo(TipoInstruccion.DECLARAR), this.id, this.expresion));
                    }
                    
                }
    
            }else{
    
                Expresion exp = (Expresion)this.expresion.interpretar(ent, ts);
    
                //castear de int a double
                if(exp.getTipo() == TipoDato.INT && this.tipo2 == TipoDato.DOUBLE && this.tipo == TipoDato.DOUBLE){
                    //pasar el valor de la expresion a double
                    int val = (int) Integer.parseInt(exp.getValor().toString());
                    double valor = (double) val;
                    exp.setValor(valor);
                    exp.setTipo(TipoDato.DOUBLE);
                    exp.setMutabilidad(this.mutabilidad);
                    exp.setId(id);
                    ts.setVariable(new Simbolo(new Tipo(TipoInstruccion.DECLARAR), this.id, this.expresion));
                }
    
                //castear de double a int
                else if (exp.getTipo() == TipoDato.DOUBLE && this.tipo2 == TipoDato.INT && this.tipo == TipoDato.INT){
                    //pasar el valor de la expresion a int
                    double dou = (double)Double.parseDouble(exp.getValor().toString());
                    int valor = (int) dou;
                    exp.setValor(valor);
                    exp.setTipo(TipoDato.INT);
                    exp.setId(id);
                    exp.setMutabilidad(this.mutabilidad);
                    ts.setVariable(new Simbolo(new Tipo(TipoInstruccion.DECLARAR), this.id, this.expresion));
                }
    
                //castear int a char
                else if (exp.getTipo() == TipoDato.INT && this.tipo2 == TipoDato.CHAR && this.tipo == TipoDato.CHAR){
                    int val = (int) Integer.parseInt(exp.getValor().toString());
                    char valor = (char) val;
                    exp.setValor(valor);
                    exp.setTipo(TipoDato.CHAR);
                    exp.setId(id);
                    exp.setMutabilidad(this.mutabilidad);
                    ts.setVariable(new Simbolo(new Tipo(TipoInstruccion.DECLARAR), this.id, this.expresion));
                }
    
                //castear de char a int
                else if (exp.getTipo() == TipoDato.CHAR && this.tipo2 == TipoDato.INT && this.tipo == TipoDato.INT){
                    char val = exp.getValor().toString().charAt(0);
                    int valor = (int) val;
                    exp.setValor(valor);
                    exp.setTipo(TipoDato.INT);
                    exp.setId(id);
                    exp.setMutabilidad(this.mutabilidad);
                    ts.setVariable(new Simbolo(new Tipo(TipoInstruccion.DECLARAR), this.id, this.expresion));
                }
    
                //castear de char a double
                else if (exp.getTipo() == TipoDato.CHAR && this.tipo2 == TipoDato.DOUBLE && this.tipo == TipoDato.DOUBLE){
                    char val = exp.getValor().toString().charAt(0);
                    double valor = (double) val;
                    exp.setValor(valor);
                    exp.setTipo(TipoDato.DOUBLE);
                    exp.setId(id);
                    exp.setMutabilidad(this.mutabilidad);
                    ts.setVariable(new Simbolo(new Tipo(TipoInstruccion.DECLARAR), this.id, this.expresion));
                }
    
                else{
                    System.out.println("ERROR SEMANTICO, No se puede castear de "+this.tipo2+" a "+this.tipo);
                    Errores.errores.add(new Errores("Semantico","No se puede castear de "+this.tipo2+" a "+this.tipo, this.fila, this.columna));
                    return new Errores("Semantico","No se puede castear de "+this.tipo2+" a "+this.tipo, this.fila, this.columna);
                }
    
            }
    
            return this;
            
        }catch(Exception e){
            Errores.errores.add(new Errores("Semantico","Error al declarar variable", this.fila, this.columna));
            return new Errores("Semantico","Error al declarar variable", this.fila, this.columna);
        }
    }
    
}
