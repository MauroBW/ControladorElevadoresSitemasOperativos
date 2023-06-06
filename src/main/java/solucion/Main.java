package solucion;

import solucion.Entidades.Elevador;
import solucion.Entidades.Pasajero;
import solucion.Entidades.LlamadosElevadoresManager;
import solucion.Helpers.Helper;
import solucion.Helpers.Logger;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {


        System.out.println(Helper.generarIdentificador());
        Logger.saveLog("Hola.txt", "Hola Mundo");
//        LlamadosElevadoresManager manager = new LlamadosElevadoresManager();
//
//        manager.agregarPasajero("P1", 90, 1, 6);
//        manager.agregarPasajero("P2", 85,4, 3);
//        manager.agregarPasajero("P3", 100,0, 6);
//        manager.agregarPasajero("P4", 70, 0, 6);
//
//        manager.iniciarElevadores();

    }
}
