package expresiones;


import AST.NodoAst;
import entorno.Entorno;
import entorno.tablaSimbolos;
import excepciones.Errores;

public class ToString extends Expresion{

    private Expresion exp;
    private int linea, columna;

    public ToString(Expresion exp, int fila, int columna){
        super("ERROR_TOSTRING", TipoDato.TOSTRING, fila, columna);
        this.exp = exp;
        this.linea = fila;
        this.columna = columna;
    }


    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("TOSTRING");
        nodo.agregarHijo(".toString");
        nodo.agregarHijo("(");
        nodo.agregarHijoAST(exp.getNodo());
        nodo.agregarHijo(")");
        return nodo;
    }

    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        try {
            
            Object valor = exp.interpretar(ent, ts);
            Expresion tipo = (Expresion) valor;
            TipoDato tipoDato = tipo.getTipo();

            switch(tipoDato){
                case INT:
                case DOUBLE:
                case CHAR:
                case BOOLEAN:
                case STRUCT:
                    return new Dato(valor.toString(), TipoDato.CADENA, this.linea, this.columna);
                default:
                    System.out.println("ERROR SEMANTICO, Tipo de dato no valido para uso de funcion toString.");
                    Errores.errores.add(new Errores("ERROR SEMANTICO", "Tipo de dato no valido para uso de funcion toString.", this.linea, this.columna));
                    return new Errores("ERROR SEMANTICO", "Tipo de dato no valido para uso de funcion toString.", this.linea, this.columna);
            }

        } catch (Exception e) {
            System.out.println("ERROR SEMANTICO, Tipo de dato no valido para uso de funcion toString.");
            Errores.errores.add(new Errores("ERROR SEMANTICO", "Tipo de dato no valido para uso de funcion toString.", this.linea, this.columna));
            return new Errores("ERROR SEMANTICO", "Tipo de dato no valido para uso de funcion toString.", this.linea, this.columna);
        }
    }
    
}
