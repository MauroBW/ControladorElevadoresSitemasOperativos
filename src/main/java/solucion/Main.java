package solucion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import solucion.Entidades.LlamadosElevadoresManager;
import solucion.Helpers.Helper;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws InterruptedException {

        String SIMULACION_PATH = null;
        if (SIMULACION_PATH == null) {
            SIMULACION_PATH = "loadTest.csv";
        }

        Helper.cargarSimulacion(
                Helper.leerSimulacion(
                        String.format("simulaciones/%s", SIMULACION_PATH)));

        Helper.informacionSimulacion(
                String.format(
                        "simulaciones/%s", SIMULACION_PATH));

        SpringApplication.run(Main.class, args); // Si se quiere levantar la api, descomentar linea

//        LlamadosElevadoresManager.agregarPasajero("P1", 300, 1, 6,0);
//        LlamadosElevadoresManager.agregarPasajero("P2", 90, 4, 6, 0);
//        LlamadosElevadoresManager.agregarPasajero("P3", 85,3, 6, 0);
//        LlamadosElevadoresManager.agregarPasajero("P4", 85,2, 6, 0);
//        LlamadosElevadoresManager.agregarPasajero("P5", 85,0, 6, 0);
//        LlamadosElevadoresManager.agregarPasajero("P6", 100,0, 6, 20);
//        LlamadosElevadoresManager.agregarPasajero("P7", 70, 0, 6, 20);

        LlamadosElevadoresManager.iniciarElevadores();

    }
}
