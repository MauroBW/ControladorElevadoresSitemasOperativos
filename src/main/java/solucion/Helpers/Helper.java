package solucion.Helpers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import solucion.Entidades.LlamadosElevadoresManager;
import solucion.Entidades.Pasajero;

import java.io.FileReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.*;

public class Helper {
    public static String generarIdentificador() {
        Date fechaActual = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("Hm_s_yyyyMMdd");
        String fechaFormateada = dateFormat.format(fechaActual);
        return fechaFormateada;
    }

    public static List<Pasajero> leerSimulacion(String filepath) {
        List<Pasajero> pasajeros = new ArrayList<>();

        try {
            Reader in = new FileReader(filepath);
            CSVParser parser = CSVFormat.DEFAULT
                    .withHeader("nombre", "peso", "pisoActual", "pisoObjetivo", "tiempoInicio")
                    .withDelimiter(',')
                    .parse(in);

            for (CSVRecord record : parser) {
                String nombre = record.get("nombre");
                int peso = Integer.parseInt(record.get("peso"));
                int pisoActual = Integer.parseInt(record.get("pisoActual"));
                int pisoObjetivo = Integer.parseInt(record.get("pisoObjetivo"));
                int tiempoInicio = Integer.parseInt(record.get("tiempoInicio"));

                Pasajero pasajero = new Pasajero(nombre, peso, pisoActual, pisoObjetivo, tiempoInicio);
                pasajeros.add(pasajero);
            }
        } catch (Exception e) {e.printStackTrace();}
        return pasajeros;
    }

    /**
     * Analiza la cantidad de llamados que hay por instante en toda la simulacion
     * @param filepath: Simulacion path
     * @return Hashmap <Instante, Cantidad Llamados>
     */
    public static HashMap<Integer, Integer> informacionSimulacion(String filepath) {
        HashMap<Integer, Integer> ocurrencias = new HashMap<>();

        try {
            Reader in = new FileReader(filepath);
            CSVParser parser = CSVFormat.DEFAULT
                    .withHeader("nombre", "peso", "pisoActual", "pisoObjetivo", "tiempoInicio")
                    .withDelimiter(',')
                    .parse(in);

            for (CSVRecord record : parser) {
                int tiempoInicio = Integer.parseInt(record.get("tiempoInicio"));
                if(ocurrencias.containsKey(tiempoInicio)) {
                    ocurrencias.put(tiempoInicio, ocurrencias.get(tiempoInicio) + 1);
                } else {
                    ocurrencias.put(tiempoInicio, 1);
                }
            }
        } catch (Exception e) {e.printStackTrace();}

        Logger.saveJson("Carga_por_tiempo.txt", ocurrencias);

        return ocurrencias;
    }

    public static void cargarSimulacion(List<Pasajero> pasajeros) {
        for (Pasajero pasajero : pasajeros) {
            LlamadosElevadoresManager.agregarPasajero(pasajero);
        }
    }


    public enum ConsoleColor {
        RESET("\u001B[0m"),
        BLACK("\u001B[30m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m");

        private final String code;

        ConsoleColor(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
    public static void colorizer(ConsoleColor color, String text) {
        System.out.println(color.getCode() + text + ConsoleColor.RESET.getCode());
    }
}
