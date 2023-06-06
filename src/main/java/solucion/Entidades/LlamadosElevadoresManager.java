package solucion.Entidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class LlamadosElevadoresManager {
    private static List<Pasajero> listaPasajeros = new LinkedList<>();

    public LlamadosElevadoresManager() {
    }

    public static void agregarPasajero(Pasajero pasajero) {
        listaPasajeros.add(pasajero);
    }

    public static void agregarPasajero(String nombre, int peso, int pisoOrigen, int pisoDestino) {
        Pasajero pasajero = new Pasajero(nombre, peso, pisoOrigen, pisoDestino);
        /*

            Acá se va a generar la lógica al agregar el pasajero.
            A su vez esta clase debe controlar o saber el tiempo, ya que a cada cambio de tiempo se debe reordenar la lista.

         */
        listaPasajeros.add(pasajero);
    }

    public static void iniciarElevadores() {
        for (Pasajero pasajero : listaPasajeros) {
            pasajero.start();
        }

        Elevador elevador1 = new Elevador(1, "##1", listaPasajeros);
        elevador1.start();

        Elevador elevador2 = new Elevador(0, "##2", listaPasajeros);
        elevador2.start();
    }
}