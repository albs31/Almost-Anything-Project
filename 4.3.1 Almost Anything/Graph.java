import java.util.ArrayList;

public class Graph {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    //Initialize a new graph
    public Graph (int numNodes, double width, double height) {
        nodes = new ArrayList<>();

        for (int i = 0; i < numNodes; i++)
        {
            Node n = new Node(Math.random() * width, Math.random() * height);
        }

        for (Node n : nodes)
        {
            for (Node.)
        }

    }

    public void addNode(Node node) {
        nodes.add(node);
    }
    public void addEdge(Node from, Node to) {
        edges.add(new Edge(from, to));
    }
    public ArrayList<Node> getNodes() {
        return nodes;
    } 
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public static ArrayList<Node> findPath(Node startNode, Node endNode)
    {

    }
}
