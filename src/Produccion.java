import java.util.Random;

public class Produccion {
    public static void generarDataAleatoria(double[][] inventarioPlata, double[][] inventarioCobre,
                                            double[][] inventarioFundente, double[][] inventarioAcido,
                                            double[][] inventarioGas, int anios, int meses) {
        Random rand = new Random();
        for (int i = 0; i < anios; i++) {
            for (int j = 0; j < meses; j++) {
                inventarioPlata[i][j] = 3000 + rand.nextDouble() * 7000; // Valores Aleatorios entre 3000 y 10000 gramos de plata pura
                inventarioCobre[i][j] = (inventarioPlata[i][j] / 19);    // Cobre con valor cercano al 5% de la plata
                inventarioFundente[i][j] = (inventarioPlata[i][j] / 10); // Fundente cercano al 10% de la plata
                inventarioAcido[i][j] = (inventarioPlata[i][j] / 20);    // Ácido cercano al 5% de la plata
                inventarioGas[i][j] = (inventarioPlata[i][j] / 15);      // Gas cercano al 6.67% de la plata
            }
        }
    }

    public static void generarVentaAleatoria (double[][] ventas, int anios, int meses) {
        Random rand = new Random();
        for (int i = 0; i < anios; i++) {
            for (int j = 0; j < meses; j++) {
                ventas[i][j] = 5000 + rand.nextDouble() * 10000;
            }
        }
    }

    public static void ajustarAnioActual(double[][] inventarioPlata, double[][] inventarioCobre,
                                         double[][] inventarioFundente, double[][] inventarioAcido,
                                         double[][] inventarioGas, double[][] ventas, int months,
                                         int anioActual, int mesActual) {
        int baseYear = 2022;
        int iAnios = anioActual - baseYear;
        for (int i = mesActual; i < months; i++) {
            inventarioPlata[iAnios][i] = 0;
            inventarioCobre[iAnios][i] = 0;
            inventarioFundente[iAnios][i] = 0;
            inventarioAcido[iAnios][i] = 0;
            inventarioGas[iAnios][i] = 0;
            ventas[iAnios][i] = 0;
        }
    }

    public static void main(String[] args) {

        int anios = 3;
        int meses = 12;

        double[][] inventarioPlata = new double[anios][meses];
        double[][] inventarioCobre = new double[anios][meses];
        double[][] inventarioFundente = new double[anios][meses];
        double[][] inventarioAcido = new double[anios][meses];
        double[][] inventarioGas = new double[anios][meses];
        double[][] ventas = new double[anios][meses];

        generarDataAleatoria(inventarioPlata, inventarioCobre, inventarioFundente, inventarioAcido, inventarioGas, anios, meses);
        generarVentaAleatoria(ventas, anios, meses);
        ajustarAnioActual(inventarioPlata, inventarioCobre, inventarioFundente, inventarioAcido, inventarioGas, ventas, meses, 2024, 6);

        System.out.println("Inventario de Plata Pura:");
        imprimirArreglo(inventarioPlata, anios, meses);

        System.out.println("\nInventario de Cobre:");
        imprimirArreglo(inventarioCobre, anios, meses);

        System.out.println("\nInventario de Fundente:");
        imprimirArreglo(inventarioFundente, anios, meses);

        System.out.println("\nInventario de Ácido:");
        imprimirArreglo(inventarioAcido, anios, meses);

        System.out.println("\nInventario de Gas:");
        imprimirArreglo(inventarioGas, anios, meses);

        System.out.println("\nVentas:");
        imprimirArreglo(ventas, anios, meses);

        /*
        Arreglo con inventario actual

        variable demandaDelMes = metodo de prediccion(mes)

        condicion
        inventario seria suficienciente
            productos terminados
        o
        no es suficiente
            comprar materia prima

            productos terminados
         */

        /*
        generar informe de datos de otros años o actual
         */
    }

    public static void imprimirArreglo(double[][] Arreglo, int anios, int meses) {
        for (int i = 0; i < anios; i++) {
            for (int j = 0; j < meses; j++) {
                System.out.printf("%8.2f ", Arreglo[i][j]);
            }
            System.out.println();
        }
    }

    /*
    metodo para pronosticar la demanda usando los datos del año pasado
     */
    /*
    metodo para calucar la cantidad optima de materiaprima
     */
}
