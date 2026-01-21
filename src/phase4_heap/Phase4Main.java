package phase4_heap;

import java.util.Scanner;

public class Phase4Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        MinHeap heap = new MinHeap(100);

        while (sc.hasNextLine()) {

            String line = sc.nextLine().trim();
            if (line.isEmpty())
                continue;

            String[] parts = line.split(" ");

            switch (parts[0]) {

                case "insert":
                    String id = parts[1];
                    int priority = Integer.parseInt(parts[2]);
                    heap.insert(id, priority);
                    break;

                case "display":
                    heap.display();
                    break;

                case "extract-min":
                    Episode min = heap.extractMin();
                    if (min != null) {
                        System.out.println("Extracted episode: "
                                + min.id + " with priority " + min.priority);
                    }
                    break;

                case "delete":
                    boolean deleted = heap.delete(parts[1]);
                    if (deleted) {
                        System.out.println("Episode " + parts[1] + " deleted");
                    }
                    break;

                case "heap-sort":
                    Episode[] sorted = heap.heapSort();
                    System.out.println("Sorted episodes by priority:");
                    for (Episode e : sorted) {
                        System.out.print(e.id + "(" + e.priority + ") ");
                    }
                    System.out.println();
                    break;
            }
        }

        sc.close();
    }
}
