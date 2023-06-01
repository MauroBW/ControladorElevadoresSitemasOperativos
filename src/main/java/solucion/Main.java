package solucion;

import solucion.Entidades.Pasajero;

public class Main {
    public static void main(String[] args) {

        (new Pasajero("P1", 4, 2)).start();
        (new Pasajero("P2", 4,6)).start();
        (new Pasajero("P3", 0,1)).start();
        (new Pasajero("P4", 0, 5)).start();
        (new Pasajero("P5", 0 ,5)).start();
        (new Pasajero("P6", 0 ,0)).start();

    }
}
