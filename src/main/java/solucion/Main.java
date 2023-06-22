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

        // Crea Log folder si no existe
        File dir = new File("logs");
        if (!dir.exists()) dir.mkdir();

        DataConstant.SIMULATION_SPEED = DataConstant.FAST_RUN;
        Simulacion simulacionActual = Simulacion.LOAD_TEST;
        String SIMULACION_PATH = (simulacionActual.getArchivoCSV());

        Logger.setSimulacionActual(simulacionActual.getNombreSimulacion());

        Helper.cargarSimulacion(Helper.leerSimulacion(SIMULACION_PATH));
        Helper.informacionSimulacion(SIMULACION_PATH);

        SpringApplication.run(Main.class, args); // Si se quiere levantar la api, descomentar linea
        LlamadosElevadoresManager.iniciarElevadores(3);
    }
}
