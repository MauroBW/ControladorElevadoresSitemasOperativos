package solucion.Entidades;

import org.springframework.boot.actuate.endpoint.web.Link;
import solucion.Helpers.Helper;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Semaphore;

import static solucion.Helpers.Helper.colorizer;

public class LlamadosElevadoresManager {
    private static List<Pasajero> listaPasajeros = new LinkedList<>();
    private static List<Pasajero> storage = new LinkedList<>();
    private static int tiempoDeInactividad = 0;
    private static volatile boolean detenerElevadores = false;

    public LlamadosElevadoresManager() {
    }

    public static int sizeStorage() {
        return storage.size();
    }

    public static void agregarPasajero(Pasajero pasajero) {

        storage.add(pasajero);
    }

    public static void updateListaPedidos(int tiempoUpdate) {
        List<Pasajero> pasajerosAsignados = new LinkedList<>();

        for (Pasajero pasajero : storage) {
            if (pasajero.getTiempoInicio() <= tiempoUpdate) {
                colorizer(Helper.ConsoleColor.RED, "@ Nueva Solicitud recibida @ Piso: " + pasajero.getPisoActual());
                pasajero.start(); // Lo inicio cuando hace la peticion
                pasajerosAsignados.add(pasajero);
                listaPasajeros.add(pasajero);
            }
        }
        storage.removeAll(pasajerosAsignados);

        reordenar(listaPasajeros);
    }

    private static void reordenar(List<Pasajero> listaPasajeros) {
        int n = listaPasajeros.size();

        // Recorremos todos los elementos de la lista
        for (int i = 0; i < n-1; i++) {

            // Los últimos i elementos ya están ordenados
            for (int j = 0; j < n-i-1; j++) {

                // Si el tiempo de un pasajero es menor al tiempo del siguiente pasajero
                if (listaPasajeros.get(j).getTiempo() < listaPasajeros.get(j+1).getTiempo()) {
                    // Entonces intercambiamos los pasajeros
                    Pasajero temp = listaPasajeros.get(j);
                    listaPasajeros.set(j, listaPasajeros.get(j+1));
                    listaPasajeros.set(j+1, temp);
                }
            }
        }
    }

    public static void iniciarElevadores(int cantidadElevadores) {
        updateListaPedidos(0);

        for (int i = 0; i < cantidadElevadores; i++) {
            Elevador elevadorAux = new Elevador(0, "##" + (i + 1) , listaPasajeros);
            elevadorAux.start();
        }
    }

    /**
     * Si se cumplen las siguientes condiciones se considera un tiempo inactivo de elevador
     *  - Sentido de elevador IDLE
     *  - Lista de pasajeros actuales y pendientes en storage vacias
     * @param pasajerosActuales
     * @param sentidoElevador
     */
    public static void detectarInactividad(List<Pasajero> pasajerosActuales, String sentidoElevador) {
        int sizeListaPasajerosActuales = pasajerosActuales.size();

        if (sizeStorage() + sizeListaPasajerosActuales == 0 && sentidoElevador == "IDLE") {
            tiempoDeInactividad++;
        } else {
            tiempoDeInactividad = 0;
        }

        if (tiempoDeInactividad == 5) {
            System.out.println(" ------------------------------------------------------------------------------------------\n" +
                    "Se detecta inactividad en los elevadores por un tiempo prolongado. Se detiene simulación.\n" +
                    " ------------------------------------------------------------------------------------------\n");
            detenerElevadores();
        }
    }

    private static void detenerElevadores() {
        detenerElevadores = true;
    }

    public static boolean ejecucionElevadores() {
        return detenerElevadores;
    }
}