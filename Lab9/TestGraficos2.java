import java.awt.*;
public class TestGraficos2 extends Frame
{
    private GraficoEstadisticas grafica2;
    public static final Color PURPLE = new Color(128, 0, 128); //im just a girllllll
    private int[] valores = {4, 5, 2, 6, 7, 3, 9 };
    private String[] etiquetas = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio"};
    private Color[] colores = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, PURPLE, Color.PINK};
    
    //  Aquí definimos el manejador de la clase que nos interesa probar (atributo de la clase)
    public TestGraficos2 (String titulo) 
    {
        super(titulo);
        // Aquí creamos la instancia del componente gráfico a crear  
        this.grafica2 = new GraficoEstadisticas(valores, etiquetas, colores); 
        
        add(grafica2, BorderLayout.CENTER);
        
        setSize(900, 500);
        setVisible(true);    
    }
    
    public boolean handleEvent(Event e) 
    {
        if (e.id == Event.WINDOW_DESTROY) {
            hide();
            dispose();
            return true;
        }
        return super.handleEvent(e);
    }
    
    public static void main(String[] args) {
        TestGraficos2 grafico2 = new TestGraficos2 ("Gráfico de estadisticas");

        grafico2.resize(600,400);

        grafico2.show();
    }
}
