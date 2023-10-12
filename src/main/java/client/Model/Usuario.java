package client.Model;

/**
 * Clase Usuario que representa a un usuario en el sistema.
 */
public class Usuario {
    // Representa el nombre del usuario
    private String nombre;
    // Representa el ID único del usuario
    private int id;
    // Representa si es turno del usuario
    private boolean esTurno;

    /**
     * Constructor de la clase Usuario.
     * Inicializa el usuario con el nombre y el ID proporcionados.
     * Establece el turno del usuario como false.
     *
     * @param nombre El nombre del usuario.
     * @param id El ID único del usuario.
     */
    public Usuario(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
        this.esTurno = false;
    }

    /**
     * Establece si es turno del usuario.
     *
     * @param esTurno True si es turno del usuario, false en caso contrario.
     */
    public void setEsTurno(boolean esTurno) {
        this.esTurno = esTurno;
    }

    /**
     * Devuelve si es turno del usuario.
     *
     * @return True si es turno del usuario, false en caso contrario.
     */
    public boolean setEsTurno() {
        return esTurno;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre del usuario.
     *
     * @param nombre El nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el ID del usuario.
     *
     * @return El ID del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Cambia el ID del usuario.
     *
     * @param id El nuevo ID del usuario.
     */
    public void setId(int id) {
        this.id = id;
    }
}
