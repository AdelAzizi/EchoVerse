package phase3_team;

import java.util.Scanner;

public class Phase3Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        TeamTree tree = new TeamTree();

        while (sc.hasNextLine()) {

            String line = sc.nextLine().trim();
            if (line.length() == 0)
                continue;

            String[] parts = line.split(" ");

            if (parts[0].equals("insert")) {

                if (parts.length == 2) {
                    tree.insert(parts[1], null);
                } 
                else if (parts.length == 3) {
                    tree.insert(parts[1], parts[2]);
                }
            }

            else if (parts[0].equals("display")) {
                tree.display();
            }

            else if (parts[0].equals("search")) {

                if (parts[1].equals("BFS")) {
                    tree.searchBFS(parts[2]);
                } 
                else if (parts[1].equals("DFS")) {
                    tree.searchDFS(parts[2]);
                }
            }

            else if (parts[0].equals("delete")) {
                tree.delete(parts[1]);
            }
        }

        sc.close();
    }
}
