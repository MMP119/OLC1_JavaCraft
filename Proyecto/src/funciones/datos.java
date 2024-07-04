package funciones;

import java.util.LinkedList;

public class datos {
    //definir una lista para almacenar los datos y luego poder mostrarlos en la consola
    public static LinkedList<String> datos = new LinkedList<>();

    public static void add(String dato){
        datos.add(dato);
    }

    public static void clear(){
        datos.clear();
    }

    public static void print(){
        for (String dato : datos) {
            System.out.println(dato);
        }
    }

    public static LinkedList<String> getDatos() {
        return datos;
    }

    public static void remove(String dato){
        datos.remove(dato);
    }

}
