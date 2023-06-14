package solucion.Entidades;

import solucion.Helpers.Logger;
import java.util.ArrayList;
import java.util.LinkedList;
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
    private static Semaphore log = new Semaphore(1);
    private int CAPACIDAD = 5;
    private int LIMITEPESO = 400;
    private final String SUBIENDO = "SUBIENDO";
    private final String BAJANDO = "BAJANDO";
    private final String IDLE = "IDLE";
    private String sentido;

    public Elevador(int pisoActual,
            String identificador,
            List<Pasajero> listaCompletaPasajeros) {
        this.pisoActual = pisoActual;
        this.identificador = identificador;
        this.listaCompletaPasajeros = listaCompletaPasajeros;
    }

    @Override
    public void run() {
        while (!LlamadosElevadoresManager.ejecucionElevadores()) {
            try {
                aceptarCliente.acquire();
                LlamadosElevadoresManager.updateListaPedidos(getTiempo());
                LlamadosElevadoresManager.detectarInactividad(pasajerosActuales, getSentido());
                aceptarCliente.release();
                System.out.println("Clientes Esperando: " + listaCompletaPasajeros.size());

                if (!pasajerosActuales.isEmpty()) {
                    // Antes de dirigirme al piso objetivo debo checkear si puedo llevar a alguien
                    // mas de paso
                    setearSentido(obtenerObjetivoMasCercano(pasajerosActuales).getPisoObjetivo());

                    // Movimiento de elevador
                    desplazamiento(sentido);
                    bajarClientes();
                } else {
                    if (!listaCompletaPasajeros.isEmpty()) {
                        if (pasajerosActuales.size() < CAPACIDAD) {
                            aceptarCliente.acquire();

                            Pasajero aux = procesarNuevoPedido(); // Obtiene pedido mas cercano de
                                                                  // listaCompletaPasajeros
                            candidatos.add(aux);
                            listaCompletaPasajeros.remove(aux);

                            aceptarCliente.release();
                        }
                    }
                    if (!candidatos.isEmpty()) {
                        Pasajero clienteCandidato = obtenerPasajeroMasCercano(candidatos);
                        setearSentido(clienteCandidato.getPisoActual());
                        desplazamiento(sentido);
                        if (clienteCandidato.getPisoActual() == getPisoActual()) {
                            subirPasajero(clienteCandidato);
                            // System.out.println("Llegue a candidato");
                            candidatos.remove(clienteCandidato);
                        }
                    }
                }

                if (pasajerosActuales.isEmpty() && listaCompletaPasajeros.isEmpty() && candidatos.isEmpty()) {
                    // throw new RuntimeException("Ejecucion terminada");
                    setearSentido(0);
                    desplazamiento(sentido);
                }

                // Sincronizacion para Logger
                log.acquire();
                Logger.crearLogs(this);
                log.release();

                tick();
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error en el controlador de elevadores. Se ejecuta protocolo de seguridad.");
            }
        }
    }

    /********************************************************************************
     * Inicio Getters, setters y funciones aux
     ********************************************************************************/

    public String getIdentificador() {
        return identificador;
    }

    public int getPisoActual() {
        return pisoActual;
    }

    public String getSentido() {
        return sentido;
    }

    /**
     * @return Lista de pasajeros dentro del elevador
     */
    public List<Pasajero> getPasajerosActuales() {
        return pasajerosActuales;
    }

    public int getTiempo() {
        return tiempo;
    }

    public String getTickRateMasID() {
        return String.format("%s - Tiempo :%s", getIdentificador(), getTiempo());
    }

    public void tick() {
        tiempo++;
    }

    public Pasajero procesarNuevoPedido() {
        return obtenerPasajeroMasCercano(listaCompletaPasajeros);
    }

    public int getCantidadPasajerosActuales() {
        return pasajerosActuales.size();
    }

    public int getPesoPasajerosActuales() {
        int pesoTotal = 0;
        for (Pasajero pasajero : pasajerosActuales) {
            pesoTotal += pasajero.getPeso();
        }
        return pesoTotal;
    }

    /********************************************************************************
     * Fin Getters, setters y funciones aux
     ********************************************************************************/

    /********************************************************************************
     * Inicio lógica y operaciones
     ********************************************************************************/

    private void subirPasajero(Pasajero aux) {
        this.pasajerosActuales.add(aux);
    }

    /**
     * 
     * @param pisoObjetivo:
     *                      - Si pisoObjetivo es menor a pisoActual, entonces Bajo
     *                      - Si pisoObjetivo es mayor a pisoActual, entonces Subo
     */
    public void setearSentido(int pisoObjetivo) {
        if (pisoObjetivo < getPisoActual()) {
            sentido = BAJANDO;
        } else if (pisoObjetivo > getPisoActual()) {
            sentido = SUBIENDO;
        } else {
            sentido = IDLE;
        }
    }

    /**
     * Recorre la lista de pasajerosActuales y baja a los que coincidan en
     * pisoObjetivo
     * Con pisoActual del Elevador
     */
    public void bajarClientes() {
        if (!getPasajerosActuales().isEmpty()) {
            List<Pasajero> pasajerosParaEliminar = new ArrayList<>();
            for (Pasajero pasajero : getPasajerosActuales()) {
                if (pasajero.getPisoObjetivo() == getPisoActual()) {
                    pasajero.setPisoActual(getPisoActual());
                    pasajerosParaEliminar.add(pasajero);
                }
            }
            if (!pasajerosParaEliminar.isEmpty()) {
                Logger.saveLog("LogPasajeros.txt", mostrarInformacion(pasajerosParaEliminar));
            }

            // System.out.println("Se bajan los pasajeros: " +
            // mostrarInformacion(pasajerosParaEliminar));
            getPasajerosActuales().removeAll(pasajerosParaEliminar);
        }
    }

    /**
     * Devuelve el pasajero cuyo pisoActual es el más cercano al elevador
     * 
     * @param listaPasajeros
     * @return
     */
    public Pasajero obtenerPasajeroMasCercano(List<Pasajero> listaPasajeros) {
        Pasajero pasajeroCercano = null;
        int menorDiferencia = Integer.MAX_VALUE;

        try {
            for (Pasajero pasajero : listaPasajeros) {
                int diferencia = diferenciaDePisos(pasajero.getPisoActual(), this.pisoActual);
                if (diferencia < menorDiferencia) {
                    pasajeroCercano = pasajero;
                    menorDiferencia = diferencia;
                }
            }
        } catch (Exception e) {
            System.out.println("Punto fragil de ejecucion, listaPasajeros está vacia");
        }

        if (pasajeroCercano != null) {
            // System.out.println(pasajeroCercano.getName());
        }

        return pasajeroCercano;
    }

    /**
     * Devuelve el Pasajero que tiene el pisoObjetivo más cercano al elevador
     * 
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
            // System.out.println(pasajeroCercano.getName());
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
     * 
     * @param sentido: Sentido para la navegación
     */
    public void desplazamiento(String sentido) {
        switch (sentido) {
            case "SUBIENDO":
                this.pisoActual++;
                break;
            case "BAJANDO":
                this.pisoActual--;
                break;
        }
    }

    /**
     * 
     * @return Verdadero si tiene capacidad para incluir 1 pasajero mas, falso en
     *         caso contrario.
     */
    private boolean hayCapacidad() {
        return (getPesoPasajerosActuales() < (LIMITEPESO - 100) && getCantidadPasajerosActuales() < CAPACIDAD);
    }

    /********************************************************************************
     * Fin lógica y operaciones
     ********************************************************************************/

    /********************************************************************************
     * Inicio registros - información
     ********************************************************************************/

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
            informacionPasajeros += String.format("%s ~BAJAN~| Nombre: %s, Piso Objetivo: %s \n",
                    getTickRateMasID(), pasajero.getNombre(), pasajero.getPisoObjetivo());
        }
        return informacionPasajeros;
    }

    /**
     * Informacion del elevador
     * 
     * @return Devuelve String con toda la información del elevador
     */
    public String informacion() {
        return String.format("[[ Elevador: %s , Piso Actual: %s, Sentido: %s, Pasajeros: %s|]]\n", getTickRateMasID(),
                getPisoActual(), getSentido(), mostrarInformacionPasajerosEnCabina());
    }

    /********************************************************************************
     * Fin registros - información
     ********************************************************************************/
}
