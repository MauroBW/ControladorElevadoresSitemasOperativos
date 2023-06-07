package solucion.Helpers;

import solucion.Entidades.Elevador;
import solucion.Entidades.Pasajero;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;

public class Logger {
    public static int instante = 0;
    public static final String identificador = Helper.generarIdentificador();
    public static final String LOG_GENERAL = "LogGeneral_" + identificador + ".txt";
    public static final String LOG_ELEVADOR = "LogElevador_" + identificador + ".txt";

    public Logger() {

    }

    /*
     * Crea log general - timeline
     */
    public static void saveTimeLine(Elevador elevador) {
        String tiempoActual = String.format("\nInstante: %s\n", instante);
        String informacion = elevador.informacion();
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
        String nombreArchivo = elevador.getIdentificador() + LOG_ELEVADOR;

        String informacion = elevador.informacion();

        saveLog(nombreArchivo, informacion);

    }

    public static void saveLog(String fileName, String text) {
        String rutaAGuardar = "logs/" + fileName;
        try {
            FileWriter writer = new FileWriter(rutaAGuardar, true);
            writer.write(text);
            writer.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al escribir el archivo.");
            e.printStackTrace();
        }
    }

    public static void crearLogs(Elevador elevador) {
        // Llama a los logs
        saveTimeLine(elevador);
        saveElevadorLog(elevador);

    }
}
