package solucion.Helpers;

import solucion.Entidades.Elevador;
import solucion.Entidades.Pasajero;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;

public class Logger {
    public static int instante = 0;
    public static final String LOG_GENERAL = "LogGeneral_" + Helper.generarIdentificador();


    public Logger() {

    }

    public static void saveTimeLine(Elevador elevador) {
        String tiempoActual = instante + "\n";
        String informacion = elevador.informacion();
        if(elevador.getTiempo() == instante) {
            saveLog(LOG_GENERAL, tiempoActual + informacion);
            instante++;
        } else {
            saveLog(LOG_GENERAL, informacion);
        }
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
}
