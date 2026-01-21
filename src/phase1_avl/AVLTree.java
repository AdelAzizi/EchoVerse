package phase1_avl;

public class AVLTree {

    private AVLNode root;

    public AVLTree() {
        root = null;
    }

    private int height(AVLNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    private int max(int a, int b) {
        if (a > b)
            return a;
        else
            return b;
    }

    private int getBalance(AVLNode node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    /* چرخش راست */
    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }

    /* چرخش چپ */
    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }

    /* درج */
    private AVLNode insert(AVLNode node, int channelID) {

        if (node == null)
            return new AVLNode(channelID);

        if (channelID < node.channelID)
            node.left = insert(node.left, channelID);
        else if (channelID > node.channelID)
            node.right = insert(node.right, channelID);
        else
            return node;

        node.height = max(height(node.left), height(node.right)) + 1;

        int balance = getBalance(node);

        if (balance > 1 && channelID < node.left.channelID)
            return rotateRight(node);

        if (balance < -1 && channelID > node.right.channelID)
            return rotateLeft(node);

        if (balance > 1 && channelID > node.left.channelID) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && channelID < node.right.channelID) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public void insert(int channelID) {
        root = insert(root, channelID);
    }

    /* پیدا کردن کوچک‌ترین نود */
    private AVLNode getMaxNode(AVLNode node) {
    AVLNode current = node;
    while (current.right != null)
        current = current.right;
    return current;
}


    /* حذف */
    private AVLNode deleteNode(AVLNode node, int key) {

    if (node == null)
        return null;

    // مرحله 1: پیدا کردن نود
    if (key < node.channelID) {
        node.left = deleteNode(node.left, key);
    } 
    else if (key > node.channelID) {
        node.right = deleteNode(node.right, key);
    } 
    else {
        // نود پیدا شد

        // حالت 1: بدون فرزند
        if (node.left == null && node.right == null) {
            return null;
        }

        // حالت 2: فقط یک فرزند
        if (node.left == null)
            return node.right;

        if (node.right == null)
            return node.left;

        // حالت 3: دو فرزند (استفاده از predecessor)
        AVLNode temp = getMaxNode(node.left);
        node.channelID = temp.channelID;
        node.left = deleteNode(node.left, temp.channelID);
    }

    // مرحله 2: بروزرسانی ارتفاع
    node.height = 1 + max(height(node.left), height(node.right));

    // مرحله 3: بالانس کردن
    int balance = getBalance(node);

    // Left Left
    if (balance > 1 && getBalance(node.left) >= 0)
        return rotateRight(node);

    // Left Right
    if (balance > 1 && getBalance(node.left) < 0) {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    // Right Right
    if (balance < -1 && getBalance(node.right) <= 0)
        return rotateLeft(node);

    // Right Left
    if (balance < -1 && getBalance(node.right) > 0) {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    return node;
    }


    public void delete(int channelID) {
        root = deleteNode(root, channelID);
    }

    /* جستجو */
    private boolean search(AVLNode node, int channelID) {
        if (node == null)
            return false;

        if (channelID == node.channelID)
            return true;

        if (channelID < node.channelID)
            return search(node.left, channelID);
        else
            return search(node.right, channelID);
    }

    public boolean search(int channelID) {
        return search(root, channelID);
    }

        /* نمایش درخت به فرم خواسته‌شده پروژه */
    public void displayTree() {
        displayNode(root);
    }

    private void displayNode(AVLNode node) {
    if (node == null)
        return;

    // فقط وقتی نود حداقل یک فرزند دارد، چاپ می‌شود
    if (node.left != null || node.right != null) {
        int leftID = (node.left != null) ? node.left.channelID : -1;
        int rightID = (node.right != null) ? node.right.channelID : -1;
        System.out.println(node.channelID + " " + leftID + "," + rightID);
    }

    // ادامه بازگشت به فرزندان (حتی اگر خودش چاپ نشده باشد)
    if (node.left != null)
        displayNode(node.left);
    if (node.right != null)
        displayNode(node.right);
    }





}
