package academia.modelo;

import java.util.LinkedList;

/**
 * CLASE Estudiante — hereda de Persona
 *
 * HERENCIA: "extends Persona" significa que Estudiante HEREDA todo lo de Persona.
 * - Hereda los atributos: nombre, id, email (aunque sean private, existen en el objeto)
 * - Hereda los métodos: getNombre(), getId(), getEmail()
 * - DEBE implementar: mostrarInformacion() porque es abstracto en Persona
 *
 * POLIMORFISMO: Un Estudiante ES una Persona. Podemos escribir:
 *   Persona p = new Estudiante(...);
 *   p.mostrarInformacion(); // Ejecuta la versión de Estudiante, no la de Persona
 *
 * Esto se llama ENLACE DINÁMICO o DISPATCH DINÁMICO.
 */
public class Estudiante extends Persona {

    // Atributos PROPIOS de Estudiante (no están en Persona)
    private int semestre;                   // Semestre actual del estudiante

    /**
     * MATRIZ BIDIMENSIONAL de notas:
     * - Dimensión 1 (filas):    10 semestres máximo
     * - Dimensión 2 (columnas): 20 materias por semestre máximo
     * - Double (con D mayúscula): puede ser null si no hay nota (a diferencia de double primitivo)
     *
     * Ejemplo: notas[2][5] = 4.3 → En el semestre 3, materia 6, la nota es 4.3
     */
    private Double[][] notas;               // Double[10][20]

    /**
     * Arreglo paralelo: lleva la cuenta de cuántas materias tiene registradas en cada semestre.
     * Si contadorMaterias[0] = 3, el semestre 1 tiene 3 materias.
     */
    private int[] contadorMaterias;         // int[10]

    /**
     * LISTA ENLAZADA: historial de códigos de materias cursadas.
     * ¿Por qué LinkedList y no ArrayList?
     * - LinkedList es más eficiente para agregar al principio o al final (O(1))
     * - No necesitamos acceso aleatorio por índice, solo recorrerla
     * - Representa la naturaleza dinámica del historial (crece con el tiempo)
     */
    private LinkedList<String> historialMaterias;

    /**
     * Constructor de Estudiante.
     * "super(nombre, id, email)" llama al constructor de Persona.
     * Esto es REUSO DE CÓDIGO: no repetimos la asignación de nombre, id, email.
     */
    public Estudiante(String nombre, String id, String email, int semestre) {
        super(nombre, id, email);                   // Inicializa atributos de Persona
        this.semestre = semestre;
        this.notas = new Double[10][20];            // 10 semestres × 20 materias
        this.contadorMaterias = new int[10];        // Inicializado a 0 por defecto
        this.historialMaterias = new LinkedList<>(); // Lista vacía
    }

