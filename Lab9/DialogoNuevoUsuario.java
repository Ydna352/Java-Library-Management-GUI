import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogoNuevoUsuario extends JDialog {
    private JTextField txtNombre, txtPreferenciaLectura, txtId;
    private JComboBox<Categorias> cbGenero;
    private JButton btnGuardar, btnCancelar;
    private boolean guardadoExitoso = false;
    private JPanel pnlBotones;
    
    private Usuario nuevoUsuario;

    public DialogoNuevoUsuario(Frame parent) {
        super(parent, "Nuevo Usuario", true);
        
        inicializarComponentes();
        
        setSize(400, 300); // Ancho x Alto
        //pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }
    
    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Componentes del formulario
        txtId = new JTextField(10);
        txtNombre = new JTextField(20);
        txtPreferenciaLectura = new JTextField(20);
        cbGenero = new JComboBox<>(Categorias.values()); // El combo para seleccionar género favorito

        // Agregar los componentes 
        agregarComponente("Id", txtId, gbc, 0);
        agregarComponente("Nombre:", txtNombre, gbc, 1);
        agregarComponente("Preferencia de Lectura:", txtPreferenciaLectura, gbc, 2);
        agregarComponente("Género Favorito:", cbGenero, gbc, 3);

        // Botones 
        pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnCancelar);

        // Agregar panel de botones al diálogo
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(pnlBotones, gbc);

        // Listeners para los botones
        btnGuardar.addActionListener(new BotonGuardar());
        btnCancelar.addActionListener(new BotonCancelar());
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
        if (txtId.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() || txtPreferenciaLectura.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos requeridos", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void guardarUsuario() {
        if (validarCampos()) {
            String nombre = txtNombre.getText().trim();
            String id = txtId.getText().trim();
            String preferenciaLectura = txtPreferenciaLectura.getText().trim();
            Categorias genero = (Categorias) cbGenero.getSelectedItem();

            // Crear el nuevo usuario
            this.nuevoUsuario = new Usuario(nombre, id, preferenciaLectura, genero);

            JOptionPane.showMessageDialog(this, "Usuario guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            guardadoExitoso = true;
            dispose();
        }
    }

    private class BotonGuardar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            guardarUsuario();
        }
    }

    private class BotonCancelar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    public Usuario getUsuario() {
        
        if (nuevoUsuario == null) 
        {
            String nombre = txtNombre.getText().trim();
            String id = txtId.getText().trim(); // Ahora incluye el ID
            String preferenciaLectura = txtPreferenciaLectura.getText().trim();
            Categorias genero = (Categorias) cbGenero.getSelectedItem();
            
            return new Usuario(nombre, id, preferenciaLectura, genero);
        }
        return nuevoUsuario;
        }

    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }

    public static void main(String[] args) {
        DialogoNuevoUsuario dialogo = new DialogoNuevoUsuario(null);
        System.exit(0);
    }
}
