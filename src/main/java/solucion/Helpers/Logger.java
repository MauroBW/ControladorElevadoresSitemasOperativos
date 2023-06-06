package solucion.Helpers;

import solucion.Entidades.Elevador;
import solucion.Entidades.Pasajero;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;

public class Logger {

//    public void logPasajeros(ArrayDeque<Pasajero> pasajeros) {
//        System.out.println("=========================================================");
//        System.out.println("              Lista de Pasajeros por Atender             ");
//        System.out.println("=========================================================");
//        for(Pasajero aux: pasajeros) {
//            aux.printInfo();
//        }
//        System.out.println("===========================================================");
//    }
//
//    public void logElevador(Elevador elevador) {
//        System.out.println("==========================================================");
//        System.out.println("                         ELEVADOR                         ");
//        System.out.println("==========================================================");
//        System.out.printf("\n<%s> | Piso actual: %s \n",
//                elevador.getIdentificador(),
//                elevador.pisoActual);
//
//        for (Pasajero aux: elevador.pasajeros) {
//            System.out.printf("Pasajero: %s | Estado: %s | Cronometro: %s\n",
//                    aux.getNombre(),
//                    aux.getEstado(),
//                    aux.getWaitTime());
//        }
//
//        System.out.println("==========================================================");
//    }
//
//    public String savePasajerosData(ArrayDeque<Pasajero> pasajeros) {
//        String data = "|Información Pasajeros|\n";
//        for(Pasajero aux: pasajeros) {
//            data += aux.getInfo() + "\n";
//        }
//        return data;
//    }
//
//    public String saveElevadorData(Elevador elevador) {
//        String data = "|Información Elevador|\n";
//
//        data += String.format("\n<%s> | Piso actual: %s \n",
//                elevador.nombreElevador,
//                elevador.pisoActual);
//
//        for (Pasajero aux: elevador.pasajeros) {
//            data += String.format("Pasajero: %s | Estado: %s | Cronometro: %s\n",
//                    aux.getNombre(),
//                    aux.getEstado(),
//                    aux.getWaitTime());
//        }
//        return data;
//    }
//
//    public void spaces(){
//        System.out.println("\n\n\n\n");
//    }

    public Logger() {

    }

    public static void saveLog(String fileName, String text) {
        String rutaAGuardar = "logs/" + fileName; // Se guarda en carpeta logs
        try {
            FileWriter writer = new FileWriter(rutaAGuardar, true);
            writer.write(text);
            writer.close();
            // System.out.println("El string se ha escrito en el archivo correctamente.");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al escribir el archivo.");
            e.printStackTrace();
        }
    }
}
