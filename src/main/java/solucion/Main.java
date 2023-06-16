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
    public static void main(String[] args) throws InterruptedException {


//        Helper.cargarSimulacion(Helper.leerSimulacion("simulaciones/prueba_2.csv"));

//        Thread.sleep(2000);
        SpringApplication.run(Main.class, args); // Si se quiere levantar la api, descomentar linea

        LlamadosElevadoresManager.agregarPasajero("P1", 300, 1, 6,0);
        LlamadosElevadoresManager.agregarPasajero("P2", 300, 6, 1,0);

        LlamadosElevadoresManager.agregarPasajero("P1", 300, 1, 6,0);
        LlamadosElevadoresManager.agregarPasajero("P2", 300, 6, 1,0);

        LlamadosElevadoresManager.agregarPasajero("P1", 300, 1, 6,0);
        LlamadosElevadoresManager.agregarPasajero("P2", 300, 6, 1,0);

        LlamadosElevadoresManager.agregarPasajero("P1", 300, 1, 6,0);
        LlamadosElevadoresManager.agregarPasajero("P2", 300, 6, 1,0);


        LlamadosElevadoresManager.agregarPasajero("P2", 90, 4, 6, 0);
        LlamadosElevadoresManager.agregarPasajero("P3", 85,3, 6, 0);
        LlamadosElevadoresManager.agregarPasajero("P4", 85,2, 6, 0);
        LlamadosElevadoresManager.agregarPasajero("P5", 85,0, 6, 0);
        LlamadosElevadoresManager.agregarPasajero("P6", 100,0, 6, 20);
        LlamadosElevadoresManager.agregarPasajero("P7", 70, 0, 6, 20);

        LlamadosElevadoresManager.iniciarElevadores();

    }
}
