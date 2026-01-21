package phase1_avl;

import java.util.Scanner;

public class Phase1Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AVLTree tree = new AVLTree();

        while (sc.hasNext()) {

            String command = sc.next();

            if (command.equals("insert")) {
                int value = sc.nextInt();
                tree.insert(value);
            }

            else if (command.equals("delete")) {
                int value = sc.nextInt();
                tree.delete(value);
            }

            else if (command.equals("search")) {
                int value = sc.nextInt();
                boolean found = tree.search(value);

                if (found)
                    System.out.println("Channel " + value + " is authorized");
                else
                    System.out.println("Channel " + value + " is NOT authorized");

                System.out.println();
            }

            else if (command.equals("display")) {
                tree.displayTree();
                System.out.println();
            }
        }

        sc.close();
    }
}
