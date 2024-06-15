package excepciones;

import java.util.ArrayList;


public class Errores {
    private String nombre;
    private String desc;
    private int linea;
    private int columna;
    public static ArrayList<Errores> errores = new ArrayList<>();

    public Errores(String nombre, String desc, int linea, int columna) {
        this.nombre = nombre;
        this.desc = desc;
        this.linea = linea;
        this.columna = columna;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String Nombre) {
        this.nombre = Nombre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public static ArrayList<Errores> getErrores() {
        return errores;
    }

    @Override
    public String toString() {
        return "Errores{" + "tipo=" + nombre + ", desc=" + desc + ", linea=" + linea + ", columna=" + columna + '}';
    }
}