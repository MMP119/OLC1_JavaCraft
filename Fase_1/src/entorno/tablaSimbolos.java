package entorno;

import java.util.HashMap;


public class tablaSimbolos {

    private tablaSimbolos tablaAnterior;
    private HashMap<String, Object> tablaActual;
    private String nombre;

    public tablaSimbolos() {
        this.tablaActual = new HashMap<>();
        this.nombre = "";
    }

    public tablaSimbolos(tablaSimbolos tablaAnterior) {
        this.tablaAnterior = tablaAnterior;
        this.tablaActual = new HashMap<>();
        this.nombre = "";
    }

    public tablaSimbolos getTablaAnterior() {
        return tablaAnterior;
    }

    public void setTablaAnterior(tablaSimbolos tablaAnterior) {
        this.tablaAnterior = tablaAnterior;
    }

    public HashMap<String, Object> getTablaActual() {
        return tablaActual;
    }

    public void setTablaActual(HashMap<String, Object> tablaActual) {
        this.tablaActual = tablaActual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean setVariable(Simbolo simbolo) {
        Simbolo busqueda
                = (Simbolo) this.tablaActual.get(
                        simbolo.getId().
                                toLowerCase());
        if (busqueda == null) {
            this.tablaActual.put(simbolo.getId().toLowerCase(),
                    simbolo);
            return true;
        }
        return false;
    }

    public Simbolo getVariable(String id) {
        for (tablaSimbolos i = this; i != null; i = i.getTablaAnterior()) {
            Object valor = i.tablaActual.get(id.toLowerCase());
            if (valor != null) {
                if (valor instanceof Simbolo) {
                    return (Simbolo) valor;
                } else {
                    // Manejar el caso donde el tipo no es el esperado
                    throw new ClassCastException("El valor encontrado para la clave '" + id + "' no es de tipo Simbolo");
                }
            }
        }
        return null;
    }
    

}
