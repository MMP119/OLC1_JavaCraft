package funciones;

import expresiones.Expresion;
import expresiones.TipoDato;
import instruccion.Instruccion;
import instruccion.TipoInstruccion;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;

import java.util.LinkedList;

import AST.NodoAst;

public class DoWhile extends Instruccion {
    private LinkedList<Instruccion> instrucciones;
    private Expresion condicion;
    
    public DoWhile(Expresion condicion, LinkedList<Instruccion> instrucciones, int linea, int columna){
        super(new Tipo(TipoInstruccion.DOWHILE), linea, columna);
        this.instrucciones = instrucciones;
        this.condicion = condicion;
    }

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("DO_WHILE");
        nodo.agregarHijo("do");
        for(var instruccion : this.instrucciones){
            nodo.agregarHijoAST(instruccion.getNodo());
        }
        nodo.agregarHijo("while");
        nodo.agregarHijo("(");
        nodo.agregarHijoAST(this.condicion.getNodo());
        nodo.agregarHijo(")");
        nodo.agregarHijo(";");
        return nodo;
    }
    
    @Override
    public Object interpretar(Entorno arbol, tablaSimbolos tabla){
        var condicional = this.condicion.interpretar(arbol, tabla);
        //tablaSimbolos local = new tablaSimbolos(tabla);
        
        if (condicional instanceof Errores){
            return condicional;
        }
        
        //Verificar la condicion de DO-While 
        if(this.condicion.getTipo() != TipoDato.BOOLEAN){
            return  new Errores("SEMANTIO", "La expresion a evaluar no es Booleano", 1, 1);
        }
        

        do{
            tablaSimbolos newTabla = new tablaSimbolos(tabla);
            newTabla.setNombre("do-while");
            tablaSimbolos.tablas.add(newTabla);
            Instruccion.cicloProfundida++;
            
            for(var instruccion : this.instrucciones){
                var a = instruccion.interpretar(arbol, newTabla);

                if(a instanceof  Errores){
                    return  instruccion;
                }

                if(a instanceof Break){
                    return  null;
                }

                if(a instanceof Continue){
                    break;
                }

                if(a instanceof Return){
                    return  a;
                }
            }
            
            condicional = this.condicion.interpretar(arbol, newTabla);
            if(condicional instanceof  Errores){
                return  condicional;
            }
        }while(Boolean.parseBoolean(condicional.toString()) == true);
        Instruccion.cicloProfundida--;
        return  null;
    }
    
}


