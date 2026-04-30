import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException 
    {
        //Initializes a new graph
        Graph graph = new Graph(300, 2000, 1000);

        //Adds cars and roadblocks to the graph, then initializes the map
        graph.addCars(500);
        graph.addRoadblocks(10);
        Map map = new Map(graph);
    }
}
