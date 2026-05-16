import java.util.LinkedList;
import java.util.Queue;

/**
 * CLASE Materia
 * 
 * Esta clase representa una materia
 * de la universidad.
 * 
 * Usa:
 * - LinkedList para listas dinámicas
 * - Queue para cola de espera
 */
public class Materia {

    // ===== ATRIBUTOS =====

    // código de la materia
    private String codigo;

    // nombre de la materia
    private String nombre;

    // cantidad máxima de estudiantes
    private int cuposMaximos;

    // créditos académicos
    private int creditos;

    /**
     * Lista de prerequisitos.
     */
    private LinkedList<String> prerequisitos;

    /**
     * Lista de estudiantes inscritos.
     */
    private LinkedList<String> estudiantesInscritos;

    /**
     * Cola de espera.
     * 
     * Queue funciona en orden FIFO:
     * el primero en entrar es el primero en salir.
     */
    private Queue<String> colaEspera;

    /**
     * CONSTRUCTOR
     */
    public Materia(String codigo, String nombre,
                   int cuposMaximos, int creditos) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.cuposMaximos = cuposMaximos;
        this.creditos = creditos;

        // listas vacías
        prerequisitos = new LinkedList<>();
        estudiantesInscritos = new LinkedList<>();

        // cola vacía
        colaEspera = new LinkedList<>();
    }

    /**
     * AGREGAR PREREQUISITO
     */
    public void agregarPrerequisito(String codigoMateria) {

        // evitar duplicados
        if (!prerequisitos.contains(codigoMateria)) {

            prerequisitos.add(codigoMateria);
        }
    }

    /**
     * VERIFICAR SI LA MATERIA ESTÁ LLENA
     */
    public boolean estaLlena() {

        return estudiantesInscritos.size() >= cuposMaximos;
    }

    /**
     * INSCRIBIR ESTUDIANTE
     * 
     * Si hay cupo:
     * - se inscribe
     * 
     * Si no hay cupo:
     * - entra a la cola de espera
     */
    public boolean inscribirEstudiante(String idEstudiante) {

        // verificar si ya está inscrito
        if (estudiantesInscritos.contains(idEstudiante)) {

            System.out.println(
                "El estudiante ya esta inscrito"
            );

            return false;
        }

        // si hay cupos
        if (!estaLlena()) {

            estudiantesInscritos.add(idEstudiante);

            System.out.println(
                idEstudiante + " inscrito en " + nombre
            );

            return true;

        } else {

            // agregar a la cola
            colaEspera.offer(idEstudiante);

            System.out.println(
                "Materia llena. "
                + idEstudiante
                + " agregado a cola de espera"
            );

            return false;
        }
    }

    /**
     * CANCELAR INSCRIPCIÓN
     * 
     * Si alguien cancela y hay estudiantes
     * esperando, el primero toma el cupo.
     */
    public String cancelarInscripcion(String idEstudiante) {

        // eliminar estudiante
        boolean removido =
            estudiantesInscritos.remove(idEstudiante);

        // si no estaba inscrito
        if (!removido) {

            System.out.println(
                "El estudiante no esta inscrito"
            );

            return null;
        }

        System.out.println(
            "Cancelacion exitosa"
        );

        /**
         * Si hay alguien en espera,
         * toma el cupo automáticamente.
         */
        if (!colaEspera.isEmpty()) {

            // sacar primero de la cola
            String siguienteEstudiante =
                colaEspera.poll();

            // agregarlo a inscritos
            estudiantesInscritos.add(
                siguienteEstudiante
            );

            System.out.println(
                "Cupo asignado a: "
                + siguienteEstudiante
            );

            return siguienteEstudiante;
        }

        return null;
    }

    /**
     * MOSTRAR COLA DE ESPERA
     */
    public void mostrarColaEspera() {

        // verificar si está vacía
        if (colaEspera.isEmpty()) {

            System.out.println(
                "La cola esta vacia"
            );

            return;
        }

        System.out.println(
            "COLA DE ESPERA"
        );

        int posicion = 1;

        /**
         * recorrer cola
         */
        for (String id : colaEspera) {

            System.out.println(
                "Posicion "
                + posicion
                + ": "
                + id
            );

            posicion++;
        }
    }

    /**
     * VERIFICAR SI UN ESTUDIANTE
     * ESTÁ EN LA COLA
     */
    public boolean estaEnColaEspera(
            String idEstudiante) {

        return colaEspera.contains(idEstudiante);
    }

    // ===== GETTERS =====

    public String getCodigo() {

        return codigo;
    }

    public String getNombre() {

        return nombre;
    }

    public int getCuposMaximos() {

        return cuposMaximos;
    }

    public int getCreditos() {

        return creditos;
    }

    /**
     * cupos disponibles
     */
    public int getCuposDisponibles() {

        return cuposMaximos
                - estudiantesInscritos.size();
    }

    public LinkedList<String> getPrerequisitos() {

        return prerequisitos;
    }

    public LinkedList<String> getEstudiantesInscritos() {

        return estudiantesInscritos;
    }

    public Queue<String> getColaEspera() {

        return colaEspera;
    }

    /**
     * MOSTRAR INFORMACIÓN
     */
    public void mostrarInformacion() {

        System.out.println("MATERIA");

        System.out.println(
            "Codigo: " + codigo
        );

        System.out.println(
            "Nombre: " + nombre
        );

        System.out.println(
            "Cupos: "
            + estudiantesInscritos.size()
            + "/"
            + cuposMaximos
        );

        System.out.println(
            "Creditos: " + creditos
        );

        // mostrar prerequisitos
        if (prerequisitos.isEmpty()) {

            System.out.println(
                "Prerequisitos: Ninguno"
            );

        } else {

            System.out.println(
                "Prerequisitos: "
                + String.join(", ", prerequisitos)
            );
        }

        System.out.println(
            "En espera: "
            + colaEspera.size()
        );
    }

    /**
     * toString()
     */
    @Override
    public String toString() {

        return "Materia[" + codigo
                + " - " + nombre
                + ", Cupos: "
                + estudiantesInscritos.size()
                + "/"
                + cuposMaximos
                + "]";
    }
}
