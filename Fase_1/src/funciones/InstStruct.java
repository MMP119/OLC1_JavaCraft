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

public class InstStruct extends Instruccion {

    private String mutabilidad;
    private String id;
    private String structId;
    private LinkedList<ValorStruct> valores;
    private int fila, columna;

    public InstStruct(String mutabilidad, String id, String structId, LinkedList<ValorStruct> valores, int fila, int columna) {
        super(new Tipo(TipoInstruccion.INSTSTRUCT), fila, columna);
        this.mutabilidad = mutabilidad;
        this.id = id;
        this.structId = structId;
        this.valores = valores;
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("INSTSTRUCT");
        nodo.agregarHijo(mutabilidad);
        nodo.agregarHijo(id + " : " + structId);
        nodo.agregarHijo("= {");
        for (ValorStruct valor : valores) {
            nodo.agregarHijo(valor.getId() + " : " + valor.getValor());
        }
        nodo.agregarHijo("}");
        return nodo;
    }

    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        try {
            // Verificar si existe una estructura con el nombre dado
            Simbolo structSimbolo = ts.getVariable(structId);

            if (structSimbolo == null || !structSimbolo.esStruct()) {
                System.out.println("SEMANTICO: No se encontró la estructura: " + structId);
                Errores.errores.add(new Errores("Semantico", "No se encontró la estructura: " + structId, fila, columna));
                return new Errores("Semantico", "No se encontró la estructura: " + structId, fila, columna);
            }

            // Crear un nuevo símbolo para la instancia del struct
            Simbolo instSimbolo = new Simbolo(new Tipo(TipoInstruccion.STRUCT), id);
            instSimbolo.setStructDef(structSimbolo.getStructDef());
            instSimbolo.setMutabilidad(mutabilidad);

            // Asignar los valores a los campos del struct
            for (ValorStruct valor : valores) {
                if (!structSimbolo.getCampos().containsKey(valor.getId())) {
                    System.out.println("SEMANTICO: La estructura " + structId + " no tiene el campo: " + valor.getId());
                    Errores.errores.add(new Errores("Semantico", "La estructura " + structId + " no tiene el campo: " + valor.getId(), fila, columna));
                    return new Errores("Semantico", "La estructura " + structId + " no tiene el campo: " + valor.getId(), fila, columna);
                }
                instSimbolo.agregarCampo(valor.getId(), new Simbolo(structSimbolo.getCampos().get(valor.getId()).getTipo(), valor.getId(), valor.getValor().interpretar(ent, ts)));
            }

            //verificar si ya existe una variable con el mismo nombre
            if (ts.getVariable(id) != null) {
                System.out.println("SEMANTICO: el ID: " + id + " ya ha sido declarado");
                Errores.errores.add(new Errores("Semantico", "el ID: " + id + " ya ha sido declarado", fila, columna));
                return new Errores("Semantico", "el ID: " + id + " ya ha sido declarado", fila, columna);
            }

            // Añadir la instancia del struct a la tabla de símbolos
            ts.setVariable(instSimbolo);

            // Imprimir la instancia del struct
            System.out.println("INSTSTRUCT: " + id);
            System.out.println(instSimbolo.toString());

            return null;

        } catch (Exception e) {
            System.out.println("SEMANTICO: Error en la instanciación de estructura: " + e);
            Errores.errores.add(new Errores("Semantico", "Error en la instanciación de estructura: " + e, fila, columna));
            return new Errores("Semantico", "Error en la instanciación de estructura: " + e, fila, columna);
        }
    }
}
