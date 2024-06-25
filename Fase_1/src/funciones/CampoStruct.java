package funciones;


import expresiones.TipoDato;

public class CampoStruct extends Campo {
    private String structName;
    private DecStruct structDef;

    public CampoStruct(String structName, String nombre, int fila, int columna) {
        super(TipoDato.STRUCT, nombre, fila, columna);
        this.structName = structName;
    }

    public String getStructName() {
        return structName;
    }

    public DecStruct getStructDef() {
        return structDef;
    }

    public void setStructDef(DecStruct structDef) {
        this.structDef = structDef;
    }

}