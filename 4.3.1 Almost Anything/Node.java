import java.util.ArrayList;

public class Node implements Comparable<Node>
{
    public int x;
    public int y;

    public double g;
    public double h;
    public double f;
    
    public Node parent;
    public ArrayList<Edge> connections = new ArrayList<>();
    public boolean isActive = true;
    public int nodeType = 0; // 0 for intersection, 1 for building, 2 for park

//generate univeral seed for each node
    public long getSeed() 
    {
        long bitsX = Double.doubleToLongBits(x);
        long bitsY = Double.doubleToLongBits(y);
        long seed = (bitsX ^ (bitsY * 31L));
        return seed; 
    }

    public Node(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Node other) 
    {
        return Double.compare(this.f, other.f);
    }
}
