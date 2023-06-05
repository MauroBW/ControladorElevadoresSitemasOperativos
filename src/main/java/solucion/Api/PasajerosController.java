package solucion.Api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solucion.Entidades.Pasajero;

@RestController
public class PasajerosController {

    @PostMapping("/pasajero")
    public ResponseEntity<String> crearPersona(@RequestBody Pasajero pasajero) {
        System.out.println("Hola mundo");
        System.out.printf("API :: Nombre: %s | PisoActual: %s | PisoObjetivo%s",pasajero.getNombre(),pasajero.getPisoActual(), pasajero.getPisoObjetivo());
        // Devuelve una respuesta HTTP 200 con un mensaje de éxito
        return ResponseEntity.ok("Persona creada con éxito");
    }
}

