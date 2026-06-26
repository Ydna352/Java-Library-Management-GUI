import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DialogoMostrarUsuarios extends JDialog {
    private JTextField txtBusquedaUsuarios;
    private JButton btnBuscarUsuarios, btnNuevoUsuario, btnEditarUsuario, btnEliminarUsuario, btnMostrarUsuarios;
    private JTable tablaUsuarios;
    private Biblioteca biblioteca;
    private JLabel lblEstado;
    private JPanel pnlUsuarios;
    
    private JButton btnCerrar;
    public DialogoMostrarUsuarios(Frame parent, Biblioteca biblioteca, Set<Usuario> usuarios)  {
        super(parent, "Lista de Usuarios", true); // true para modal
        setSize(800, 600);
        
        this.biblioteca = biblioteca;
        setTitle("Gestión de Usuarios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        getContentPane().setLayout(new BorderLayout());
        
        crearPanelUsuarios();
        actualizarTablaUsuarios(usuarios);
        
        setLocationRelativeTo(parent);

        //setVisible(true);
    }

    public void crearPanelUsuarios() {
        pnlUsuarios = new JPanel(new BorderLayout());
        
        // Panel de búsqueda
        JPanel pnlBusquedaUsuarios = new JPanel(new FlowLayout());
        txtBusquedaUsuarios = new JTextField(30);
        btnBuscarUsuarios = new JButton("Buscar");
        pnlBusquedaUsuarios.add(txtBusquedaUsuarios);
        pnlBusquedaUsuarios.add(btnBuscarUsuarios);
        
         // Inicializar lblEstado
        lblEstado = new JLabel("Estado: Listo", JLabel.LEFT);
        lblEstado.setBorder(BorderFactory.createLoweredBevelBorder());
        lblEstado.setPreferredSize(new Dimension(800, 20));
        
        // Agregar un ActionListener al botón de búsqueda
        btnBuscarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizaBusquedaRapidaUsuarios();  // Llamar a la búsqueda rápida cuando se hace clic en el botón
            }
        });
        
        // Tabla de usuarios
        String[] columnasUsuarios = {"ID", "Nombre", "Género Favorito", "Preferencia de Lectura", "Estado"};
        DefaultTableModel modeloUsuarios = new DefaultTableModel(null, columnasUsuarios);
        tablaUsuarios = new JTable(modeloUsuarios);
        JScrollPane scrollTablaUsuarios = new JScrollPane(tablaUsuarios);
        
        // Panel de acciones
        JPanel pnlAccionesUsuarios = new JPanel();
        btnNuevoUsuario = new JButton("Agregar Usuario");
        btnNuevoUsuario.addActionListener(new MostrarDialogoNuevoUsuario());
        btnMostrarUsuarios = new JButton("Lista de Usuarios");
        btnMostrarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTablaUsuarios(biblioteca.getUsuarios()); // Actualiza la tabla con los libros más recientes
                mostrarPanelUsuarios(); // Muestra el panel con la tabla actualizada
            }
        });
        btnEditarUsuario = new JButton("Editar");
        btnEliminarUsuario = new JButton("Eliminar");
        
        pnlAccionesUsuarios.add(btnNuevoUsuario);
        pnlAccionesUsuarios.add(btnMostrarUsuarios);
        pnlAccionesUsuarios.add(btnEditarUsuario);
        pnlAccionesUsuarios.add(btnEliminarUsuario);
        
        // Añadir el panel al JFrame
        this.add(pnlUsuarios);
        this.add(lblEstado,BorderLayout.SOUTH);
        
        // Crear botón de cerrar
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana
            }
        });
    
        pnlAccionesUsuarios.add(btnCerrar);
        
        // Agregar todo al panel
        pnlUsuarios.add(pnlBusquedaUsuarios, BorderLayout.NORTH);
        pnlUsuarios.add(scrollTablaUsuarios, BorderLayout.CENTER);
        pnlUsuarios.add(pnlAccionesUsuarios, BorderLayout.SOUTH);
    }

    private void realizaBusquedaRapidaUsuarios() 
    {
        String buscar_id = txtBusquedaUsuarios.getText().trim();
        if (!buscar_id.isEmpty()) 
        {
            Usuario resultado = biblioteca.buscarUsuarioPorId(buscar_id);
            if (resultado != null) {
                Set<Usuario> resultados_usuarios = new HashSet<Usuario>();
                resultados_usuarios.add(resultado);
                actualizarTablaUsuarios(resultados_usuarios);
                actualizarEstado("Búsqueda rápida completada: 1 usuario encontrado");
            } else {
                actualizarTablaUsuarios(new HashSet<Usuario>());
                actualizarEstado("No se encontró ningún usuario con ese ID");
            }
        }
    }

    // Actualiza la tabla de usuarios con los datos de la biblioteca
    private void actualizarTablaUsuarios(Set<Usuario> usuarios) {
        DefaultTableModel modelo = (DefaultTableModel) tablaUsuarios.getModel();
        modelo.setRowCount(0); 
        for (Usuario u : usuarios) {
            String estado = u.getLibrosPrestado().isEmpty() ? "Sin préstamo" : "Con préstamo";
            Object[] fila = {
                u.getId(),
                u.getNombre(),
                u.getGeneroFav(),
                u.getPreferenciaLectura(),
                estado
            };
            modelo.addRow(fila);
        }
    }
    
    private void llenaBaseUsuarios() {
        biblioteca.agregarUsuario(new Usuario("Ana López", "U001", "Libros cortos", Categorias.FICCION));
        biblioteca.agregarUsuario(new Usuario("Carlos Ruiz", "U002", "Libros largos", Categorias.ROMANCE));

        actualizarTablaUsuarios(biblioteca.getUsuarios());
    }
    
    private void actualizarEstado(String mensaje) 
    {
        lblEstado.setText(" " + mensaje);
    }
    
    private void mostrarPanelUsuarios() 
    {
        getContentPane().removeAll();
        //crearPanelUsuarios(); // Crea el panel
        getContentPane().add(pnlUsuarios, BorderLayout.CENTER);
        getContentPane().add(lblEstado, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }
    
    private void mostrarDialogoNuevoUsuario() 
    {
        DialogoNuevoUsuario dialogo = new DialogoNuevoUsuario(null);
        
        if (dialogo.isGuardadoExitoso()) 
        {
            Usuario nuevoUsuario = dialogo.getUsuario();
            biblioteca.agregarUsuario(nuevoUsuario);
            actualizarEstado("Catalogo Actualizado");
            actualizarTablaUsuarios(biblioteca.getUsuarios());
        }
    }
    
    private class MostrarDialogoNuevoUsuario implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            mostrarDialogoNuevoUsuario();
        }
    }
    
}
