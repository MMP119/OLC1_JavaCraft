package funciones;

import instruccion.Instruccion;
import expresiones.Expresion;
import expresiones.TipoDato;
import instruccion.TipoInstruccion;
import java.util.LinkedList;

import AST.NodoAst;
import entorno.Entorno;
import entorno.Tipo;
import entorno.tablaSimbolos;
import excepciones.Errores;

public class If extends Instruccion{

    private Expresion condicion;
    private LinkedList<Instruccion> inst_if;
    private LinkedList<Instruccion> instr_else;
    private int fila;
    private int columna;


    public If(Expresion condicion, LinkedList<Instruccion> inst_if, LinkedList<Instruccion> instr_else, int fila, int columna) {
        super(new Tipo(TipoInstruccion.IF), fila, columna);
        this.condicion = condicion;
        this.inst_if = inst_if;
        this.instr_else = instr_else;
    }
    

    public NodoAst getNodo(){
        NodoAst nodo = new NodoAst("IF");
        nodo.agregarHijo("if");
        nodo.agregarHijo("(");
        nodo.agregarHijoAST(this.condicion.getNodo());
        nodo.agregarHijo(")");
        nodo.agregarHijo("{");
        NodoAst nodoIf = new NodoAst("IF");
        for (var a: inst_if){
            nodoIf.agregarHijoAST(a.getNodo());
        }
        nodo.agregarHijoAST(nodoIf);
        nodo.agregarHijo("}");
        return nodo;
    }


    public Object interpretar(Entorno ent, tablaSimbolos ts) {

        
        Entorno EntornoIf = new Entorno(inst_if);
        tablaSimbolos tsIf = new tablaSimbolos();
        tsIf.setTablaAnterior(ts);
        tsIf.setNombre("If");
        this.condicion = (Expresion)this.condicion.interpretar(EntornoIf, tsIf);
        

        if(this.condicion.getTipo()!= TipoDato.BOOLEAN){
            System.out.println("ERROR SEMANTICO: Se esperaba una expresion booleana en la condicion del if");
            return new Errores("Semantico", "Se esperaba una expresion booleana en la condicion del if", fila, columna);
        }else{
            //System.out.println("La condicion del if es: " + this.condicion.getValor());
        }

        if(this.condicion.getValor().toString().toLowerCase().equals("true")){
            for (var a: EntornoIf.getInstrucciones()){
                a.interpretar(EntornoIf, tsIf);
            }
            System.out.println(EntornoIf.getConsola());
        }else{
            if(instr_else != null){
                for (var a: instr_else){
                    a.interpretar(EntornoIf, tsIf);
                }
            }else{
                System.out.println("No se cumple la condicion del if y no hay un else para ejecutar");
                return new Errores("Semantico", "Se esperaba una expresion booleana en la condicion del if", fila, columna);
            }
        }
    return this;   
    }

}