 


/*
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.time.temporal.ChronoUnit;
*/
/**
 * The test class LibroTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
/*
public class LibroTest
{
    private Libro libro1, libro2, libro3, libro4, libro5;
    
    @BeforeEach
    public void setUp()
    {
        this.libro1 = new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "9788424922498", 863,Categorias.NOVELA, "No hay");
        this.libro2 = new Libro("Cien años de soledad", "Gabriel García Márquez", "9780307474728", 417,Categorias.NOVELA, "No hay");
        this.libro3 = new Libro("El Principito", "Antoine de Saint-Exupéry", "9788498381498", 96,Categorias.NOVELA, "No hay");
        this.libro4 = new Libro("1984", "George Orwell", "9788499890944", 326,Categorias.NOVELA, "No hay"); 
    }
    
    @Test
    public void testConstructorDefault()
    {
        libro5 = new Libro();
        System.out.println(libro5.toString());
        assertEquals("Libro: Sin título por Desconocido\nISBN: 0000000000000\nPáginas: 0\nEstado: Disponible\nGénero: Desconocido", libro5.toString());
    }
    
    @Test
    public void testConstructorBase()
    {
        libro5 = new Libro("La Odisea", "Homero");
        System.out.println(libro5.toString());
        assertEquals("Libro: La Odisea por Homero\nISBN: 0000000000000\nPáginas: 0\nEstado: Disponible\nGénero: Desconocido", libro5.toString());
    }
    
    @Test
    public void testConstructorCompleto()
    {
        assertEquals("Libro: Don Quijote de la Mancha por Miguel de Cervantes\nISBN: 9788424922498\nPáginas: 863\nEstado: Disponible\nGénero: Novela", libro1.toString());
    }
    
    @Test
    public void testConstructorCopia()
    {
        libro5 = new Libro(libro1);
        assertEquals("Libro: Don Quijote de la Mancha por Miguel de Cervantes\nISBN: 9788424922498\nPáginas: 863\nEstado: Disponible\nGénero: Novela", libro5.toString());
    }
    
    @Test
    public void testSetters() 
    {
        libro5 = new Libro();
        libro5.setTitulo("El Arte de la Guerra");
        libro5.setAutor("Szun Tzu");
        libro5.setIsbn("8365957639001");
        libro5.setNumPaginas(211);
        //libro5.setGenero("Novela");
        assertEquals("Libro: El Arte de la Guerra por Szun Tzu\nISBN: 8365957639001\nPáginas: 211\nEstado: Disponible\nGénero: Novela", libro5.toString());
    }
    
    @Test
    public void testGetters()
    {
        assertEquals("El Principito", libro3.getTitulo());
        assertEquals("Antoine de Saint-Exupéry", libro3.getAutor());
        assertEquals("9788498381498", libro3.getIsbn());
        assertEquals(96, libro3.getNumPaginas());
        assertEquals("Novela", libro3.getGenero());
    }
    
    @Test
    public void testPrestamos01(Usuario usuario) 
    {
        libro1.prestarLibro(usuario);
        libro3.setPrestado(false);
        
        assertTrue(libro1.isPrestado());
        assertFalse(libro2.isPrestado());
        assertFalse(libro3.isPrestado());
        assertFalse(libro4.isPrestado());
    }
    
    @Test
    public void testPrestamos02(Usuario usuario) 
    {
        libro1.prestarLibro(usuario);
        assertEquals(true,libro1.isPrestado());
        libro1.devolverLibro();
        assertEquals(false,libro1.isPrestado());
        
    }
    
    @AfterEach
    public void tearDown()
    {
        libro1 = libro2 = libro3 = libro4 = libro5 = null;
    }
}

*/

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Queue;

public class LibroTest {

    private Libro libro1, libro2, libro3, libro4;
    private Usuario usuario1, usuario2;

    @BeforeEach
    public void setUp() {
        libro1 = new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "9788424922498", 863, Categorias.NOVELA, "No hay");
        libro2 = new Libro("Cien años de soledad", "Gabriel García Márquez", "9780060883287", 417, Categorias.NOVELA, "No hay");
        libro3 = new Libro("1984", "George Orwell", "9780451524935", 328, Categorias.FICCION, "No hay");
        libro4 = new Libro("El amor en los tiempos del cólera", "Gabriel García Márquez", "9780307389732", 368, Categorias.NOVELA, "No hay");
        
