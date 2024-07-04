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
        nodo.agregarHijo("for");
        nodo.agregarHijo("(");
        nodo.agregarHijoAST(asigVariable.getNodo());
        nodo.agregarHijoAST(condicion.getNodo());
        nodo.agregarHijoAST(update.getNodo());
        nodo.agregarHijo(")");
        nodo.agregarHijo("{");
        for(Instruccion i : local){
            nodo.agregarHijoAST(i.getNodo());
        }
        nodo.agregarHijo("}");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        try {
            Instruccion.cicloProfundida++;
            
            var nuevaTabla = new tablaSimbolos(ts);
            nuevaTabla.setNombre("for");
            tablaSimbolos.tablas.add(nuevaTabla);

            var res1 = this.asigVariable.interpretar(ent, nuevaTabla);
            if(res1 instanceof Errores){
                return res1;
            }

            var cond = this.condicion.interpretar(ent, nuevaTabla);
            if(cond instanceof Errores){
                return cond;
            }

            if(this.condicion.getTipo() != TipoDato.BOOLEAN){
                System.out.println("ERROR SEMANTICO: Se esperaba una expresion booleana en la condicion del for");
                Errores.errores.add(new Errores("Semantico", "Se esperaba una expresion booleana en la condicion del for", fila, columna));
                return new Errores("Semantico", "Se esperaba una expresion booleana en la condicion del for", fila, columna);
            }

            while(Boolean.parseBoolean(this.condicion.interpretar(ent, nuevaTabla).toString())){

                //nuevo entorno
                var newTabla2 = new tablaSimbolos(nuevaTabla);

                //ejecutar instrucciones
                for(var i:this.local){
                    if(i instanceof Break){
                        Instruccion.cicloProfundida--;
                        return null;
                    }

                    var res = i.interpretar(ent, newTabla2);
                    if(res instanceof Errores){
                        return res;
                    }
                    if(res instanceof Break){
                        Instruccion.cicloProfundida--;
                        return null;
                    }

                    if(res instanceof Continue){
                        break;
                    }

                    if(res instanceof Return){
                        Instruccion.cicloProfundida--;
                        //System.out.println("RETURN EN FOR: " + res);
                        return res;
                    }

                }

                //actualizar
                var act = this.update.interpretar(ent, nuevaTabla);
                if(act instanceof Errores){
                    return act;
                }                
            }

            Instruccion.cicloProfundida--;
            return null;
        } catch (Exception e) {
            System.out.println("ERROR SEMANTICO: " + e.getMessage());
            Errores.errores.add(new Errores("Semantico", "Error en el for: " + e.getMessage(), fila, columna));
            return new Errores("Semantico", "Error en el for: " + e.getMessage(), fila, columna);
        }
    }
}
