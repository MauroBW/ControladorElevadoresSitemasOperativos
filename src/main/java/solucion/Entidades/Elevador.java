package solucion.Entidades;

import solucion.Helpers.Logger;

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
    private int CAPACIDAD = 2;

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
                mostrarInfromacion();
                System.out.println("Clientes Esperando: " + listaCompletaPasajeros.size());


                if(!pasajerosActuales.isEmpty()) {
                    comenzarMovimiento(obtenerObjetivoMasCercano(pasajerosActuales).getPisoObjetivo());
                } else if(pasajerosActuales.size() < CAPACIDAD) {
                    Pasajero aux = procesarNuevoPedido();
                    System.out.println("Aux -> "  + aux.getNombre());
                    comenzarMovimiento(aux.getPisoActual());
                    if (aux.getPisoActual() == getPisoActual()) {
                        subirPasajero(aux);
                    }
                }






//                if (pasajerosActuales.size() != 0) {
//                    Pasajero siguienteEnBajar = pasajerosActuales.get(0);
//                    for (Pasajero pasajero : pasajerosActuales) {
//                        if (pasajero.getPisoObjetivo() < siguienteEnBajar.getPisoObjetivo()) {
//                            siguienteEnBajar = pasajero;
//                        }
//                    }
//                    System.out.println(getIdentificador() + " Siguiente en Bajar: " + siguienteEnBajar.getNombre());
//                    if (siguienteEnBajar.pisoObjetivo < getPisoActual()) {
//                        desplazamiento("bajar");
//                    } else if (siguienteEnBajar.pisoObjetivo > getPisoActual()) {
//                        desplazamiento("subir");
//                    } else {
//                        pasajerosActuales.remove(siguienteEnBajar);
//                    }
//                    mostrarInfromacion();
//                    Thread.sleep(1000);

//                } else {

//                    aceptarCliente.acquire();
//
//                    if (listaCompletaPasajeros.size() != 0) {
//                        if (listaCompletaPasajeros.peek().getPisoActual() == getPisoActual()) {
//                            System.out.println(listaCompletaPasajeros.peek().getName());
//                            candidato = listaCompletaPasajeros.poll();
//                        } else if (listaCompletaPasajeros.peek().getPisoActual() < getPisoActual()) { // Objetivo en piso 0 y elevador en piso 3
//                            desplazamiento("bajar");
//                        } else {
//                            desplazamiento("subir");
//                        }
//                    }
//                    aceptarCliente.release();
//
//                    if (candidato != null && candidato.getPisoActual() == getPisoActual()) {
//                        pasajerosActuales.add(candidato);
//                    }
//
//                    mostrarInfromacion();
                    Thread.sleep(1000);
//                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Se rompio todito");
            }
        }
    }

    private void subirPasajero(Pasajero aux) {
        this.pasajerosActuales.add(aux);
    }

    public String getIdentificador() {
        return identificador;
    }

    public int getPisoActual() {
        return pisoActual;
    }

    public Pasajero procesarNuevoPedido() {
        return obtenerPasajeroMasCercano((List<Pasajero>) listaCompletaPasajeros);
    }

    public void comenzarMovimiento(int pisoObjetivo) {
            if (pisoObjetivo < getPisoActual()) {
                desplazamiento("BAJAR");
            } else  if (pisoObjetivo > getPisoActual()) {
                desplazamiento("SUBIR");
            } else {
                System.out.println(getIdentificador() + "Llegue a objetivo");
            }
    }

    public Pasajero obtenerPasajeroMasCercano(List<Pasajero> listaPasajeros) {
        Pasajero pasajeroCercano = null;
        int menorDiferencia = Integer.MAX_VALUE;

        for (Pasajero pasajero : listaPasajeros) {
            int diferencia = diferenciaDePisos(pasajero.getPisoActual(), this.pisoActual);
//            System.out.println(getIdentificador() + " Difiere " + diferencia + " con " + pasajero.getNombre());
            if (diferencia < menorDiferencia) {
                pasajeroCercano = pasajero;
                menorDiferencia = diferencia;
            }
        }
        if (pasajeroCercano != null) {System.out.println(pasajeroCercano.getName());};

        return pasajeroCercano;
    }

    public Pasajero obtenerObjetivoMasCercano(List<Pasajero> listaPasajeros) {
        Pasajero pasajeroCercano = null;
        int menorDiferencia = Integer.MAX_VALUE;

        for (Pasajero pasajero : listaPasajeros) {
            pasajero.setPisoActual(this.pisoActual);
            int diferencia = diferenciaDePisos(pasajero.getPisoObjetivo(), this.pisoActual);
//            System.out.println(getIdentificador() + " Difiere " + diferencia + " con " + pasajero.getNombre());
            if (diferencia < menorDiferencia) {
                pasajeroCercano = pasajero;
                menorDiferencia = diferencia;
            }
        }
        if (pasajeroCercano != null) {System.out.println(pasajeroCercano.getName());};

        return pasajeroCercano;
    }

    public int diferenciaDePisos(int piso_1, int piso_2) {
        return Math.abs(piso_1 - piso_2);
    }

    public void desplazamiento(String sentido) {
        switch (sentido) {
            case "SUBIR":
                System.out.println(getIdentificador() + "Subiendo");
                this.pisoActual++;
                break;
            case "BAJAR":
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
        new Logger().saveLog(getIdentificador() + "_Log.txt", String.format("[[ Elevador: %s , PisoActual: %s, Pasajeros: %s|]]\n", getIdentificador(), getPisoActual(), informacionPasajeros));
        System.out.printf("[[ Elevador: %s , PisoActual: %s, Pasajeros: %s|]]\n",
                getIdentificador(), getPisoActual(), informacionPasajeros);
    }
}
