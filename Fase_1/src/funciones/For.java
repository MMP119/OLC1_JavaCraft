package funciones;

import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import expresiones.Expresion;
import expresiones.TipoDato;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import excepciones.*;

public class For extends Instruccion{

    private Instruccion asigVariable;
    private Expresion condicion;
    private Instruccion update;
    private LinkedList<Instruccion> local;
    private int fila, columna;

    public For(Instruccion asigVariable, Expresion condicion, Instruccion update, LinkedList<Instruccion> local, int fila, int columna ){
        super(new Tipo(TipoInstruccion.FOR), fila, columna);
        this.asigVariable = asigVariable;
        this.condicion = condicion;
        this.update = update;
        this.local = local;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("FOR");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        try {
            Instruccion.cicloProfundida++;

            // Asignación inicial fuera del ciclo
            var asig = asigVariable.interpretar(ent, ts);
            if (asig instanceof Break) {
                return null;
            }

            while (true) {
                // Evaluar la condición en el entorno actual
                Expresion condicion = (Expresion) this.condicion.interpretar(ent, ts);

                if (condicion.getTipo() != TipoDato.BOOLEAN) {
                    System.out.println("ERROR SEMANTICO: Se esperaba una expresion booleana en la condicion del for");
                    Errores.errores.add(new Errores("Semantico", "Se esperaba una expresion booleana en la condicion del for", fila, columna));
                    return new Errores("Semantico", "Se esperaba una expresion booleana en la condicion del for", fila, columna);
                }

                if (condicion.getValor().toString().equals("true")) {
                    // Crear un nuevo entorno y tabla de símbolos para cada iteración
                    Entorno entFor = new Entorno(local);
                    tablaSimbolos tsFor = new tablaSimbolos();
                    tsFor.setNombre("For");
                    tsFor.setTablaAnterior(ts);

                    for (int i = 0; i < local.size(); i++) {
                        Instruccion a = local.get(i);
                        Object res = a.interpretar(entFor, tsFor);
                        ent.setConsola(ent.getConsola() + entFor.getConsola());
                        entFor.setConsola("");

                        // Break
                        if (res instanceof Break || a instanceof Break) {
                            Instruccion.cicloProfundida--;
                            return null;
                        }

                        // Continue
                        if (res instanceof Continue || a instanceof Continue) {
                            ent.setConsola(ent.getConsola() + entFor.getConsola());
                            entFor.setConsola("");
                            break;
                        }
                    }

                    // Actualizar la variable de control del ciclo
                    update.interpretar(ent, ts);
                    ent.setConsola(ent.getConsola() + entFor.getConsola());
                    entFor.setConsola("");
                } else {
                    break;
                }
            }

            Instruccion.cicloProfundida--;
            return this;
        } catch (Exception e) {
            System.out.println("ERROR SEMANTICO: " + e.getMessage());
            Errores.errores.add(new Errores("Semantico", "Error en el for: " + e.getMessage(), fila, columna));
            return new Errores("Semantico", "Error en el for: " + e.getMessage(), fila, columna);
        }
    }
}

