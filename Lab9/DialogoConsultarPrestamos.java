import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout; 
import java.awt.FlowLayout; 
import java.awt.Dimension; 
import java.awt.event.*; 
import java.awt.Frame;
import java.util.List;
import java.util.ArrayList;
public class DialogoConsultarPrestamos extends JDialog
{
    private JTextField txtBusquedaPrestamosUsuario;
    private JButton btnBuscarPrestramosUsuario, btnNuevoPrestamo, btnEditarPrestamo, btnEliminarPrestamo, btnMostrarPrestamo;
    private JPanel pnlVistaPrestamos;
    private JLabel lblEstado;
    private JTable tablaPrestamosUsuario;
    private Usuario usuario;
    private Libro libro;
    private Biblioteca biblioteca;
    private JButton btnCerrar;
    
    public void setBiblioteca(Biblioteca biblioteca) 
    {
        this.biblioteca = biblioteca;
    }
    
    public DialogoConsultarPrestamos(Frame parent)
    {
        super(parent, "Lista de Prestamos", true); // true para modal
        setSize(800, 600);

        setTitle("Visualizar Prestamos");
        
        getContentPane().setLayout(new BorderLayout());
        
        crearPanelVistaPrestamos();
        
        setLocationRelativeTo(parent);
    }

     public void crearPanelVistaPrestamos()
     {
         pnlVistaPrestamos = new JPanel(new BorderLayout());
        
        // Panel de búsqueda
        JPanel pnlBusquedaUsuarios = new JPanel(new FlowLayout());
        txtBusquedaPrestamosUsuario = new JTextField(30);
        btnBuscarPrestramosUsuario = new JButton("Buscar");
        pnlBusquedaUsuarios.add(txtBusquedaPrestamosUsuario);
        pnlBusquedaUsuarios.add(btnBuscarPrestramosUsuario);
        
        lblEstado = new JLabel("Estado: Listo", JLabel.LEFT);
        lblEstado.setBorder(BorderFactory.createLoweredBevelBorder());
        lblEstado.setPreferredSize(new Dimension(800, 20));
        
        btnBuscarPrestramosUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizaBusquedaRapidaPrestamosUsuario(); // Llamar a la búsqueda rápida cuando se hace clic en el botón
            }
        });
        
        btnEditarPrestamo = new JButton("Editar");
        btnEliminarPrestamo = new JButton("Eliminar");
        
        // Tabla de prestamos
        String[] columnasPrestamosUsuario = {"ID Prestamo", "ID Usuario", "Nombre Usuario", "Título Libro", "Autor Libro", "Estado"};
        DefaultTableModel modeloPrestamosUsuario = new DefaultTableModel(null, columnasPrestamosUsuario);
        tablaPrestamosUsuario = new JTable(modeloPrestamosUsuario);
        JScrollPane scrollTablaUsuarios = new JScrollPane(tablaPrestamosUsuario);
        
        JPanel pnlAccionesPrestamosUsuario = new JPanel();
        btnNuevoPrestamo = new JButton("Agregar Usuario");
        btnMostrarPrestamo = new JButton("Lista de Prestamos por Usuario");
        btnMostrarPrestamo.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String buscar_id = txtBusquedaPrestamosUsuario.getText().trim();
        
        if (!buscar_id.isEmpty()) {
            List<Prestamo> prestamosUsuario = biblioteca.buscarPrestamosPorUsuarioId(buscar_id);
            
            if (!prestamosUsuario.isEmpty()) {
                actualizarTablaPrestamosUsuario(prestamosUsuario);
                actualizarEstado("Préstamos del usuario " + buscar_id + ": " + prestamosUsuario.size());
            } else {
                actualizarTablaPrestamosUsuario(new ArrayList<>());
                actualizarEstado("El usuario " + buscar_id + " no tiene préstamos");
            }
        } else {
            actualizarEstado("Ingrese un ID de usuario para mostrar préstamos");
        }
            }
        });
    
        btnEditarPrestamo = new JButton("Editar");
        btnEliminarPrestamo = new JButton("Eliminar");
        
        pnlAccionesPrestamosUsuario.add(btnNuevoPrestamo);
        pnlAccionesPrestamosUsuario.add(btnMostrarPrestamo);
        pnlAccionesPrestamosUsuario.add(btnEditarPrestamo);
        pnlAccionesPrestamosUsuario.add(btnEliminarPrestamo);
        
        // Añadir el panel al JFrame
        this.add(pnlVistaPrestamos);
        this.add(lblEstado,BorderLayout.SOUTH);
        
        // Crear botón de cerrar
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        }); 
    
        pnlAccionesPrestamosUsuario.add(btnCerrar);
        
        // Agregar todo al panel
        pnlVistaPrestamos.add(pnlBusquedaUsuarios, BorderLayout.NORTH);
        pnlVistaPrestamos.add(scrollTablaUsuarios, BorderLayout.CENTER);
        pnlVistaPrestamos.add(pnlAccionesPrestamosUsuario, BorderLayout.SOUTH);
     }

     
      // Realizar búsquedas
    private void realizaBusquedaRapidaPrestamosUsuario() {
    String buscar_id = txtBusquedaPrestamosUsuario.getText().trim();

    if (!buscar_id.isEmpty()) {
        // Buscar los préstamos asociados al usuario por ID
        List<Prestamo> prestamosUsuario = biblioteca.buscarPrestamosPorUsuarioId(buscar_id);
        
        if (!prestamosUsuario.isEmpty()) {
            actualizarTablaPrestamosUsuario(prestamosUsuario);
            actualizarEstado("Préstamos encontrados: " + prestamosUsuario.size());
        } else {
            // Si no se encuentran préstamos
            actualizarTablaPrestamosUsuario(new ArrayList<>());
            actualizarEstado("No se encontraron préstamos para el ID: " + buscar_id);
        }
    }else{
        actualizarEstado("Ingrese un ID de usuario para buscar");
    }
}
    
    private void actualizarTablaPrestamosUsuario(List<Prestamo> prestamos) {
    DefaultTableModel modelo = (DefaultTableModel) tablaPrestamosUsuario.getModel();
    modelo.setRowCount(0);

    if (prestamos.isEmpty()) {
        modelo.addRow(new Object[]{"--", "--", "--", "--", "--", "SIN PRÉSTAMOS"});
    } else {
        for (Prestamo prestamo : prestamos) {
            Usuario usuario = prestamo.getUsuario();
            Libro libro = prestamo.getLibro();
            
            modelo.addRow(new Object[]{
                prestamo.getId(),
                usuario.getId(),
                usuario.getNombre(),
                libro.getTitulo(),
                libro.getAutor(),
                "PRÉSTAMO ACTIVO" 
            });
        }
    }
}

// Método para actualizar el estado de la búsqueda
private void actualizarEstado(String estado) {
    lblEstado.setText("Estado: " + estado);
}
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.ERROR_MESSAGE);
    }
    
}
