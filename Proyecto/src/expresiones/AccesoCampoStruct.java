package expresiones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Simbolo;
import entorno.tablaSimbolos;
import excepciones.Errores;


public class AccesoCampoStruct extends Expresion{

    private String idStruct;
    private String Campo;
    private int fila, columna;

    public AccesoCampoStruct(String idStruct, String Campo, int fila, int columna) {
        super("ERROR ACC CAMPO STRUCT", TipoDato.ERROR, fila, columna);
        this.idStruct = idStruct;
        this.Campo = Campo;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("ACCESO CAMPO STRUCT");
        nodo.agregarHijo(idStruct);
        nodo.agregarHijo(".");
        nodo.agregarHijo(Campo);
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts){
        try{
            
            Simbolo structSimbolo = ts.getVariable(idStruct);
            if(structSimbolo == null){
                System.out.println("No se ha encontrado la variable " + idStruct);
                Errores.errores.add(new Errores("Semantico", "No se ha encontrado la variable " + idStruct, this.getFila(), this.getColumna()));
                return new Errores("Semantico", "No se ha encontrado la variable " + idStruct, this.getFila(), this.getColumna());
            }

            if(!structSimbolo.esStruct()){
                System.out.println("La variable " + idStruct + " no es de tipo Struct");
                Errores.errores.add(new Errores("Semantico", "La variable " + idStruct + " no es de tipo Struct", this.getFila(), this.getColumna()));
                return new Errores("Semantico", "La variable " + idStruct + " no es de tipo Struct", this.getFila(), this.getColumna());
            }

            Simbolo campoSimbolo = structSimbolo.getCampo(Campo);
            if(campoSimbolo == null){
                System.out.println("No se ha encontrado el campo " + Campo + " en la variable " + idStruct);
                Errores.errores.add(new Errores("Semantico", "No se ha encontrado el campo " + Campo + " en la variable " + idStruct, this.getFila(), this.getColumna()));
                return new Errores("Semantico", "No se ha encontrado el campo " + Campo + " en la variable " + idStruct, this.getFila(), this.getColumna());
            }
            Object sal = campoSimbolo.getValor();
            Expresion salid = (Expresion) sal;
            Dato salida = new Dato(salid.getValor(), salid.getTipo(), this.fila, this.columna);
            //return campoSimbolo.getValor();
            return salida;

        }catch(Exception e){
            return new Errores("Semantico", "Error al interpretar AccesoCampoStruct", this.getFila(), this.getColumna());
        }
    }
    
}
