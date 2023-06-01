package solucion.Entidades;

public class Pasajero extends Thread {

    public int tiempo = 0;
    public String nombre;
    public int pisoActual;
    public int pisoObjetivo;

    public Pasajero(String nombre, int pisoActual, int pisoObjetivo) {
        this.nombre = nombre;
        this.pisoActual = pisoActual;
        this.pisoObjetivo = pisoObjetivo;
    }


    @Override
    public void run() {
        while (true) {
            tick();

            if (getPisoActual() == getPisoObjetivo()) {
                System.out.println("Me Bajo!!");
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) { }
            System.out.printf("{ Nombre: %s \nTiempo: %s \nPisoObjetivo: %s \nPisoActual: %s \n}\n",
                    getNombre(), getTiempo(), getPisoObjetivo(), getPisoActual());
        }
    }

    public void tick() {
        this.tiempo += 1;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int getPisoObjetivo() {
        return pisoObjetivo;
    }

    public int getPisoActual() {
        return pisoActual;
    }

    public String getNombre() {
        return nombre;
    }
}
