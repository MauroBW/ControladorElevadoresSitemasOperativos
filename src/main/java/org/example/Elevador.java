package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Elevador extends Thread{
    public int pisoActual;
    public List<Pasajero> pasajeros;
    public String nombreElevador;
    private final int CAPACIDAD =  1;


    public Elevador(String nombreElevador) {
        this.nombreElevador = nombreElevador;
        this.pasajeros = new ArrayList<>();
        this.pisoActual = 0;
    }

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

    public boolean estaDisponible() {
        return pasajeros.size() < CAPACIDAD;
    }

    private void subir() throws InterruptedException {
        this.pisoActual++;
        Thread.sleep(500);
    }

    private void bajar() throws InterruptedException {
        this.pisoActual--;
        Thread.sleep(500);
    }

    private int distanciaACliente(Pasajero pasajero) {
        return (this.pisoActual - pasajero.getPisoActual());
    }

    private void irACliente(Pasajero pasajero) throws InterruptedException {

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

    @Override
    public void run() {
        System.out.println("Enciendo Elevador");
        while(true) {
            if (pasajeros.size() != 0) {
                int diferenciaPisos = this.pisoActual - pasajeros.get(0).getPisoObjetivo();
                if (diferenciaPisos < 0) {
                    try {
                        subir();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else if (diferenciaPisos > 0) {
                    try {
                        bajar();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("\nAbrir Puertas\n");
                    bajarPasajeros();
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
