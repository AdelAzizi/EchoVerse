package phase2_graph;

public class Edge {

    GraphNode to;     // رأس مقصد
    int weight;       // وزن یال
    Edge next;        // یال بعدی در لیست

    public Edge(GraphNode to, int weight) {
        this.to = to;
        this.weight = weight;
        this.next = null;
    }
}
