package solucion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import solucion.Entidades.Elevador;
import solucion.Entidades.Pasajero;
import solucion.Entidades.LlamadosElevadoresManager;
import solucion.Helpers.Helper;
import solucion.Helpers.Logger;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

//        SpringApplication.run(Main.class, args); // Si se quiere levantar la api, descomentar linea

        List<Pasajero> listaPasajeros = new LinkedList<>();

        LlamadosElevadoresManager.agregarPasajero("P1", 90, 1, 6);
        LlamadosElevadoresManager.agregarPasajero("P2", 85,4, 3);
        LlamadosElevadoresManager.agregarPasajero("P3", 100,0, 6);
        LlamadosElevadoresManager.agregarPasajero("P4", 70, 0, 6);

        LlamadosElevadoresManager.iniciarElevadores();

    }
}
