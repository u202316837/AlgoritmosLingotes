import java.util.Random;

public class Produccion {
    public static int anioActual = 2024;
    public static int anioBase = 2022;
    public static int mesActual = 6;
    public static int anios = anioActual - anioBase + 1;
    public static int meses = 12;

    public static void generarDataAleatoria(double[][] inventarioPlata, double[][] inventarioCobre,
                                            double[][] inventarioFundente, double[][] inventarioAcido,
                                            double[][] inventarioGas) {
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

    public static void generarVentaAleatoria (double[][] ventas) {
        Random rand = new Random();
        for (int i = 0; i < anios; i++) {
            for (int j = 0; j < meses; j++) {
                ventas[i][j] = 5000 + rand.nextDouble() * 10000;
            }
        }
    }

    public static void ajustarAnioActual(double[][] inventarioPlata, double[][] inventarioCobre,
                                         double[][] inventarioFundente, double[][] inventarioAcido,
                                         double[][] inventarioGas, double[][] ventas) {
        int iAnios = anioActual - anioBase;
        for (int i = mesActual; i < meses; i++) {
            inventarioPlata[iAnios][i] = 0;
            inventarioCobre[iAnios][i] = 0;
            inventarioFundente[iAnios][i] = 0;
            inventarioAcido[iAnios][i] = 0;
            inventarioGas[iAnios][i] = 0;
            ventas[iAnios][i] = 0;
        }
    }

    public static double calcularDemandaEstacional(double[][] ventas, int mes) {
        return (int) calcularDemandaMes(ventas, ventas.length - 1, mes) / (double)ventas.length;
    }

    private static double calcularDemandaMes(double[][] ventas, int anio, int mes) {
        if (anio < 0) {
            return 0;
        }
        return ventas[anio][mes] + calcularDemandaMes(ventas, anio - 1, mes);
    }

    public static void main(String[] args) {

        double[][] inventarioPlata = new double[anios][meses];
        double[][] inventarioCobre = new double[anios][meses];
        double[][] inventarioFundente = new double[anios][meses];
        double[][] inventarioAcido = new double[anios][meses];
        double[][] inventarioGas = new double[anios][meses];
        double[][] ventas = new double[anios][meses];

        generarDataAleatoria(inventarioPlata, inventarioCobre, inventarioFundente, inventarioAcido, inventarioGas);
        generarVentaAleatoria(ventas);
        ajustarAnioActual(inventarioPlata, inventarioCobre, inventarioFundente, inventarioAcido, inventarioGas, ventas);

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

        System.out.println("Demanda stacional para el mes actual:");
        System.out.println(calcularDemandaEstacional(ventas, mesActual));

        System.out.println("\nInventario Ordenado:");
        double[] ventasOrdenado = ordenarInventario(ventas);
        imprimir(ventasOrdenado);
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

    public static void imprimir(double[] Arreglo) {
        for (double elemento:Arreglo) {
            System.out.printf("%8.2f ", elemento);
            System.out.println();
        }
    }

    public static void imprimirArreglo(double[][] Arreglo, int anios, int meses) {
        for (int i = 0; i < anios; i++) {
            for (int j = 0; j < meses; j++) {
                System.out.printf("%8.2f ", Arreglo[i][j]);
            }
            System.out.println();
        }
    }
    public static double[] ordenarInventario(double[][] Arreglo) {
        int totalMeses = anios * meses;
        double[] inventarioTotalOrdenado = new double[totalMeses];
        int indice = 0;
        for (int i = 0; i < anios; i++) {
            for (int j = 0; j < meses; j++) {
                inventarioTotalOrdenado[indice++] = Arreglo[i][j];
            }
        }
        //Ordenamiento del inventarioTotal
        for (int i = 0; i < totalMeses-1; i++) {
            int indiceTemp = i;
            for (int j = i+1; j < totalMeses; j++) {
                if (inventarioTotalOrdenado[indiceTemp] > inventarioTotalOrdenado[j]) {
                    indiceTemp = j;
                }
            }
            double temp = inventarioTotalOrdenado[indiceTemp];
            inventarioTotalOrdenado[indiceTemp]=inventarioTotalOrdenado[i];
            inventarioTotalOrdenado[i]=temp;
        }
        return inventarioTotalOrdenado;
    }

    /*
    metodo para pronosticar la demanda usando los datos del año pasado ver1
     */
    /*
    metodo para calucar la cantidad optima de materiaprima
     */
}