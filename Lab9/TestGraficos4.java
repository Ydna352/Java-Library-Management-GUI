import java.awt.*;
public class TestGraficos4 extends Frame
{
    private GraficoPastel grafica4;
    private int[] valores = {4, 5, 2, 6, 7, 3, 9};
    private String[] etiquetas = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio"};


    //  Aquí definimos el manejador de la clase que nos interesa probar (atributo de la clase)
    public TestGraficos4 (String titulo) 
    {
        super(titulo);
        // Aquí creamos la instancia del componente gráfico a crear  
        this.grafica4 = new GraficoPastel(valores, etiquetas); 
        
        add(grafica4, BorderLayout.CENTER);
        
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
        TestGraficos4 grafico4 = new TestGraficos4 ("Gráfico de estadisticas");

        grafico4.resize(600,400);

        grafico4.show();
    }
}
