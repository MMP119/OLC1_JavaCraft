package funciones;


import expresiones.TipoDato;

public class CampoStruct extends Campo {
    private String structName;

    public CampoStruct(String structName, String nombre, int fila, int columna) {
        super(TipoDato.STRUCT, nombre, fila, columna);
        this.structName = structName;
    }

    public String getStructName() {
        return structName;
    }
}