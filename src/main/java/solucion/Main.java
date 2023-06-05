package solucion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import solucion.Entidades.Elevador;
import solucion.Entidades.Pasajero;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        List<Pasajero> listaPasajeros = new LinkedList<>();

        listaPasajeros.add(new Pasajero("P1", 1,6));
        listaPasajeros.add(new Pasajero("P2", 4,3));
        listaPasajeros.add(new Pasajero("P3", 0,6));
        listaPasajeros.add(new Pasajero("P4", 0,6));

        // Despierto pasajeros
        for (Pasajero pasajero : listaPasajeros) {
            pasajero.start();
        }

        (new Elevador(1, "##1", listaPasajeros)).start();
        (new Elevador(0, "##2", listaPasajeros)).start();

    }
}
