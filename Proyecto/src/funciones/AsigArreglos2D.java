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


public class AsigArreglos2D extends Instruccion {
    
    private String id;
    private Expresion indice1;
    private Expresion indice2;
    private Expresion valor;
    private int fila, columna;

    public AsigArreglos2D(String id, Expresion indice1, Expresion indice2, Expresion valor, int fila, int columna) {
        super(new Tipo(TipoInstruccion.ASIG_ARREGLOS), fila, columna); 
        this.id = id;
        this.indice1 = indice1;
        this.indice2 = indice2;
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("ASIG_ARREGLOS_2D");
        nodo.agregarHijo("ASIG");
        nodo.agregarHijo(id);
        nodo.agregarHijo("[");
        nodo.agregarHijoAST(indice1.getNodo());
        nodo.agregarHijo("]");
        nodo.agregarHijo("[");
        nodo.agregarHijoAST(indice2.getNodo());
        nodo.agregarHijo("]");
        nodo.agregarHijo("=");
        nodo.agregarHijoAST(valor.getNodo());
        nodo.agregarHijo(";");
        return nodo;
    }

    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts){
        try{

            Simbolo sim = ts.getVariable(id);

            if(sim == null){
                System.out.println("Error en la asignacion de arreglos de dos dimensiones");
                Errores.errores.add(new Errores("Semantico","Error en la asignacion de arreglos de dos dimensiones", fila, columna));
                return new Errores("Semantico","Error en la asignacion de arreglos de dos dimensiones", fila, columna);
            }

            if(!(sim.getValor() instanceof DatoArreglo2D)){
                System.out.println("El simbolo: "+id+" no es un arreglo de dos dimensiones");
                Errores.errores.add(new Errores("Semantico","El simbolo: "+id+" no es un arreglo de dos dimensiones", fila, columna));
                return new Errores("Semantico","El simbolo: "+id+" no es un arreglo de dos dimensiones", fila, columna);
            }

            DatoArreglo2D arreglo = (DatoArreglo2D) sim.getValor();
            LinkedList<LinkedList<Object>> valores = arreglo.getValor();

            //interpretar los indices
            Object indiceValor1 = indice1.interpretar(ent, ts);
            Object indiceValor2 = indice2.interpretar(ent, ts);

            int index1, index2;

            try{
                index1 = Integer.parseInt(indiceValor1.toString());
                index2 = Integer.parseInt(indiceValor2.toString());

            }catch(Exception e){
                System.out.println("Error Semantico: Indice no es un entero");
                Errores.errores.add(new Errores("Semantico", "Indice no es un entero", this.fila, this.columna));
                return new Errores("Semantico", "Indice no es un entero", this.fila, this.columna);
            }

            
            if (index1 < 0 || index1 >= valores.size() || index2 < 0 || index2 >= valores.get(index1).size()) {
                System.out.println("Indice fuera de rango");
                Errores.errores.add(new Errores("Semantico", "Indice fuera de rango", fila, columna));
                return new Errores("Semantico", "Indice fuera de rango", fila, columna);
            }

            //interpretar valor a asignar
            Object valorAsig = valor.interpretar(ent, ts);

            //asignar el valor al arreglo
            valores.get(index1).set(index2, valorAsig);

            //actualizar la tabla de simbolos
            sim.setValor(new DatoArreglo2D(valores, arreglo.getTipo()));

            return null;

        }catch(Exception e){
            System.out.println("Error en la asignacion de arreglos de dos dimensiones");
            Errores.errores.add(new Errores("Semantico","Error en la asignacion de arreglos de dos dimensiones", fila, columna));
            return new Errores("Semantico","Error en la asignacion de arreglos de dos dimensiones", fila, columna);
        }
    }

}
