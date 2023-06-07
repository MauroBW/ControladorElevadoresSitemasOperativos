package solucion.Entidades;

import solucion.Helpers.Logger;

public class Pasajero extends Thread {

    public int tiempo = 0;
    public String nombre;
    public int peso;
    public int pisoActual;
    public int pisoObjetivo;

    public Pasajero(String nombre, int peso, int pisoActual, int pisoObjetivo) {
        this.nombre = nombre;
        this.peso = peso;
        this.pisoActual = pisoActual;
        this.pisoObjetivo = pisoObjetivo;
        this.setName(nombre);
    }

    public Pasajero() {

    }

    @Override
    public void run() {
        while (true) {
            tick();

            if (getPisoActual() == getPisoObjetivo()) {
                System.out.println("Me Bajo!!");
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) { }
            Logger.saveLog("##pasajeros_Log.txt", String.format("{ Nombre: %s Tiempo: %s || PisoObjetivo: %s PisoActual: %s }\n",
                    getNombre(), getTiempo(), getPisoObjetivo(), getPisoActual()));
        }
    }

    public void tick() {
        this.tiempo += 1;
    }

    public int getPeso() {
        return peso;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int getPisoObjetivo() {
        return pisoObjetivo;
    }

    public int getPisoActual() {
        return pisoActual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setPisoActual(int pisoActual) {
        this.pisoActual = pisoActual;
    }
}
