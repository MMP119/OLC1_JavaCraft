package expresiones;

import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.tablaSimbolos;
import excepciones.Errores;
import funciones.DecVariables;
import funciones.Metodo;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import entorno.Tipo;

public class Llamada extends Instruccion {

    private String id;
    private LinkedList<Expresion> parametros;
    private int fila, columna;

    public Llamada(String id, LinkedList<Expresion> parametros, int fila, int columna) {
        super(new Tipo(TipoInstruccion.LLAMADA), fila, columna);
        this.id = id;
        this.parametros = parametros;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("LLAMADA");
        nodo.agregarHijo(id);
        nodo.agregarHijo("(");
        for (Expresion expresion : parametros) {
            nodo.agregarHijoAST(expresion.getNodo());
        }
        nodo.agregarHijo(")");
        return nodo;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts){

        try{

        var busqueda = ent.getFuncion(this.id);

        if(busqueda == null){
            System.out.println("La funcion "+id+" no existe en este ambito");
            Errores.errores.add(new Errores("Semantico", "La funcion "+id+" no existe en este ambito", fila, columna));
            return new Errores("Semantico", "La funcion "+id+" no existe en este ambito", fila, columna);
        }

        if(busqueda instanceof Metodo metodo){
            
            var newTabla = new tablaSimbolos(ent.getTablaGlobal());
            newTabla.setNombre("LLAMADA MÉTODO: "+this.id);
            tablaSimbolos.tablas.add(newTabla);

            if(metodo.parametros.size() != this.parametros.size()){
                System.out.println("La cantidad de parametros no coincide con la cantidad de parametros de la funcion "+id);
                Errores.errores.add(new Errores("Semantico", "La cantidad de parametros no coincide con la cantidad de parametros de la funcion "+id, fila, columna));
                return new Errores("Semantico", "La cantidad de parametros no coincide con la cantidad de parametros de la funcion "+id, fila, columna);
            }

            for(int i = 0; i<this.parametros.size(); i++){
                
                var identificador = (String)metodo.parametros.get(i).get("id");
                var valor = this.parametros.get(i);
                var tipo2 = (TipoDato)metodo.parametros.get(i).get("tipo");

                var declaracionParametro = new DecVariables("var", identificador, tipo2, null, null, this.fila, this.columna);

                var resultado = declaracionParametro.interpretar(ent, newTabla);

                if(resultado instanceof Errores){
                    return resultado;
                }

                var valorInterpretado = valor.interpretar(ent, ts);
                if(valorInterpretado instanceof Errores){
                    return valorInterpretado;
                }

                var variable1 = newTabla.getVariable(identificador);
                Expresion variable = (Expresion)variable1.getValor(); 

                if(variable ==null){
                    System.out.println("Error semantico: la variable "+identificador+" no existe en la tabla de simbolos");
                    Errores.errores.add(new Errores("Semantico", "La variable "+identificador+" no existe en la tabla de simbolos", fila, columna));
                    return new Errores("Semantico", "La variable "+identificador+" no existe en la tabla de simbolos", fila, columna);
                }

                Expresion var = (Expresion)valor.interpretar(ent, ts);

                //System.out.println("variable.getTipo() "+variable.getTipo()+" valor.getTipo() "+var.getTipo());

                if(variable.getTipo() != var.getTipo()){
                    System.out.println("Error semantico: el tipo de la variable "+identificador+" no coincide con el tipo de la expresion en parametro");
                    Errores.errores.add(new Errores("Semantico", "El tipo de la variable "+identificador+" no coincide con el tipo de la expresion en parametro", fila, columna));
                    return new Errores("Semantico", "El tipo de la variable "+identificador+" no coincide con el tipo de la expresion en parametro", fila, columna);
                }

                variable1.setValor(valorInterpretado);
            }

            var resultadoFuncion = metodo.interpretar(ent, newTabla);
            if(resultadoFuncion instanceof Errores){
                return resultadoFuncion;
            }



        }

        return null;

    }catch(Exception e){
        System.out.println("Error en la llamada a la funcion "+this.id);
        Errores.errores.add(new Errores("Semantico", "Error en la llamada a la funcion "+this.id, fila, columna));
        return new Errores("Semantico", "Error en la llamada a la funcion "+this.id, fila, columna);
    }
    }

}
