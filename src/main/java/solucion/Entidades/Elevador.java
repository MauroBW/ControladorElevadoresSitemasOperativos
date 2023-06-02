package solucion.Entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Elevador extends Thread {
    private int pisoActual;
    private String identificador;
    private List<Pasajero> pasajerosActuales = new ArrayList<>();
    private static Queue<Pasajero> listaCompletaPasajeros;
    private List<Pasajero> candidatos = new ArrayList<>();
    private static Semaphore aceptarCliente = new Semaphore(1);
    private Semaphore capacidad = new Semaphore(5);

    public Elevador(int pisoActual,
                    String identificador,
                    Queue<Pasajero> listaCompletaPasajeros) {
        this.pisoActual = pisoActual;
        this.identificador = identificador;
        this.listaCompletaPasajeros = listaCompletaPasajeros;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Clientes Esperando: " + listaCompletaPasajeros.size());
                Pasajero candidato = null;



                if(pasajerosActuales.size() != 0 ) {
                    Pasajero siguienteEnBajar = pasajerosActuales.get(0);
                    for(Pasajero pasajero : pasajerosActuales) {
                        if(pasajero.getPisoObjetivo() < siguienteEnBajar.getPisoObjetivo()) {
                            siguienteEnBajar = pasajero;
                        }
                    }
                    System.out.println(getIdentificador() + " Siguiente en Bajar: " + siguienteEnBajar.getNombre());
                    if (siguienteEnBajar.pisoObjetivo < getPisoActual()) {
                        desplazamiento("bajar");
                    } else if (siguienteEnBajar.pisoObjetivo > getPisoActual()){
                        desplazamiento("subir");
                    } else {
                        pasajerosActuales.remove(siguienteEnBajar);
                    }
                    mostrarInfromacion();
                    Thread.sleep(1000);

                } else {

                    aceptarCliente.acquire();

                    if (listaCompletaPasajeros.size() != 0) {
                        if (listaCompletaPasajeros.peek().getPisoActual() == getPisoActual()) {
                            System.out.println(listaCompletaPasajeros.peek().getName());
                            candidato = listaCompletaPasajeros.poll();
                        } else if (listaCompletaPasajeros.peek().getPisoActual() < getPisoActual()) { // Objetivo en piso 0 y elevador en piso 3
                            desplazamiento("bajar");
                        } else {
                            desplazamiento("subir");
                        }
                    }
                    aceptarCliente.release();

                    if (candidato != null && candidato.getPisoActual() == getPisoActual()) {
                        pasajerosActuales.add(candidato);
                    }

                    mostrarInfromacion();
                    Thread.sleep(1000);
                }} catch (Exception e) {throw new RuntimeException("Se rompio todito");}
        }
    }

    public String getIdentificador() {
        return identificador;
    }

    public int getPisoActual() {
        return pisoActual;
    }

    public void llevarPasajerosActuales() {
        if(pasajerosActuales.size() != 0 ) {
            Pasajero siguienteEnBajar = pasajerosActuales.get(0);
            for(Pasajero pasajero : pasajerosActuales) {
                if(pasajero.getPisoObjetivo() < siguienteEnBajar.getPisoObjetivo()) {
                    siguienteEnBajar = pasajero;
                }
            }
            System.out.println(getIdentificador() + " Siguiente en Bajar: " + siguienteEnBajar.getNombre());
            if (siguienteEnBajar.pisoObjetivo < getPisoActual()) {
                desplazamiento("bajar");
            } else if (siguienteEnBajar.pisoObjetivo > getPisoActual()){
                desplazamiento("subir");
            } else {
                pasajerosActuales.remove(siguienteEnBajar);
            }
            mostrarInfromacion();
    }

    public void desplazamiento(String sentido) {
        switch (sentido) {
            case "subir":
                System.out.println(getIdentificador() + "Subiendo");
                this.pisoActual++;
                break;
            case "bajar":
                System.out.println(getIdentificador() + "Bajando");
                this.pisoActual--;
                break;
        }
    }

    public List<Pasajero> getPasajerosActuales() {
        return pasajerosActuales;
    }

    public void mostrarInfromacion() {
        String informacionPasajeros = "";

        for (Pasajero pasajero : getPasajerosActuales()) {
            informacionPasajeros += String.format("| Nombre: %s, PisoObjetivo: %s ",
                    pasajero.getNombre(), pasajero.getPisoObjetivo());
        }

        System.out.printf("[[ Elevador: %s , PisoActual: %s, Pasajeros: %s|]]\n",
                getIdentificador(), getPisoActual(), informacionPasajeros);
    }
}
