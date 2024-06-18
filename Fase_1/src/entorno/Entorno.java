package entorno;

import java.util.LinkedList;

import excepciones.Errores;
import instruccion.Instruccion;


public class Entorno {

    private LinkedList<Instruccion> instrucciones;
    private String consola;
    private tablaSimbolos tablaGlobal;
    private LinkedList<Errores> errores;

    public Entorno(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        this.consola = "";
        this.tablaGlobal = new tablaSimbolos();
        this.errores = new LinkedList<>();
    }

    // Constructor de copia
    public Entorno(Entorno otro) {
        this.instrucciones = otro.instrucciones;
        this.consola = otro.consola;
        this.tablaGlobal = otro.tablaGlobal;
        this.errores = otro.errores;
    }

    // Constructor de copia con tabla de símbolos
    public Entorno(Entorno otro, tablaSimbolos tablaSimbolos) {
        this.instrucciones = otro.instrucciones;
        this.consola = otro.consola;
        this.tablaGlobal = tablaSimbolos;
        this.errores = otro.errores;
    }

    public LinkedList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getConsola() {
        return consola;
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }

    public tablaSimbolos getTablaGlobal() {
        return tablaGlobal;
    }

    public void setTablaGlobal(tablaSimbolos tablaGlobal) {
        this.tablaGlobal = tablaGlobal;
    }

    public LinkedList<Errores> getErrores() {
        return errores;
    }

    public void setErrores(LinkedList<Errores> errores) {
        this.errores = errores;
    }

    public void Print(String mensaje){
        this.consola += mensaje + "\n";
    }

}
