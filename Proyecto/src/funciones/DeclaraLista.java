package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.TipoDato;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import entorno.Simbolo;

public class DeclaraLista extends Instruccion {

    private String id;
    private TipoDato tipoElemento;
    private int fila, columna;

    public DeclaraLista(TipoDato tipoElemento, String id, int fila, int columna) {
        super(new Tipo(TipoInstruccion.DECLARA_LISTA), fila, columna);
        this.id = id;
        this.tipoElemento = tipoElemento;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("DECLARA_LISTA");
        nodo.agregarHijo("List<");
        nodo.agregarHijo(tipoElemento.toString());
        nodo.agregarHijo(">");
        nodo.agregarHijo(id);
        nodo.agregarHijo("=");
        nodo.agregarHijo("new List()");
        nodo.agregarHijo(";");
        return nodo;
    }

    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts){

        try{

            if(ts.getTablaActual().containsKey(this.id)){
                System.out.println("La lista "+id+" ya existe en este ambito");
                Errores.errores.add(new Errores("Semantico", "La lista "+id+" ya existe en este ambito", fila, columna));
                return new Errores("Semantico", "La lista "+id+" ya existe en este ambito", fila, columna);
            }

            DatoLista nuevaLista = new DatoLista(tipoElemento);
            Simbolo simbolo = new Simbolo(new Tipo(TipoInstruccion.DECLARA_LISTA),id, nuevaLista);
            ts.setVariable(simbolo);
            return null;

        }catch(Exception e){
            System.out.println("Error en la declaracion de la lista");
            Errores.errores.add(new Errores("Semantico", "Error en la declaracion de la lista", fila, columna));
            return new Errores("Semantico", "Error en la declaracion de la lista", fila, columna);
        }

    }

}