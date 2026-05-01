import java.io.IOException;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) throws IOException 
    {
        //Initializes a new graph with nodes based on user input
        Graph graph = new Graph(Integer.parseInt(JOptionPane.showInputDialog("Number of Nodes:")), 2000, 1000);

        //Adds cars and roadblocks based on user input to the graph, then initializes the map
        graph.addCars(Integer.parseInt(JOptionPane.showInputDialog("Number of Cars:")));
        graph.addRoadblocks(Integer.parseInt(JOptionPane.showInputDialog("Number of Roadblocks:")));
        
        Map map = new Map(graph);
    }
}
