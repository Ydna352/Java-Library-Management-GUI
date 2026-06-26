import java.awt.*;
import java.awt.geom.*; /*Para utilizar Rectangle2D y 
otras clases geométricas avanzadas, es necesario importar el paquete */
import java.awt.event.*;

public class DiagramaBiblioteca extends Canvas 
{
    //Modificado por bri
    public static boolean modoOscuro = false; //para el modo oscurso solo hay que cambiar la bandera
    private String[] etiquetas;
    private Color[] colores;
    
    public static final int DISPONIBLE=0;
    public static final int RESERVADO=1;
    public static final int OCUPADO=2;
    
    private int [][] estadoCubiculos = new int[4][4]; //son 16 cubiculos

    private int ancho,alto;
    private int x1,y1;
    
    //variable del laboratorio 10
    private int libroUbicacion = -1;

    public DiagramaBiblioteca(String[] etiquetas, Color[] colores)
    {
        this.etiquetas = etiquetas;
        this.colores = colores;
        
        //por defecto, todos los cubiculos deben estar disponibles, se hace una matriz
        
        for(int i=0; i<4; i++){
            for(int j=0; j<4;j++){
                estadoCubiculos[i][j]=DISPONIBLE;
            }
        }
    }
    
    //metodo mas deprecado
       public boolean mouseUp(Event e, int x, int y) //coordenadas de donde se ubica el mouse
       {
           
           int startX = 270;//aqui empieza la esquina de los cubiculos
           int startY = 110;
           
           ancho = Math.abs (x - x1); //siempre devuelve el positivo
           alto = Math.abs (y - y1);
           
           x1 = Math.min (x1, x);
           y1 = Math.min (y1, y);
           
           
           for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    int cubiculoX = startX + (col * 70);
                    int cubiculoY = startY + (row * 70);
                    
                    if (x >= cubiculoX && x <= cubiculoX + 50 && y >= cubiculoY && y <= cubiculoY + 50) {
                        int estadoActual = estadoCubiculos[row][col];
                        estadoCubiculos[row][col] = (estadoActual + 1) % 3; //modulo de 3 es 0 (vuelve al inicio), si es 2 (mantiene el estado), si es 1 cambia al siguiente
                        repaint(); //para que se vuelvan a dibujarse viendo el cambio en la interfaz
                        break;
                    }
                }
            }
           return true; //todo cool? entonces retorna true
    }  

    
     public void paint(Graphics g) {
        // Aquí implementamos nuestro código de dibujo
        Graphics2D g2d = (Graphics2D) g; //casteo
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
                             
        if(modoOscuro)
        {
            //color de fondo negro
            g2d.setColor(Color.black);
            g2d.fillRect(0,0,getWidth(),getHeight());
        }
                             
        g2d.setStroke(new BasicStroke(3.0f));
        
        //contorno
        if(modoOscuro)
        {
            g2d.setColor(Color.white); //blanco
        }
        else
        {
            g2d.setColor(new Color(180, 0, 0)); //rojo
        }
        g2d.drawRect(40, 40, 720, 520); //rectangulo de contorno
        
        //libreros 
        if(modoOscuro)
        {
            g2d.setColor(new Color(153, 204, 255)); //naranja claro
            g2d.fill(new Rectangle2D.Double(80, 100, 120, 60));
            g2d.fill(new Rectangle2D.Double(80, 200, 120, 60)); 
            g2d.fill(new Rectangle2D.Double(80, 300, 120, 60)); 
            g2d.fill(new Rectangle2D.Double(80, 400, 120, 60));
            g2d.fill(new Rectangle2D.Double(600, 100, 120, 60)); 
            g2d.fill(new Rectangle2D.Double(600, 200, 120, 60)); 
            g2d.fill(new Rectangle2D.Double(600, 300, 120, 60));
            g2d.fill(new Rectangle2D.Double(600, 400, 120, 60)); 
        }
        else
        {
            GradientPaint gp = new GradientPaint(0, 0, 
            new Color(160, 160, 160), 80, 0, 
            new Color(90, 120, 120)); //verde gris
            g2d.setPaint(gp);
             g2d.fill(new Rectangle2D.Double(80, 100, 120, 60));
            g2d.fill(new Rectangle2D.Double(80, 200, 120, 60)); 
            g2d.fill(new Rectangle2D.Double(80, 300, 120, 60)); 
            g2d.fill(new Rectangle2D.Double(80, 400, 120, 60));
            g2d.fill(new Rectangle2D.Double(600, 100, 120, 60)); 
            g2d.fill(new Rectangle2D.Double(600, 200, 120, 60)); 
            g2d.fill(new Rectangle2D.Double(600, 300, 120, 60));
            g2d.fill(new Rectangle2D.Double(600, 400, 120, 60)); 
        }
        
        //letras de color negro
        g2d.setColor(Color.black);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Rangos izquierda - numeros
        g2d.drawString("100-200", 110, 135);
        g2d.drawString("201-300", 110, 235);
        g2d.drawString("301-400", 110, 335);
        g2d.drawString("401-500", 110, 435);
        
        // Rangos derecha - numeros
        g2d.drawString("501-600", 630, 135);
        g2d.drawString("601-700", 630, 235);
        g2d.drawString("701-800", 630, 335);
        g2d.drawString("801-900", 630, 435);
        
        //aqui terminan los numeros de los libreros
        
        //cubiculos de la libreria - morado/lila
        g2d.setColor(new Color(200, 200, 255));
        g2d.setStroke(new BasicStroke(2.0f));

        int startX = 270; 
        int startY = 110;
        
        for(int row = 0; row < 4; row++) 
        {
            for(int col = 0; col < 4; col++) 
            {
                int x = startX + (col * 70);
                int y = startY + (row * 70);
                //aqui hacemos el switch case para el mouse event
                switch(estadoCubiculos[row][col]){
                    case DISPONIBLE:
                        if(modoOscuro)
                        {
                            g2d.setColor(new Color(140, 255, 0)); //verde manzana
                        }
                        else
                        {
                            g2d.setColor(new Color(170, 255, 100)); //verde psitacho
                        }
                        break;
                    case RESERVADO:
                        if(modoOscuro)
                        {
                            g2d.setColor(new Color(255, 221, 51)); //amarillo brillante no tan intenso
                        }
                        else
                        {
                            g2d.setColor(new Color(255, 204, 0)); //amarillo tonatiuh
                        }
                        break;
                    case OCUPADO:
                        if(modoOscuro)
                        {
                            g2d.setColor(new Color(204, 0, 0)); //rojo para modo oscuro
                        }
                        else
                        {
                            g2d.setColor(new Color(255, 0, 0)); //rojo para modo claro
                        }
                        break;
                }
                
                g2d.fill(new Rectangle2D.Float(x, y, 50, 50));
                g2d.setColor(new Color(100, 100, 200)); //azul claro
                g2d.draw(new Rectangle2D.Float(x, y, 50, 50));
                
                // El código del rótulo  va aquí 
                // Rótulo 
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                int cubiculo = row * 4 + col + 1;
                g2d.drawString("C" + cubiculo, x + 18, y + 30); 
                                  
                g2d.setColor(new Color(200, 200, 255)); //azul muy claro
            }
        }
        
        if(modoOscuro)
        {
            g2d.setColor(new Color(255, 204, 153)); //azul claro
        }
        else
        {
            g2d.setColor(new Color(0, 0, 200, 180));//azul rey
        }
        
        g2d.fill(new Ellipse2D.Float(278, 400, 40, 40));
        g2d.fill(new Ellipse2D.Float(378, 400, 40, 40));
        g2d.fill(new Ellipse2D.Float(478, 400, 40, 40));
        g2d.fill(new Ellipse2D.Float(278, 450, 40, 40));
        g2d.fill(new Ellipse2D.Float(378, 450, 40, 40));
        g2d.fill(new Ellipse2D.Float(478, 450, 40, 40));
        
        //letras de los circulos en negro
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("M1", 290, 425);
        g2d.drawString("M2", 390, 425);
        g2d.drawString("M3", 490, 425);
        g2d.drawString("M4", 290, 475);
        g2d.drawString("M5", 390, 475);
        g2d.drawString("M6", 490, 475);
        
        //letero de recepcion 
        if(modoOscuro)
        {
            g2d.setColor(new Color(255, 127, 80));
            g2d.fill(new Rectangle2D.Double(200, 60, 400, 25));
        }
        else
        {
            GradientPaint gpMostrador = new GradientPaint(200, 60, 
            new Color(220, 0, 0), 600, 85, 
            new Color(180, 0, 0));
            g2d.setPaint(gpMostrador);
            g2d.fill(new Rectangle2D.Double(200, 60, 400, 25));
        }
        
        //encabezados en negro
        if(modoOscuro)
        {
            g2d.setColor(Color.white);
        }
        else
        {
            g2d.setColor(Color.black);
        }
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Entrada", 370, 35);
        g2d.drawString("Área de Lectura", 360, 520);
        
        g2d.setColor(Color.black);
        g2d.drawString("Recepción", 365, 75);
        
        for (int i = 0; i < etiquetas.length; i++) 
       {
            g.setFont(new Font("Times New Roman",Font.PLAIN,15)); 
            if(modoOscuro)
            {
                g2d.setColor(Color.WHITE);
            }
            else
            {
                g2d.setColor(Color.BLACK);
            }
            g2d.drawString(etiquetas[i], 115, (i*20) + 600);
            g2d.draw(new Rectangle2D.Double(100, (i * 20) + 590, 10, 10));
            g2d.setColor(colores[i]);
            g2d.fill(new Rectangle2D.Double(100, (i * 20) + 590, 10, 10));
       }
       
       // Si hay ubicación definida, marcarla
       if (libroUbicacion >= 0) 
       {
           marcarUbicacion((Graphics2D)g, libroUbicacion);
       }
    
    }
    //Aqui empiezan las mejoras
    
    //Diagrama biblioteca
        //estado de ocupacion
        //leyenda
        //modo oscuro
        //interactividad con el mouse
        //info en tiempo real - preguntar
        
    public void setLibroUbicacion(int ubicacion) 
    {
        this.libroUbicacion = ubicacion;
        repaint();
    }
    
    private void marcarUbicacion(Graphics2D g2d, int numLibrero) 
    {
	// Determinar en qué estantería se encuentra basado en el rango
        int x = 0, y = 0;
        if (numLibrero >= 100 && numLibrero <= 200) { x = 140; y = 130; }
        else if (numLibrero >= 201 && numLibrero <= 300) { x = 140; y = 230; }
        else if (numLibrero >= 301 && numLibrero <= 400) { x = 140; y = 330; }
        else if (numLibrero >= 401 && numLibrero <= 500) { x = 140; y = 430; }
        else if (numLibrero >= 501 && numLibrero <= 600) { x = 660; y = 130; }
        else if (numLibrero >= 601 && numLibrero <= 700) { x = 660; y = 230; }
        else if (numLibrero >= 701 && numLibrero <= 800) { x = 660; y = 330; }
        else if (numLibrero >= 801 && numLibrero <= 900) { x = 660; y = 430; }
        
        // Dibujar X en rojo
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(3.0f));
        int size = 20;
        g2d.drawLine(x - size/2, y - size/2, x + size/2, y + size/2);
        g2d.drawLine(x - size/2, y + size/2, x + size/2, y - size/2);
    }
        
        
}
