package entorno;

import java.util.HashMap;

public class Simbolo {
    private Tipo tipo;
    private String id;
    private Object valor;
    private int fila, columna;
    private String mutabilidad;
    private HashMap<String, Simbolo> campos;

    public Simbolo(Tipo tipo, String id) {
        this.tipo = tipo;
        this.id = id;
        this.campos = new HashMap<>();
    }

    public Simbolo(Tipo tipo, String id, Object valor) {
        this.tipo = tipo;
        this.id = id;
        this.valor = valor;
        this.campos = new HashMap<>();
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getMutabilidad() {
        return mutabilidad;
    }

    public void setMutabilidad(String mutabilidad) {
        this.mutabilidad = mutabilidad;
    }

    public HashMap<String, Simbolo> getCampos() {
        return campos;
    }

    public void setCampos(HashMap<String, Simbolo> campos) {
        this.campos = campos;
    }

    public void agregarCampo(String id, Simbolo simbolo) {
        this.campos.put(id, simbolo);
    }

    public Simbolo obtenerCampo(String id) {
        return this.campos.get(id);
    }
        
    
}
