import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class UsuarioTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
import java.time.LocalDate;
public class UsuarioTest
{
    private Usuario usuario;
    private Libro libro;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario("Juan Pérez", "E001", "Libros Cortos",Categorias.NOVELA);
        libro = new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "9788423919",100, Categorias.NOVELA,"Plagas");
    }

    @Test
    public void testConstructor() {
        assertEquals("E001", usuario.getId());
        assertEquals("Juan Pérez", usuario.getNombre());
        assertNull(usuario.getLibrosPrestados());
    }
    
    @Test
    public void testSolicitarPrestamo() {
        assertTrue(usuario.solicitarPrestamo(libro));
        assertTrue(libro.isPrestado());
    }
    
    @Test
    public void testSolicitarPrestamoLibroNoPrestado() {
        libro.prestarLibro(usuario); // Libro ya prestado
        assertFalse(usuario.solicitarPrestamo(libro));
    }
    
    @Test
    public void testDevolverLibro() {
        usuario.solicitarPrestamo(libro);
        assertTrue(usuario.devolverLibro(libro));
        assertNull(usuario.getLibrosPrestados());
    }
    
    @Test
    public void testDevolverLibroSinPrestamo() {
        assertFalse(usuario.devolverLibro(libro));
    }
    
    @Test
    public void testToString() {
        System.out.println(usuario.toString());
        assertEquals("ID: E001, Nombre: Juan Pérez. No tiene en préstamo un libro.", usuario.toString());
    }
}

