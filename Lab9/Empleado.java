 


/**
 * Write a description of class Empleado here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class Empleado extends Persona
{
    private String numeroEmpleado;
    private String puesto;
    private double salario; 
    private int turno;
    private static int contadorId = 0;
    
    private Queue<Prestamo> prestamosEnProceso;
    private List<Prestamo> historialPrestamos;
    
    public static final int MATUTINO = 0;
    public static final int VESPERTINO = 1;
    public static final int MIXTO = 2;
    
    private String horarioE; //horario entrada
    private String horarioS; //horario salida
    private String especialidad;
    private String nivelA; //nivel autorizacion (Basico, Medio,Admin)
    
    public Empleado(String nombre, String numeroEmpleado, String puesto, double salario, int turno, 
    String horarioE, String horarioS, String especialidad, String nivelA) {
        super(nombre, numeroEmpleado);
        this.numeroEmpleado = numeroEmpleado;
        this.puesto = puesto;
        this.salario = salario; //mpdificado por Naomi
        this.turno = turno; //mpdificado por Naomi
        this.horarioE = horarioE;
        this.horarioS = horarioS;
        this.especialidad = especialidad; //archivista
        this.nivelA = nivelA; //nivel de autorización
        this.prestamosEnProceso = new LinkedList<>();
        this.historialPrestamos = new ArrayList<>();
    }
    
    public String getHorarioS()
    {
        return horarioS;
    }
    
    public String getHorarioE()
    {
        return horarioE;
    }
    
    public String getEspecialidad()
    {
        return especialidad;
    }
    
    public String getNivelA()
    {
        return nivelA;
    }
    
    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario > 0 ? salario : 0;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }
    
    public String obtenerTipo() 
    {
        return "Empleado";
    }
    
    public static String generarId() 
    {
        contadorId++;
        return "P" + String.format("%04d", contadorId);
    }
    
    //los getters de los prestamos en proceso y el historial
    public Queue<Prestamo> getPrestamosEnProceso() 
    {
        return new LinkedList<>(prestamosEnProceso);
    }
		
    public List<Prestamo> getHistorialPrestamos() 
    {
	return new ArrayList<>(historialPrestamos);
    }
    
    public boolean procesarPrestamo(Libro libro, Usuario usuario) {
        if (libro != null && usuario != null && !libro.isPrestado()) {
            if (usuario.solicitarPrestamo(libro)) {
                Prestamo nuevoPrestamo = new Prestamo(generarId(), usuario, libro);
                prestamosEnProceso.offer(nuevoPrestamo);
                historialPrestamos.add(nuevoPrestamo);
                return true;
            }
        }
        return false;
    }
    
    public boolean devolverPrestamo() {
        Prestamo prestamo = prestamosEnProceso.poll();
        return prestamo != null;
    }
    
    public String toString() {
        return "Empleado [puesto=" + puesto + 
               ", salario=" + salario + 
               ", turno=" + turno + 
               ", prestamos activos=" + prestamosEnProceso.size() + 
               ", nombre=" + getNombre() + 
               ", id=" + getId() + "]";
    }
    
    //modificado por Naomi
    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

}
