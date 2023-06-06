package solucion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import solucion.Entidades.Elevador;
import solucion.Entidades.Pasajero;
import solucion.Entidades.LlamadosElevadoresManager;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        LlamadosElevadoresManager manager = new LlamadosElevadoresManager();
        SpringApplication.run(Main.class, args);
        List<Pasajero> listaPasajeros = new LinkedList<>();

        manager.agregarPasajero("P1", 90, 1, 6);
        manager.agregarPasajero("P2", 85,4, 3);
        manager.agregarPasajero("P3", 100,0, 6);
        manager.agregarPasajero("P4", 70, 0, 6);

        manager.iniciarElevadores();

    }
}
