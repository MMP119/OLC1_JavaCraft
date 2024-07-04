package funciones;

import AST.NodoAst;
import entorno.Entorno;
import expresiones.TipoDato;
import entorno.tablaSimbolos;
import excepciones.Errores;
import expresiones.Expresion;
import entorno.Simbolo;

public class RemoveElementoLista extends Expresion {

    private String id;
    private Expresion indice;
    private int fila, columna;

    public RemoveElementoLista(String id, Expresion indice, int fila, int columna) {
        super("ERROR_REMOVE_LISTA", TipoDato.ERROR, fila, columna);
        this.id = id;
        this.indice = indice;
        this.fila = fila;
        this.columna = columna;
    }

    public NodoAst getNodo() {
        NodoAst nodo = new NodoAst("REMOVE_ELEMENTO_LISTA");
        nodo.agregarHijo(id);
        nodo.agregarHijo(".");
        nodo.agregarHijo("remove");
        nodo.agregarHijo("(");
        nodo.agregarHijoAST(indice.getNodo());
        nodo.agregarHijo(")");
        nodo.agregarHijo(";");
        return nodo;
    }



    @Override
    public Object interpretar(Entorno ent, tablaSimbolos ts) {
        try {
            Simbolo sim = ts.getVariable(id);

            if (sim == null) {
                System.out.println("La lista " + id + " no existe");
                Errores.errores.add(new Errores("Semantico", "La lista " + id + " no existe", fila, columna));
                return new Errores("Semantico", "La lista " + id + " no existe", fila, columna);
            }

            if (!(sim.getValor() instanceof DatoLista)) {
                System.out.println("El simbolo: " + id + " no es una lista");
                Errores.errores.add(new Errores("Semantico", "El simbolo: " + id + " no es una lista", fila, columna));
                return new Errores("Semantico", "El simbolo: " + id + " no es una lista", fila, columna);
            }

            DatoLista lista = (DatoLista) sim.getValor();
            Object indiceValor = indice.interpretar(ent, ts);

            int index;

            try {
                index = Integer.parseInt(indiceValor.toString());
            } catch (Exception e) {
                System.out.println("El indice no es un entero");
                Errores.errores.add(new Errores("Semantico", "El indice no es un entero", fila, columna));
                return new Errores("Semantico", "El indice no es un entero", fila, columna);
            }

            if (index < 0 || index >= lista.getElementos().size()) {
                System.out.println("Indice fuera de rango");
                Errores.errores.add(new Errores("Semantico", "Indice fuera de rango", fila, columna));
                return new Errores("Semantico", "Indice fuera de rango", fila, columna);
            }

            // Eliminar el elemento de la lista
            Object valorEliminado = lista.remove(index);

            // Actualizar el valor en el símbolo
            sim.setValor(lista);

            return valorEliminado;

        } catch (Exception e) {
            System.out.println("Error al eliminar elemento de la lista " + id);
            Errores.errores.add(new Errores("Semantico", "Error al eliminar elemento de la lista " + id, fila, columna));
            return new Errores("Semantico", "Error al eliminar elemento de la lista " + id, fila, columna);
        }
    }


}

