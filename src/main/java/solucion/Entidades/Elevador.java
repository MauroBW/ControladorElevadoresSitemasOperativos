package solucion.Entidades;

import solucion.Helpers.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Elevador extends Thread {
    private int pisoActual;
    private String identificador;
    private int tiempo = 0;
    private List<Pasajero> pasajerosActuales = new ArrayList<>();
    private static List<Pasajero> listaCompletaPasajeros;
    private List<Pasajero> candidatos = new ArrayList<>();
    private static Semaphore aceptarCliente = new Semaphore(1);
    private int CAPACIDAD = 2;
    private String identificadorLog;

    public Elevador(int pisoActual,
                    String identificador,
                    List<Pasajero> listaCompletaPasajeros, String identificadorLog) {
        this.pisoActual = pisoActual;
        this.identificador = identificador;
        this.listaCompletaPasajeros = listaCompletaPasajeros;
        this.identificadorLog = identificadorLog;
    }

    @Override
    public void run() {
        while (true) {
            try {
                registrarInfromacion();
                timelineLog();

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
                        comenzarMovimiento(clienteCandidato.getPisoActual());
                        if(clienteCandidato.getPisoActual() == getPisoActual()) {
                            subirPasajero(clienteCandidato);
                            System.out.println("Llegue a candidato");
                            candidatos.remove(clienteCandidato);
                        }
                    }
                }

                if(pasajerosActuales.isEmpty() && listaCompletaPasajeros.isEmpty() && candidatos.isEmpty()) {
//                    throw new RuntimeException("Ejecucion terminada");
                    comenzarMovimiento(0);
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

    /**
     * Realiza movimiento del Elevador hacia objetivo
     * @param pisoObjetivo:
     *  - Si pisoObjetivo es menor a pisoActual, entonces Bajo
     *  - Si pisoObjetivo es mayor a pisoActual, entonces Subo
     */
    public void comenzarMovimiento(int pisoObjetivo) {
        if (pisoObjetivo < getPisoActual()) {
            desplazamiento("BAJAR");
        } else if (pisoObjetivo > getPisoActual()) {
            desplazamiento("SUBIR");
        } else {
            System.out.println(getIdentificador() + "Llegue a destino");
        }
    }

    /**
     * Recorre la lista de pasajerosActuales y baja a los que coincidan en pisoObjetivo
     * Con pisoActual del Elevador
     */
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

    /**
     * Devuelve el pasajero cuyo pisoActual es el más cercano al elevador
     * @param listaPasajeros
     * @return
     */
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

    /**
     * Devuelve el Pasajero que tiene el pisoObjetivo más cercano al elevador
     * @param listaPasajeros
     * @return Pasajero con pisoObjetivo mas cercano
     */
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

    /**
     * @param piso_1
     * @param piso_2
     * @return Devuelve diferencia de pisos en valor Absoluto
     */
    public int diferenciaDePisos(int piso_1, int piso_2) {
        return Math.abs(piso_1 - piso_2);
    }

    /**
     * Sube o Baja una unidad dependiendo del sentido de navegaciín
     * @param sentido: Sentido para la navegación
     */
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
        return String.format("Elevador%s - Tiempo:%s", getIdentificador(), getTiempo());
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
        System.out.printf("[[ %s , PisoActual: %s, Pasajeros: %s|]]\n",
                getTickRateMasID(), getPisoActual(), mostrarInformacionPasajerosEnCabina());
    }


    // Se logea por cada tiempo el estado de todo
    public void timelineLog(){
        int tiempoActual = getTiempo();

        // Obtener la fecha y hora actual
        Date fechaActual = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String fechaFormateada = dateFormat.format(fechaActual);

        String nombreArchivo = "log_" + fechaFormateada + "_" + this.identificadorLog + ".txt";
        new Logger().saveLog(nombreArchivo, String.format("[[ Elevador: %s , PisoActual: %s, Pasajeros: %s|]]\n", tiempoActual, getPisoActual(), mostrarInformacionPasajerosEnCabina()));

    }



}
