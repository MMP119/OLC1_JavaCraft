package funciones;

import entorno.*;
import instruccion.*;
import AST.*;
import expresiones.Expresion;
import expresiones.TipoDato;
import excepciones.Errores;
import expresiones.Dato;

public class DecVariables extends Instruccion {

    private String mutabilidad;
    private String id;
    private TipoDato tipo;
    private Expresion expresion;
    private TipoDato tipo2;
    private int fila;
    private int columna;

    public DecVariables(String mutabilidad, String id, TipoDato tipo, Expresion expresion, TipoDato tipo2 ,int fila, int columna) {
        super(new Tipo(TipoInstruccion.DECLARAR), fila, columna);
        this.mutabilidad = mutabilidad;
        this.id = id;
        this.tipo = tipo;
        this.expresion = expresion;
        this.tipo2 = tipo2;
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("DECLARACION_VARIABLES");
        nodo.agregarHijo(this.mutabilidad);
        nodo.agregarHijo(id);
        nodo.agregarHijo(":");
        nodo.agregarHijo(tipo.name());
        if (expresion != null) {
            nodo.agregarHijo("=");
            nodo.agregarHijoAST(expresion.getNodo());
        }
        nodo.agregarHijo(";");
        return nodo;
    }

    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        try {

            if(this.tipo2 == null){
            // Si la expresión es null, asignar un valor por defecto según el tipo
                if (this.expresion == null) {
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
                            return new Errores("Semantico", "Tipo de dato no valido", this.fila, this.columna);
                    }
                }

                // Interpretar la expresión para obtener el valor
                Expresion valorInterpretado = (Expresion)this.expresion.interpretar(ent, ts);
                System.out.println("Valor interpretado: " + valorInterpretado.getValor() + " ID: " + this.id + " Tipo: " + valorInterpretado.getTipo());
                

                //verificar si ya existe
                if(ts.getVariable(this.id) != null){
                    return new Errores("Semantico", "Variable " + this.id + " ya existe", this.fila, this.columna);
                }else{
                    valorInterpretado.setMutabilidad(this.mutabilidad);
                    Simbolo simbolo = new Simbolo(new Tipo(TipoInstruccion.DECLARAR), this.id, valorInterpretado);
                    ts.setVariable(simbolo);
                }

            }else{
                this.expresion = (Expresion)this.expresion.interpretar(ent, ts);

                //castear a int a double
                if(this.expresion.getTipo() == TipoDato.INT && this.tipo2 == TipoDato.DOUBLE && this.tipo == TipoDato.DOUBLE){
                    int val = (int) Integer.parseInt(this.expresion.getValor().toString());
                    double valor = (double) val;
                    this.expresion.setValor(valor);
                    this.expresion.setTipo(TipoDato.DOUBLE);
                    this.expresion.setMutabilidad(this.mutabilidad);
                    this.expresion.setId(this.id);
                    Simbolo simbolo = new Simbolo(new Tipo(TipoInstruccion.DECLARAR), this.id, this.expresion);
                    
                    // Verificar si la variable ya existe
                    if(ts.getVariable(this.id) != null){
                        Errores.errores.add(new Errores("Semantico", "Variable " + this.id + " ya existe", this.fila, this.columna));
                        return new Errores("Semantico", "Variable " + this.id + " ya existe", this.fila, this.columna);
                    }else{
                        ts.setVariable(simbolo);
                    }
                }

                //castear a double a int
                else if(this.expresion.getTipo() == TipoDato.DOUBLE && this.tipo2 == TipoDato.INT && this.tipo == TipoDato.INT){
                    double val = (double) Double.parseDouble(this.expresion.getValor().toString());
                    int valor = (int) val;
                    this.expresion.setValor(valor);
                    this.expresion.setTipo(TipoDato.INT);
                    this.expresion.setMutabilidad(this.mutabilidad);
                    this.expresion.setId(this.id);
                    Simbolo simbolo = new Simbolo(new Tipo(TipoInstruccion.DECLARAR), this.id, this.expresion);
                    
                    // Verificar si la variable ya existe
                    if (ts.getTablaActual().containsKey(this.id)) {
                        Errores.errores.add(new Errores("Semantico", "Variable " + this.id + " ya existe", this.fila, this.columna));
                        return new Errores("Semantico", "Variable " + this.id + " ya existe", this.fila, this.columna);
                    } else {
                        ts.setVariable(simbolo);
                    }                  
                }

                //castear a int a char
                else if(expresion.getTipo() == TipoDato.INT && this.tipo2 == TipoDato.CHAR && this.tipo == TipoDato.CHAR){
                    int val = (int) Integer.parseInt(this.expresion.getValor().toString());
                    char valor = (char) val;
                    this.expresion.setValor(valor);
                    this.expresion.setTipo(TipoDato.CHAR);
                    this.expresion.setMutabilidad(this.mutabilidad);
                    this.expresion.setId(this.id);
                    Simbolo simbolo = new Simbolo(new Tipo(TipoInstruccion.DECLARAR), this.id, this.expresion);
                    
                    // Verificar si la variable ya existe
                    if (ts.getTablaActual().containsKey(this.id)) {
                        Errores.errores.add(new Errores("Semantico", "Variable " + this.id + " ya existe", this.fila, this.columna));
                        return new Errores("Semantico", "Variable " + this.id + " ya existe", this.fila, this.columna);
                    } else {
                        ts.setVariable(simbolo);
                    }                  
                }

                //castear a char a int
                else if(this.expresion.getTipo() == TipoDato.CHAR && this.tipo2 == TipoDato.INT && this.tipo == TipoDato.INT){
                    char val = this.expresion.getValor().toString().charAt(0);
                    int valor = (int) val;
                    this.expresion.setValor(valor);
                    this.expresion.setTipo(TipoDato.INT);
                    this.expresion.setMutabilidad(this.mutabilidad);
                    this.expresion.setId(this.id);
                    Simbolo simbolo = new Simbolo(new Tipo(TipoInstruccion.DECLARAR), this.id, this.expresion);
                    
                    // Verificar si la variable ya existe
                    if (ts.getTablaActual().containsKey(this.id)) {
                        Errores.errores.add(new Errores("Semantico", "Variable " + this.id + " ya existe", this.fila, this.columna));
                        return new Errores("Semantico", "Variable " + this.id + " ya existe", this.fila, this.columna);
                    } else {
                        ts.setVariable(simbolo);
                    }                  
                }

                //castear de char a double
                else if(this.expresion.getTipo() == TipoDato.CHAR && this.tipo2 == TipoDato.DOUBLE && this.tipo == TipoDato.DOUBLE){
                    char val = this.expresion.getValor().toString().charAt(0);
                    double valor = (double) val;
                    this.expresion.setValor(valor);
                    this.expresion.setTipo(TipoDato.DOUBLE);
                    this.expresion.setMutabilidad(this.mutabilidad);
                    this.expresion.setId(this.id);
                    Simbolo simbolo = new Simbolo(new Tipo(TipoInstruccion.DECLARAR), this.id, this.expresion);
                    
                    // Verificar si la variable ya existe
                    if (ts.getTablaActual().containsKey(this.id)) {
                        Errores.errores.add(new Errores("Semantico", "Variable " + this.id + " ya existe", this.fila, this.columna));
                        return new Errores("Semantico", "Variable " + this.id + " ya existe", this.fila, this.columna);
                    } else {
                        ts.setVariable(simbolo);
                    }                   
                }

                else{
                    System.out.println("SEMANTICO: no se puede castear de" + expresion.getTipo() + " a " + this.tipo2);
                    Errores.errores.add(new Errores("Semantico", "No se puede castear de " + expresion.getTipo() + " a " + this.tipo2, this.fila, this.columna));
                    return new Errores("Semantico", "Error al declarar la variable " + this.id, this.fila, this.columna);
                }

            }
        } catch (Exception e) {
            Errores.errores.add(new Errores("Semantico", "Error al declarar la variable " + this.id, this.fila, this.columna));
            return new Errores("Semantico", "Error al declarar la variable " + this.id, this.fila, this.columna);
        }
        return null;
    }
}