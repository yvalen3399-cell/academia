package academia.modelo;

/**
 * CLASE ABSTRACTA Persona
 *
 * ¿Por qué abstracta?
 * - Nunca crearemos un objeto "Persona" genérico. Siempre será Estudiante o Profesor.
 * - La palabra "abstract" OBLIGA a las subclases a implementar mostrarInformacion().
 * - Esto es ABSTRACCIÓN: ocultamos los detalles específicos en la clase padre
 *   y dejamos que cada hijo defina su propio comportamiento.
 *
 * Concepto POO → ENCAPSULAMIENTO:
 * - Los atributos son "private": nadie puede acceder a ellos directamente.
 * - Solo se pueden leer/modificar a través de los métodos get/set.
 * - Esto protege la integridad de los datos.
 */
public abstract class Persona {

    // ENCAPSULAMIENTO: atributos privados. Nadie de afuera los toca directamente.
    private String nombre;
    private String id;
    private String email;

    /**
     * Constructor de Persona.
     * Al ser abstracta, este constructor solo se llama desde las subclases con "super()".
     *
     * @param nombre Nombre completo de la persona
     * @param id     Identificador único (ej: "2024001")
     * @param email  Correo electrónico institucional
     */
    public Persona(String nombre, String id, String email) {
        this.nombre = nombre; // "this" diferencia el atributo del parámetro cuando tienen el mismo nombre
        this.id = id;
        this.email = email;
    }

    /**
     * MÉTODO ABSTRACTO → POLIMORFISMO
     * Cada subclase DEBE sobrescribir este método con @Override.
     * Si no lo hacen, Java no compilará. Esto garantiza que todo Estudiante
     * y Profesor sepa mostrarse a sí mismo.
     *
     * Esto es SOBRESCRITURA DE MÉTODOS (overriding).
     */
    public abstract void mostrarInformacion();

    // ====== GETTERS Y SETTERS ======
    // Son los "guardianes" del encapsulamiento.
    // Si quisiéramos validar el email antes de asignarlo, lo haríamos en el setter.

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        // Validación simple: no permitir nombres vacíos
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * toString() sobrescrito para representación textual del objeto.
     * Cuando hacemos System.out.println(persona), Java llama este método.
     */
    @Override
    public String toString() {
        return "Persona{nombre='" + nombre + "', id='" + id + "', email='" + email + "'}";
    }
}
