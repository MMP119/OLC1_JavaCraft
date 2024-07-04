package funciones;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Simbolo;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.Dato;
import expresiones.Expresion;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class AsignacionCampoStruct extends Instruccion{

    private String idStruct;
    private String campo;
    private Expresion valor;
    private int fila, columna;

    public AsignacionCampoStruct(String idStruct, String campo, Expresion valor, int fila, int columna) {
        super(new Tipo(TipoInstruccion.ASIG_STRUCT), fila, columna);
        this.idStruct = idStruct;
        this.campo = campo;
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
    }
    

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("Asig_Structs");
        nodo.agregarHijo(idStruct);
        nodo.agregarHijo(".");
        nodo.agregarHijo(campo);
        nodo.agregarHijo("=");
        nodo.agregarHijoAST(valor.getNodo());
        nodo.agregarHijo(";");
        return nodo;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts){
        try{

            Simbolo structSimbolo = ts.getVariable(idStruct);

            if(structSimbolo == null){
                System.out.println("No se ha encontrado el struct " + idStruct);
                Errores.errores.add(new Errores("Semantico", "No se ha encontrado el struct " + idStruct, this.fila, this.columna));
                return new Errores("Semantico", "No se ha encontrado el struct " + idStruct, this.fila, this.columna);
            }

            if(!structSimbolo.esStruct()){
                System.out.println("La variable " + idStruct + " no es de tipo Struct");
                Errores.errores.add(new Errores("Semantico", "La variable " + idStruct + " no es de tipo Struct", this.fila, this.columna));
                return new Errores("Semantico", "La variable " + idStruct + " no es de tipo Struct", this.fila, this.columna);
            }

            if(structSimbolo.getMutabilidad().equals("const")){
                System.out.println("No se puede modificar el struct " + idStruct + " porque es constante");
                Errores.errores.add(new Errores("Semantico", "No se puede modificar el struct " + idStruct + " porque es constante", this.fila, this.columna));
                return new Errores("Semantico", "No se puede modificar el struct " + idStruct + " porque es constante", this.fila, this.columna);
            }


            Simbolo campoSimbolo = structSimbolo.getCampo(campo);
            if(campoSimbolo == null){
                System.out.println("No se ha encontrado el campo " + campo + " en el struct " + idStruct);
                Errores.errores.add(new Errores("Semantico", "No se ha encontrado el campo " + campo + " en el struct " + idStruct, this.fila, this.columna));
                return new Errores("Semantico", "No se ha encontrado el campo " + campo + " en el struct " + idStruct, this.fila, this.columna);
            }

            Object val = valor.interpretar(ent, ts);
            Expresion valExp = (Expresion) val;

            Dato salida = new Dato(valExp.getValor(), valExp.getTipo(), this.fila, this.columna);
            campoSimbolo.setValor(salida);          
            
            return null;

        }catch(Exception e){
            System.out.println("Error en la asignacion de campo de struct");
            Errores.errores.add(new Errores("Semantico", "Error en la asignacion de campo de struct", this.fila, this.columna));
            return new Errores("Semantico", "Error en la asignacion de campo de struct", this.fila, this.columna);
        }
    }

}
