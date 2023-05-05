package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Elevador extends Thread{
    public int pisoActual; // Piso actual del elevador
    public List<Pasajero> pasajeros; // Lista de pasajeros dentro del elevador
    public String nombreElevador; // Nombre del elevador
    private final int CAPACIDAD =  1; // Capacidad máxima del elevador


    public Elevador(String nombreElevador) {
        this.nombreElevador = nombreElevador;
        this.pasajeros = new ArrayList<>();
        this.pisoActual = 0;
    }

    // Acciones con pasajeros
    public void subirPasajero(Pasajero pasajero) throws InterruptedException {
        irACliente(pasajero);
        System.out.printf("Elevador <%s>: Abriendo Puertas\n", this.nombreElevador);
        if (estaDisponible()) {
            this.pasajeros.add(pasajero);
            pasajero.setEstado("En Progreso");
        }
    }

    public void bajarPasajeros() {
        for (int i = 0; i < pasajeros.size(); i++) {
            if(pasajeros.get(i).pisoObjetivo == this.pisoActual){
                this.pasajeros.remove(i);
            }
        }
    }

    // Estados del elevador
    public boolean estaDisponible() {
        return pasajeros.size() < CAPACIDAD;
    }

    // Movimientos del elevador
    private void subir() {
        this.pisoActual++;
        waitTime(1000);
    }

    private void bajar() {
        this.pisoActual--;
        waitTime(1000);
    }

    public void irAPlantaBaja() {
        while (this.pisoActual != 0) {
            bajar();
        }
    }

    private void irACliente(Pasajero pasajero) {
        while (distanciaACliente(pasajero) != 0) {
            System.out.println(distanciaACliente(pasajero));
            if (distanciaACliente(pasajero) > 0) {
                bajar();
            }
            else {
                subir();
            }
        }
        System.out.printf("!!!ELEVADOR EN EL MISMO PISO QUE OBJETIVO!!!\n");
    }

    private void waitTime(int seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitTime() {
        waitTime(2000);
    }

    // Helpers
    private int distanciaACliente(Pasajero pasajero) {
        return (this.pisoActual - pasajero.getPisoActual());
    }

    @Override
    public void run() {
        System.out.println("Enciendo Elevador");
        while(true) {
            if (pasajeros.size() != 0) {
                int diferenciaPisosConObjetivo = this.pisoActual - pasajeros.get(0).getPisoObjetivo();

                if (diferenciaPisosConObjetivo < 0) {
                    subir();
                }
                else if (diferenciaPisosConObjetivo > 0) {
                    bajar();
                }
                else {
                    System.out.println("\nAbrir Puertas\n");
                    bajarPasajeros();
                }
            }
        }
    }
}
