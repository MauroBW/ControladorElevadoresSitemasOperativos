package solucion.Helpers;

import solucion.Entidades.Elevador;
import solucion.Entidades.Pasajero;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;

public class Logger {
    public static int instante = 0;
    public static final String identificador = Helper.generarIdentificador();
    public static final String LOG_GENERAL =  "LogGeneral.txt";
    public static final String LOG_ELEVADOR =  "LogElevador";
    public static String SIMULACION_ACTUAL = "";

    public Logger() {

    }

    public static void crearLogs(Elevador elevador) {
        // Llama a los logs
        saveTimeLine(elevador);
        saveElevadorLog(elevador);
    }

    /*
     * Crea log general - timeline
     */
    public static void saveTimeLine(Elevador elevador) {
        String tiempoActual = String.format("\nInstante: %s\n", instante);
        String informacion = elevador.informacion();
        if (instante == 0) {
            System.out.println("Se guarda un log general de cada instante en /logs/" + LOG_GENERAL);
        }
        if (elevador.getTiempo() == instante) {
            saveLog(LOG_GENERAL, tiempoActual + informacion);
            instante++;
        } else {
            saveLog(LOG_GENERAL, informacion);
        }
    }

    /*
     * Crea log por elevador
     */
    public static void saveElevadorLog(Elevador elevador) {
        String nombreArchivo =  LOG_ELEVADOR + elevador.getIdentificador() + ".txt";

        String informacion = elevador.informacion();
        if (elevador.getTiempo() == 0) {
            System.out.println("Se guarda un log del elevador" + elevador.getIdentificador() + " por instante en /logs/"
                    + nombreArchivo);
        }
        saveLog(nombreArchivo, informacion);

    }

    public static void saveLog(String fileName, String text) {
        String rutaAGuardar = String.format("logs/%s_%s_%s", identificador, SIMULACION_ACTUAL, fileName );
        try {
            FileWriter writer = new FileWriter(rutaAGuardar, true);
            writer.write(text);
            writer.close();
        } catch (Exception e) { e.printStackTrace();}
    }

    public static void saveJson(String fileName, HashMap<Integer, Integer> json) {
        try {
            String rutaAGuardar = "logs/info-" + fileName;
            for (int clave : json.keySet()){
                String text = String.format("%s,%s\n", clave, json.get(clave));

                FileWriter writer = new FileWriter(rutaAGuardar, true);
                writer.write(text);
                writer.close();
            }
        } catch (Exception e) { e.printStackTrace();}
    }

    public static void setSimulacionActual(String simulacionActual) {
        SIMULACION_ACTUAL = simulacionActual;
    }
}
