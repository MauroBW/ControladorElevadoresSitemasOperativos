package solucion.Entidades;

import solucion.Helpers.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Elevador extends Thread {
    private int pisoActual;
    private String identificador;
    private int tiempo = 0;
    private List<Pasajero> pasajerosActuales = new ArrayList<>();
    private static List<Pasajero> listaCompletaPasajeros;
    private List<Pasajero> candidatos = new ArrayList<>();
    private static Semaphore aceptarCliente = new Semaphore(1);
    private int CAPACIDAD = 2;

    public Elevador(int pisoActual,
                    String identificador,
                    List<Pasajero> listaCompletaPasajeros) {
        this.pisoActual = pisoActual;
        this.identificador = identificador;
        this.listaCompletaPasajeros = listaCompletaPasajeros;
    }

    @Override
    public void run() {
        while (true) {
            try {
                registrarInfromacion();
                System.out.println("Clientes Esperando: " + listaCompletaPasajeros.size());


                if (!pasajerosActuales.isEmpty()) {
                    comenzarMovimiento(obtenerObjetivoMasCercano(pasajerosActuales).getPisoObjetivo());
                    bajarClientes();
                } else {
                    if(!listaCompletaPasajeros.isEmpty()){
                        if (pasajerosActuales.size() < CAPACIDAD) {
                            aceptarCliente.acquire();

                            Pasajero aux = procesarNuevoPedido(); // Obtiene pedido mas cercano de listaCompletaPasajeros
                            candidatos.add(aux);
                            listaCompletaPasajeros.remove(aux);

                            aceptarCliente.release();
                        }
                    }
                    if(!candidatos.isEmpty()) {
                        Pasajero clienteCandidato = obtenerPasajeroMasCercano(candidatos);
                        movimientoHaciaCliente(clienteCandidato.getPisoActual());
                        if(clienteCandidato.getPisoActual() == getPisoActual()) {
                            subirPasajero(clienteCandidato);
                            System.out.println("Llegue a candidato");
                            candidatos.remove(clienteCandidato);
                        }
                    }
                }

                if(pasajerosActuales.isEmpty() && listaCompletaPasajeros.isEmpty() && candidatos.isEmpty()) {
                    throw new RuntimeException("Ejecucion terminada");
                }

                tick();
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
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
        return obtenerPasajeroMasCercano(listaCompletaPasajeros);
    }

    public void movimientoHaciaCliente(int pisoActualCliente) {
        if (pisoActualCliente < getPisoActual()) {
            desplazamiento("BAJAR");
        } else if (pisoActualCliente > getPisoActual()) {
            desplazamiento("SUBIR");
        } else {
            System.out.println(getIdentificador() + "Llegue a cliente");
        }
    }

    public void comenzarMovimiento(int pisoObjetivo) {
        if (pisoObjetivo < getPisoActual()) {
            desplazamiento("BAJAR");
        } else if (pisoObjetivo > getPisoActual()) {
            desplazamiento("SUBIR");
        } else {
            System.out.println(getIdentificador() + "Llegue a destino");
        }
    }

    public void bajarClientes() {
        if (!getPasajerosActuales().isEmpty()) {
            List<Pasajero> pasajerosParaEliminar = new ArrayList<>();
            for (Pasajero pasajero : getPasajerosActuales()) {
                if (pasajero.getPisoObjetivo() == getPisoActual()) {
                    pasajerosParaEliminar.add(pasajero);
                }
            }
            if (!pasajerosParaEliminar.isEmpty()){
                new Logger().saveLog(getIdentificador() + "_Log.txt", mostrarInformacion(pasajerosParaEliminar));
            }

            System.out.println("Se bajan los pasajeros: " + mostrarInformacion(pasajerosParaEliminar));
            getPasajerosActuales().removeAll(pasajerosParaEliminar);
        }
    }

    public Pasajero obtenerPasajeroMasCercano(List<Pasajero> listaPasajeros) {
        Pasajero pasajeroCercano = null;
        int menorDiferencia = Integer.MAX_VALUE;

        for (Pasajero pasajero : listaPasajeros) {
            int diferencia = diferenciaDePisos(pasajero.getPisoActual(), this.pisoActual);
            if (diferencia < menorDiferencia) {
                pasajeroCercano = pasajero;
                menorDiferencia = diferencia;
            }
        }
        if (pasajeroCercano != null) {
            System.out.println(pasajeroCercano.getName());
        }

        return pasajeroCercano;
    }

    public Pasajero obtenerObjetivoMasCercano(List<Pasajero> listaPasajeros) {
        Pasajero pasajeroCercano = null;
        int menorDiferencia = Integer.MAX_VALUE;

        for (Pasajero pasajero : listaPasajeros) {
            pasajero.setPisoActual(this.pisoActual);
            int diferencia = diferenciaDePisos(pasajero.getPisoObjetivo(), this.pisoActual);
            if (diferencia < menorDiferencia) {
                pasajeroCercano = pasajero;
                menorDiferencia = diferencia;
            }
        }
        if (pasajeroCercano != null) {
            System.out.println(pasajeroCercano.getName());
        }

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

    public void tick() {
        tiempo ++;
    }

    public int getTiempo() {
        return tiempo;
    }

    public String getTickRateMasID() {
        return String.format("Tick:%s - Elevador%s", getTiempo(), getIdentificador());
    }

    public String mostrarInformacionPasajerosEnCabina() {
        String informacionPasajeros = "";

        for (Pasajero pasajero : getPasajerosActuales()) {
            informacionPasajeros += String.format("| Nombre: %s, PisoObjetivo: %s ",
                    pasajero.getNombre(), pasajero.getPisoObjetivo());
        }
        return informacionPasajeros;
    }

    public String mostrarInformacion(List<Pasajero> pasajeros) {
        String informacionPasajeros = "";

        for (Pasajero pasajero : pasajeros) {
            informacionPasajeros += String.format("%s ~BAJAN~| Nombre: %s, PisoObjetivo: %s \n",
                    getTickRateMasID(), pasajero.getNombre(), pasajero.getPisoObjetivo());
        }
        return informacionPasajeros;
    }

    public void registrarInfromacion() {

        new Logger().saveLog(getIdentificador() + "_Log.txt", String.format("[[ Elevador: %s , PisoActual: %s, Pasajeros: %s|]]\n", getTickRateMasID(), getPisoActual(), mostrarInformacionPasajerosEnCabina()));
        System.out.printf("[[ Elevador: %s , PisoActual: %s, Pasajeros: %s|]]\n",
                getTickRateMasID(), getPisoActual(), mostrarInformacionPasajerosEnCabina());
    }
}
