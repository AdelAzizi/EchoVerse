package phase2_graph;

public class Graph {

    private GraphNode[] nodes;
    private int size;

    public Graph(int maxNodes) {
        nodes = new GraphNode[maxNodes];
        size = 0;
    }

    /* اضافه کردن رأس جدید */
    public void addVertex(int id) {
        nodes[size++] = new GraphNode(id);
    }

    /* پیدا کردن رأس با id */
    private GraphNode getNode(int id) {
        for (int i = 0; i < size; i++) {
            if (nodes[i].id == id)
                return nodes[i];
        }
        return null;
    }

    /* اضافه کردن یال بدون جهت */
    public void addEdge(int from, int to, int weight) {
        GraphNode fromNode = getNode(from);
        GraphNode toNode = getNode(to);

        if (fromNode == null || toNode == null)
            return;

        // یال از from به to
        Edge edge1 = new Edge(toNode, weight);
        edge1.next = fromNode.edgeList;
        fromNode.edgeList = edge1;

        // یال از to به from (چون گراف بدون جهت است)
        Edge edge2 = new Edge(fromNode, weight);
        edge2.next = toNode.edgeList;
        toNode.edgeList = edge2;
    }

    /* الگوریتم دایکسترا */
    public void dijkstra(int sourceId) {

        GraphNode source = getNode(sourceId);
        if (source == null)
            return;

        // مقداردهی اولیه
        for (int i = 0; i < size; i++) {
            nodes[i].distance = Integer.MAX_VALUE;
            nodes[i].visited = false;
            nodes[i].previous = null;
        }

        source.distance = 0;

        // اجرای الگوریتم
        for (int i = 0; i < size; i++) {

            GraphNode current = getMinDistanceNode();
            if (current == null)
                break;

            current.visited = true;

            Edge edge = current.edgeList;
            while (edge != null) {
                GraphNode neighbor = edge.to;

                if (!neighbor.visited) {
                    int newDist = current.distance + edge.weight;
                    if (newDist < neighbor.distance) {
                        neighbor.distance = newDist;
                        neighbor.previous = current;
                    }
                }
                edge = edge.next;
            }
        }
    }

    /* پیدا کردن نودی با کمترین distance */
    private GraphNode getMinDistanceNode() {
        GraphNode minNode = null;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < size; i++) {
            if (!nodes[i].visited && nodes[i].distance < min) {
                min = nodes[i].distance;
                minNode = nodes[i];
            }
        }
        return minNode;
    }

    /* چاپ مسیر از مبدأ تا مقصد */
    public void printPath(int destId) {
        GraphNode dest = getNode(destId);
        if (dest == null || dest.distance == Integer.MAX_VALUE) {
            System.out.println("No path exists");
            return;
        }

        printPathRecursive(dest);
        System.out.println("\nCost: " + dest.distance);
    }

    private void printPathRecursive(GraphNode node) {
    if (node == null)
        return;

    if (node.previous != null) {
        printPathRecursive(node.previous);
        System.out.print(" -> ");
    }

    System.out.print((char) ('A' + node.id));
    }   
}
