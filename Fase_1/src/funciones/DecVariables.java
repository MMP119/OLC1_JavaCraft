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
    private int fila;
    private int columna;

    public DecVariables(String mutabilidad, String id, TipoDato tipo, Expresion expresion, TipoDato tipo2 ,int fila, int columna) {
        super(new Tipo(TipoInstruccion.DECLARAR), fila, columna);
        this.mutabilidad = mutabilidad;
        this.id = id;
        this.tipo = tipo;
        this.expresion = expresion;
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
            
            // Verificar si la variable ya existe en la tabla actual
            if (ts.getTablaActual().containsKey(this.id)) {
                return new Errores("Semantico", "Variable " + this.id + " ya existe", this.fila, this.columna);
            } else {
                valorInterpretado.setMutabilidad(this.mutabilidad);
                Simbolo simbolo = new Simbolo(new Tipo(TipoInstruccion.DECLARAR), this.id, valorInterpretado);
                ts.setVariable(simbolo);
            }

        } catch (Exception e) {
            return new Errores("Semantico", "Error al declarar la variable " + this.id, this.fila, this.columna);
        }

        return null;
    }
}