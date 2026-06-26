 

/**
 * La clase Libro representa un libro en un sistema de gestión bibliotecaria.
 * Permite manejar y organizar la información básica de los libros, incluyendo
 * su título, autor, ISBN, número de páginas y estado de préstamo.
 * 
 * Esta clase proporciona funcionalidades para:
 * - Gestionar la información básica del libro
 * - Controlar el estado de préstamo
 * - Obtener información detallada del libro
 * 
 */

//cada libro debe de tener su propia lista de espera 
//agregar en esta clase un getLista_de_espera para poder visualizar las personas que estan esperando por el libro.
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.time.temporal.ChronoUnit;
import java.util.*;
public class Libro {
   private String titulo;
   private String autor;
   private String isbn; 
   private int numPaginas;
   private boolean prestado; //la ocuparemos como disponible
   private LocalDate fechaDevolucion;
   private LocalDate fechaPrestamo;
   public Categorias genero; 
   
   //atributos del estado fisico del libro (daños)
   private String tipoDanio;
   private int contadorPrestamos = 0;
       
   public Queue<Usuario> listaEspera; // lista de usuarios de espera
   
   // Constructor por defecto
   public Libro() {
       this.titulo = "Sin título";
       this.autor = "Desconocido";
       this.isbn = "0000000000000";
       this.numPaginas = 0;
       this.prestado = false;
       this.fechaDevolucion = null;
       this.genero = null;
       this.tipoDanio = "No hay";
       this.listaEspera = new LinkedList<>();
   }
   
   // Constructor con título y autor
   public Libro(String titulo, String autor) {
       this.titulo = titulo;
       this.autor = autor;
       this.isbn = "0000000000000";
       this.numPaginas = 0;
       this.prestado = false; 
       this.fechaDevolucion = null;
       this.genero = null;
       this.tipoDanio = "No hay";
       this.listaEspera = new LinkedList<>();
   }
   
   // Constructor con todos los atributos
   public Libro(String titulo, String autor, String isbn, int numPaginas, Categorias genero, String tipoDanio) {
       this.titulo = titulo;
       this.autor = autor;
       this.isbn = isbn;
       this.numPaginas = numPaginas;
       this.prestado = false;
       this.fechaDevolucion = null;
       this.genero = genero;
       this.tipoDanio = tipoDanio;
       this.listaEspera = new LinkedList<>();
   }
   
   // Constructor de copia
   public Libro(Libro otroLibro) {
       this.titulo = otroLibro.titulo;
       this.autor = otroLibro.autor;
       this.isbn = otroLibro.isbn;
       this.numPaginas = otroLibro.numPaginas;
       this.prestado = false;  // El nuevo libro siempre inicia como no prestado
       this.fechaDevolucion = null;
       this.genero = otroLibro.genero;
       this.tipoDanio = tipoDanio;
       this.listaEspera = new LinkedList<>();
   }
   
   // Métodos getter y setter
   public void incrementarPrestamos()
   {
       contadorPrestamos++;
   }
   
   public int getContadorPrestamos()
   {
       return contadorPrestamos;
   }
   
   public String getTitulo() {
       return titulo;
   }
   
   public void setTitulo(String titulo) {
       if (titulo != null && !titulo.trim().isEmpty()) {
           this.titulo = titulo;
       }
   }
   
   public String getAutor() {
       return autor;
   }
   
   public void setAutor(String autor) {
       if (autor != null && !autor.trim().isEmpty()) {
           this.autor = autor;
       }
   }
   
   public String getIsbn() {
       return isbn;
   }
   
   public void setIsbn(String isbn) {
       if (isbn != null && Pattern.matches("\\d{13}", isbn)) {
           this.isbn = isbn;
       }
   }
   
   public int getNumPaginas() {
       return numPaginas;
   }
   
   public void setNumPaginas(int numPaginas) {
       if (numPaginas > 0) {
           this.numPaginas = numPaginas;
       }
   }
   
   public boolean isPrestado() {
       return prestado;
   }
   
   public void setPrestado(boolean prestado) {
       this.prestado = prestado;
   }
   
   public LocalDate getFechaDevolucion() {
       return fechaDevolucion;
   }
   
   public void setFechaDevolucion(LocalDate fechaDevolucion) {
       this.fechaDevolucion = fechaDevolucion;
   }
   
   public Categorias getGenero() {
       return genero;
   }
   
   /*
   public void setGenero(Categorias genero) {
       if (genero != null && !genero.trim().isEmpty()) {
           this.genero = genero;
       }
   }
   */
  
   public void setTipoDanio(String tipoDanio){
       this.tipoDanio = tipoDanio;
   }
   
   public String getTipoDanio(){
       return tipoDanio;
   }
   
   // Métodos para prestar y devolver el libro
   public boolean prestarLibro(Usuario usuario) {
       if (prestado == false) {
           prestado = true;
           fechaPrestamo = LocalDate.now();
           fechaDevolucion = LocalDate.now().plusDays(14);
           return true;
       }
       else
       {
           listaEspera.offer(usuario);  
            return false;
       }
   }
   
   /*
   public void devolverLibro() {
       prestado = false;
       fechaDevolucion = null;
   }
   */
   public void devolverLibro() {
        if (!listaEspera.isEmpty()) {
            Usuario siguienteUsuario = listaEspera.poll();  
            siguienteUsuario.solicitarPrestamo(this);
        } else {
            prestado = false;
            
        }
   }
    
   public Queue<Usuario> getListaEspera() {
        return listaEspera;
   }
    
   public boolean verificarDisponibilidad() {
       return !prestado;
   }
   
   @Override
   public String toString() {
       return "Libro: " + titulo + " por " + autor + 
              "\nISBN: " + isbn + 
              "\nPáginas: " + numPaginas +
              "\nEstado: " + (prestado ? "Prestado" : "Disponible") +
              "\nGénero: " + genero +
              (prestado ? "\nFecha de devolución: " + fechaDevolucion : "");
   }
   
   public long calcularMultas()
   {
    long dias_retraso=0;
    long multa=0;
    if (fechaDevolucion != null && prestado == true)
    {
        //la funcion calcula cuantos dias ha pasado desde que se debia devolver a hoy
        dias_retraso=ChronoUnit.DAYS.between(fechaDevolucion, LocalDate.now());
        if (dias_retraso > 0)
        {
            multa = dias_retraso * 20;
        }
    }
    return multa;
   }
   
   public String recibo()
   {
       return "\n\n== Recibo =="+
               "\nLibro: " + titulo + " por " + autor + ", \nPRESENTA RETRASO" + 
              "\nISBN: " + isbn + 
              "\nPáginas: " + numPaginas +
              "\nGénero: " + genero + 
              "\nFecha óptima de devolución: " + fechaDevolucion +
              "\nFecha actual de devolución: " + LocalDate.now() + 
              "\nMulta: $" + calcularMultas();
   }
   
   public void historial() 
   {
    if (!tipoDanio.equalsIgnoreCase("No hay")) { // Si el libro tiene daño
        prestado = true; // Lo marcamos como "no disponible"
        System.out.println("El libro " + titulo + " está dañado y no puede ser prestado");
    } else {
        System.out.println("El libro " + titulo + " está en buen estado");
    }
    }
    
   public String notificacion(Usuario usuario)
    {
        return ("Gracias, " + usuario.getNombre() + ". Has devuelto el libro '" + titulo + "' correctamente.");
    }
}

