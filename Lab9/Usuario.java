 


/**
 * Write a description of class Usuario here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.time.LocalDate;
import java.util.*;
public class Usuario extends Persona
{
    private List<Libro> librosPrestados;
    private Set<String> historialPrestamos;
    private String preferenciaLectura;//libros cortas, largos, caps cortos, caps largos
    public Categorias generoFav;
    private int cont=0;
    
    
    public Usuario(String nombre, String id, String preferenciaLectura, Categorias generoFav) 
    {
        super(nombre, id);
        this.librosPrestados = new ArrayList<>();
        this.historialPrestamos = new HashSet<>();
        this.preferenciaLectura = preferenciaLectura;
        this.generoFav = generoFav;
    }
    
    public Usuario(Usuario usuario) {
        super(usuario.getNombre(), usuario.getId());
        this.librosPrestados = usuario.getLibrosPrestado();
    }
    
    public void setPreferenciaLectura(String preferenciaLectura)
    {
        this.preferenciaLectura = preferenciaLectura;
    }
    
    /*
    public void setGeneroFav(String generoFav)
    {
        this.generoFav = generoFav;
    }
    */
    public String getPreferenciaLectura()
    {
        return preferenciaLectura;
    }
    
    public Categorias getGeneroFav()
    {
        return generoFav;
    }
    
    public Set<String> getHistorialPrestamos() {
        return new HashSet<>(historialPrestamos);
    }
    
    /*
    public boolean solicitarPrestamo(Libro libro) {
        if (!libro.isPrestado() && libro.prestarLibro(LocalDate.now())) {
            librosPrestados.add(libro);
            historialPrestamos.add(libro.getIsbn());
            return true;
        }
        return false;
    }
    */
    public boolean solicitarPrestamo(Libro libro) {
    if (libro.prestarLibro(this)) {  // Intentar prestar el libro
        librosPrestados.add(libro);
        historialPrestamos.add(libro.getIsbn());
        return true;
    } else {
        return false;  // Se maneja la lista de espera en `Libro`
    }
    }   
   
    public boolean devolverLibro(Libro libro) {    
        if (librosPrestados.contains(libro)) {
            libro.devolverLibro();
            librosPrestados.remove(libro);
            return true;
        }
        return false;
    }
    
    /**
     * Deprecado, para eliminación: Este elemento de la API será eliminado en una versión futura.
     * Obtiene una copia del libro prestado actualmente 
     * 
     * @return Una copia del libro prestado o null si no hay préstamos activos
     */
    
    public Libro getLibrosPrestados() {
        if(librosPrestados.size() > 0 )
            return new Libro(librosPrestados.get(0)); // Retorna una copia de la lista
        else
            return null;
    }
    
    //luego se habra que cambiar este 
    public List<Libro> getLibrosPrestado() {
        return new ArrayList<>(librosPrestados);
    }
    
    public String obtenerTipo() 
    {
        return "Usuario";
    }
    
    public String toString() {
        String cad = "ID: " + getId() + ", " + "Nombre: " + getNombre() + ". ";
        if (librosPrestados.size() > 0)
            cad += "Tiene en préstamo" + librosPrestados.toString() +  " libros.";
        else
            cad += "No tiene en préstamo un libro.";
        return cad;
    }
    
    
}
