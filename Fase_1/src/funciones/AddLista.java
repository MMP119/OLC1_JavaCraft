package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.Expresion;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import entorno.Simbolo;
import entorno.Tipo;


public class AddLista extends Instruccion{

    private String id;
    private Expresion valor;
    private int fila, columna;

    public AddLista(String id, Expresion valor, int fila, int columna) {
        super(new Tipo(TipoInstruccion.ADD_LISTA), fila, columna);
        this.id = id;
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
    }


    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("APPEND_LISTA");
        nodo.agregarHijo(id);
        nodo.agregarHijo(".append(");
        nodo.agregarHijoAST(valor.getNodo());
        nodo.agregarHijo(");");
        return nodo;
    }


    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts){
        
        try{

            Simbolo sim = ts.getVariable(id);

            if(sim == null){
                System.out.println("La lista "+id+" no existe en este ambito");
                Errores.errores.add(new Errores("Semantico", "La lista "+id+" no existe en este ambito", fila, columna));
                return new Errores("Semantico", "La lista "+id+" no existe en este ambito", fila, columna);
            }

            if(!(sim.getValor() instanceof DatoLista)){
                System.out.println("El simbolo "+id+" no es una lista");
                Errores.errores.add(new Errores("Semantico", "El simbolo "+id+" no es una lista", fila, columna));
                return new Errores("Semantico", "El simbolo "+id+" no es una lista", fila, columna);
            }

            DatoLista lista = (DatoLista)sim.getValor();
            Object valorAsig = valor.interpretar(ent, ts);
            Expresion valorA =  (Expresion)valorAsig;

            //validacion del tipo de valor 
            if(lista.getTipo() != valorA.getTipo()){
                System.out.println("El tipo de dato no coincide con el tipo de la lista: "+id);
                Errores.errores.add(new Errores("Semantico", "El tipo de dato no coincide con el tipo de la lista: "+id, fila, columna));
                return new Errores("Semantico", "El tipo de dato no coincide con el tipo de la lista: "+id, fila, columna);
            }

            lista.agregar(valorAsig);

            //actualizar la lista en la tabla de simbolos
            sim.setValor(lista);

            return null;

        }catch(Exception e){
            System.out.println("Error al agregar un elemento a la lista: "+id);
            Errores.errores.add(new Errores("Semantico", "Error al agregar un elemento a la lista: "+id, fila, columna));
            return new Errores("Semantico", "Error al agregar un elemento a la lista: "+id, fila, columna);
        }

    }

    
}
