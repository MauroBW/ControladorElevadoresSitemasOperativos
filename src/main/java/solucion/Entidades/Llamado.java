package solucion.Entidades;

public class Llamado {

    public String nombre;
    public int peso;
    public int pisoActual;
    public int pisoObjetivo;
    public int tiempoInicio;

    public Llamado() {

    }

    public String getNombre() {
        return nombre;
    }

    public int getPeso() {
        return peso;
    }

    public int getPisoActual() {
        return pisoActual;
    }

    public int getPisoObjetivo() {
        return pisoObjetivo;
    }

    public int getTiempoInicio() {
        return tiempoInicio;
    }
}
