class Node implements Comparable<Node>
{
    //Position/identifier variables
    public double x;
    public double y;

    //g, h, f (cost estimates)
    public double g;
    public double h;
    public double f;

    //Node relationships
    public Node parent;
    public Edge[] connections = new Edge[3];

    //generate seed from node location
    public long getSeed(){
        long bitsX = Double.doubleToLongBits(x);
        long bitsY = Double.doubleToLongBits(y);
        long seed = (bitsX^(bitsY*31L));
        return seed; 
    }


    public Node (double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public int compareTo(Node other)
    {
        return Double.compare(this.f, other.f);
    }
}