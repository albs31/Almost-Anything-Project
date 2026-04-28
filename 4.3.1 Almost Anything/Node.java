class Node implements Comparable<Node>
{
    public double x;
    public double y;

    public double g;
    public double h;
    public double f;
    
    public Node parent;
    public Edge[] connections = new Edge[3];
    public boolean isActive = true;
    public int nodeType = 0; // 0 for intersection, 1 for building, 2 for park

    public long getSeed() 
    {
        long bitsX = Double.doubleToLongBits(x);
        long bitsY = Double.doubleToLongBits(y);
        long seed = (bitsX ^ (bitsY * 31L));
        return seed; 
    }

    public Node(double x, double y) 
    {
        this.x = x;
        this.y = y;
    }

    public int compareTo(Node other) 
    {
        return Double.compare(this.f, other.f);
    }
}
