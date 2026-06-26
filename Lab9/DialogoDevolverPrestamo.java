
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class DialogoDevolverPrestamo extends JDialog
{
    private JTextField txtIdPrestamo, txtIdUsuario, txtISBN, txtTipoDanio;
    private JPanel pnlBotones;
    private JButton btnGuardar, btnCancelar;
    private boolean guardar = false;
    private Biblioteca biblioteca;

    public DialogoDevolverPrestamo(Frame parent)
    {
        super(parent, "Devolver prestamo", true);
        
        inicializarComponentes();
        setSize(400, 300); 
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    public void setBiblioteca(Biblioteca biblioteca) 
    {
        this.biblioteca = biblioteca;
    }
    private void inicializarComponentes() 
    {
       setLayout(new GridBagLayout());
       GridBagConstraints gbc = new GridBagConstraints();
       gbc.insets = new Insets(5, 5, 5, 5);
       
       txtIdPrestamo = new JTextField(20);
       txtIdUsuario = new JTextField(20);
       txtISBN = new JTextField(20);
       txtTipoDanio = new JTextField(20);  
       
       agregarComponente("ID del Prestamo:", txtIdPrestamo, gbc, 0);
       agregarComponente("ID del Usuario:", txtIdUsuario, gbc, 1);
       agregarComponente("ISBN:", txtISBN, gbc, 2);
       agregarComponente("Tipo Danio:", txtTipoDanio, gbc, 3);
       
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
   
   private class BotonGuardar implements ActionListener 
   {
       public void actionPerformed(ActionEvent e) 
       {
          guardarDevolucion(); 
       }    
   }
   
   public boolean isGuardar() {
        return guardar;
    }

   
   private class BotonCancelar implements ActionListener 
   {
       public void actionPerformed(ActionEvent e) 
       {
           dispose();
       }
   }
   
   private boolean validarCampos() 
   {
        String isbn = txtISBN.getText().trim();
        
    if (txtISBN.getText().trim().isEmpty() || txtIdPrestamo.getText().trim().isEmpty() || txtIdUsuario.getText().trim().isEmpty() || txtTipoDanio.getText().trim().isEmpty()) 
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
   
   private void guardarDevolucion() 
   {
        if (!validarCampos()){
            return;
        }
    
        String idPrestamo = txtIdPrestamo.getText().trim();
        String idUsuario = txtIdUsuario.getText().trim();
        String isbn = txtISBN.getText().trim();
        String tipoDanio = txtTipoDanio.getText().trim();  

        Prestamo prestamo = biblioteca.buscarPrestamoPorId(idPrestamo);

        
        if (prestamo == null) {
            JOptionPane.showMessageDialog(this, "Préstamo no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (prestamo.getEstado() == Prestamo.DEVUELTO) {
            JOptionPane.showMessageDialog(this, "Este préstamo ya fue devuelto anteriormente", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!prestamo.getUsuario().getId().equals(idUsuario) ||
            !prestamo.getLibro().getIsbn().equals(isbn)) {
            JOptionPane.showMessageDialog(this, "Los datos no coinciden con el préstamo registrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Registrar tipo de daño en el libro
        Libro libro = prestamo.getLibro();
        libro.setTipoDanio(tipoDanio);

        if (prestamo.procesarDevolucion()) {
            // Actualizar el libro en la biblioteca
            for (int i = 0; i < biblioteca.getLibros().size(); i++) {
                if (biblioteca.getLibros().get(i).getIsbn().equals(isbn)) {
                    biblioteca.getLibros().set(i, libro);
                    break;
                }
            }
            
            JOptionPane.showMessageDialog(this, 
                "Devolución exitosa\n" + libro.notificacion(prestamo.getUsuario()), 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                guardar = true;
                dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "No se pudo procesar la devolución", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    
   }
    
   public static void main(String[] args) 
   {
        DialogoDevolverPrestamo dialogo = new DialogoDevolverPrestamo(null);
        System.exit(0);
    }
}
