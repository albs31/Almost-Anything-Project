import java.util.ArrayList;

public class Edge 
{
    public Node from;
    public Node to;
    public ArrayList<Car> cars;

    public Edge(Node from, Node to) 
    {
        this.from = from;
        this.to = to;
        this.cars = new ArrayList<>();
    }

    public double getDistance() 
    {
        return Math.sqrt(Math.pow((to.x - from.x), 2) + Math.pow((to.y - from.y), 2));
    }

    public double getCost() 
    {
        return getDistance() + (cars.size() * 1.2);
    }
}