        usuario1 = new Usuario("Juan Pérez", "123", "Libros largos", Categorias.FICCION);
        usuario2 = new Usuario("Ana García", "456", "Libros cortos", Categorias.FICCION);

    }
    
    @Test
    public void testSolicitarPrestamo() {
        // Inicialización del usuario
        Usuario siguienteUsuario = new Usuario("Juan Pérez", "123", "Libros largos", Categorias.FICCION);

        // Crear un libro para la prueba
        Libro libro = new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "9788424922498", 863, Categorias.NOVELA, "No hay");

        // Intentar solicitar el préstamo
        boolean resultado = siguienteUsuario.solicitarPrestamo(libro);

        // Verifica si el préstamo se realizó correctamente
        assertTrue(resultado);
    }

    @Test
    public void testConstructorCompleto() {
        assertEquals("Libro: Don Quijote de la Mancha por Miguel de Cervantes\nISBN: 9788424922498\nPáginas: 863\nEstado: Disponible\nGénero: NOVELA", libro1.toString());
    }

    @Test
    public void testPrestamos01() {
        libro1.prestarLibro(usuario1);
        libro3.setPrestado(false);  // Aseguramos que el libro 3 no esté prestado

        assertTrue(libro1.isPrestado());  // Libro 1 debe estar prestado
        assertFalse(libro2.isPrestado()); // Libro 2 debe estar disponible
        assertFalse(libro3.isPrestado()); // Libro 3 debe estar disponible
        assertFalse(libro4.isPrestado()); // Libro 4 debe estar disponible
    }

    @Test
    public void testPrestamos02() {
        libro2.prestarLibro(usuario1);  // Prestar el libro 2 a usuario1
        libro3.prestarLibro(usuario2);  // Prestar el libro 3 a usuario2
        libro4.prestarLibro(usuario1);  // Prestar el libro 4 a usuario1

        assertTrue(libro2.isPrestado());
        assertTrue(libro3.isPrestado());
        assertTrue(libro4.isPrestado());
    }

    @Test
    public void testListaEspera() {
        libro1.prestarLibro(usuario1);  // Prestar el libro 1 a usuario1
        assertFalse(libro1.prestarLibro(usuario2));  // El libro está prestado, por lo que el segundo usuario debe ser agregado a la lista de espera

        Queue<Usuario> listaEspera = libro1.getListaEspera();
        assertTrue(listaEspera.contains(usuario2));  // El segundo usuario debe estar en la lista de espera
    }

    @Test
    public void testCalcularMultas() {
        libro1.prestarLibro(usuario1);

        // Simulamos un retraso de más de 14 días
        libro1.setFechaDevolucion(LocalDate.now().minusDays(20));

        assertEquals(400, libro1.calcularMultas());  // 20 días de retraso * 20 = 400
    }

    @Test
    public void testDevolverLibroSinEspera() {
        libro1.prestarLibro(usuario1);
        libro1.devolverLibro();

        // Verificar que el libro se haya devuelto y no haya más usuarios esperando
        assertFalse(libro1.isPrestado());
        assertTrue(libro1.getListaEspera().isEmpty());
    }

    @Test
    public void testDevolverLibroConEspera() {
        libro1.prestarLibro(usuario1);
        libro1.prestarLibro(usuario2);  // Usuario 2 va a la lista de espera
        libro1.devolverLibro();

        // Verificar que el libro se haya prestado al siguiente usuario de la lista de espera
        assertTrue(libro1.isPrestado());
        assertFalse(libro1.getListaEspera().contains(usuario1));
        assertTrue(libro1.getListaEspera().contains(usuario2));
    }

    @Test
    public void testNotificacion() {
        libro1.prestarLibro(usuario1);
        libro1.devolverLibro();

        assertEquals("Gracias, Juan Pérez. Has devuelto el libro 'Don Quijote de la Mancha' correctamente.", libro1.notificacion(usuario1));
    }

    @Test
    public void testHistorial() {
        libro1.historial();  // Verificar que el estado del libro se imprime correctamente
    }

    @Test
    public void testRecibo() {
        libro1.prestarLibro(usuario1);
        libro1.setFechaDevolucion(LocalDate.now().minusDays(20));  // Simulamos un retraso

        String reciboEsperado = "\n\n== Recibo ==" +
                "\nLibro: Don Quijote de la Mancha por Miguel de Cervantes, \nPRESENTA RETRASO" +
                "\nISBN: 9788424922498" +
                "\nPáginas: 863" +
                "\nGénero: NOVELA" +
                "\nFecha óptima de devolución: " + LocalDate.now().minusDays(20) +
                "\nFecha actual de devolución: " + LocalDate.now() +
                "\nMulta: $400";

        assertEquals(reciboEsperado, libro1.recibo());
    }
}
