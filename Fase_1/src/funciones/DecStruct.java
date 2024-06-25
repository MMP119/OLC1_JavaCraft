package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Simbolo;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

import java.util.LinkedList;

public class DecStruct extends Instruccion{

    private String id;
    private LinkedList<Campo> campos;
    private int fila, columna;

    public DecStruct(String id, LinkedList<Campo> campos, int fila, int columna) {
        super(new Tipo(TipoInstruccion.STRUCT), fila, columna);
        this.id = id;
        this.campos = campos;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("STRUCT");
        nodo.agregarHijo("struct {");
        for (Campo campo : campos) {
            nodo.agregarHijo(campo.getNombre()+" : "+campo.getTipo());
        }
        nodo.agregarHijo("}");
        nodo.agregarHijo(id);
        return nodo;
    }

    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts){
        try{
            //VERIFICAR SI YA EXISTE UNA ESTRUCTURA CON EL MISMO NOMBRE
            if(ts.getVariable(id)!=null){
                System.out.println("SEMANTICO: Ya existe una estructura con el nombre: "+id);
                Errores.errores.add(new Errores("Semantico", "Ya existe una estructura con el nombre: "+id, fila, columna));
                return new Errores("Semantico", "Ya existe una estructura con el nombre: "+id, fila, columna);
            }

            Simbolo structSimbolo = new Simbolo(new Tipo(TipoInstruccion.STRUCT), id);

            for (Campo campo : campos) {
                structSimbolo.agregarCampo(campo.getNombre(), new Simbolo(new Tipo(TipoInstruccion.STRUCT), this.id));
            }
            ts.setVariable(structSimbolo);

            //ver el struct creado y sus campos asignados
            System.out.println("STRUCT: "+id);
            for (Campo campo : campos) {
                System.out.println("Campo: "+campo.getNombre()+" Tipo: "+campo.getTipo());
            }

            return null;
        
        }catch(Exception e){
            System.out.println("SEMANTICO: Error en la declaracion de estructura: "+e);
            Errores.errores.add(new Errores("Semantico", "Error en la declaracion de estructura: "+e, fila, columna));
            return new Errores("Semantico", "Error en la declaracion de estructura: "+e, fila, columna);
        }      
    }
    
}
