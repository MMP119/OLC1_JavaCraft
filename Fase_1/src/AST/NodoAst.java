package AST;

import java.util.ArrayList;
import java.util.List;

public class NodoAst {

    private Object valor;
    private List<NodoAst> listaHijos;

    public NodoAst(Object valor) {
        this.valor = valor;
        this.listaHijos = new ArrayList<>();
    }

    public void agregarHijo(Object val) {
        NodoAst hijo = new NodoAst(val);
        this.listaHijos.add(hijo);
    }

    public void agregarHijoAST(NodoAst hijo) {
        if (hijo != null) {
            this.listaHijos.add(hijo);
        }
    }

    public Object getValor() {
        return valor;
    }

    public List<NodoAst> getListaHijos() {
        return listaHijos;
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}