package phase2_graph;

public class GraphNode {

    int id;                 // شناسه اپیزود
    Edge edgeList;          // لیست یال‌های متصل به این رأس

    // متغیرهای مورد نیاز برای دایکسترا
    boolean visited;
    int distance;
    GraphNode previous;

    public GraphNode(int id) {
        this.id = id;
        this.edgeList = null;
        this.visited = false;
        this.distance = Integer.MAX_VALUE;
        this.previous = null;
    }
}
