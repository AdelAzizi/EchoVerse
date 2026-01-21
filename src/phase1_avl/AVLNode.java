package phase1_avl;

public class AVLNode {

    int channelID;      
    int height;         
    AVLNode left;       
    AVLNode right;      

  
    public AVLNode(int channelID) {
        this.channelID = channelID;
        this.height = 1;  
        this.left = null;
        this.right = null;
    }
}
