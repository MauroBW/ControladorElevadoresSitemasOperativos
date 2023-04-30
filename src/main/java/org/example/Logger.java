package org.example;

import java.util.ArrayDeque;

public class Logger {

    public void logPasajeros(ArrayDeque<Pasajero> pasajeros) {
        System.out.println("===========================================================");
        System.out.println("                         PASAJEROS                         ");
        System.out.println("===========================================================");
        for(Pasajero aux: pasajeros) {
            System.out.printf("Pasajero: %s | Piso: %s | Cronometro: %s \n", aux.getNombre(), aux.getPisoActual(), aux.getWaitTime());
        }
        System.out.println("===========================================================");
    }

    public void logElevador(Elevador elevador) {
        System.out.println("==========================================================");
        System.out.println("                         ELEVADOR                         ");
        System.out.println("==========================================================");
        System.out.printf("\n<%s> | Piso actual: %s \n",
                elevador.nombreElevador,
                elevador.pisoActual);

        for (Pasajero aux: elevador.pasajeros) {
            System.out.printf("Pasajero: %s | Estado: %s\n",
                    aux.getNombre(),
                    aux.getEstado());
        }

        System.out.println("==========================================================");
    }
}
