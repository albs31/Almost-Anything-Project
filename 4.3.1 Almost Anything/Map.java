import java.awt.*;
import javax.swing.*;

//Graphics class for drawing the graph/cars

public class Map extends JComponent {
    private JFrame frame;

    public int width;
    public int height;
    private Graph graph;

    public Map(Graph graph)
    {
        this.width = graph.width;
        this.height = graph.height;
        this.graph = graph;

        frame = new JFrame("Traffic Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(false); 

        frame.add(this);

        //this block is supposed to update car movements
        Timer timer = new Timer(30, 
            e -> {
                for (int i = 0; i < graph.getEdges().size(); i++) 
                {
                    Edge edge = graph.getEdges().get(i);
                
                    for (int j = 0; j < edge.cars.size(); j++) 
                    {
                        Car car = edge.cars.get(j);
                        car.move();
                    }
                }
                repaint();
            }
        );
        timer.start();
    }

    //This method is not meant to be called, Swing uses "repaint()" instead
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Paints all the edges and nodes on graph
        for (Edge edge : graph.getEdges())
        {
            //edge color more red based on more cars 
            //5 cars = max red
            double ratio = edge.cars.size() / 5.0;
            ratio = Math.min(ratio, 1.0);
            
       
            //calculate color based on ratio value
            int red = (int) (128 + ((255 - 128) * ratio));
            int green = (int) (128 + ((0 - 128) * ratio));
            int blue = (int) (128 + ((0 - 128) * ratio));

            Color c = new Color(red, green, blue);

            //change line colors and sizes for num cars on line
            g2.setColor(c);
            g2.setStroke(new BasicStroke((int) (ratio * 10)));
            g2.drawLine(edge.from.x, edge.from.y, edge.to.x, edge.to.y);

            
            if (edge.from.isActive) g2.setColor(Color.BLACK);
            else g2.setColor(Color.GRAY);

            g2.fillOval(edge.from.x - 10, edge.from.y - 10, 20, 20);
            
            if (edge.to.isActive) g2.setColor(Color.BLACK);
            else g2.setColor(Color.GRAY);

            g2.fillOval(edge.to.x - 10, edge.to.y - 10, 20, 20);
        }
        

        for (int i = 0; i < graph.getEdges().size(); i++) 
        {
            Edge edge = graph.getEdges().get(i);
        
            for (int j = 0; j < edge.cars.size(); j++) 
            {
                Car car = edge.cars.get(j);

                g2.setColor(new Color(128, 64, 64, 200));
                g2.fillOval((int) car.x - 5, (int) car.y - 5, 10, 10);
            }
        }
    }
}
