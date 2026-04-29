import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Graph 
{
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    public int width;
    public int height;

    public Graph(int numNodes, int width, int height) 
    {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();

        this.width = width;
        this.height = height;

        for (int i = 0; i < numNodes; i++) 
        {
            Node n = new Node((int) (Math.random() * width), (int) (Math.random() * height));
            nodes.add(n);
        }

        for (int i = 0; i < nodes.size(); i++) 
        {
            Node current = nodes.get(i);
            
            Node closest1 = null;
            Node closest2 = null;
            Node closest3 = null;
            
            double dist1 = Double.MAX_VALUE;
            double dist2 = Double.MAX_VALUE;
            double dist3 = Double.MAX_VALUE;
            
            for (int j = 0; j < nodes.size(); j++)
            {
                if (i == j)
                {
                    continue;
                }
                
                Node other = nodes.get(j);
                double dist = Math.sqrt(Math.pow(current.x - other.x, 2) + Math.pow(current.y - other.y, 2));
                
                if (dist < dist1) 
                {
                    dist3 = dist2;
                    closest3 = closest2;
                    
                    dist2 = dist1;
                    closest2 = closest1;
                    
                    dist1 = dist;
                    closest1 = other;
                } else if (dist < dist2) 
                {
                    dist3 = dist2;
                    closest3 = closest2;
                    
                    dist2 = dist;
                    closest2 = other;
                } else if (dist < dist3) 
                {
                    dist3 = dist;
                    closest3 = other;
                }
            }
            
            if (closest1 != null) 
            {
                Edge e1 = new Edge(current, closest1);
                edges.add(e1);
                current.connections[0] = e1;
            }
            if (closest2 != null) 
            {
                Edge e2 = new Edge(current, closest2);
                edges.add(e2);
                current.connections[1] = e2;
            }
            if (closest3 != null) 
            {
                Edge e3 = new Edge(current, closest3);
                edges.add(e3);
                current.connections[2] = e3;
            }
        }
    }

    public void addRoadblocks(int numNodes) 
    {
        for (int i = 0; i < numNodes; i++) 
        {
            int index = (int)(Math.random() * nodes.size());
            nodes.get(index).isActive = false;
        }
    }

    public void addCars(int numCars) 
    {
        for (int i = 0; i < numCars; i++) 
        {
            int index = (int)(Math.random() * edges.size());
            Edge e = edges.get(index);
            e.cars.add(new Car(e));
        }
    }

    public void addNode(Node node) 
    {
        nodes.add(node);
    }

    public void addEdge(Node from, Node to) 
    {
        edges.add(new Edge(from, to));
    }

    public ArrayList<Node> getNodes()
    {
        return nodes;
    } 

    public ArrayList<Edge> getEdges() 
    {
        return edges;
    }

    public static double h(Node a, Node b) 
    {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    public static ArrayList<Node> findPath(Node startNode, Node endNode, Graph graph)
    {
        //A* implementation soon (working on it)
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        HashSet<Node> closedSet = new HashSet<>();

        //Initialize start node
        startNode.g = 0;
        startNode.h = h(startNode, endNode);   
        startNode.f = startNode.g + startNode.h;
        startNode.parent = null;

        
        openSet.add(startNode);

        while(!openSet.isEmpty()){
            Node current = openSet.poll();

            if (current == endNode) {
                //Reconstructing path
                ArrayList<Node> path = new ArrayList<>();
                Node step = current;
                while (step != null) {
                    path.add(0, step);
                    step = step.parent;
                }
                
                return path;
            }
                
            closedSet.add(current);

            //Fetch neighbors
            for (Edge edge : current.connections) {
                if (edge == null) continue;
                Node neighbor = (edge.from == current) ? edge.to : edge.from;
                if (!neighbor.isActive|| closedSet.contains(neighbor)) continue;

                double tentativeG = current.g + edge.getCost();

                if (tentativeG < neighbor.g || !openSet.contains(neighbor)) {
                    neighbor.g = tentativeG;
                    neighbor.h = h(neighbor, endNode);
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.parent = current;

                    if (!openSet.contains(neighbor))
                    openSet.add(neighbor);

                }

                }
            }

        return new ArrayList<>();
    }
}
