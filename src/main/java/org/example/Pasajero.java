package org.example;

import java.util.List;

public class Pasajero {
    public int waitTime;
    public String nombre;
    public int pisoActual;
    public int pisoObjetivo;
    public String estado;

    public Pasajero(String nombre, int pisoActual, int pisoObjetivo) {
        this.nombre = nombre;
        this.pisoActual = pisoActual;
        this.pisoObjetivo = pisoObjetivo;
        this.estado = "Esperando";
        this.waitTime = 0;
    }

    public void increment() {
        this.waitTime += 1;
    }

    public int getWaitTime() {
        return this.waitTime;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPisoActual() {
        return pisoActual;
    }

    public int getPisoObjetivo() {
        return pisoObjetivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
