import java.lang.reflect.Array;
import java.util.*;


public class Produccion {

    //Variables Bases
    public static int anioActual = 2024;
    public static int anioBase = 2022;
    public static int mesActual = 6;
    public static int anios = anioActual - anioBase + 1;
    public static int meses = 12;

    // Pilas para cada insumo
    public static Stack<Double>[] pilaPlata = new Stack[anios];
    public static Stack<Double>[] pilaCobre = new Stack[anios];
    public static Stack<Double>[] pilaFundente = new Stack[anios];
    public static Stack<Double>[] pilaAcido = new Stack[anios];
    public static Stack<Double>[] pilaGas = new Stack[anios];

    // Variables para registrar las acciones
    public static List<String> registroAcciones = new LinkedList<>();

    //Cola para la gestion de pedidos
    private static Queue<PedidosClientes> colaPedidosClientes = new LinkedList<>();

    //Metodo para Generar Data Aleatoria del Almacen
    public static void generarDataAleatoria(double[][] inventarioPlata, double[][] inventarioCobre,
                                            double[][] inventarioFundente, double[][] inventarioAcido,
                                            double[][] inventarioGas) {
        Random rand = new Random();
        for (int i = 0; i < anios; i++) {
            pilaPlata[i] = new Stack<>();
            pilaCobre[i] = new Stack<>();
            pilaFundente[i] = new Stack<>();
            pilaAcido[i] = new Stack<>();
            pilaGas[i] = new Stack<>();
            for (int j = 0; j < meses; j++) {
                inventarioPlata[i][j] = 3000 + rand.nextDouble() * 7000; // Valores Aleatorios entre 3000 y 10000 gramos de plata pura
                inventarioCobre[i][j] = (inventarioPlata[i][j] / 19);    // Cobre con valor cercano al 5% de la plata
                inventarioFundente[i][j] = (inventarioPlata[i][j] / 10); // Fundente cercano al 10% de la plata
                inventarioAcido[i][j] = (inventarioPlata[i][j] / 20);    // Ácido cercano al 5% de la plata
                inventarioGas[i][j] = (inventarioPlata[i][j] / 15);      // Gas cercano al 6.67% de la plata
                pilaPlata[i].push(inventarioPlata[i][j]);
                pilaCobre[i].push(inventarioCobre[i][j]);
                pilaFundente[i].push(inventarioFundente[i][j]);
                pilaAcido[i].push(inventarioAcido[i][j]);
                pilaGas[i].push(inventarioGas[i][j]);
            }
        }
        //Ajustes los meses del año actual
        int iAnios = anioActual - anioBase;
        for (int i = mesActual; i < meses; i++) {
            inventarioPlata[iAnios][i] = 0;
            inventarioCobre[iAnios][i] = 0;
            inventarioFundente[iAnios][i] = 0;
            inventarioAcido[iAnios][i] = 0;
            inventarioGas[iAnios][i] = 0;
        }
    }

