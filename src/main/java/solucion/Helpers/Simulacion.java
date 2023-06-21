package solucion.Helpers;

public enum Simulacion {
    LOAD_TEST("simulaciones/loadTest.csv"),
    STRESS_TEST("simulaciones/stressTest.csv"),
    SOAK_TEST("simulaciones/SOAK.csv");

    private final String archivoCSV;

    Simulacion(String archivoCSV) {
        this.archivoCSV = archivoCSV;
    }

    public String getArchivoCSV() {
        return archivoCSV;
    }

    public String getNombreSimulacion() {
        String[] info = this.archivoCSV.split("/");
        String[] purgar = info[1].split("\\.");
        return purgar[0];
    }
}