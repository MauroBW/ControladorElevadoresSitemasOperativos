package solucion.Api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solucion.Entidades.Llamado;
import solucion.Entidades.LlamadosElevadoresManager;
import solucion.Entidades.Pasajero;

import java.util.List;

@RestController
public class PasajerosController {

    @PostMapping("/generadorCarga")
    public ResponseEntity<String> generadorCarga(@RequestBody List<Llamado> llamados) {
        String res = "";

        for(Llamado llamado : llamados) {
            res += String.format("\nNuevo Pasajero: \n%s\n%s\n%s\n%s\n%s",
                    llamado.getNombre(),
                    llamado.getPeso(),
                    llamado.getPisoActual(),
                    llamado.getPisoObjetivo(),
                    llamado.getTiempoInicio());
            Pasajero pasajero = new Pasajero(llamado.getNombre(),
                    llamado.getPeso(),
                    llamado.getPisoActual(),
                    llamado.getPisoObjetivo(),
                    llamado.getTiempoInicio());
            LlamadosElevadoresManager.agregarPasajero(pasajero);
        }
        return ResponseEntity.ok(res);
    }
}

