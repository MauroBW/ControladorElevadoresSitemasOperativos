package solucion.Api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solucion.Entidades.LlamadosElevadoresManager;
import solucion.Entidades.Pasajero;

import java.util.List;

@RestController
public class PasajerosController {

    @PostMapping("/pasajero")
    public ResponseEntity<String> crearPersona(@RequestBody List<Pasajero> pasajeros) {
        for(Pasajero nuevoPasajero: pasajeros) {
            LlamadosElevadoresManager.agregarPasajero(nuevoPasajero);
            System.out.printf("API :: Nombre: %s | Peso: %s | PisoActual: %s | PisoObjetivo%s\n",
                    nuevoPasajero.getNombre(), nuevoPasajero.getPeso(), nuevoPasajero.getPisoActual(), nuevoPasajero.getPisoObjetivo());
        }
        return ResponseEntity.ok("Persona creada con éxito");
    }
}

