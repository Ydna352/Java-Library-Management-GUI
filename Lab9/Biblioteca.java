import java.util.*;
import java.time.LocalDate;

public class Biblioteca
{
    private String nombre;
    private String ubicacion;
    private List<Libro> libros;
    private Set<Usuario> usuarios;
    private Map<String, Empleado> empleados; 
    
    private List<Prestamo> prestamos = new ArrayList<>();

    private Map<String, Integer> prestamosmensuales = new HashMap<>();
    
    public Biblioteca(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.empleados = new HashMap<>();
        this.libros = new ArrayList<>();
        this.usuarios = new HashSet<>();
        this.prestamos = new ArrayList<>();

    }
    
    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }
    //agregamos libros
    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }
    //lo que sustituimos por getLibro
    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        List<Libro> resultados = new ArrayList<>();
        for (Libro libro : libros) 
        {
            if (libro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) 
            {
                resultados.add(libro);
            }
        }
        return resultados;
    }
    
     //Buscar por libro, pero por el isnb del autor 
    public List<Libro> buscarLibrosPorISBN(String isbn) {
            List<Libro> resultados = new ArrayList<>();
            for (Libro libro : libros) {
                if (libro.getIsbn().equalsIgnoreCase(isbn)) { //antes era .equalsignorecase(isbn)
                resultados.add(libro);
            }
            }
        return resultados;
    }

    //Buscar por libro, pero por el nombre del autor
    public List<Libro> buscarLibrosPorAutor(String autor) {
        List<Libro> resultados = new ArrayList<>();
            for (Libro libro : libros) {
                if (libro.getAutor().toLowerCase().contains(autor.toLowerCase())) {
                resultados.add(libro);
            }
        }
        return resultados;
    }
    
    //quitar libros
    public void eliminarLibro(Libro libro) {
        libros.remove(libro);
    }
    
    //Reemplazar set y get por agregar y eliminar usuarios
    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        usuarios.remove(usuario);
    }
    
    //buscar usuario por id
    public Usuario buscarUsuarioPorId(String id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
    return null;
    }
    
    //Reemplazar set y get por agregar,obtener y eliminar empleado 
    public void agregarEmpleado(Empleado empleado) {
        empleados.put(empleado.getId(), empleado);
    }
    
    public void eliminarEmpleado(String id) {
        empleados.remove(id);
    }

    public Empleado obtenerEmpleado(String id) {
        return empleados.get(id);
    }
    
    private void registrarPrestamoMensual() {
        LocalDate fecha = LocalDate.now();
        String mesKey = String.format("%d-%02d", fecha.getYear(), fecha.getMonthValue());
        prestamosmensuales.merge(mesKey, 1, Integer::sum);
    }
    
     public Map<String, Integer> getEstadisticasMensuales() {
        return new HashMap<>(prestamosmensuales);
    }
    
    public boolean prestarLibro(String idLibro, String idUsuario, String idEmpleado) {
        Libro libro = null;
        for (Libro l : libros) 
        {
            if (l.getIsbn().equals(idLibro)) 
            {
               libro = l;
               break;
            }
        }
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        Empleado empleado = empleados.get(idEmpleado);
        
        if (libro != null && usuario != null && empleado != null && !libro.isPrestado()) 
        {
            return empleado.procesarPrestamo(libro, usuario);
        }
        
        //modificado por noemi
        if (empleado.procesarPrestamo(libro, usuario)) {
            registrarPrestamoMensual();
            return true;
        }
        return false;
    }
    
    public boolean devolverLibro(String idLibro, String idEmpleado) {
        Libro libro = null;
        for (Libro l : libros) 
        {
            if (l.getIsbn().equals(idLibro)) 
            {
            libro = l;
            break;
            }
        }
            
        Empleado empleado = empleados.get(idEmpleado);
        
        if (libro != null && empleado != null && libro.isPrestado()) 
        {
            libro.devolverLibro();
            empleado.devolverPrestamo();
            return true;
        }
        return false;
    }
    
    public List<Libro> getLibrosDisponibles() 
    {
        List<Libro> disponibles = new ArrayList<>();
        for (Libro libro : libros) {
            if (!libro.isPrestado()) {
                disponibles.add(libro);
            }
        }
        return disponibles;
    }
    
    public List<Libro> getLibrosPrestados() {
        List<Libro> prestados = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.isPrestado()) {
                prestados.add(libro);
            }
        }
        return prestados;
    }
    
    public List<Prestamo> buscarPrestamosPorUsuarioId(String usuarioId) {
    List<Prestamo> prestamosUsuario = new ArrayList<>();
    for (Prestamo prestamo : prestamos) {
        if (prestamo.getUsuario().getId().equals(usuarioId)) {
            prestamosUsuario.add(prestamo);
        }
    }
    return prestamosUsuario;
}
    
    public String toString() {
        String estado = "";
        estado += "Biblioteca: " + nombre + "\n";
        estado += "Ubicación: " + ubicacion + "\n";
        estado += "Total de libros: " + libros.size() + "\n";
        estado += "Libros disponibles: " + getLibrosDisponibles().size() + "\n";
        estado += "Libros prestados: " + getLibrosPrestados().size() + "\n";
        estado += "Total de usuarios registrados: " + usuarios.size() + "\n";
        estado += "Total de empleados: " + empleados.size() + "\n";
        
        // Información detallada de libros prestados
        estado += "\nLibros actualmente prestados:\n";
        for (Libro libro : getLibrosPrestados()) {
            estado += "- " + libro.getTitulo() + "\n";
        }
        
        return estado;
    }
    
    public void agregarPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
    }

    public Prestamo buscarPrestamoPorId(String id) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getId().equals(id)) {
            return prestamo;
            }
        }
        return null;
    }

    public Map<String, Integer> getPréstamosPorMes() {
        Map<String, Integer> prestamosPorMes = new HashMap<>();
        
        // Recorremos todos los préstamos registrados
        for (Prestamo prestamo : prestamos) {
            // Obtener el mes y año del préstamo
            String mes = prestamo.getFechaPrestamo().getMonth().toString(); // Ejemplo: "JANUARY"
            int cantidad = prestamosPorMes.getOrDefault(mes, 0);
            
            // Incrementamos la cantidad de préstamos para ese mes
            prestamosPorMes.put(mes, cantidad + 1);
        }
        
        return prestamosPorMes;
    }

    //metodo para que funcione el for en BibliotecaGui
    public List<Libro> getLibros() 
    {
        return libros;
    }
    
    public Set<Usuario> getUsuarios() 
    {
        return usuarios;
    }
}
    
    