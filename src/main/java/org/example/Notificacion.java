package org.example;

public class Notificacion {
    private Pasajero pasajero;
    private String estado;

    public Notificacion(Pasajero pasajero) {
        this.pasajero = pasajero;
        this.estado = "Pendiente";
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
