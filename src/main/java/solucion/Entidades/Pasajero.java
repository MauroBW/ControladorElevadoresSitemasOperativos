package solucion.Entidades;

import solucion.Helpers.DataConstant;
import solucion.Helpers.Logger;

public class Pasajero extends Thread {

    public int tiempo = 0;
    public int tiempoInicio;
    public String nombre;
    public int peso;
    public int pisoActual;
    public int pisoObjetivo;

    public String sentido;
    public int pisoInicio;

    public Pasajero(String nombre, int peso, int pisoActual, int pisoObjetivo, int tiempoInicio) {
        this.nombre = nombre;
        this.peso = peso;
        this.pisoActual = pisoActual;
        this.pisoObjetivo = pisoObjetivo;
        this.tiempoInicio = tiempoInicio;
        this.setName(nombre); // Setea el nombre para el hilo
        this.pisoInicio = pisoActual;
        this.sentido = determinarSentido();
    }

    public Pasajero() {

    }

    @Override
    public void run() {
        while (true) {
            tick();
            try {
             if (getPisoActual() == getPisoObjetivo()) {
                 Logger.saveLog("##Pasajeros_Log_General.txt",
                         String.format("{ Nombre: %s Tiempo: %s || PisoObjetivo: %s PisoInicio: %s }\n",
                                 getNombre(), getTiempo(), getPisoObjetivo(), getPisoInicio()));
                 Logger.saveLog("Informacion_viajes_completados.txt", String.format("%s,%s\n", getNombre(), getTiempo()));
                 this.stop();
             }
             Thread.sleep(DataConstant.SIMULATION_SPEED);
            } catch (Exception e) {}
        }
    }

    public void tick() {
        this.tiempo += 1;
    }

    public int getPeso() {
        return peso;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int getPisoObjetivo() {
        return pisoObjetivo;
    }

    public int getPisoActual() {
        return pisoActual;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setPisoInicio(int pisoInicio) {
        this.pisoInicio = pisoInicio;
    }

    public void setPisoObjetivo(int pisoObjetivo) {
        this.pisoObjetivo = pisoObjetivo;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public void setTiempoInicio(int tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setPisoActual(int pisoActual) {
        this.pisoActual = pisoActual;
    }

    public int getTiempoInicio() {
        return tiempoInicio;
    }

    public int getPisoInicio() {
        return pisoInicio;
    }

    public String getSentido() {
        return sentido;
    }

    private String determinarSentido() {
        int diferencia = getPisoActual() - getPisoObjetivo(); // 1 - 8
        if (diferencia < 0) {
            return "SUBIR";
        } else {
            return "BAJAR";
        }
    }

    public void mostrarInformacionPasajero() {
        System.out.printf("\n%s, A:%s, D:%s, %s\n",
                getNombre(),
                getPisoActual(),
                getPisoObjetivo(),
                getSentido());
    }
}
