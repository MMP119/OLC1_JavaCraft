package expresiones;

import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.tablaSimbolos;
import excepciones.Errores;
import funciones.DatoArreglo;
import funciones.DatoLista;
import entorno.Simbolo;

public class AccesoVector extends Expresion {
    

    private String id;
    private Expresion indice;
    private int fila, columna;


    public AccesoVector(String id, Expresion indice, int fila, int columna) {
        super("ERROR_ACCESO_VECTOR", TipoDato.ERROR, fila, columna);
        this.id = id;
        this.indice = indice;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("ACCESO_VECTOR");
        nodo.agregarHijo(id);
        nodo.agregarHijo("[");
        nodo.agregarHijoAST(indice.getNodo());
        nodo.agregarHijo("]");
        return nodo;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts){
        
        try{

            Simbolo simbolo = ts.getVariable(this.id);

            if(simbolo != null && (simbolo.getValor() instanceof DatoArreglo || simbolo.getValor() instanceof DatoLista)){
                
                if(simbolo.getValor() instanceof DatoArreglo){
                    DatoArreglo vector = (DatoArreglo) simbolo.getValor();
                    LinkedList<Object>  valores = vector.getValor();

                    //interpretar el indice
                    Object indiceValor = indice.interpretar(ent, ts);

                    int index;

                        try{
                            index = Integer.parseInt(indiceValor.toString());

                        }catch(Exception e){
                            System.out.println("Error Semantico: Indice no es un entero");
                            Errores.errores.add(new Errores("Semantico", "Indice no es un entero", this.fila, this.columna));
                            return new Errores("Semantico", "Indice no es un entero", this.fila, this.columna);
                        }

                        if(index >= 0 && index < valores.size()){
                            return valores.get(index);
                        }else{
                            System.out.println("Error Semantico: Indice fuera de rango");
                            Errores.errores.add(new Errores("Semantico", "Indice fuera de rango", this.fila, this.columna));
                            return new Errores("Semantico", "Indice fuera de rango", this.fila, this.columna);
                        }
                }

                if(simbolo.getValor() instanceof DatoLista){
                    DatoLista lista = (DatoLista) simbolo.getValor();
                    LinkedList<Object>  valores = lista.getElementos();

                    //interpretar el indice
                    Object indiceValor = indice.interpretar(ent, ts);

                    int index;

                        try{
                            index = Integer.parseInt(indiceValor.toString());

                        }catch(Exception e){
                            System.out.println("Error Semantico: Indice no es un entero");
                            Errores.errores.add(new Errores("Semantico", "Indice no es un entero", this.fila, this.columna));
                            return new Errores("Semantico", "Indice no es un entero", this.fila, this.columna);
                        }

                        if(index >= 0 && index < valores.size()){
                            return valores.get(index);
                        }else{
                            System.out.println("Error Semantico: Indice fuera de rango");
                            Errores.errores.add(new Errores("Semantico", "Indice fuera de rango", this.fila, this.columna));
                            return new Errores("Semantico", "Indice fuera de rango", this.fila, this.columna);
                        }
                }
                
            }else{
                System.out.println("Error Semantico: El vector o lista: "+this.id+" no existe");
                Errores.errores.add(new Errores("Semantico", "El vector o lista: "+this.id+" no existe", this.fila, this.columna));
                return new Errores("Semantico", "El vector o lista: "+this.id+" no existe", this.fila, this.columna);
            }

            return null;

        }catch(Exception e){
            e.printStackTrace();
            // Imprimir información específica sobre la línea exacta del error
            StackTraceElement[] stackTrace = e.getStackTrace();
            if (stackTrace.length > 0) {
                StackTraceElement element = stackTrace[0];
                System.out.println("Error en la clase: " + element.getClassName());
                System.out.println("Error en el método: " + element.getMethodName());
                System.out.println("Error en la línea: " + element.getLineNumber());
            }
            Errores.errores.add(new Errores("Semantico", "Error al interpretar Acceso a Vector", this.fila, this.columna));
            return new Errores("Semantico", "Error al interpretar Acceso a Vector", this.fila, this.columna);
        }
    }


}
