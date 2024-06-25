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
            structSimbolo.setStructDef(this); //alamcenar la deficion del struct en el simbolo

            for (Campo campo : campos) {
                if (campo instanceof CampoStruct) {
                    // Recuperar la definición del struct anidado
                    Simbolo simboloAnidado = ts.getVariable(((CampoStruct) campo).getStructName());
                    if (simboloAnidado == null || !simboloAnidado.esStruct()) {
                        System.out.println("SEMANTICO: No se encontró la estructura anidada: " + ((CampoStruct) campo).getStructName());
                        Errores.errores.add(new Errores("Semantico", "No se encontró la estructura anidada: " + ((CampoStruct) campo).getStructName(), fila, columna));
                        return new Errores("Semantico", "No se encontró la estructura anidada: " + ((CampoStruct) campo).getStructName(), fila, columna);
                    }
                    ((CampoStruct) campo).setStructDef(simboloAnidado.getStructDef());
                    structSimbolo.agregarCampo(campo.getNombre(), simboloAnidado);
                } else {
                    structSimbolo.agregarCampo(campo.getNombre(), new Simbolo(new Tipo(TipoInstruccion.STRUCT), campo.getNombre()));
                }
            }
            ts.setVariable(structSimbolo);

            System.out.println(structSimbolo.toString());

            return null;
        
        }catch(Exception e){
            System.out.println("SEMANTICO: Error en la declaracion de estructura: "+e);
            Errores.errores.add(new Errores("Semantico", "Error en la declaracion de estructura: "+e, fila, columna));
            return new Errores("Semantico", "Error en la declaracion de estructura: "+e, fila, columna);
        }      
    }
    
}
