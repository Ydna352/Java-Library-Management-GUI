
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class DialogoNuevoLibro extends JDialog
{
   private JTextField txtISBN, txtTitulo, txtAutor, txtEditorial, txtTipoDanio;
   private JSpinner spnPaginas;
   private JButton btnGuardar, btnCancelar;
   private boolean guardadoExitoso = false;
   private JPanel pnlBotones;
   private JMenuBar menuBar; // Añadido para el menú
   private JMenu mnuCatalogo;
   private JButton btnNuevoLibro;
   
   private JComboBox<Categorias> cbGenero;
   
   private Libro nuevoLibro;
   
   public DialogoNuevoLibro(Frame parent) 
   {
       super(parent, "Nuevo Libro", true);
        
       inicializarComponentes();

        // Eliminamos setSize(200, 250);
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
   }
   
   private void inicializarComponentes() 
   {
       setLayout(new GridBagLayout());
       GridBagConstraints gbc = new GridBagConstraints();
       gbc.insets = new Insets(5, 5, 5, 5);
       
       cbGenero = new JComboBox<>(Categorias.values());
       
       txtISBN = new JTextField(20);
       txtTitulo = new JTextField(20);
       txtAutor = new JTextField(20);
       txtEditorial = new JTextField(20);
       txtTipoDanio = new JTextField(20); // Asegúrate de haber inicializado este campo

       spnPaginas = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        
       // Agregar componentes con sus etiquetas
       agregarComponente("Título:", txtTitulo, gbc, 0);
       agregarComponente("Autor:", txtAutor, gbc, 1);
       agregarComponente("ISBN:", txtISBN, gbc, 2);
       agregarComponente("Páginas:", spnPaginas, gbc, 3);
       agregarComponente("Género:", cbGenero, gbc, 4);  // Agregar JComboBox para género
       agregarComponente("Tipo de Daño:", txtTipoDanio, gbc, 5); // Agregar tipo de daño
        
       // Panel de botones
       pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
       btnGuardar = new JButton("Guardar");
       btnCancelar = new JButton("Cancelar");
       pnlBotones.add(btnGuardar);
       pnlBotones.add(btnCancelar);
       
       // Agregar panel de botones al diálogo
       gbc.gridx = 0;
       gbc.gridy = 7;
       gbc.gridwidth = 2;
       gbc.fill = GridBagConstraints.HORIZONTAL;
       add(pnlBotones, gbc);
       
       // botones relacionados con los listeners
       btnGuardar.addActionListener(new BotonGuardar());
       btnCancelar.addActionListener(new BotonCancelar());
   }
   
   private void agregarComponente(String etiqueta, JComponent componente, GridBagConstraints gbc, int y) 
   {
       gbc.gridx = 0;
       gbc.gridy = y;
       gbc.gridwidth = 1;
       gbc.anchor = GridBagConstraints.EAST;   
       add(new JLabel(etiqueta), gbc);
       
       gbc.gridx = 1;
       gbc.anchor = GridBagConstraints.WEST;
       add(componente, gbc);
   }
   
   private boolean validarCampos() 
   {
        String isbn = txtISBN.getText().trim();
        
    if (txtISBN.getText().trim().isEmpty() || txtTitulo.getText().trim().isEmpty() || txtAutor.getText().trim().isEmpty()) 
    {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos requeridos", "Error de validación", JOptionPane.ERROR_MESSAGE);
        return false;         
    }
    
    if (isbn.length() != 10 && isbn.length() != 13) 
    {
            JOptionPane.showMessageDialog(this, "El ISBN debe tener 10 o 13 dígitos", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
   }
   
   private void guardarLibro() 
   {
    if (validarCampos()) 
    {
        // Lógica para guardar el libro
        String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String isbn = txtISBN.getText().trim();
            int numPaginas = (int) spnPaginas.getValue();
            Categorias genero = (Categorias) cbGenero.getSelectedItem();  // Obtener genero del JComboBox
            String tipoDanio = txtTipoDanio.getText().trim();  // Obtener tipo de daño del campo de texto

            // Crear el nuevo libro con los datos obtenidos
            Libro nuevoLibro = new Libro(titulo, autor, isbn, numPaginas, genero, tipoDanio);
        
        JOptionPane.showMessageDialog(this, "Libro guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            guardadoExitoso = true;
            dispose();
    }    
   }
   
   private class BotonGuardar implements ActionListener 
   {
       public void actionPerformed(ActionEvent e) 
       {
          guardarLibro(); 
       }    
   }
   
   private class BotonCancelar implements ActionListener 
   {
       public void actionPerformed(ActionEvent e) 
       {
           dispose();
       }
   }
   
   public Libro getLibro() 
   {
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String isbn = txtISBN.getText().trim();
        int numPaginas = (int) spnPaginas.getValue();
        Categorias genero = (Categorias) cbGenero.getSelectedItem();
        String tipoDanio = txtTipoDanio.getText().trim();
        
        return new Libro(titulo, autor, isbn, numPaginas, genero, tipoDanio);
   }        
   
   public boolean isGuardadoExitoso() 
   {
        return guardadoExitoso;
   }
      
   public static void main(String[] args) 
   {
        DialogoNuevoLibro dialogo = new DialogoNuevoLibro(null);
        System.exit(0);
    }
    
    
}
