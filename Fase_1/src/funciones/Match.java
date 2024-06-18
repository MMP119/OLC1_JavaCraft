package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import expresiones.Expresion;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import java.util.LinkedList;
import excepciones.Errores;

public class Match extends Instruccion {

    private Expresion exp;
    private LinkedList<Casos> casos; // Cambiamos a un solo objeto Instruccion que representa todos los casos
    private int fila, columna;

    public Match(Expresion exp, LinkedList<Casos> casos, int fila, int columna) {
        super(new Tipo(TipoInstruccion.MATCH), fila, columna);
        this.exp = exp;
        this.casos = casos;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("MATCH");
        nodo.agregarHijoAST(this.exp.getNodo());
        for (int i = 0; i < casos.size(); i++) {
            Casos caso = casos.get(i);
            nodo.agregarHijoAST((caso.getNodo()));
        }
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        try{

            Expresion valorExp = (Expresion) this.exp.interpretar(ent, ts);

            for(Casos caso : casos){
                caso.setMatch(valorExp);
                Object resultado = caso.interpretar(ent, ts);
                if(resultado != null){
                    return resultado;
                }
            }
            return null;
        }catch(Exception e){
            Errores.errores.add(new Errores("Error Semantico", "Error en Match: "+e.getMessage(), fila, columna));
            System.out.println("Error en Match: "+e.getMessage());
            return null;
        }

        
    }
}