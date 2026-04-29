public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(150, 2000, 1000);
        graph.addCars(50);
        graph.addRoadblocks(10);
        Map map = new Map(graph);
        map.repaint();
    }
}
