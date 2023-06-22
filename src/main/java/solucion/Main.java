package solucion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import solucion.Entidades.LlamadosElevadoresManager;
import solucion.Helpers.DataConstant;
import solucion.Helpers.Helper;
import solucion.Helpers.Logger;
import solucion.Helpers.Simulacion;

import java.io.File;


@SpringBootApplication
public class Main {
    public static void main(String[] args) throws InterruptedException {

        /**
         * =====================================================================================
         *                                  LEER README
         * =====================================================================================
         */

        // Crea Log folder si no existe
        File dir = new File("logs");
        if (!dir.exists()) dir.mkdir();

        // Velocidad de Simulacion
        DataConstant.SIMULATION_SPEED = DataConstant.SLOW_RUN;
        Simulacion simulacionActual = Simulacion.SOAK_TEST;

        String SIMULACION_PATH = (simulacionActual.getArchivoCSV());

        Logger.setSimulacionActual(simulacionActual.getNombreSimulacion());

        // Lectura de simulación
        Helper.cargarSimulacion(Helper.leerSimulacion(SIMULACION_PATH));
        Helper.informacionSimulacion(SIMULACION_PATH);

        // Run de SPRING BOOT
        SpringApplication.run(Main.class, args);

        // Indicar cantidad de elevadores a simular
        LlamadosElevadoresManager.iniciarElevadores(3);
    }
}