    //Metodo para generar Ventas Aleatorias
    public static void generarVentaAleatoria (double[][] ventas) {
        Random rand = new Random();
        for (int i = 0; i < anios; i++) {
            for (int j = 0; j < meses; j++) {
                ventas[i][j] = 5000 + rand.nextDouble() * 10000;
            }
        }
        //Ajustes los meses del año actual
        int iAnios = anioActual - anioBase;
        for (int i = mesActual; i < meses; i++) {
            ventas[iAnios][i] = 0;
        }
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

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Mostrar Inventarios");
            System.out.println("2. Mostrar Ventas");
            System.out.println("3. Ordenar y Visualizar Ventas");
            System.out.println("4. Predecir Demanda Futura");
            System.out.println("5. Ordenar y Visualizar Inventario de Plata");
            System.out.println("6. Mes con Mayor Venta por Año");
            System.out.println("7. Consumir o Aumentar Insumos");
            System.out.println("8. Ver Registro de Consumos y Aumentos");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            int[] resultado = historicoDemandaTrimestral(ventas);

            switch (opcion) {
                case 1:
                    System.out.println("Inventario de Plata Pura:");
                    imprimirArreglo(inventarioPlata);

                    System.out.println("\nInventario de Cobre:");
                    imprimirArreglo(inventarioCobre);

                    System.out.println("\nInventario de Fundente:");
                    imprimirArreglo(inventarioFundente);

                    System.out.println("\nInventario de Ácido:");
                    imprimirArreglo(inventarioAcido);

                    System.out.println("\nInventario de Gas:");
                    imprimirArreglo(inventarioGas);
                    break;

                case 2:
                    System.out.println("\nVentas:");
                    imprimirArreglo(ventas);
                    System.out.println("\nTrimestre con mayor demanda historica: " + resultado[0]);
                    System.out.println("Cantidad promedio de demanda en ese trimestre: " + resultado[1]);
                    System.out.println(Arrays.toString(resultado));

                    double[] trimestres = new double[4];
                    for (int i = 0; i < anios; i++) {
                        trimestres[0] += demandaTrimestre(ventas, i, 0);
                        trimestres[1] += demandaTrimestre(ventas, i, 1);
                        trimestres[2] += demandaTrimestre(ventas, i, 2);
                        trimestres[3] += demandaTrimestre(ventas, i, 3);
                    }
                    System.out.println(Arrays.toString(trimestres));
                    break;

                case 3:
                    System.out.println("\nVentas Ordenadas:");
                    double[] ventasOrdenado = ordenarInventario(ventas);
                    imprimirArregloOrdenado(ventasOrdenado);
                    break;

                case 4:
                    int[] demandaFutura = predecirDemandaFuturaPorMeses(ventas, anios - 1, resultado[0] - 1);
                    System.out.println("\nPredicción de demanda para el próximo trimestre:");
                    for (int i = 0; i < demandaFutura.length; i++) {
                        System.out.println("Mes " + ((resultado[0] % 4) * 3 + i + 1) + ": " + demandaFutura[i]);
                    }
                    System.out.println(Arrays.toString(demandaFutura));
                    break;

                case 5:
                    System.out.println("\nInventario de Plata Ordenado de Menor a Mayor:");
                    ordenarYVisualizarInventarioPlata(inventarioPlata);
                    break;

                case 6:
                    System.out.println("\nReporte del mes con mayor venta de los años 2022, 2023 y 2024 usando método recursivo");
                    for (int year = 2022; year <= 2024; year++) {
                        int mesMayorVenta = buscarMesMayorVenta(ventas, year, 0, 0);
                        System.out.printf("Año %d: Mes con mayor venta es el %d con ventas de %.2f\n", year, mesMayorVenta + 1, ventas[year - 2022][mesMayorVenta]);
                    }
                    break;

                case 7:
                    gestionarInsumos(scanner);
                    break;

                case 8:
                    verRegistro();
                    break;

                case 9:
                    exit = true;
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
                    break;
            }
        }
    }

    public static void imprimir(double[] Arreglo) {
        for (double elemento:Arreglo) {
            System.out.printf("%8.2f ", elemento);
            System.out.println();
        }
    }

    public static void imprimirArreglo(double[][] Arreglo) {
        System.out.print("        "); // Espacio para el encabezado del año
        for (int j = 0; j < meses; j++) {
            System.out.printf("%8s ", "Mes " + (j + 1));
        }
        System.out.println();

        for (int i = 0; i < anios; i++) {
            System.out.printf("Año %2d: ", i + anioBase);
            for (int j = 0; j < meses; j++) {
                System.out.printf("%8.2f ", Arreglo[i][j]);
            }
            System.out.println();
        }
    }

    //Metodo recursivo 1 demanda trimestral
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

    //Metodo recursivo 2 demanda estimada siguiente trimestre
    public static int[] predecirDemandaFuturaPorMeses(double[][] ventas, int anioActual, int trimestreActual) {
        double tasaCrecimiento = calcularTasaCrecimiento(ventas, anioActual, trimestreActual);
        int mesInicio = (trimestreActual + 1) * 3 % 12; // Mes de inicio del siguiente trimestre
        int anioFuturo = (trimestreActual + 1) * 3 >= 12 ? anioActual + 1 : anioActual;

        int[] demandaFutura = new int[3];
        for (int i = 0; i < 3; i++) {
            int mesActual = (mesInicio + i) % 12;
            int anioEvaluar = (mesInicio + i) >= 12 ? anioFuturo : anioActual;
            double demandaActual = ventas[anioEvaluar][mesActual];
            demandaFutura[i] = (int) Math.round(demandaActual * (1 + tasaCrecimiento));
        }
        return demandaFutura;
    }

    private static double calcularTasaCrecimiento(double[][] ventas, int anioActual, int trimestreActual) {
        if (anioActual <= 0) {
            return 0;
        }
        double demandaActual = demandaTrimestre(ventas, anioActual, trimestreActual);
        double demandaAnterior = demandaTrimestre(ventas, anioActual - 1, trimestreActual);
        double tasaCrecimiento = (demandaActual - demandaAnterior) / demandaAnterior;
        return tasaCrecimiento + calcularTasaCrecimiento(ventas, anioActual - 1, trimestreActual) / anioActual;
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

    public static void imprimirArregloOrdenado(double[] arreglo) {
        int valoresPorLinea = 10;
        for (int i = 0; i < arreglo.length; i++) {
            System.out.printf("%.2f", arreglo[i]);
            if (i < arreglo.length - 1) {
                System.out.print(", ");
            }
            if ((i + 1) % valoresPorLinea == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public static void ordenarYVisualizarInventarioPlata(double[][] inventario) {
        int totalMeses = anios * meses;
        Produccion_L.ItemInventario[] items = new Produccion_L.ItemInventario[totalMeses];
        int indice = 0;

        for (int i = 0; i < anios; i++) {
            for (int j = 0; j < meses; j++) {
                items[indice++] = new Produccion_L.ItemInventario(inventario[i][j], anioBase + i, j + 1);
            }
        }

        items = bubbleSortItems(items);

        for (Produccion_L.ItemInventario item : items) {
            System.out.printf("Plata: %.2f gramos, Año: %d, Mes: %d\n", item.valor, item.anio, item.mes);
        }
    }

    public static Produccion_L.ItemInventario[] bubbleSortItems(Produccion_L.ItemInventario[] arr) {
        int n = arr.length;
        boolean swapped;
        swapped = false;
        for (int i = 0; i < n - 1; i++) {
            if (arr[i].valor > arr[i + 1].valor) {
                Produccion_L.ItemInventario temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
                swapped = true;
            }
        }
        n--;
        while (swapped) {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                if (arr[i].valor > arr[i + 1].valor) {
                    Produccion_L.ItemInventario temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }
            n--;
        }
        return arr;
    }

    static class ItemInventario {
        double valor;
        int anio;
        int mes;

        ItemInventario(double valor, int anio, int mes) {
            this.valor = valor;
            this.anio = anio;
            this.mes = mes;
        }
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
    public static void gestionarInsumos(Scanner scanner) {
        System.out.println("\n--- Gestión de Insumos ---");
        System.out.println("1. Plata");
        System.out.println("2. Cobre");
        System.out.println("3. Fundente");
        System.out.println("4. Ácido");
        System.out.println("5. Gas");
        System.out.print("Seleccione un insumo: ");
        int insumo = scanner.nextInt();

        System.out.println("\n--- Acción ---");
        System.out.println("1. Consumir");
        System.out.println("2. Aumentar");
        System.out.print("Seleccione una acción: ");
        int accion = scanner.nextInt();

        System.out.print("Ingrese la cantidad: ");
        double cantidad = scanner.nextDouble();

        modificarPila(insumo, accion, cantidad);
    }

    public static void modificarPila(int insumo, int accion, double cantidad) {
        Stack<Double>[] pila = null;
        String nombreInsumo = "";

        switch (insumo) {
            case 1:
                pila = pilaPlata;
                nombreInsumo = "Plata";
                break;
            case 2:
                pila = pilaCobre;
                nombreInsumo = "Cobre";
                break;
            case 3:
                pila = pilaFundente;
                nombreInsumo = "Fundente";
                break;
            case 4:
                pila = pilaAcido;
                nombreInsumo = "Ácido";
                break;
            case 5:
                pila = pilaGas;
                nombreInsumo = "Gas";
                break;
            default:
                System.out.println("Insumo no válido.");
                return;
        }

        if (accion == 1) {
            // Consumir
            if (!pila[anioActual - anioBase].isEmpty()) {
                pila[anioActual - anioBase].push(pila[anioActual - anioBase].pop() - cantidad);
                registrarAccion(nombreInsumo, "Consumir", cantidad);
            } else {
                System.out.println("No hay suficiente " + nombreInsumo + " para consumir.");
            }
        } else if (accion == 2) {
            // Aumentar
            pila[anioActual - anioBase].push(pila[anioActual - anioBase].pop() + cantidad);
            registrarAccion(nombreInsumo, "Aumentar", cantidad);
        } else {
            System.out.println("Acción no válida.");
        }
    }

    public static void registrarAccion(String insumo, String accion, double cantidad) {
        String registro = String.format("%s: %s %.2f gramos", insumo, accion, cantidad);
        registroAcciones.add(registro);
    }

    public static void verRegistro() {
        System.out.println("\n--- Registro de Consumos y Aumentos ---");
        if (registroAcciones.isEmpty()) {
            System.out.println("No hay acciones registradas.");
        } else {
            for (String registro : registroAcciones) {
                System.out.println(registro);
            }
        }
        System.out.println("\n--- Estado Actual de los Insumos ---");
        System.out.printf("Plata: %.2f gramos\n", pilaPlata[anioActual - anioBase].peek());
        System.out.printf("Cobre: %.2f gramos\n", pilaCobre[anioActual - anioBase].peek());
        System.out.printf("Fundente: %.2f gramos\n", pilaFundente[anioActual - anioBase].peek());
        System.out.printf("Ácido: %.2f gramos\n", pilaAcido[anioActual - anioBase].peek());
        System.out.printf("Gas: %.2f gramos\n", pilaGas[anioActual - anioBase].peek());
    }

    //
}