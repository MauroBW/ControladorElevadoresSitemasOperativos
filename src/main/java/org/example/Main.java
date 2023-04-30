package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Logger logger = new Logger();
        Pasajero p1 = new Pasajero("Mauro", 0, 5);
        Pasajero p2 = new Pasajero("Facundo", 0, 6);
        Pasajero p3 = new Pasajero("Chelo", 0, 4);

        Elevador elevador = new Elevador("Elevador 1");

        ArrayDeque<Pasajero> pasajeros = new ArrayDeque<>();
        pasajeros.add(p1);
        pasajeros.add(p2);
        pasajeros.add(p3);

        logger.logPasajeros(pasajeros);
        logger.logElevador(elevador);
        elevador.start();

        while (true) {
            for(Pasajero pasajero: pasajeros) {
                pasajero.increment();

                if (pasajero.waitTime > 1) {
                    if (elevador.estaDisponible()){
                        elevador.subirPasajero(pasajeros.pop());
                    }
                }
            }

            Thread.sleep(2000);
            logger.logPasajeros(pasajeros);
            logger.logElevador(elevador);
        }
    }
}