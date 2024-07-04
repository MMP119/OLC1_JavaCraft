package entorno;

import java.util.HashMap;
import java.util.Map;

import funciones.DecStruct;

public class Simbolo {
    private Tipo tipo;
    private String id;
    private Object valor;
    private int fila, columna;
    private String mutabilidad;
    private HashMap<String, Simbolo> campos;
    private DecStruct structDef; //para la definicion de structs anidados

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

    public void setStructDef(DecStruct structDef) {
        this.structDef = structDef;
    }

    public DecStruct getStructDef() {
        return structDef;
    }

    public boolean esStruct() {
        return structDef != null;
    }

    public Simbolo getCampo(String id) {
        if(esStruct() && campos.containsKey(id)) {
            return campos.get(id);
        }
        return null;
    }
        
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("STRUCT: ").append(id).append("\n");
        for (Map.Entry<String, Simbolo> campo : campos.entrySet()) {
            sb.append("Campo: ").append(campo.getKey()).append(" Tipo: ");
            if (campo.getValue().esStruct()) {
                sb.append("STRUCT\n").append(campo.getValue().toString());
            } else {
                sb.append(campo.getValue().getTipo().toString()).append("\n");
                sb.append("Valor: ").append(campo.getValue().getValor()).append("\n");
            }
        }
        return sb.toString();
    }
    
}
