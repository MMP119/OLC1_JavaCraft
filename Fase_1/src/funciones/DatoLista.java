package funciones;

import java.util.LinkedList;

import expresiones.TipoDato;

public class DatoLista {
    private LinkedList<Object> elementos;
    private TipoDato tipo;

    public DatoLista(TipoDato tipo) {
        this.tipo = tipo;
        this.elementos = new LinkedList<>();
    }

    public DatoLista(LinkedList<Object> elementos, TipoDato tipo) {
        this.elementos = elementos;
        this.tipo = tipo;
    }

    public void agregar(Object valor) {
        elementos.add(valor);
    }

    public Object remove(int index) {
        return elementos.remove(index);
    }

    public Object obtener(int index) {
        return elementos.get(index);
    }

    public void establecer(int index, Object valor) {
        elementos.set(index, valor);
    }

    public int tamano() {
        return elementos.size();
    }

    public LinkedList<Object> getElementos() {
        return elementos;
    }

    public TipoDato getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return elementos.toString();
    }
}

