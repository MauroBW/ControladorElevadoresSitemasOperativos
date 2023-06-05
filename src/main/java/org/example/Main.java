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

        Elevador elevador = new Elevador("Elevator_#1");
        ElevadorNotificaciones test = new ElevadorNotificaciones("test");
        test.start();

        ArrayDeque<Pasajero> pasajeros = new ArrayDeque<>();
        pasajeros.add(p1);
        pasajeros.add(p2);
        pasajeros.add(p3);

//        logger.logPasajeros(pasajeros);
        elevador.start();

        System.out.println("Cantidad de hilos antes de iniciar simulacion: " + Thread.activeCount() );

        while (true) {
            for(Pasajero pasajero: pasajeros) {
                pasajero.increment();

                if (pasajero.waitTime > 1) {
                    if (elevador.estaDisponible()){
                        elevador.subirPasajero(pasajeros.pop()); // Esta llamada se tiene que hacer dentro del run, sino será ejecutada en el main thread
                    }
                }
            }



            if (pasajeros.size() != 0){
                logger.logPasajeros(pasajeros);

            } else if (pasajeros.size() == 0 && elevador.pasajeros.size() == 0){
                System.out.println("No hay nuevos pedidos");
                elevador.irAPlantaBaja();
            }
//            logger.saveLog(logger.savePasajerosData(pasajeros));
//            logger.saveLog(logger.saveElevadorData(elevador));

            logger.logElevador(elevador);
            logger.spaces();
            Thread.sleep(1000);
        }
    }
}