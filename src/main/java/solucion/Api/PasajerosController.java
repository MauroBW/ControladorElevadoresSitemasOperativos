package solucion.Api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solucion.Entidades.LlamadosElevadoresManager;
import solucion.Entidades.Pasajero;

@RestController
public class PasajerosController {

    @PostMapping("/pasajero")
    public ResponseEntity<String> crearPersona(@RequestBody Pasajero pasajero) {
        new LlamadosElevadoresManager().agregarPasajero(pasajero);
        System.out.printf("API :: Nombre: %s | Peso: %s | PisoActual: %s | PisoObjetivo%s\n",pasajero.getNombre(), pasajero.getPeso(), pasajero.getPisoActual(), pasajero.getPisoObjetivo());
        return ResponseEntity.ok("Persona creada con éxito");
    }
}

