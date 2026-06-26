
/**
 * Write a description of class TestGraficos3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.*;
public class TestGraficos3 extends Frame
{
    private GraficoLineal grafica3;
    private int[] valores = {4, 5, 2, 6, 7, 3, 9 };
    private String[] etiquetas = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio"};

    /**
     * Constructor for objects of class TestGraficos3
     */
    public TestGraficos3(String titulo)
    {
        super(titulo);
        // Aquí creamos la instancia del componente gráfico a crear  
        this.grafica3 = new GraficoLineal(valores, etiquetas); 
        
        add(grafica3, BorderLayout.CENTER);
        
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
        TestGraficos3 grafico3 = new TestGraficos3 ("Gráfico de lineas");

        grafico3.resize(600,400);

        grafico3.show();
    }
}
