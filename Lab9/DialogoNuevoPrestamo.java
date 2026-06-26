

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class DialogoNuevoPrestamo extends JDialog
{
    private JTextField txtIdPrestamo, txtIdUsuario, txtISBN;
    private JButton btnGuardar, btnCancelar, btnBuscarUsuario, btnBuscarLibro;
    private boolean guardar = false;
    private JPanel pnlBotones;
    
    private Prestamo nuevoPrestamo;
    private Biblioteca biblioteca;

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public DialogoNuevoPrestamo(Frame parent)
    {
        super(parent, "Nuevo Préstamo", true);
        inicializarComponentes();
        pack();
        setLocationRelativeTo(parent);
    }
    
    //para los botones de busqueda, es decir, panel con su boton
    
    private JPanel crearPanelConBoton(JTextField texto, JButton boton){
        JPanel panel = new JPanel(new BorderLayout (5,0));
        panel.add(texto, BorderLayout.CENTER);
        panel.add(boton, BorderLayout.EAST);
        return panel;
    }
    
    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        //usando el metodo de panel con boton
       // Componentes del formulario
        txtIdPrestamo = new JTextField(10);
        txtIdUsuario = new JTextField(10);
        txtISBN = new JTextField(10);
        
        btnBuscarUsuario = new JButton("Buscar Usuario");
        btnBuscarLibro = new JButton("Buscar Libro");
    
        JPanel panelUsuario = crearPanelConBoton(txtIdUsuario, btnBuscarUsuario);
        JPanel panelLibro = crearPanelConBoton(txtISBN, btnBuscarLibro);
        
        agregarComponente("Id del préstamo:", txtIdPrestamo, gbc, 0);
        agregarComponente("Id del usuario:", panelUsuario, gbc, 1);
        agregarComponente("Id del libro:", panelLibro, gbc, 2);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnCancelar);

        // Agregar panel de botones al diálogo
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(pnlBotones, gbc);


        // Listeners para los botones
        btnGuardar.addActionListener(new BotonGuardar());
        btnCancelar.addActionListener(new BotonCancelar());
        btnBuscarLibro.addActionListener(new BotonBuscarLibro());
        btnBuscarUsuario.addActionListener(new BotonBuscarUsuario());

    }
    
    private void agregarComponente(String etiqueta, JComponent componente, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel(etiqueta), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(componente, gbc);
    }
    
    private boolean validarCampos() {
        if (txtIdPrestamo.getText().trim().isEmpty()) {
            mostrarError("Por favor ingrese un ID de préstamo");
            return false;
        }
        
        if (txtIdUsuario.getText().trim().isEmpty()) {
            mostrarError("Por favor seleccione un usuario");
            return false;
        }
        
        if (txtISBN.getText().trim().isEmpty()) {
            mostrarError("Por favor seleccione un libro");
            return false;
        }
        
        return true;
    }    
    
     private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.ERROR_MESSAGE);
    }
    
    private void buscarUsuario() {
        //no se debe abrir un nueva ventana
        String idUsuario = txtIdUsuario.getText().trim();
        if(idUsuario.isEmpty()){
            mostrarError("Ingrese el ID del usuario");
            return;
        }
        
        //comparar que coincide
        Usuario usuario = biblioteca.buscarUsuarioPorId(idUsuario);
        if(usuario != null){
            JOptionPane.showMessageDialog(this, "Usuario encontrado: " + usuario.getNombre());
        }else{
            mostrarError("Usuario no encontrado");
        }
    }
    
    private void buscarLibro() {
        String idLibro = txtISBN.getText().trim();
        
        if(idLibro.isEmpty()){
            mostrarError("Ingrese el ISBN del libro");
            return;
        }
        
        List<Libro> libros = biblioteca.buscarLibrosPorISBN(idLibro);
        if(!libros.isEmpty()){
            Libro libro = libros.get(0);
            JOptionPane.showMessageDialog(this, "Libro encontrado: " + libro.getTitulo());

        }else{
            mostrarError("El libro no encontrado");
        }

    }
    
    private void guardarPrestamo() {
        if (!validarCampos()) {
            return;
        }

        String idPrestamo = txtIdPrestamo.getText().trim();
        String idUsuario = txtIdUsuario.getText().trim();
        String isbn = txtISBN.getText().trim();
        
        // buscar el usuario y el libro
        Usuario usuario = biblioteca.buscarUsuarioPorId(idUsuario);
        List<Libro> librosEncontrados = biblioteca.buscarLibrosPorISBN(isbn);

        if(usuario == null && librosEncontrados.isEmpty()){
            mostrarError("Usuario y libro no encontrados");
            return;
        }
        
        if (usuario == null) {
            mostrarError("Usuario no encontrado");
            return;
        }
        

        if (librosEncontrados.isEmpty()) {
            mostrarError("Libro no encontrado");
            return;
        }

        Libro libro = librosEncontrados.get(0);
    
        // si ya lo prestaron
        if (libro.isPrestado()) {
            mostrarError("El libro ya está prestado");
            return;
        }

        // hacemos el nuevo prestamo
        nuevoPrestamo = new Prestamo(idPrestamo, usuario, libro);
    
        if (nuevoPrestamo.registrarPrestamo()) {
            usuario.getLibrosPrestado().add(libro);
            libro.prestarLibro(usuario);
            JOptionPane.showMessageDialog(this, "Préstamo registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            guardar = true;
            biblioteca.agregarPrestamo(nuevoPrestamo);
            dispose();
        } else {
            mostrarError("No se pudo registrar el préstamo");
        } 
    }
    
    public Prestamo getNuevoPrestamo() {
        return nuevoPrestamo;
    }
    
    public boolean isGuardar() {
        return guardar;
    }

    private class BotonGuardar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            guardarPrestamo();
        }
    }
    
    private class BotonBuscarLibro implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            buscarLibro();
        }
    }
    
    private class BotonBuscarUsuario implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            buscarUsuario();
        }
    }


    private class BotonCancelar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    public static void main(String[] args) {
        DialogoNuevoPrestamo prestamo = new DialogoNuevoPrestamo(null);
        System.exit(0);
    }
}
