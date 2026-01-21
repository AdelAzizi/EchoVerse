package phase3_team;

public class TeamTree {

    private TeamMember root;

    public TeamTree() {
        root = null;
    }

    /* اضافه کردن عضو */
    public void insert(String parentName, String childName) {

        // اگر فقط یک اسم آمده باشد (ریشه)
        if (childName == null) {
            if (root == null) {
                root = new TeamMember(parentName);
            }
            return;
        }

        TeamMember parent = searchDFSNode(root, parentName);

        if (parent == null) {
            System.out.println("Parent " + parentName + " does not exist");
            return;
        }

        TeamMember child = new TeamMember(childName);

        if (parent.firstChild == null) {
            parent.firstChild = child;
        } else {
            TeamMember current = parent.firstChild;
            while (current.nextSibling != null) {
                current = current.nextSibling;
            }
            current.nextSibling = child;
        }
    }

    /* نمایش درخت */
    public void display() {
        displayNode(root);
    }

    private void displayNode(TeamMember node) {
        if (node == null)
            return;

        System.out.print(node.name + ":");

        TeamMember child = node.firstChild;
        boolean first = true;

        while (child != null) {
            if (first) {
                System.out.print(" ");
                first = false;
            } else {
                System.out.print(",");
            }
            System.out.print(child.name);
            child = child.nextSibling;
        }

        System.out.println();

        child = node.firstChild;
        while (child != null) {
            displayNode(child);
            child = child.nextSibling;
        }
    }

    /* جستجو DFS */
    public void searchDFS(String name) {
        String path = searchDFSPath(root, name, "");
        if (path == null)
            System.out.println("Node " + name + " NOT found");
        else
            System.out.println("Path (DFS): " + path);
    }

    private String searchDFSPath(TeamMember node, String target, String path) {
        if (node == null)
            return null;

        String currentPath = path.isEmpty() ? node.name : path + " -> " + node.name;

        if (node.name.equals(target))
            return currentPath;

        TeamMember child = node.firstChild;
        while (child != null) {
            String result = searchDFSPath(child, target, currentPath);
            if (result != null)
                return result;
            child = child.nextSibling;
        }

        return null;
    }

    /* جستجو BFS (پیاده‌سازی سطحی ساده) */
    public void searchBFS(String name) {
        String path = searchBFSPath(name);
        if (path == null)
            System.out.println("Node " + name + " NOT found");
        else
            System.out.println("Path (BFS): " + path);
    }

    private String searchBFSPath(String target) {
        if (root == null)
            return null;

        return bfsLevel(root, target);
    }

    private String bfsLevel(TeamMember node, String target) {
        if (node == null)
            return null;

        if (node.name.equals(target))
            return node.name;

        TeamMember child = node.firstChild;
        while (child != null) {
            if (child.name.equals(target))
                return node.name + " -> " + child.name;
            child = child.nextSibling;
        }

        child = node.firstChild;
        while (child != null) {
            String result = bfsLevel(child, target);
            if (result != null)
                return node.name + " -> " + result;
            child = child.nextSibling;
        }

        return null;
    }

    /* حذف نود و زیر درخت */
    public void delete(String name) {
        if (root == null)
            return;

        if (root.name.equals(name)) {
            root = null;
            System.out.println("Node " + name + " and its subtree deleted");
            return;
        }

        deleteRecursive(root, name);
    }

    private void deleteRecursive(TeamMember parent, String target) {

        TeamMember prev = null;
        TeamMember current = parent.firstChild;

        while (current != null) {
            if (current.name.equals(target)) {
                if (prev == null)
                    parent.firstChild = current.nextSibling;
                else
                    prev.nextSibling = current.nextSibling;

                System.out.println("Node " + target + " and its subtree deleted");
                return;
            }

            deleteRecursive(current, target);
            prev = current;
            current = current.nextSibling;
        }
    }

    /* جستجوی ساده برای insert */
    private TeamMember searchDFSNode(TeamMember node, String target) {
        if (node == null)
            return null;

        if (node.name.equals(target))
            return node;

        TeamMember child = node.firstChild;
        while (child != null) {
            TeamMember result = searchDFSNode(child, target);
            if (result != null)
                return result;
            child = child.nextSibling;
        }

        return null;
    }
}
