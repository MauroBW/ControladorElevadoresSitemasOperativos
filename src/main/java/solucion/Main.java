package solucion;

import solucion.Entidades.Elevador;
import solucion.Entidades.Pasajero;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {

        Queue<Pasajero> listaPasajeros = new LinkedList<>();

        listaPasajeros.add(new Pasajero("P1", 1,6));
        listaPasajeros.add(new Pasajero("P2", 4,6));
        listaPasajeros.add(new Pasajero("P3", 0,6));

        // Despierto pasajeros
        for (Pasajero pasajero : listaPasajeros) {
            pasajero.start();
        }

        (new Elevador(0, "##1", listaPasajeros)).start();
        (new Elevador(0, "##2", listaPasajeros)).start();

    }
}
