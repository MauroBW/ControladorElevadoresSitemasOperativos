package solucion.Entidades;

import solucion.Helpers.Helper;
import solucion.Helpers.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import static solucion.Helpers.Helper.colorizer;

public class Elevador extends Thread {
    private int pisoActual;
    private String identificador;
    private int tiempo = 0;
    private List<Pasajero> pasajerosActuales = new ArrayList<>();
    private static List<Pasajero> listaCompletaPasajeros;
    private static Semaphore aceptarCliente = new Semaphore(1);
    private static Semaphore log = new Semaphore(1);
    private int CAPACIDAD = 5;
    private int LIMITEPESO = 400;
    private final String SUBIENDO = "SUBIENDO";
    private final String BAJANDO = "BAJANDO";
    private final String IDLE = "IDLE";
    private String sentido;
    private int CANTIDAD_VIAJES = 0;

    public Elevador(int pisoActual,
            String identificador,
            List<Pasajero> listaCompletaPasajeros) {
        this.pisoActual = pisoActual;
        this.identificador = identificador;
        this.listaCompletaPasajeros = listaCompletaPasajeros;
        this.sentido = IDLE;
    }

    @Override
    public void run() {
        while (!LlamadosElevadoresManager.ejecucionElevadores()) {
            try {
                aceptarCliente.acquire();
                LlamadosElevadoresManager.updateListaPedidos(getTiempo());
                LlamadosElevadoresManager.detectarInactividad(pasajerosActuales, getSentidoElevador());



                System.out.println("Clientes Esperando: " + listaCompletaPasajeros.size());
                System.out.println(mostrarInformacion(listaCompletaPasajeros));
                aceptarCliente.release();

                if (hayPasajerosEnCabina()) {
                    // Hay pasajeros en cabina
                    setearSentido(obtenerObjetivoMasCercano(pasajerosActuales).getPisoObjetivo());

                    if (hayPasajerosEsperando()) {
                        // Busco pasjeros para compartir el viaje
                            aceptarCliente.acquire();

                            if(!obtenerPasajerosPuedenSubir(listaCompletaPasajeros).isEmpty()) {
                                // Hay pasajeros que pueden subir. Coinciden en piso con elevador y sentido
                                List<Pasajero> aux = obtenerPasajerosPuedenSubir(listaCompletaPasajeros);
                                subirPasajeros(aux);
                                listaCompletaPasajeros.removeAll(aux);
                            }

                            aceptarCliente.release();
                    }

                    desplazamiento(sentido);
                    bajarClientes();
                } else {
                    // No hay pasajeros en cabina

                    if (hayPasajerosEsperando()) {
                        // Hay pasajeros esperando a ser atendidos
                            aceptarCliente.acquire();

                            if(!obtenerPasajerosPuedenSubir(listaCompletaPasajeros).isEmpty()) {
                                // Hay pasajeros que pueden subir. Coinciden en piso con elevador y sentido

                                List<Pasajero> aux = obtenerPasajerosPuedenSubir(listaCompletaPasajeros);
                                subirPasajeros(aux);
                                listaCompletaPasajeros.removeAll(aux);
                            } else {

                                // No hay pasajeros en cabina ni en el mismo piso que el elevador

                                Pasajero pasajero = obtenerPasajeroMasCercano(listaCompletaPasajeros);
                                setearSentido(pasajero.getPisoActual());
                                desplazamiento(getSentidoElevador());
                            }

                            aceptarCliente.release();
                    }
                }

                if (pasajerosActuales.isEmpty() && listaCompletaPasajeros.isEmpty()) {
//                    setearSentido(0);
//                    desplazamiento(sentido);
                }

                // Sincronizacion para Logger
                log.acquire();
                Logger.crearLogs(this);
                log.release();

                colorizer(Helper.ConsoleColor.CYAN, informacion());
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

    public String getSentidoElevador() {
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

    public int getCAPACIDAD() {
        return CAPACIDAD;
    }

    public int getLIMITEPESO() {
        return LIMITEPESO;
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

    public boolean hayPasajerosEnCabina() {
        return !getPasajerosActuales().isEmpty();
    }

    public boolean hayPasajerosEsperando() {
        return !listaCompletaPasajeros.isEmpty();
    }

    /**
     *
     * @return Verdadero si tiene capacidad para incluir 1 pasajero mas, falso en
     *         caso contrario.
     */
    private boolean hayCapacidad() {
        return (getPesoPasajerosActuales() < (LIMITEPESO - 100) && getCantidadPasajerosActuales() < CAPACIDAD);
    }

    private int getCANTIDAD_VIAJES() {
        return CANTIDAD_VIAJES;
    }

    public void setCANTIDAD_VIAJES(int CANTIDAD_VIAJES) {
        this.CANTIDAD_VIAJES = CANTIDAD_VIAJES;
    }

    private void sumarContadorViajes() {
        setCANTIDAD_VIAJES(getCANTIDAD_VIAJES() + 1);
    }

    /********************************************************************************
     * Fin Getters, setters y funciones aux
     ********************************************************************************/





     /********************************************************************************
     * Inicio lógica y operaciones
     ********************************************************************************/
    /**
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
     * Sube o Baja una unidad dependiendo del sentido de navegaciín
     *
     * @param sentido: Sentido para la navegación
     */
    public void desplazamiento(String sentido) {
        switch (sentido) {
            case SUBIENDO:
                this.pisoActual++;
                break;
            case BAJANDO:
                this.pisoActual--;
                break;
        }
    }

    /**
     * Baja los clientes que coinciden en pisoActual con pisoObjetivo
     */
    public void bajarClientes() {
        List<Pasajero> pasajerosParaBajar = new ArrayList<>();
        for (Pasajero pasajero : getPasajerosActuales()) {
            if (pasajero.getPisoObjetivo() == getPisoActual()) {
                pasajerosParaBajar.add(pasajero);
                pasajero.setPisoActual(getPisoActual());
                sumarContadorViajes();
            }
        }
        if (!pasajerosParaBajar.isEmpty()) {
            Logger.saveLog("LogPasajeros.txt", mostrarInformacion(pasajerosParaBajar));
            colorizer(Helper.ConsoleColor.YELLOW, mostrarInformacion(pasajerosParaBajar));
        }

        getPasajerosActuales().removeAll(pasajerosParaBajar);
    }


    /**
     * @param pasajero
     * @return Verdadero si estoy en piso pasajero y voy hacia su objetivo
     */
    private boolean puedeSubirPasajero(Pasajero pasajero) {
        if (getPisoActual() == pasajero.getPisoActual()) {
            if (pasajero.getPisoObjetivo() > getPisoActual() && (getSentidoElevador() == SUBIENDO || getSentidoElevador() == IDLE)) {
                return true;
            } else if (pasajero.getPisoObjetivo() < getPisoActual() && (getSentidoElevador() == BAJANDO || getSentidoElevador() == IDLE)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Devuelve lista de pasajeros que pueden subir considerando la capacidad restante del elevador
     * @param listaPasajerosTotal
     * @return
     */
    private List<Pasajero> obtenerPasajerosPuedenSubir(List<Pasajero> listaPasajerosTotal) {
        List<Pasajero> salidaPasajeros = new ArrayList();
        int capacidadDisponible = getCAPACIDAD() - getCantidadPasajerosActuales();
        int pesoDisponible = getLIMITEPESO() - getPesoPasajerosActuales();
        for (Pasajero pasajero : listaPasajerosTotal) {
            if (puedeSubirPasajero(pasajero) && capacidadDisponible > 0 && pesoDisponible > pasajero.getPeso()) {

                salidaPasajeros.add(pasajero);
                pesoDisponible -= pasajero.getPeso();
                capacidadDisponible--;
            }
        }
        return salidaPasajeros;
    }

    private void subirPasajeros(List<Pasajero> pasajeros) {
        this.pasajerosActuales.addAll(pasajeros);
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
        } catch (Exception e) {}

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
        return String.format("[[ Elevador: %s , Piso Actual: %s, ViajesCompletados: %s, Sentido: %s, Pasajeros: %s|]]\n", getTickRateMasID(),
                getPisoActual(), getCANTIDAD_VIAJES(), getSentidoElevador(), mostrarInformacionPasajerosEnCabina());
    }

    /********************************************************************************
     * Fin registros - información
     ********************************************************************************/
}
