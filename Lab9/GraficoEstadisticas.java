/*
 * Para cada grafico
 * - Título del gráfico listo
   - Ejes X e Y con escalas listo
   - Diferentes colores para las barras listo
   - Leyendas de los datos 
 */
import java.awt.*;
import java.awt.geom.*;
public class GraficoEstadisticas extends Component {
    private int[] datos;
    private String[] etiquetas;
    private Color[] colores;
    
    public GraficoEstadisticas(int[] datos, String[] etiquetas, Color[] colores) {
        this.datos = datos;
        this.etiquetas = etiquetas;
        this.colores = colores;
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int margen = 80;
        
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g.drawString("Prestamos al mes", 300, 30);
        
        

        g2d.drawLine(margen, margen, margen, getHeight() - margen);
        g2d.drawLine(margen, getHeight() - margen, getWidth() - margen, getHeight() - margen);   
        
        int anchoGrafico = getWidth() - 2 * margen;
        int altoGrafico = getHeight() -((3 * margen) + 20);     
        
        int maximo = 0;
       for(int valor : datos) {
            maximo = Math.max(maximo, valor);
        }
        
       int anchoBarra = anchoGrafico / datos.length;
        
       for(int i = 0; i < datos.length; i++) {
           int altoBarra = (int)((datos[i] * altoGrafico) / maximo);
           int x = margen + i * anchoBarra;
           int y = getHeight() - margen - altoBarra;
           g2d.setColor(colores[i]);
           g2d.fill(new Rectangle2D.Double(x, y, anchoBarra-5, altoBarra));
           g2d.setColor(Color.BLACK);
           g2d.draw(new Rectangle2D.Double(x, y, anchoBarra-5, altoBarra));
           g2d.drawString(etiquetas[i], x, getHeight() - margen/2);
           g2d.drawString(String.valueOf(datos[i]), x, y - 5);
       }
       
       for (int i = 0; i <= maximo; i ++) 
        {
            int y = getHeight() - margen - (i * altoGrafico / maximo);
            g2d.drawString(String.valueOf(i), margen - 30, y);
            g2d.drawLine(margen - 5, y, margen, y);
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
    }
}
