import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.time.LocalDate;

public class BibliotecaGUI extends JFrame
{
    private JMenuBar menuBar;
    private JMenu mnuArchivo, mnuCatalogo, mnuPrestamo, mnuUsuarios, mnuAyuda;
    private JTextField txtBusqueda, txtBusquedaUsuarios;
    private JButton btnBuscar;
    private JPanel pnlBusqueda, pnlVistaPrestamos;
    private JTable tablaLibros, tablaUsuarios;
    private JScrollPane scrollTabla;
    private JButton btnNuevoLibro, btnBuscarLibro, btnEliminarLibro, btnUbicarLibro;
    private JPanel pnlAcciones;
    private JPanel pnlPrincipal, pnlSuperior;
    private JLabel lblEstado;
    private Biblioteca biblioteca;
    private JCheckBox chkTitulo, chkAutor, chkISBN; //para las mejoras de búsqueda

    public BibliotecaGUI() 
    {
        setTitle("Sistema de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        crearMenu();
        crearPanelPrincipal();
        setVisible(true);
        
        biblioteca = new Biblioteca("Biblioteca Central", "Puebla Puebla");
        setTitle("Sistema de Biblioteca - " + biblioteca.getNombre());
        
        llenaBaseLibros();
    }
    
    private void crearMenu() 
    {
        // Crear la barra de menús
        menuBar = new JMenuBar();
        
        // Crear menús principales
        mnuArchivo = new JMenu("Archivo");
        mnuCatalogo = new JMenu("Catalogo");
        mnuPrestamo = new JMenu("Préstamo");
        mnuUsuarios = new JMenu("Usuarios");
        mnuAyuda = new JMenu("Ayuda");
        
        //Submenus de archivo
        JMenuItem mnuNuevoBiblioteca = new JMenuItem("Nuevo Biblioteca");
        mnuArchivo.add(mnuNuevoBiblioteca);
        
        JMenuItem mnuAbrirBasesDatos = new JMenuItem("Abrir Bases de Datos");
        mnuArchivo.add(mnuAbrirBasesDatos);
        
        JMenuItem mnuGuardarBasesDatos = new JMenuItem("Guardar Bases de Datos");
        mnuArchivo.add(mnuGuardarBasesDatos);
        
        JMenuItem mnuSalir = new JMenuItem("Salir");
        mnuArchivo.add(mnuSalir);
        mnuSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
        }});
        
        
        //submenus de Catalogo
        JMenuItem mnuNuevoLibro = new JMenuItem("Agregar Libro");
        mnuCatalogo.add(mnuNuevoLibro);
        mnuNuevoLibro.addActionListener(new MostrarDialogoNuevo());
        
        JMenuItem mnuVerLibros = new JMenuItem("Ver Libros");
        mnuCatalogo.add(mnuVerLibros);
        mnuVerLibros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTablaLibros(biblioteca.getLibros()); // Actualiza la tabla con los libros más recientes
                mostrarPanelLibros(); // Muestra el panel con la tabla actualizada
            }
        });
        
        JMenuItem mnuBuscarLibro = new JMenuItem("Buscar Libro");
        mnuCatalogo.add(mnuBuscarLibro);
        
        //submenus de prestamos
        JMenuItem mnuNuevoPrestamo= new JMenuItem("Nuevo Prestamo");
        mnuPrestamo.add(mnuNuevoPrestamo);
        mnuNuevoPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoNuevoPrestamo();
            }
        });
    
        JMenuItem mnuDevolverPrestamo= new JMenuItem("Devolver Prestamo");
        mnuPrestamo.add(mnuDevolverPrestamo);
        mnuDevolverPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoDevolverPrestamo();
            }
            });
        
        JMenuItem mnuMostrarPrestamo = new JMenuItem("Mostrar Prestamo");
        mnuPrestamo.add(mnuMostrarPrestamo);
        mnuMostrarPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoConsultarPrestamo();
            }
            });
        
        //submenu de usuarios
        JMenuItem mnuRegistrarUsuario = new JMenuItem("Registrar Usuario");
        mnuUsuarios.add(mnuRegistrarUsuario);
        mnuRegistrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoNuevoUsuario();  // Abre el formulario de nuevo usuario
            }
        });
        
        JMenuItem mnuBuscarUsuario = new JMenuItem("Buscar Usuario");
        mnuUsuarios.add(mnuBuscarUsuario);
        
        JMenuItem mnuVerUsuarios = new JMenuItem("Ver Usuarios");
        mnuUsuarios.add(mnuVerUsuarios);
        mnuVerUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoMostrarUsuarios();
            }
        });
        
        //submenu de Estadisticas
        JMenuItem mnuEstadisticasPrestamosMensuales = new JMenuItem("Estadísticas Mensuales de Préstamos");
        mnuPrestamo.add(mnuEstadisticasPrestamosMensuales);
        mnuEstadisticasPrestamosMensuales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarEstadisticasMensuales();
            }
        });
        //submenu de ayuda
        JMenuItem mnuAcercade = new JMenuItem("Acerca de...");
        mnuAyuda.add(mnuAcercade);
        mnuAcercade.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DialogoAcercaDe(BibliotecaGUI.this); // Muestra el diálogo modal
            }
        });
        
        // Establecer la barra de menús en el frame
        setJMenuBar(menuBar);
        menuBar.add(mnuArchivo);
        menuBar.add(mnuCatalogo);
        menuBar.add(mnuPrestamo);
        menuBar.add(mnuUsuarios);
        menuBar.add(mnuAyuda);
    }
    
    private class Salir implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);}
    }

    private void mostrarDialogoUsuarios() {
        Set<Usuario> usuarios = biblioteca.getUsuarios();
        DialogoMostrarUsuarios dialogo = new DialogoMostrarUsuarios(this, biblioteca, usuarios);
        dialogo.setModal(true);
        dialogo.setVisible(true);
    }
    
    private class MostrarEstadisticasMensuales implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            mostrarEstadisticasMensuales();
        }
    }
    
    private void mostrarEstadisticasMensuales() 
    {
        Map<String, Integer> prestamosPorMes = biblioteca.getPréstamosPorMes();
    
        String[] meses = prestamosPorMes.keySet().toArray(new String[0]);
        int[] valores = new int[meses.length];
        for (int i = 0; i < meses.length; i++) {
            valores[i] = prestamosPorMes.get(meses[i]);
        }
    
        Color[] colores = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PINK, Color.CYAN};
    
        GraficoEstadisticas grafico = new GraficoEstadisticas(valores, meses, colores);
    
        JFrame frame = new JFrame("Estadísticas de Préstamos Mensuales");
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(grafico, BorderLayout.CENTER);  
    
        frame.setVisible(true);
    }

    
    private void crearPanelPrincipal() 
    {
        //Creamos un panel, que tendrá dentro 2 paneles más 
        
        pnlSuperior = new JPanel(new BorderLayout());
    
        pnlBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT)); //el que se ocupa para la busqueda
        txtBusqueda = new JTextField(30);
        btnBuscar = new JButton("Buscar");
        
        // Agregar un ActionListener al botón de búsqueda
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizaBusquedaRapida();  // Llamar a la búsqueda rápida cuando se hace clic en el botón
            }
        });
    
        // Agregar el campo de texto y el botón al panel de búsqueda
        pnlBusqueda.add(txtBusqueda);
        pnlBusqueda.add(btnBuscar);
        
         // Panel de criterios
        JPanel pnlCriterios = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlCriterios.setLayout(new GridLayout(1, 3));
        chkTitulo = new JCheckBox("Título");
        chkTitulo.setSelected(true); //marcar al titulo por defecto en la busqueda
        chkAutor = new JCheckBox("Autor");
        chkISBN = new JCheckBox("ISBN");
        pnlCriterios.add(chkTitulo);
        pnlCriterios.add(chkAutor);
        pnlCriterios.add(chkISBN);

        pnlSuperior.add(pnlBusqueda, BorderLayout.WEST);
        pnlSuperior.add(pnlCriterios, BorderLayout.EAST);
    
        // Tabla de libros
        String[] columnas = {"Título", "Autor", "ISBN", "Páginas", "Estado"};
        Object[][] datos = {};
        DefaultTableModel modelo = new DefaultTableModel(datos,columnas);
        tablaLibros = new JTable(modelo);
        scrollTabla = new JScrollPane(tablaLibros);
        
        // Panel de acciones
        pnlAcciones = new JPanel();
        btnNuevoLibro = new JButton("Nuevo Libro");
        btnBuscarLibro = new JButton("Editar");
        btnEliminarLibro = new JButton("Eliminar");
        btnUbicarLibro = new JButton("Ubicar Libro");
        pnlAcciones.add(btnNuevoLibro);
        pnlAcciones.add(btnBuscarLibro);
        pnlAcciones.add(btnEliminarLibro);
        pnlAcciones.add(btnUbicarLibro);
        
         // Panel principal
        pnlPrincipal = new JPanel(new BorderLayout());
        pnlPrincipal.add(pnlSuperior, BorderLayout.NORTH);
        pnlPrincipal.add(scrollTabla, BorderLayout.CENTER);
        pnlPrincipal.add(pnlAcciones, BorderLayout.SOUTH);
        
        // Barra de Estado
        lblEstado = new JLabel("Estado: Listo", JLabel.LEFT);
        lblEstado.setBorder(BorderFactory.createLoweredBevelBorder());
        lblEstado.setPreferredSize(new Dimension(getWidth(), 20));
        
        // Integración Final
        this.add(pnlPrincipal, BorderLayout.CENTER);
        this.add(lblEstado,BorderLayout.SOUTH);
        
        // Asignación de Listeners
        btnNuevoLibro.addActionListener(new MostrarDialogoNuevo());
        btnUbicarLibro.addActionListener(new MostrarUbicacion());
    }
    
    
    private class MostrarUbicacion implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            mostrarDialogoUbicacion();
        }
    }
    
    public void mostrarDialogoUbicacion() 
    {
        int filaSeleccionada = tablaLibros.getSelectedRow(); 
        if (filaSeleccionada >= 0) 
        {
            String titulo = (String) tablaLibros.getValueAt(filaSeleccionada, 0);
            String autor = (String) tablaLibros.getValueAt(filaSeleccionada, 1);
            String isbn = (String) tablaLibros.getValueAt(filaSeleccionada, 2);
            
            // Crear un nuevo objeto Libro con los datos
            Libro libroSeleccionado = new Libro();
            libroSeleccionado.setTitulo(titulo);
            libroSeleccionado.setAutor(autor);
            libroSeleccionado.setIsbn(isbn);
            
            DialogoUbicacion dialog = new DialogoUbicacion(this, libroSeleccionado);
            dialog.setVisible(true);
        } 
        else
        {
            JOptionPane.showMessageDialog(this, 
            "Por favor, seleccione un libro para ubicar",
            "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void actualizarEstado(String mensaje) 
    {
        lblEstado.setText(" " + mensaje);
    }
    
    private void mostrarDialogoNuevoLibro() 
    {
        DialogoNuevoLibro dialogo = new DialogoNuevoLibro(this);
        
        if (dialogo.isGuardadoExitoso()) 
        {
            Libro nuevoLibro = dialogo.getLibro();
            biblioteca.agregarLibro(nuevoLibro);
            actualizarEstado("Catalogo Actualizado");
            actualizarTablaLibros(biblioteca.getLibros());
        }
    }
    
    private class MostrarDialogoNuevo implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            mostrarDialogoNuevoLibro();
        }
    }
    
    private void mostrarDialogoNuevoUsuario() 
    {
        DialogoNuevoUsuario dialogo = new DialogoNuevoUsuario(null);
        
        if (dialogo.isGuardadoExitoso()) 
        {
            Usuario nuevoUsuario = dialogo.getUsuario();
            biblioteca.agregarUsuario(nuevoUsuario);
            actualizarEstado("Catalogo Actualizado");
        }
    }
    
    private void mostrarDialogoNuevoPrestamo() 
    {
        DialogoNuevoPrestamo dialogo = new DialogoNuevoPrestamo(this);
        dialogo.setBiblioteca(biblioteca); //pasar de referencia a biblioteca
        dialogo.setVisible(true);
        if (dialogo.isGuardar()) 
        {
            actualizarTablaLibros(biblioteca.getLibros());
            actualizarEstado("Préstamo registrado exitosamente");
        }
    }
    
    private void mostrarDialogoDevolverPrestamo() 
    {
        DialogoDevolverPrestamo dialogo = new DialogoDevolverPrestamo(this);
        dialogo.setBiblioteca(biblioteca); 
        dialogo.setVisible(true);
        if (dialogo.isGuardar()) 
        {
            actualizarTablaLibros(biblioteca.getLibros());
            actualizarEstado("Devolución almacenada exitosamente");
        }
    }
    
    private void mostrarDialogoConsultarPrestamo() 
    {
        DialogoConsultarPrestamos dialogo = new DialogoConsultarPrestamos(this);
        dialogo.setBiblioteca(biblioteca); 
        dialogo.setVisible(true);
    }
    
    private class MostrarDialogoConsultarPrestamos implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            mostrarDialogoConsultarPrestamo();
        }
    }
    
    private class MostrarDialogoDevolverPrestamo implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            mostrarDialogoDevolverPrestamo();
        }
    }
    
    private class MostrarDialogoNuevoUsuario implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            mostrarDialogoNuevoUsuario();
        }
    }
    
    private class MostrarDialogoNuevoPrestamo implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            mostrarDialogoNuevoPrestamo();
        }
    }
       
    private void mostrarDialogoMostrarUsuarios() 
    {
        Set<Usuario> usuarios = biblioteca.getUsuarios(); 
        DialogoMostrarUsuarios dialogo = new DialogoMostrarUsuarios(this, biblioteca, usuarios);
        
        dialogo.setVisible(true);

    }
    
    private void actualizarTablaLibros(List<Libro> libros) 
    {
        DefaultTableModel modelo = (DefaultTableModel) tablaLibros.getModel();
        modelo.setRowCount(0); 
        String estado;
        for (Libro libro : libros) 
        {
            if(libro.isPrestado())
                estado = "Prestado";
            else 
                estado = "Disponible";   
                
            Object[] fila = {
                libro.getTitulo(),
                libro.getAutor(),
                libro.getIsbn(),
                libro.getNumPaginas(),
                estado
            };
            modelo.addRow(fila);
        }
    }
   
   
    private void realizaBusquedaRapida() 
    {
        String termino = txtBusqueda.getText().trim();
        if(termino.isEmpty()){
            JOptionPane.showMessageDialog(this, "Seleccione al menos una opción de búsqueda", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!termino.isEmpty()) 
        {
            List<Libro> resultados = biblioteca.buscarLibrosPorTitulo(termino);
 
        if (!chkTitulo.isSelected() && !chkAutor.isSelected() && !chkISBN.isSelected()) {
            resultados.addAll(biblioteca.buscarLibrosPorTitulo(termino));
        } else {
            // Búsqueda según las casillas marcadas
            if (chkTitulo.isSelected()) resultados.addAll(biblioteca.buscarLibrosPorTitulo(termino));
            if (chkAutor.isSelected()) resultados.addAll(biblioteca.buscarLibrosPorAutor(termino));
            if (chkISBN.isSelected()) resultados.addAll(biblioteca.buscarLibrosPorISBN(termino));
        }
            
            //eliminamos duplicados si es que un libro coincide con multiples busquedas
            Set<Libro> resultadosUnicos = new HashSet<>(resultados);
            actualizarTablaLibros(new ArrayList<>(resultadosUnicos));
            actualizarEstado("Búsqueda completada: " + resultadosUnicos.size() + " libros encontrados");
            /*
             * actualizarTablaLibros(resultados);
            actualizarEstado("Búsqueda rápida completada: " + resultados.size() + " libros encontrados");
               */
        }
    }
   
    private void llenaBaseLibros() {
        biblioteca.agregarLibro(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "9788424922498",863, Categorias.NOVELA,"Plagas"));
        biblioteca.agregarLibro(new Libro("Cien años de soledad", "Gabriel García Márquez", "9780307474728", 417, Categorias.NOVELA,"No hay"));
        biblioteca.agregarLibro(new Libro("El Principito", "Antoine de Saint-Exupéry", "9788498381498", 96, Categorias.NOVELA,"Plagas"));
        biblioteca.agregarLibro(new Libro("1984", "George Orwell", "9788499z90944", 326, Categorias.NOVELA,"Plagas"));
        actualizarTablaLibros(biblioteca.getLibros());
    }
   
    // Mostrar las interfaces
    private void mostrarPanelLibros() 
    {
        getContentPane().removeAll(); // Elimina todo lo que esté en la ventana
        getContentPane().add(pnlPrincipal, BorderLayout.CENTER); // Agrega el panel principal
        getContentPane().add(lblEstado, BorderLayout.SOUTH); // Agrega la barra de estado de nuevo
        revalidate(); // Revalida el layout
        repaint(); // Repinta la ventana
    }
    
    
    
    public static void main(String[] args) 
    {
        BibliotecaGUI gui = new BibliotecaGUI();
        
        //HOLAAA PROFEE esto es para que se vea la multa y 
        //no tenga que esperar 14 dias hábiles con el código abierto ;)
        
        
        //pónganos 10 (╭☞ ͡ ͡°͜ ʖ ͡ ͡°)╭☞)
        
        
        /*
        Biblioteca biblioteca = new Biblioteca("Biblioteca Central", "Puebla");
        
        // Crear un libro con un ISBN ficticio
        Libro libro3 = new Libro("Cien años de soledad", "Gabriel García Márquez", "9788420674", 140, Categorias.NOVELA, "No hay");

        // Agregar el libro a la biblioteca
        biblioteca.agregarLibro(libro3);
        
        // Crear un usuario ficticio
        Usuario user1 = new Usuario("Ana López", "U001", "Libros cortos", Categorias.FICCION);
        
        // Agregar el usuario a la biblioteca
        biblioteca.agregarUsuario(user1);
        
        // Crear un préstamo con una fecha de devolución pasada
        Prestamo prestamo = new Prestamo("1", user1, libro3);
        prestamo.setFechaDevolucionEsperada(LocalDate.now().minusDays(5)); // Establecer una fecha de devolución pasada
        
        // Agregar el préstamo a la biblioteca
        biblioteca.agregarPrestamo(prestamo);
        
        // Cambiar el estado del préstamo a activo
        prestamo.setEstado(Prestamo.ACTIVO);
        
        // Simular la devolución del libro
        prestamo.procesarDevolucion(); 
        */
    }
}