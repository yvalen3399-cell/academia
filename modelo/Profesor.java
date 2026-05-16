/**
 * CLASE Profesor
 * 
 * Esta clase hereda de Persona.
 * 
 * Profesor y Estudiante son hijos
 * de la clase Persona.
 */
public class Profesor extends Persona {

    // ===== ATRIBUTOS =====

    // departamento del profesor
    private String departamento;

    // salario mensual
    private double salario;

    /**
     * CONSTRUCTOR
     * 
     * super() llama al constructor
     * de Persona.
     */
    public Profesor(String nombre, String id, String email,
                     String departamento, double salario) {

        // constructor de Persona
        super(nombre, id, email);

        // inicializar atributos
        this.departamento = departamento;
        this.salario = salario;
    }

    /**
     * MÉTODO SOBREESCRITO
     * 
     * Este método viene de Persona.
     */
    @Override
    public void mostrarInformacion() {

        System.out.println("INFORMACION DEL PROFESOR");

        System.out.println("ID: " + getId());
        System.out.println("Nombre: " + getNombre());
        System.out.println("Email: " + getEmail());
        System.out.println("Departamento: " + departamento);
        System.out.println("Salario: " + salario);
    }

    // ===== GETTERS Y SETTERS =====

    /**
     * GETTER DEL DEPARTAMENTO
     */
    public String getDepartamento() {

        return departamento;
    }

    /**
     * SETTER DEL DEPARTAMENTO
     */
    public void setDepartamento(String departamento) {

        this.departamento = departamento;
    }

    /**
     * GETTER DEL SALARIO
     */
    public double getSalario() {

        return salario;
    }

    /**
     * toString()
     * 
     * Retorna información resumida
     * del profesor.
     */
    @Override
    public String toString() {

        return "Profesor[ID=" + getId()
                + ", Nombre=" + getNombre()
                + ", Departamento=" + departamento + "]";
    }
}
