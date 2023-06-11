package solucion.Helpers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import solucion.Entidades.LlamadosElevadoresManager;
import solucion.Entidades.Pasajero;

import java.io.FileReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Helper {
    public static String generarIdentificador() {
        Date fechaActual = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String fechaFormateada = dateFormat.format(fechaActual);
        String sb = "";

        // Concateno Fecha
        sb += (fechaFormateada + "_");

        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(caracteres.length());
            sb += (caracteres.charAt(index));
        }

        return sb.toString();
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pasajeros;
    }

    public static void cargarSimulacion(List<Pasajero> pasajeros) {
        for (Pasajero pasajero : pasajeros) {
            LlamadosElevadoresManager.agregarPasajero(pasajero);
        }
    }
}
