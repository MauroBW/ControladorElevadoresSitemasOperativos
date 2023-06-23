package solucion.Api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import solucion.Entidades.Llamado;
import solucion.Entidades.LlamadosElevadoresManager;
import solucion.Entidades.Pasajero;
import solucion.Helpers.DataConstant;

import java.util.List;

@RestController
public class SpeedController {

    @PostMapping("/speed")
    public ResponseEntity<String> changeSpeed(@RequestBody String speed) {
        String res = "";

        switch (speed) {
            case "slow":
                DataConstant.SIMULATION_SPEED = DataConstant.SLOW_RUN;
                res += DataConstant.SIMULATION_SPEED;
                break;
            case "fast":
                DataConstant.SIMULATION_SPEED = DataConstant.FAST_RUN;
                res += DataConstant.SIMULATION_SPEED;
                break;
            case "mid":
                DataConstant.SIMULATION_SPEED = DataConstant.MID_RUN;
                res += DataConstant.SIMULATION_SPEED;
                break;
        }

        return ResponseEntity.ok(res);
    }
}
