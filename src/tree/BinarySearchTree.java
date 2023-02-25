package tree;

public class BinarySearchTree {
    TreeNode root;

    public BinarySearchTree(TreeNode root) {
        this.root = root;
    }

    public BinarySearchTree(int value) {
        this.root = new TreeNode(value);
    }

    public boolean find(int value) {
        TreeNode node = root;

        while (node != null) {
            if (node.value == value) {
                return true;
            } else if (node.value > value) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return false;
    }
}
