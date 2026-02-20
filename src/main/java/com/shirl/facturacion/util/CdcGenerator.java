package com.shirl.facturacion.util;

public class CdcGenerator {

    public static String generarCDC(
            String ruc,
            String tipoDoc,
            String establecimiento,
            String puntoExpedicion,
            String numeroDocumento,
            String tipoEmision,
            String codigoSeguridad
    ) {

        String base = ruc
                + tipoDoc
                + establecimiento
                + puntoExpedicion
                + numeroDocumento
                + tipoEmision
                + codigoSeguridad;

        int dv = calcularModulo11(base);

        return base + dv;
    }

    private static int calcularModulo11(String numero) {

        int suma = 0;
        int peso = 2;

        for (int i = numero.length() - 1; i >= 0; i--) {

            int digito = Character.getNumericValue(numero.charAt(i));
            suma += digito * peso;

            peso++;
            if (peso > 7) {
                peso = 2;
            }
        }

        int resto = suma % 11;
        int dv = 11 - resto;

        if (dv == 11) return 0;
        if (dv == 10) return 1;

        return dv;
    }
}