import java.util.ArrayList;

//Edge class to connect nodes and hold cars
public class Edge 
{
    public Node from;
    public Node to;
    public ArrayList<Car> cars;

    //Constructor to make edge between two nodes
    public Edge(Node from, Node to) 
    {
        this.from = from;
        this.to = to;
        this.cars = new ArrayList<>();
    }

    //Solve for line distance
    public double getDistance() 
    {
        return Math.sqrt(Math.pow((to.x - from.x), 2) + Math.pow((to.y - from.y), 2));
    }

    //Solve for cost (taking into account cars)
    public double getCost() 
    {
        return getDistance() + (cars.size() * 1.2);
    }
}
