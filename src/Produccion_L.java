import java.util.Random;

public class Produccion_L {
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

    public static int[] historicoDemandaTrimestral(double[][] ventas) {
        double[] trimestres = new double[4];
        for (int i = 0; i < anios; i++) {
            trimestres[0] += demandaTrimestre(ventas, i, 0);
            trimestres[1] += demandaTrimestre(ventas, i, 1);
            trimestres[2] += demandaTrimestre(ventas, i, 2);
            trimestres[3] += demandaTrimestre(ventas, i, 3);
        }

        int mejorTrimestre = 0;
        for (int i = 1; i < trimestres.length; i++) {
            if (trimestres[i] > trimestres[mejorTrimestre]) {
                mejorTrimestre = i;
            }
        }

        double promedio = trimestres[mejorTrimestre] / anios;
        return new int[] {mejorTrimestre + 1, (int) Math.round(promedio)};
    }

    private static double demandaTrimestre(double[][] ventas, int anio, int trimestre) {
        int mesInicio = trimestre * 3;
        double suma = 0;
        for (int i = 0; i < 3; i++) {
            suma += ventas[anio][mesInicio + i];
        }
        return suma;
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

        int[] resultado = historicoDemandaTrimestral(ventas);
        System.out.println("Trimestre con mayor demanda historica: " + resultado[0]);
        System.out.println("Cantidad promedio de demanda en ese trimestre: " + resultado[1]);

        System.out.println("\nInventario Ordenado:");
        double[] ventasOrdenado = ordenarInventario(ventas);
        imprimir(ventasOrdenado);

        // Reporte del mes con mayor venta de los años 2022, 2023 y 2024 usando método recursivo
        for (int year = 2022; year <= 2024; year++) {
            int mesMayorVenta = buscarMesMayorVenta(ventas, year, 0, 0);
            System.out.printf("Año %d: Mes con mayor venta es el %d con ventas de %.2f\n", year, mesMayorVenta + 1, ventas[year - 2022][mesMayorVenta]);
        }
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
Método recursivo para buscar el mes con mayor venta en un año específico
 */
    public static int buscarMesMayorVenta(double[][] ventas, int anio, int mesActual, int mesMax) {
        int baseYear = 2022;
        int iAnio = anio - baseYear;

        if (mesActual == ventas[iAnio].length) {
            return mesMax;
        }

        if (ventas[iAnio][mesActual] > ventas[iAnio][mesMax]) {
            mesMax = mesActual;
        }

        return buscarMesMayorVenta(ventas, anio, mesActual + 1, mesMax);
    }

    /*
    metodo para pronosticar la demanda usando los datos del año pasado ver1
     */
    /*
    metodo para calucar la cantidad optima de materiaprima
     */
}