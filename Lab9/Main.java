/**
 * Clase principal para probar la funcionalidad de la biblioteca.
 * 
 * @author Roberto Salazar Marquez
 * @version 1.1
 */
public class Main {
    public static void main(String[] args) {
        // Crear una nueva biblioteca
        Biblioteca biblioteca = new Biblioteca("Biblioteca Central", "Av. Universidad 3000");
        
        // Crear y agregar empleados
        Empleado emp1 = new Empleado("Juan Pérez", "E001", "Bibliotecario", 10000, 1, "2:00 pm", "8:00 pm", "Archivista", "Master");
        Empleado emp2 = new Empleado("María García", "E002", "Asistente", 8000, 2, "11:00 pm", "6:00 am", "Planificador", "Básico");
        biblioteca.agregarEmpleado(emp1);
        biblioteca.agregarEmpleado(emp2);
        
        // Crear y agregar libros
        Libro libro1 = new Libro("Don Quijote", "Miguel de Cervantes", "9788423919",100, Categorias.NOVELA,"Plagas");
        Libro libro2 = new Libro("Crimen y Castigo","Fiódor Dostoyevski", "9780140449", 120,Categorias.NOVELA, "Químico");
        Libro libro3 = new Libro("Cien años de soledad", "Gabriel García Márquez", "9788420674", 140, Categorias.NOVELA, "No hay");
        biblioteca.agregarLibro(libro1);
        biblioteca.agregarLibro(libro2);
        biblioteca.agregarLibro(libro3);
        
        // Crear y agregar usuarios
        Usuario user1 = new Usuario("Ana López", "U001", "Libros cortos", Categorias.FICCION);
        Usuario user2 = new Usuario("Carlos Ruiz", "U002", "Libros largos", Categorias.ROMANCE);
        biblioteca.agregarUsuario(user1);
        biblioteca.agregarUsuario(user2);
        
        // Probar búsqueda de libros
        System.out.println("Búsqueda de libros con 'don':");
        for (Libro libro : biblioteca.buscarLibrosPorTitulo("don")) {
            System.out.println(libro.getTitulo());
        }
        
        // Probar préstamo de libro
        System.out.println("\nProbando préstamo de libro:");
        if (biblioteca.prestarLibro("9788423919", "U001", "E001")) {
            System.out.println("Préstamo realizado con éxito");
        } else {
            System.out.println("No se pudo realizar el préstamo");
        }
        
        // Mostrar libros prestados
        System.out.println("\nLibros prestados:");
        for (Libro libro : biblioteca.getLibrosPrestados()) {
            System.out.println(libro.getTitulo());
        }
        
        // Probar devolución de libro
        System.out.println("\nProbando devolución de libro:");
        if (biblioteca.devolverLibro("9788423919", "E001")) {
            System.out.println("Devolución realizada con éxito");
        } else {
            System.out.println("No se pudo realizar la devolución");
        }
        
        // Mostrar estado final de la biblioteca
        System.out.println("\nEstado final de la biblioteca:");
        System.out.println(biblioteca.toString());
    }
}