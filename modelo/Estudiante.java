package academia.modelo;

import java.util.LinkedList;

/**
 * CLASE Estudiante
 * 
 * Esta clase hereda de Persona.
 * Aquí guardamos información del estudiante,
 * sus notas y su historial de materias.
 */
public class Estudiante extends Persona {

    // ===== ATRIBUTOS =====

    // semestre actual del estudiante
    private int semestre;

    /**
     * MATRIZ DE NOTAS
     * 
     * 10 filas = 10 semestres
     * 20 columnas = máximo 20 materias por semestre
     */
    private Double[][] notas;

    /**
     * Este arreglo guarda cuántas materias
     * tiene cada semestre.
     */
    private int[] contadorMaterias;

    /**
     * Lista enlazada donde guardamos
     * el historial de materias vistas.
     */
    private LinkedList<String> historialMaterias;

    /**
     * CONSTRUCTOR
     * 
     * super() llama al constructor de Persona.
     */
    public Estudiante(String nombre, String id, String email, int semestre) {

        // constructor de Persona
        super(nombre, id, email);

        // inicializamos atributos
        this.semestre = semestre;

        // matriz de 10 x 20
        notas = new Double[10][20];

        // arreglo de 10 posiciones
        contadorMaterias = new int[10];

        // lista vacía
        historialMaterias = new LinkedList<>();
    }

    /**
     * MÉTODO SOBREESCRITO
     * 
     * Este método viene de Persona.
     */
    @Override
    public void mostrarInformacion() {

        System.out.println("ID: " + getId());
        System.out.println("Nombre: " + getNombre());
        System.out.println("Email: " + getEmail());
        System.out.println("Semestre: " + semestre);
        System.out.println("Promedio: " + getPromedioAcumulado());
    }

    /**
     * REGISTRAR NOTA
     * 
     * Guarda una nota en la matriz.
     */
    public void registrarNota(int semestreIdx, String codigoMateria, double nota) {

        // validar semestre
        if (semestreIdx < 0 || semestreIdx >= 10) {
            System.out.println("Semestre invalido");
            return;
        }

        // validar nota
        if (nota < 0 || nota > 5) {
            System.out.println("Nota invalida");
            return;
        }

        /**
         * columna será la próxima posición libre
         * del semestre.
         */
        int columna = contadorMaterias[semestreIdx];

        // guardar nota
        notas[semestreIdx][columna] = nota;

        // aumentar contador
        contadorMaterias[semestreIdx]++;

        /**
         * si la materia no existe en el historial,
         * la agregamos.
         */
        if (!historialMaterias.contains(codigoMateria)) {
            historialMaterias.add(codigoMateria);
        }
    }

    /**
     * PROMEDIO DE UN SEMESTRE
     */
    public double getPromedio(int semestreIdx) {

        double suma = 0;

        // cantidad de materias del semestre
        int cantidad = contadorMaterias[semestreIdx];

        // evitar división por cero
        if (cantidad == 0) {
            return 0;
        }

        /**
         * recorremos las materias del semestre
         */
        for (int i = 0; i < cantidad; i++) {

            suma += notas[semestreIdx][i];
        }

        // retornar promedio
        return suma / cantidad;
    }

    /**
     * PROMEDIO ACUMULADO
     * 
     * Recorre toda la matriz.
     */
    public double getPromedioAcumulado() {

        double suma = 0;
        int total = 0;

        // recorrer semestres
        for (int i = 0; i < 10; i++) {

            // recorrer materias
            for (int j = 0; j < contadorMaterias[i]; j++) {

                suma += notas[i][j];
                total++;
            }
        }

        // si no hay notas
        if (total == 0) {
            return 0;
        }

        return suma / total;
    }

    /**
     * MOSTRAR REPORTE ACADÉMICO
     */
    public void mostrarReporteAcademico() {

        System.out.println("REPORTE ACADEMICO");
        System.out.println("Estudiante: " + getNombre());

        // recorrer semestres
        for (int i = 0; i < 10; i++) {

            // si el semestre tiene materias
            if (contadorMaterias[i] > 0) {

                System.out.println("Semestre " + (i + 1));

                // recorrer materias
                for (int j = 0; j < contadorMaterias[i]; j++) {

                    double nota = notas[i][j];

                    // validar si aprobó
                    if (nota >= 3) {

                        System.out.println(
                            "Materia " + (j + 1) +
                            ": " + nota +
                            " Aprobada"
                        );

                    } else {

                        System.out.println(
                            "Materia " + (j + 1) +
                            ": " + nota +
                            " Reprobada"
                        );
                    }
                }

                // mostrar promedio semestre
                System.out.println(
                    "Promedio semestre: " + getPromedio(i)
                );
            }
        }

        // mostrar promedio total
        System.out.println(
            "Promedio acumulado: " + getPromedioAcumulado()
        );
    }

    /**
     * AGREGAR MATERIA AL HISTORIAL
     */
    public void agregarAlHistorial(String codigoMateria) {

        // evitar duplicados
        if (!historialMaterias.contains(codigoMateria)) {

            historialMaterias.add(codigoMateria);
        }
    }

    /**
     * ELIMINAR MATERIA DEL HISTORIAL
     */
    public void eliminarDelHistorial(String codigoMateria) {

        historialMaterias.remove(codigoMateria);
    }

    // ===== GETTERS Y SETTERS =====

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {

        this.semestre = semestre;
    }

    public Double[][] getNotas() {
        return notas;
    }

    public int[] getContadorMaterias() {
        return contadorMaterias;
    }

    public LinkedList<String> getHistorialMaterias() {
        return historialMaterias;
    }

    /**
     * toString()
     * 
     * Muestra información resumida del estudiante.
     */
    @Override
    public String toString() {

        return "Estudiante: " + getNombre()
                + " | ID: " + getId()
                + " | Semestre: " + semestre
                + " | Promedio: " + getPromedioAcumulado();
    }
}
