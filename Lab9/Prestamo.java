 


/**
 * Write a description of class Prestamo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.time.*;
import java.util.*;
import java.awt.Frame;
public class Prestamo
{
    private String id;
    private Usuario usuario;
    private Libro libro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private int estado;
    
    public Queue<Usuario> reservasU;
    
    public static final int ACTIVO = 0;
    public static final int DEVUELTO = 1;
    public static final int VENCIDO = 2;
    
    
    
    public Prestamo(String id, Usuario usuario, Libro libro) 
    {
        this.id = id;
        this.usuario = usuario;
        this.libro = libro;
        
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucionEsperada = fechaPrestamo.plusDays(14); // 14 días de préstamo por defecto
        
        this.fechaDevolucionReal = null;
        this.estado = ACTIVO;
    }
    
    public String getId() { 
        return id; 
    }
    
    public Usuario getUsuario() { 
        return new Usuario(usuario); 
    }
    
    public Libro getLibro() { 
        return new Libro(libro); 
    }
    
    public LocalDate getFechaPrestamo() { 
        return fechaPrestamo; 
    }
    
    public LocalDate getFechaDevolucionEsperada() { 
        return fechaDevolucionEsperada; 
    }
    
    public LocalDate getFechaDevolucionReal() { 
        return fechaDevolucionReal; 
    }
    
    public int getEstado() { 
        return estado; 
    }
    
    public void setFechaDevolucionEsperada(LocalDate fecha)
    {
        fechaDevolucionEsperada = fecha;
    }
    
    public boolean setEstado(int estado) {
    
    if (estado == ACTIVO || estado == DEVUELTO || estado == VENCIDO) {
        this.estado = estado;
        return true;  
    } else {
        return false;  
    }
    }
    
    public boolean procesarDevolucion() {
        if (estado == ACTIVO) {
            fechaDevolucionReal = LocalDate.now();
            libro.devolverLibro();  // Primero marcamos el libro como disponible
            
             // Calcular la multa si hay retraso
            long diasDeRetraso = java.time.temporal.ChronoUnit.DAYS.between(fechaDevolucionEsperada, fechaDevolucionReal);
            
            if (diasDeRetraso > 0) {
                double multa = diasDeRetraso * 35.5;  // Ejemplo: $35.5 por día de retraso
                // Mostrar el diálogo de multa
                new DialogoMulta((Frame) null, multa).setVisible(true); 
            }
            if (usuario.devolverLibro(libro)) {  // Pasamos el libro como parámetro
                    estado = DEVUELTO;
                return true;
            }
            // Si la devolución falla, revertimos el estado del libro
            libro.prestarLibro(usuario);
    }
    return false;
    }
   
    public boolean registrarPrestamo() {
    if (usuario == null || libro == null) {
        return false;
    }

    if (usuario.solicitarPrestamo(libro)) {
        estado = ACTIVO;
        return true;
    }
    return false;
    }

    public void verificarEstado() 
    {
        if (estado == ACTIVO && LocalDate.now().isAfter(fechaDevolucionEsperada)) {
            estado = VENCIDO;
        }
    }
    
    public boolean extenderPrestamo(int dias) 
    {
        if (estado == ACTIVO && !LocalDate.now().isAfter(fechaDevolucionEsperada)) {
            fechaDevolucionEsperada = fechaDevolucionEsperada.plusDays(dias);
            return true;
        }
        return false;
    }
    
    public String toString() 
    {
        String estadoStr;
        switch(estado) {
            case ACTIVO:
                estadoStr = "ACTIVO";
                break;
            case DEVUELTO:
                estadoStr = "DEVUELTO";
                break;
            case VENCIDO:
                estadoStr = "VENCIDO";
                break;
        }
        
        return "Prestamo [ID=" + id + 
               ", Usuario=" + usuario.getNombre() +
               ", Libro=" + libro.getTitulo() +
               ", Fecha Prestamo=" + fechaPrestamo +
               ", Fecha Devolución Esperada=" + fechaDevolucionEsperada +
               ", Fecha Devolución Real=" + (fechaDevolucionReal != null ? fechaDevolucionReal : "No devuelto") +
               ", Estado=" + estado + "]";
    }
    
    public void notificacion(Libro libro)
    {
        LocalDate hoy = LocalDate.now();
        // Caso de tres días antes de la fecha de devolución
        if (hoy.equals(fechaDevolucionEsperada.minusDays(3))) {
            System.out.println("ATENCIÓN: Faltan 3 días para devolver el libro.");
        }
    
        // Caso de un día antes de la fecha de devolución
        if (hoy.equals(fechaDevolucionEsperada.minusDays(1))) {
            System.out.println("ATENCIÓN: Mañana es el último día para devolver el libro.");
        }
    
        // Caso de último día para devolver el libro
        if (hoy.equals(fechaDevolucionEsperada)) {
            System.out.println("ATENCIÓN: Hoy es el último día para devolver el libro.");
        }
        
        //notificacion para el siguiente en la lista
        if (!libro.listaEspera.isEmpty()) {
            Usuario siguienteUsuario = libro.listaEspera.peek(); // Solo visualizamos el primero en la cola
            System.out.println("Notificación: " + siguienteUsuario.getNombre() + ", el libro " + libro.getTitulo() + " estará disponible pronto.");
        }
        
    }
}
