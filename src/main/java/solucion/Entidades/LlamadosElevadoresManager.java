package solucion.Entidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LlamadosElevadoresManager {
    private List<Pasajero> listaPasajeros;
    private String identificadorLog;

    public LlamadosElevadoresManager() {
        listaPasajeros = new LinkedList<>();
        this.identificadorLog = generarIdentificador();
    }

    public void agregarPasajero(String nombre, int peso, int pisoOrigen, int pisoDestino) {
        Pasajero pasajero = new Pasajero(nombre, peso, pisoOrigen, pisoDestino);
        /*

            Acá se va a generar la lógica al agregar el pasajero.
            A su vez esta clase debe controlar o saber el tiempo, ya que a cada cambio de tiempo se debe reordenar la lista.

         */
        listaPasajeros.add(pasajero);
    }

    public void iniciarElevadores() {
        for (Pasajero pasajero : listaPasajeros) {
            pasajero.start();
        }

        Elevador elevador1 = new Elevador(1, "##1", listaPasajeros, this.identificadorLog);
        elevador1.start();

        Elevador elevador2 = new Elevador(0, "##2", listaPasajeros, this.identificadorLog);
        elevador2.start();
    }


    //Se genera identificador para diferenciar los logs
    public String generarIdentificador() {
        Date fechaActual = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String fechaFormateada = dateFormat.format(fechaActual);
        String nombreArchivo = "log_" + fechaFormateada + "_" + this.identificadorLog + ".txt";

        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(6);
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(index));
        }
        return sb.toString();
    }
}