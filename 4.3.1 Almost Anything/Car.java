public class Car 
{
    public Edge currentEdge;
    public boolean movingToB;
    public double progress;
    public double speed;
    public double x;
    public double y;

    public Car(Edge edge) 
    {
        this.currentEdge = edge;
        this.movingToB = true;
        this.progress = 0.0;
        this.speed = Math.random() * 2.0 + 1.0;
        updatePosition();
    }

    public void move() 
    {
        if (currentEdge == null) 
        {
            return;
        }
        
        progress += speed / currentEdge.getDistance();
        
        if (progress >= 1.0) 
        {
            progress = 0.0;
            Node targetNode;
            
            if (movingToB) 
            {
                targetNode = currentEdge.to;
            } 
            else 
            {
                targetNode = currentEdge.from;
            }
            
            if (!targetNode.isActive) 
            {
                movingToB = !movingToB;
                updatePosition();
                return;
            }
            
            Edge nextEdge = pickNextEdge(targetNode);
            
            if (nextEdge != null) 
            {
                currentEdge.cars.remove(this);
                currentEdge = nextEdge;
                currentEdge.cars.add(this);
                
                if (currentEdge.from == targetNode) 
                {
                    movingToB = true;
                } 
                else 
                {
                    movingToB = false;
                }
            } 
            else 
            {
                movingToB = !movingToB;
            }
        }
        
        updatePosition();
    }

    private Edge pickNextEdge(Node node) 
    {
        int totalWeight = 0;
        
        for (int i = 0; i < node.connections.length; i++) 
        {
            Edge e = node.connections[i];
            if (e != null && e != currentEdge) 
            {
                totalWeight += 1 + e.cars.size();
            }
        }
        
        if (totalWeight == 0) 
        {
            return currentEdge;
        }
        
        int random = (int) (Math.random() * totalWeight);
        int count = 0;
        
        for (int i = 0; i < node.connections.length; i++) 
        {
            Edge e = node.connections[i];
            if (e != null && e != currentEdge) 
            {
                count += 1 + e.cars.size();
                if (random < count) 
                {
                    return e;
                }
            }
        }
        
        return currentEdge;
    }

    private void updatePosition() 
    {
        if (currentEdge == null) 
        {
            return;
        }
        
        Node startNode;
        Node endNode;
        
        if (movingToB) 
        {
            startNode = currentEdge.from;
            endNode = currentEdge.to;
        } 
        else 
        {
            startNode = currentEdge.to;
            endNode = currentEdge.from;
        }
        
        x = startNode.x + (endNode.x - startNode.x) * progress;
        y = startNode.y + (endNode.y - startNode.y) * progress;
    }
}
