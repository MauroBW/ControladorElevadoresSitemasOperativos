package solucion.Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Helper {
    public static String generarIdentificador() {
        Date fechaActual = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String fechaFormateada = dateFormat.format(fechaActual);
        String sb = "";

        // Concateno Fecha
        sb += (fechaFormateada + "_");

        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(caracteres.length());
            sb += (caracteres.charAt(index));
        }

        return sb.toString();
    }
}
