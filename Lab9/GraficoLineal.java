
import java.awt.*;
import java.awt.geom.*;
public class GraficoLineal extends Component
{
    private int[] datos;
    private String[] etiquetas;
    public static final Color PURPLE = new Color(128, 0, 128); //im just a girllllll
    private Color[] colores = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, PURPLE, Color.PINK};
    /**
     * Constructor for objects of class GraficoLineal
     */
    public GraficoLineal(int[] datos, String[] etiquetas)
    {
        this.datos = datos;
        this.etiquetas = etiquetas;
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int margen = 80;
        
        g.setFont(new Font("Times New Roman", Font.BOLD, 20)); 
        g.drawString("Prestamos al mes", 300, 30);

        
        int anchoGrafico = getWidth() - 2 * margen;
        int altoGrafico = getHeight() -((3 * margen) + 20);      
        
        int maximo = 0;
        
        g2d.drawLine(margen, margen, margen, getHeight() - margen);
        g2d.drawLine(margen, getHeight() - margen, getWidth() - margen, getHeight() - margen); 
        
        for(int valor : datos) {
            maximo = Math.max(maximo, valor);
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
        
        int espacio_entre_puntos = anchoGrafico / (datos.length - 1);
        //posicion de los puntos
        int[] puntosX = new int[datos.length];
        int[] puntosY = new int[datos.length];
        
        for (int i = 0; i < datos.length; i++) 
        {
            int x = margen + i * espacio_entre_puntos;
            int y = getHeight() - margen - (int)((datos[i] * altoGrafico) / maximo);
            puntosX[i] = x;
            puntosY[i] = y;
        }
    
        // Dibujar líneas conectando los puntos
        g2d.setColor(new Color(0, 100, 200));
        for (int i = 0; i < datos.length - 1; i++) 
        {
            g2d.drawLine(puntosX[i], puntosY[i], puntosX[i + 1], puntosY[i + 1]);
        }
        
        for (int i = 0; i < datos.length; i++) 
        {
            g2d.setColor(colores[i]);
            g2d.fillOval(puntosX[i] - 3, puntosY[i] - 3, 6, 6); // punto pintao
            g2d.setColor(Color.BLACK);
            g2d.drawOval(puntosX[i] - 3, puntosY[i] - 3, 6, 6); // contorno del punto

            g2d.drawString(etiquetas[i], puntosX[i] - 10, getHeight() - margen / 2);
            g2d.drawString(String.valueOf(datos[i]), puntosX[i], puntosY[i] - 5);
        }
        
        for (int i = 0; i <= maximo; i ++) 
        {
            int y = getHeight() - margen - (i * altoGrafico / maximo);
            g2d.drawString(String.valueOf(i), margen - 30, y);
            g2d.drawLine(margen - 5, y, margen, y);
        }
    }
}