    /**
     * SOBRESCRITURA (@Override) de mostrarInformacion()
     *
     * @Override le dice al compilador: "Esta es la implementación de la clase padre".
     * Si el nombre del método está mal escrito, Java detecta el error.
     *
     * POLIMORFISMO en acción: si alguien tiene una referencia Persona p = new Estudiante(...)
     * y llama p.mostrarInformacion(), Java ejecuta ESTA versión, no la de Persona.
     */
    @Override
    public void mostrarInformacion() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        INFORMACIÓN DEL ESTUDIANTE    ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ ID:       " + getId());        // Accede con getter (herencia)
        System.out.println("║ Nombre:   " + getNombre());
        System.out.println("║ Email:    " + getEmail());
        System.out.println("║ Semestre: " + semestre);
        System.out.printf( "║ Promedio acumulado: %.2f%n", getPromedioAcumulado());
        System.out.println("║ Materias en historial: " + historialMaterias.size());
        System.out.println("╚══════════════════════════════════════╝");
    }

    /**
     * Registra una nota en la matriz bidimensional.
     *
     * @param semestreIdx Índice del semestre (0-9)
     * @param nota        Nota entre 0.0 y 5.0
     *
     * Lógica de la matriz:
     * - notas[semestreIdx][contadorMaterias[semestreIdx]] apunta a la próxima celda libre
     * - Luego incrementamos el contador para esa fila
     */
    public void registrarNota(int semestreIdx, String codigoMateria, double nota) {
        // Validaciones de rango
        if (semestreIdx < 0 || semestreIdx >= 10) {
            System.out.println("Error: Semestre inválido. Debe estar entre 1 y 10.");
            return;
        }
        if (nota < 0.0 || nota > 5.0) {
            System.out.println("Error: Nota inválida. Debe estar entre 0.0 y 5.0.");
            return;
        }
        if (contadorMaterias[semestreIdx] >= 20) {
            System.out.println("Error: El semestre ya tiene el máximo de 20 materias.");
            return;
        }

        // Guardamos la nota en la celda correspondiente
        int columna = contadorMaterias[semestreIdx]; // Próxima posición libre en esa fila
        notas[semestreIdx][columna] = nota;          // Asignación en la matriz
        contadorMaterias[semestreIdx]++;             // Avanzamos el contador

        // Agregamos la materia al historial (LinkedList.add() agrega al final → O(1))
        if (!historialMaterias.contains(codigoMateria)) {
            historialMaterias.add(codigoMateria);
        }
    }

    /**
     * Calcula el promedio de un semestre específico.
     * Recorre solo las celdas con notas reales (hasta contadorMaterias[semestreIdx]).
     *
     * @param semestreIdx Índice del semestre (0 = primer semestre)
     * @return Promedio del semestre, o 0.0 si no hay notas
     */
    public double getPromedio(int semestreIdx) {
        if (semestreIdx < 0 || semestreIdx >= 10) return 0.0;
        int cantidad = contadorMaterias[semestreIdx]; // Cuántas notas hay en este semestre
        if (cantidad == 0) return 0.0;               // Evitar división por cero

        double suma = 0.0;
        // Recorremos solo hasta "cantidad", no hasta 20 (las demás son null)
        for (int j = 0; j < cantidad; j++) {
            suma += notas[semestreIdx][j]; // Auto-unboxing: Double → double
        }
        return suma / cantidad;
    }

    /**
     * Calcula el promedio acumulado de TODOS los semestres.
     * Itera sobre la matriz fila por fila.
     */
    public double getPromedioAcumulado() {
        double suma = 0.0;
        int total = 0;

        // Recorremos las 10 filas (semestres)
        for (int i = 0; i < 10; i++) {
            // Dentro de cada semestre, recorremos las materias registradas
            for (int j = 0; j < contadorMaterias[i]; j++) {
                suma += notas[i][j];
                total++;
            }
        }
        return (total == 0) ? 0.0 : suma / total; // Operador ternario para evitar /0
    }

    /**
     * Genera el reporte académico completo del estudiante.
     * Recorre la matriz bidimensional semestre por semestre.
     */
    public void mostrarReporteAcademico() {
        System.out.println("\n══════ REPORTE ACADÉMICO ══════");
        System.out.println("Estudiante: " + getNombre() + " (ID: " + getId() + ")");
        System.out.println("─────────────────────────────────");

        boolean tieneDatos = false;
        // i = índice de semestre (0 a 9)
        for (int i = 0; i < 10; i++) {
            if (contadorMaterias[i] > 0) { // Solo mostramos semestres con datos
                tieneDatos = true;
                System.out.println("\nSemestre " + (i + 1) + ":");
                // j = índice de materia dentro del semestre
                for (int j = 0; j < contadorMaterias[i]; j++) {
                    double nota = notas[i][j]; // Obtenemos la nota de esa celda
                    String estado = (nota >= 3.0) ? "✓ Aprobada" : "✗ Reprobada";
                    System.out.printf("  Materia %d: %.1f  %s%n", (j + 1), nota, estado);
                }
                System.out.printf("  Promedio del semestre: %.2f%n", getPromedio(i));
            }
        }

        if (!tieneDatos) {
            System.out.println("  Sin notas registradas aún.");
        }

        System.out.println("\n═══════ RESUMEN ═══════");
        System.out.printf("Promedio acumulado: %.2f%n", getPromedioAcumulado());
        System.out.println("Materias en historial: " + historialMaterias.size());
    }

    /**
     * Agrega una materia al historial (sin nota, solo registro de inscripción activa).
     * La LinkedList.add() tiene complejidad O(1) al agregar al final.
     */
    public void agregarAlHistorial(String codigoMateria) {
        if (!historialMaterias.contains(codigoMateria)) { // Evitar duplicados
            historialMaterias.add(codigoMateria);
        }
    }

    /**
     * Elimina una materia del historial.
     * LinkedList.remove() busca y elimina por valor → O(n)
     */
    public void eliminarDelHistorial(String codigoMateria) {
        historialMaterias.remove(codigoMateria); // Busca y elimina
    }

    // ====== GETTERS Y SETTERS ======

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        if (semestre < 1 || semestre > 10) {
            throw new IllegalArgumentException("Semestre debe estar entre 1 y 10.");
        }
        this.semestre = semestre;
    }

    public Double[][] getNotas() {
        return notas; // Retorna referencia a la matriz completa
    }

    public int[] getContadorMaterias() {
        return contadorMaterias;
    }

    public LinkedList<String> getHistorialMaterias() {
        return historialMaterias;
    }

    /**
     * toString() → versión corta para mostrar en listas
     */
    @Override
    public String toString() {
        return String.format("Estudiante[ID=%s, Nombre=%s, Semestre=%d, Promedio=%.2f]",
                getId(), getNombre(), semestre, getPromedioAcumulado());
    }
}
