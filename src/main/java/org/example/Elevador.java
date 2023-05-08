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

    @Override
    public void run() {
        System.out.println("Enciendo Elevador");
        while(true) {
            if (pasajeros.size() != 0) {
                Pasajero pasajeroActual = pasajeros.get(0);
                pasajeroActual.increment();

                System.out.println("Desplazamiento hasta el cliente");
                irACliente(pasajeroActual);

                System.out.println("Comienza viaje");
                irADestino(pasajeroActual);
            }
        }
    }

    // Acciones con pasajeros
    public void subirPasajero(Pasajero pasajero) throws InterruptedException {
//        irACliente(pasajero);
//        System.out.printf("Elevador <%s>: Abriendo Puertas\n", this.nombreElevador);
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
            System.out.println("Distancia a Cliente " + distanciaACliente(pasajero));
            if (distanciaACliente(pasajero) > 0) {
                bajar();
            }
            else {
                subir();
            }
        }
        System.out.println(Thread.currentThread().getName()); // Quiero saber si este wait se hace en el hilo main o en elevator
        System.out.printf("!!!ELEVADOR EN EL MISMO PISO QUE OBJETIVO!!!\n");
    }

    private void irADestino(Pasajero pasajero) {
//        int distanciaObjetivo = ;

        while (distanciaAObjetivo(pasajero) != 0) {
            pasajero.increment();
            System.out.println("Yendo a Destino");
            if (distanciaAObjetivo(pasajero) < 0) { subir(); }
            else { bajar(); }
        }
        System.out.println("Llegue a destino");
        bajarPasajeros();
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

    /**
     * Calcula la distancia del elevador hasta el pasajero que lo solicitó
     * @param pasajero: Pasajero que solicita el elevador
     * @return: Distancia de pisos
     *     (Numero negativo indica que el pasajero está en un piso superior)
     *     (Numero positivo indica que el pasajero está en un piso inferior)
     */
    private int distanciaACliente(Pasajero pasajero) {
        return (this.pisoActual - pasajero.getPisoActual());
    }

    /**
     * Calcula la distancia del elevador hasta el piso objetivo del pasajero dentro del elevador
     * @param pasajero: Pasajero dentro del elevador
     * @return: Distancia de pisos
     *      (Numero positivo indica que el pisoObjetivo está hacia abajo)
     *      (Numero negativo indica que el pisoObjetivo está hacia arriba)
     */
    private int distanciaAObjetivo(Pasajero pasajero) {
        return (this.pisoActual - pasajero.getPisoObjetivo());
    }
}
