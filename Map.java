import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

//Map class for visualizing the graph and user interactions
public class Map extends JComponent {
    private JFrame frame;

    //Node radius for drawing
    private final int nodeRadius = 10;
    private Image buildingImage;
    private Image parkImage;

    //Graph parameters
    private int width;
    private int height;
    private Graph graph;

    //Pathfinding parameters
    private Node selectedStartNode = null;
    private Node selectedEndNode = null;
    private ArrayList<Node> solutionPath = null;

    //Constructor initializes the map, sets up frame, drawing, and user interaction
    public Map(Graph graph) throws IOException
    {
        this.width = graph.width;
        this.height = graph.height;
        this.graph = graph;

        this.buildingImage = ImageIO.read(new File("building.png"));
        this.parkImage = ImageIO.read(new File("park.png"));

        frame = new JFrame("Map Navigator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(false); 

        frame.add(this);

        JOptionPane.showMessageDialog(frame, 
            "Map Navigator\nLeft Click: Select starting point\nRight Click: Select ending point\n(Greyed out points are roadblocks and cannot be selected)\nThe algorithm will pick the fastest route (taking into account traffic)!\n"
        );

        //Mouse listener for selecting start and end nodes, then finding path between them
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                int x = e.getX();
                int y = e.getY();

                boolean leftClick = SwingUtilities.isLeftMouseButton(e);
                
                //Check if click is within radius of any node
                for (Node node : graph.getNodes())
                {
                    if (Math.sqrt(Math.pow(node.x - x, 2) + Math.pow(node.y - y, 2)) <= nodeRadius) 
                    {
                        if (leftClick) 
                        {
                            selectedStartNode = node;
                        } 
                        else 
                        {
                            selectedEndNode = node;
                        }

                        break;
                    }
                }

                //If both start and end nodes are selected, find path between them
                if (selectedStartNode != null && selectedEndNode != null) {
                    solutionPath = Graph.findPath(selectedStartNode, selectedEndNode, graph);
                }
                
            }
        });

        //Update loop for moving cars and repainting map
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
            //Edge color more red based on more cars 
            //5 cars = max red
            double ratio = edge.cars.size() / 5.0;
            ratio = Math.min(ratio, 1.0);
    
            //Calculate color based on ratio value
            int red = (int) (128 + ((255 - 128) * ratio));
            int green = (int) (128 + ((0 - 128) * ratio));
            int blue = (int) (128 + ((0 - 128) * ratio));

            Color c = new Color(red, green, blue);

            //Change line colors and sizes for number of cars on line
            g2.setColor(c);
            g2.setStroke(new BasicStroke((int) (ratio * 10)));
            g2.drawLine(edge.from.x, edge.from.y, edge.to.x, edge.to.y);

            //Draw image on nodes based on node type, gray out if inactive
            if (edge.from.isActive) 
            {
                g2.setColor(Color.BLACK);

                if (edge.from.nodeType == 1) g2.drawImage(buildingImage, edge.from.x - nodeRadius, edge.from.y - nodeRadius, nodeRadius * 2, nodeRadius * 2, null);
                else if (edge.from.nodeType == 2) g2.drawImage(parkImage, edge.from.x - nodeRadius, edge.from.y - nodeRadius, nodeRadius * 2, nodeRadius * 2, null);
                else g2.fillOval(edge.from.x - nodeRadius, edge.from.y - nodeRadius, nodeRadius * 2, nodeRadius * 2);
            }
            else
            {
                g2.setColor(Color.GRAY);
                g2.fillOval(edge.from.x - nodeRadius, edge.from.y - nodeRadius, nodeRadius * 2, nodeRadius * 2);
            }
            
            if (edge.to.isActive) 
            {
                g2.setColor(Color.BLACK);

                if (edge.to.nodeType == 1) g2.drawImage(buildingImage, edge.to.x - nodeRadius, edge.to.y - nodeRadius, nodeRadius * 2, nodeRadius * 2, null);
                else if (edge.to.nodeType == 2) g2.drawImage(parkImage, edge.to.x - nodeRadius, edge.to.y - nodeRadius, nodeRadius * 2, nodeRadius * 2, null);
                else g2.fillOval(edge.to.x - nodeRadius, edge.to.y - nodeRadius, nodeRadius * 2, nodeRadius * 2);
            }
            else
            {
                g2.setColor(Color.GRAY);
                g2.fillOval(edge.to.x - nodeRadius, edge.to.y - nodeRadius, nodeRadius * 2, nodeRadius * 2);
            }
        }

        //Draw selected start and end nodes
        if (selectedStartNode != null) 
        {
            g2.setColor(new Color(0, 240, 255, 150));
            g2.fillOval(selectedStartNode.x - nodeRadius, selectedStartNode.y - nodeRadius, nodeRadius * 2, nodeRadius * 2);
        }

        if (selectedEndNode != null) 
        {
            g2.setColor(new Color(0, 240, 255, 150));
            g2.fillOval(selectedEndNode.x - nodeRadius, selectedEndNode.y - nodeRadius, nodeRadius * 2, nodeRadius * 2);
        }

        //Draw solution path if it exists
        if (solutionPath != null)
        {
            g2.setColor(new Color(0, 240, 255, 150));
            g2.setStroke(new BasicStroke(10));

            for (int i = 0; i < solutionPath.size() - 1; i++) 
            {
                Node from = solutionPath.get(i);
                Node to = solutionPath.get(i + 1);
                g2.drawLine(from.x, from.y, to.x, to.y);
            }
        }

        //Draw cars on edges
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
