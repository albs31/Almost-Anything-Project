import java.util.ArrayList;

//Node class for A* nodes
public class Node implements Comparable<Node>
{
    public int x;
    public int y;

    //A* variables
    public double g;
    public double h;
    public double f;
    
    public Node parent;
    public ArrayList<Edge> connections = new ArrayList<>();
    public boolean isActive = true;
    public int nodeType = 0; // 0 for intersection, 1 for building, 2 for park

    //Constructor with random node type assignment
    public Node(int x, int y) 
    {
        this.x = x;
        this.y = y;

        nodeType = (int)(Math.random() * 3); // Randomly assign node type
    }

    //Comparison method for A* priority queue
    @Override
    public int compareTo(Node other) 
    {
        return Double.compare(this.f, other.f);
    }
}
