package org.example;

import java.util.ArrayList;
import java.util.List;

public class ElevadorNotificaciones extends Elevador{
    private List<Notificacion> notificaciones;
    public ElevadorNotificaciones(String nombreElevador) {
        super(nombreElevador);
        this.notificaciones = new ArrayList<>();
    }

    @Override
    public void run(){
        System.out.println("Hola Mundo");
    }
}
