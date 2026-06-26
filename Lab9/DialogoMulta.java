import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class DialogoMulta extends JDialog
{
    
    
    private JButton btnCerrar;
    private JLabel lblMensaje;
    
    public DialogoMulta(Frame parent, double multa) {
        super(parent, "Multa por retraso", true);
        
        // Inicializamos la ventana
        setLayout(new FlowLayout());
        setSize(300, 150);
        setLocationRelativeTo(parent);
        
        lblMensaje = new JLabel("Debe pagar una multa de: $" + multa);
        add(lblMensaje);
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        }); 
        add(btnCerrar);
        
        setResizable(false);
    }

    
}
