import java.awt.*;
public class TestGraficos extends Frame
{
    private DiagramaBiblioteca grafica;
    
    private String[] etiquetas = { "Ocupado", "Disponible", "Reservado"};
    private Color[] colores = {Color.RED, Color.GREEN, Color.YELLOW};

        
    //  Aquí definimos el manejador de la clase que nos interesa probar (atributo de la clase)
    public TestGraficos (String titulo) 
    {
        super(titulo);
        // Aquí creamos la instancia del componente gráfico a crear  
        this.grafica = new DiagramaBiblioteca(etiquetas, colores);

        add(grafica, BorderLayout.CENTER);
        
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
        TestGraficos grafico = new TestGraficos ("Gráfico");
        
        grafico.resize(600,400);

        grafico.show();
    }
}
