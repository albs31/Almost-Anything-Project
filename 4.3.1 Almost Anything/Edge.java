import java.util.ArrayList;

//edge class
public class Edge 
{
    public Node from;
    public Node to;
    public ArrayList<Car> cars;
//make edge between two nodes
    public Edge(Node from, Node to) 
    {
        this.from = from;
        this.to = to;
        this.cars = new ArrayList<>();
    }

//solve for line length
    public double getDistance() 
    {
        return Math.sqrt(Math.pow((to.x - from.x), 2) + Math.pow((to.y - from.y), 2));
    }

//solve for cost
    public double getCost() 
    {
        return getDistance() + (cars.size() * 1.2);
    }
}
