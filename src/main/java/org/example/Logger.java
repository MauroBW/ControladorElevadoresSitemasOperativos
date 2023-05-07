package org.example;

import java.util.ArrayDeque;

public class Logger {

    public void logPasajeros(ArrayDeque<Pasajero> pasajeros) {
        System.out.println("=========================================================");
        System.out.println("              Lista de Pasajeros por Atender             ");
        System.out.println("=========================================================");
        for(Pasajero aux: pasajeros) {
            aux.getInfo();
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

    public void spaces(){
        System.out.println("\n\n\n\n");
    }
}
