import java.awt.*;
import java.awt.geom.*;

public class GraficoPastel extends Component {
    private int[] datos;
    private String[] etiquetas;
    public static final Color PURPLE = new Color(128, 0, 128); //im just a girllllll
    private Color[] colores = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, PURPLE, Color.PINK};

    public GraficoPastel(int[] datos, String[] etiquetas) {
        this.datos = datos;
        this.etiquetas = etiquetas;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int margen = 80;
        
         g.setFont(new Font("Times New Roman", Font.BOLD, 20)); 
        g.drawString("Prestamos al mes", 300, 30);
        
        int diametro = Math.min(getWidth(), getHeight()) - 2 * margen; // Ajuste para asegurar que el gráfico quepa bien
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;

        // Calcular el total de los datos para las proporciones
        int totalDatos = 0;
        for (int valor : datos) {
            totalDatos += valor;
        }
        
        //escribir las leyendas
        for (int i = 0; i < datos.length; i++) 
        {
            g.setFont(new Font("Times New Roman",Font.PLAIN,15)); 
            g2d.setColor(Color.BLACK);
            g2d.drawString(etiquetas[i], 115, (i*20) + 30);
            g2d.draw(new Rectangle2D.Double(100, (i * 20) + 20, 10, 10));
            g2d.setColor(colores[i]);
            g2d.fill(new Rectangle2D.Double(100, (i * 20) + 20, 10, 10));
        }

        // Dibuja cada sección del gráfico de pastel
        int anguloInicio = 0;
        for (int i = 0; i < datos.length; i++) {
            int anguloFinal = (int) ((datos[i] * 360.0) / totalDatos); // Proporción del ángulo total (360 grados)
            
            // Dibuja el segmento de pastel
            g2d.setColor(colores[i]);
            g2d.fillArc(centroX - diametro / 2, centroY - diametro / 2, diametro, diametro, anguloInicio, anguloFinal);
            
            // Actualiza el ángulo de inicio para el siguiente segmento
            anguloInicio += anguloFinal;
        }

        // Dibujar etiquetas y porcentajes
        anguloInicio = 0;
        for (int i = 0; i < datos.length; i++) {
            int anguloFinal = (int) ((datos[i] * 360.0) / totalDatos);
            int anguloMedio = anguloInicio - anguloFinal / 2; // Calculamos el ángulo medio para colocar la etiqueta

            // Convertimos el ángulo medio a coordenadas
            int x = (int) (centroX + (diametro / 2) * Math.cos(Math.toRadians(anguloMedio)));
            int y = (int) (centroY + (diametro / 2) * Math.sin(Math.toRadians(anguloMedio)));
            
            int rectX = x - 15; // Ajuste para que el rectángulo quede bien posicionado
            int rectY = y - 20; // Ajuste para que el rectángulo quede debajo de la etiqueta
            int rectWidth = 110;  // Ancho del rectángulo
            int rectHeight = 30; 

            // Dibuja el texto con la etiqueta y el valor
            g2d.setColor(Color.WHITE);
            g2d.fill(new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight));
            //Colocar el color de contorno
            g2d.setColor(Color.BLACK);
            g2d.draw(new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight));
            g2d.drawString(etiquetas[i] + " (" + (int)((datos[i] * 100.0) / totalDatos) + "%)", x, y);

            // Actualiza el ángulo de inicio
            anguloInicio -= anguloFinal;
        }
    }
}
