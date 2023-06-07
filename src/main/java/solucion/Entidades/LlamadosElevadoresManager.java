package solucion.Entidades;

import org.springframework.boot.actuate.endpoint.web.Link;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Semaphore;

public class LlamadosElevadoresManager {
    private static List<Pasajero> listaPasajeros = new LinkedList<>();
    private static List<Pasajero> storage = new LinkedList<>();

    public LlamadosElevadoresManager() {
    }

    public static void agregarPasajero(Pasajero pasajero) {
        pasajero.start();
        storage.add(pasajero);
    }

    public static void agregarPasajero(String nombre, int peso, int pisoOrigen, int pisoDestino, int tiempoInicio) {
        Pasajero pasajero = new Pasajero(nombre, peso, pisoOrigen, pisoDestino, tiempoInicio);
        /*

            Acá se va a generar la lógica al agregar el pasajero.
            A su vez esta clase debe controlar o saber el tiempo, ya que a cada cambio de tiempo se debe reordenar la lista.

         */
        storage.add(pasajero);
    }

    public static void updateListaPedidos(int tiempoUpdate) {
        List<Pasajero> pasajerosAsignados = new LinkedList<>();
        for (Pasajero pasajero : storage) {
            if(pasajero.getTiempoInicio() == tiempoUpdate) {
                pasajerosAsignados.add(pasajero);
                listaPasajeros.add(pasajero);
            }
        }
        storage.removeAll(pasajerosAsignados);
    }

    public static void iniciarElevadores() {
        updateListaPedidos(0);
        for (Pasajero pasajero : listaPasajeros) {
            pasajero.start();
        }

        Elevador elevador1 = new Elevador(1, "##1", listaPasajeros);
        elevador1.start();

        Elevador elevador2 = new Elevador(0, "##2", listaPasajeros);
        elevador2.start();
    }
}