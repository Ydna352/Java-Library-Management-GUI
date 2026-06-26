import javax.swing.*;
import java.awt.*;

public class DialogoAcercaDe extends JDialog {

    public DialogoAcercaDe(Frame parent) {
        super(parent, "Acerca de...", true);
        
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea texto = new JTextArea(
            "Versión del programa: 1.0\n" +
            "Andrea Martinez Arroyo\n" +
            "Jorge Bryan Piedras Mora\n" +
            "Naomi Astrid Paul Ortega"
        );
        texto.setEditable(false);
        texto.setFont(new Font("SansSerif", Font.PLAIN, 14));
        texto.setBackground(panel.getBackground());
        texto.setMargin(new Insets(10, 10, 10, 10));

        panel.add(texto, BorderLayout.CENTER);

        // Botón cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        JPanel pnlBoton = new JPanel();
        pnlBoton.add(btnCerrar);
        panel.add(pnlBoton, BorderLayout.SOUTH);

        // Agregar panel al diálogo
        add(panel);
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }
}
