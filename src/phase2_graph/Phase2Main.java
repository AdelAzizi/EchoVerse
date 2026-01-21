package phase2_graph;

import java.util.Scanner;

public class Phase2Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Graph graph = new Graph(50); // حداکثر 50 رأس
        boolean[] added = new boolean[26]; // برای جلوگیری از اضافه شدن تکراری رأس‌ها

        while (sc.hasNextLine()) {

            String line = sc.nextLine().trim();
            if (line.length() == 0)
                continue;

            String[] parts = line.split(" ");

            if (parts[0].equals("insert")) {
                char from = parts[1].charAt(0);
                char to = parts[2].charAt(0);
                int weight = Integer.parseInt(parts[3]);

                int fromId = from - 'A';
                int toId = to - 'A';

                if (!added[fromId]) {
                    graph.addVertex(fromId);
                    added[fromId] = true;
                }

                if (!added[toId]) {
                    graph.addVertex(toId);
                    added[toId] = true;
                }

                graph.addEdge(fromId, toId, weight);
            }

            else if (parts[0].equals("show")) {
                char sourceChar = parts[1].charAt(0);
                int sourceId = sourceChar - 'A';

                System.out.println("Source Episode: " + sourceChar + "\n");

                graph.dijkstra(sourceId);

                for (int i = 0; i < 26; i++) {
                    if (added[i]) {
                        char destChar = (char) ('A' + i);
                        System.out.println("Episode " + destChar + ":");
                        System.out.print("Path: ");
                        graph.printPath(i);
                        System.out.println();
                    }
                }
            }
        }

        sc.close();
    }
}
