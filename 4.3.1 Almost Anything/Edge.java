import java.util.ArrayList;

//traffic datas
public class Edge{
    public Node from;
    public Node to;
    public ArrayList<Car> cars;

    public double getWeight()
    {
        return 1 + 0.1 * cars.size();
    }

    public double getDistance()
    {
        return Math.sqrt(
            Math.pow((to.x - from.x), 2) + Math.pow((to.y - from.y), 2)
        );
    }
    
    //return num cars from startnode to endnode
    public int getTraffic(Node from, Node to){
      int x = 0;
      long seed1 = to.getSeed();
      long seed2 = from.getSeed();
      long seed
      this.from = from;
      this.to = to;
      x = ();
    
      return x;
    }
}