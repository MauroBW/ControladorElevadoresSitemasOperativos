package solucion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import solucion.Entidades.LlamadosElevadoresManager;
import solucion.Helpers.Helper;
import solucion.Helpers.Logger;
import solucion.Helpers.Simulacion;


@SpringBootApplication
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Simulacion simulacionActual = Simulacion.STRESS_TEST;
        String SIMULACION_PATH = (simulacionActual.getArchivoCSV());

        Logger.setSimulacionActual(simulacionActual.getNombreSimulacion());

        Helper.cargarSimulacion(Helper.leerSimulacion(SIMULACION_PATH));
        Helper.informacionSimulacion(SIMULACION_PATH);

        SpringApplication.run(Main.class, args); // Si se quiere levantar la api, descomentar linea
        LlamadosElevadoresManager.setPriorizarDemorados(true);
        LlamadosElevadoresManager.iniciarElevadores(5);
    }
}
