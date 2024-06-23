package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.Expresion;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import entorno.Simbolo;
import java.util.LinkedList;

public class AsigArreglos extends Instruccion {
    
    private String id;
    private Expresion exp;
    private Expresion valor;
    private int fila, columna;

    public AsigArreglos(String id, Expresion exp, Expresion valor, int fila, int columna) {
        super(new Tipo(TipoInstruccion.ASIG_ARREGLOS), fila, columna); 
        this.id = id;
        this.exp = exp;
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
    }


    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("ASIG_ARREGLOS");
        nodo.agregarHijo("ASIG");
        nodo.agregarHijo(id);
        nodo.agregarHijo("[");
        nodo.agregarHijoAST(exp.getNodo());
        nodo.agregarHijo("]");
        nodo.agregarHijo("=");
        nodo.agregarHijoAST(valor.getNodo());
        nodo.agregarHijo(";");
        return nodo;
    }


    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        try{
            Simbolo sim = ts.getVariable(id);
            
            if(sim == null){
                System.out.println("Error en la asignacion de arreglos de una dimension");
                Errores.errores.add(new Errores("Semantico","Error en la asignacion de arreglos de una dimension", fila, columna));
                return new Errores("Semantico","Error en la asignacion de arreglos de una dimension", fila, columna);
            }

            if(!(sim.getValor() instanceof DatoArreglo)){
                System.out.println("El simbolo: "+id+" no es un arreglo");
                Errores.errores.add(new Errores("Semantico","El simbolo: "+id+" no es un arreglo", fila, columna));
                return new Errores("Semantico","El simbolo: "+id+" no es un arreglo", fila, columna);
            }

            DatoArreglo arre = (DatoArreglo) sim.getValor();
            LinkedList<Object> arreglo = arre.getValor();

            //evaluar el indice
            Object indiceValor = exp.interpretar(ent, ts);

            int index;

            try{
                index = Integer.parseInt(indiceValor.toString());
            }catch(Exception e){
                System.out.println("El indice no es un entero");
                Errores.errores.add(new Errores("Semantico","El indice no es un entero", fila, columna));
                return new Errores("Semantico","El indice no es un entero", fila, columna);
            }

            if(index < 0 || index >= arreglo.size()){
                System.out.println("Indice fuera de rango");
                Errores.errores.add(new Errores("Semantico","Indice fuera de rango", fila, columna));
                return new Errores("Semantico","Indice fuera de rango", fila, columna);
            }

            //evaluar el valor a asignar
            Object valorAsig = valor.interpretar(ent, ts);

            //asignar el valor al arreglo
            arreglo.set(index, valorAsig);

            //actualizar la tabla de simbolos
            sim.setValor(new DatoArreglo(arreglo, arre.getTipo()));

            return null;

        }catch(Exception e){
            System.out.println("Error en la asignacion de arreglos de una dimension");
            Errores.errores.add(new Errores("Semantico","Error en la asignacion de arreglos de una dimension", fila, columna));
            return new Errores("Semantico","Error en la asignacion de arreglos de una dimension", fila, columna);
        }
    }

}
