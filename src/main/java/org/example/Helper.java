package org.example;

import java.util.ArrayDeque;
import java.util.List;

public class Helper {
    public void getInfo(ArrayDeque<Pasajero> pasajeros) {
        System.out.println("-------------------------------------------------------------------");
        for (Pasajero aux: pasajeros){
            System.out.printf("Pasajero: %s | Piso: %s | Cronometro: %s \n", aux.getNombre(), aux.getPisoActual(), aux.getWaitTime());
        }
        System.out.println("-------------------------------------------------------------------");
    }
}
