

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


public class PrestamoTest
{
    private Prestamo prestamo;
    private Usuario usuario;
    private Libro libro;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp() {
        usuario = new Usuario("Juan Pérez", "U001", "Libros Cortos", Categorias.ROMANCE);
        libro = new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "9788424922498", 863, Categorias.NOVELA, "No hay");
        prestamo = new Prestamo("P001", usuario, libro);
    }
    
    @Test
    public void testConstructor() {
        assertEquals("P001", prestamo.getId());
        assertEquals(usuario.getId(), prestamo.getUsuario().getId());
        assertEquals(libro.getIsbn(), prestamo.getLibro().getIsbn());
        assertEquals(prestamo.getFechaPrestamo(), prestamo.getFechaPrestamo());
        assertEquals(Prestamo.ACTIVO, prestamo.getEstado());
    }
    
    @Test
    public void testRegistrarPrestamoExitoso() {
        assertTrue(prestamo.registrarPrestamo());
        assertEquals(Prestamo.ACTIVO, prestamo.getEstado());
    }
    
    @Test
    public void testRegistrarPrestamoLibroNoPrestado() {
        libro.prestarLibro(usuario); // Libro ya prestado
        assertFalse(prestamo.registrarPrestamo());
    }
    
    @Test
    public void testProcesarDevolucionExitosa() {
        prestamo.registrarPrestamo();
        assertTrue(prestamo.procesarDevolucion());
        assertEquals(Prestamo.DEVUELTO, prestamo.getEstado());
        assertNotNull(prestamo.getFechaDevolucionReal());
    }
    
    @Test
    public void testProcesarDevolucionPrestamoNoActivo() {
        assertFalse(prestamo.procesarDevolucion());
    }
    
    @Test
    void testProcesarDevolucion() {
        prestamo.registrarPrestamo();
        assertTrue(prestamo.procesarDevolucion());
        assertEquals(Prestamo.DEVUELTO, prestamo.getEstado());
        assertNotNull(prestamo.getFechaDevolucionReal());
        assertFalse(libro.isPrestado());
        
        // Intentar devolver un préstamo ya devuelto
        assertFalse(prestamo.procesarDevolucion());
    }
    
    
    @Test
    public void testExtenderPrestamoExitoso() {
        prestamo.registrarPrestamo();
        LocalDate fechaOriginal = prestamo.getFechaDevolucionEsperada();
        assertTrue(prestamo.extenderPrestamo(7));
        assertEquals(fechaOriginal.plusDays(7), prestamo.getFechaDevolucionEsperada());
    }
    
    
    @Test
    public void testToString() {
        String resultado = prestamo.toString();
        assertTrue(resultado.contains("P001"));
        assertTrue(resultado.contains(usuario.getNombre()));
        assertTrue(resultado.contains(libro.getTitulo()));
        assertFalse(resultado.contains("ACTIVO"));
    }

    @AfterEach
    public void tearDown()
    {
    }
}
