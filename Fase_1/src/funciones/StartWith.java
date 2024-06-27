package funciones;

import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.Expresion;
import expresiones.TipoDato;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;

public class StartWith extends Instruccion{
    
    public String id;
    public LinkedList<Expresion> parametros;
    private int fila, columna;

    public StartWith(String id, LinkedList<Expresion> parametros, int fila, int columna) {
        super(new Tipo(TipoInstruccion.VOID), fila, columna);
        this.id = id;
        this.parametros = parametros;
    }

    public NodoAst getNodo() {
        return null;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        
        //encontrar la funcion
        var buscarFuncion = ent.getFuncion(id);
        if(buscarFuncion == null){
            //error semantico
            System.out.println("Error semantico: la funcion "+id+" no existe");
            Errores.errores.add(new Errores("Semantico", "El indice no es un entero", fila, columna));
            return new Errores("Semantico", "la funcion "+id+" no existe", this.fila, this.columna);
        }

        if(buscarFuncion instanceof Metodo metodo){
            var nuevaTabla = new tablaSimbolos(ent.getTablaGlobal());
            nuevaTabla.setNombre("Start_With");
            tablaSimbolos.tablas.add(nuevaTabla);


            if(metodo.parametros.size()!=this.parametros.size()){
                System.out.println("Error semantico: la funcion "+id+" no tiene la misma cantidad de parametros");
                Errores.errores.add(new Errores("Semantico", "la funcion "+id+" no tiene la misma cantidad de parametros", this.fila, this.columna));
                return new Errores("Semantico", "la funcion "+id+" no tiene la misma cantidad de parametros", this.fila, this.columna);
            }

            
            for (int i=0; i<this.parametros.size(); i++){
                var identificador = (String)metodo.parametros.get(i).get("id");
                var valor = this.parametros.get(i);
                System.out.println("TIPO 2: "+metodo.parametros.get(i).get("tipo"));
                var tipo2 = (TipoDato)metodo.parametros.get(i).get("tipo");

                var declaracionParametro = new DecVariables("var", identificador, tipo2, valor, null, this.fila, this.columna);

                var resultado = declaracionParametro.interpretar(ent, nuevaTabla);

                if(resultado instanceof Errores){
                    return resultado;
                }

            }

            var resultadoFuncion = metodo.interpretar(ent, nuevaTabla);
            if(resultadoFuncion instanceof Errores){
                return resultadoFuncion;
            }
            
        }
        return null;

    }
    
}
