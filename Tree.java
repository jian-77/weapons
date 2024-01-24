public class Tree {
    private static class Node {
        Node left;
        Node right;
        Node parent;
        int value;
        int level;
        int num;

        public Node(int value) {
            this.value = value;
            this.level=1;
            this.num=1;
        }
    }

    public  static int rootLevel(Node node) {
        if (node == null) return 0;
        else return node.level;
    }
    public  static int size(Node node) {
        if (node == null) return 0;
        else return node.num;
    }

    public static Node insert(Node root, int value, Node newNode) {//insert 方法应该是正确的
        if (root == null) return newNode;
        else {
            if (root.value > value) {
                Node node=insert(root.left, value,newNode);
                root.left = node;
                node.parent=root;
            } else {
                Node node=insert(root.right, value,newNode);
                root.right =node;
                node.parent=root;
            }
            renewSizeAndLevel(root);
        }
        //每次迭代判断是否平衡
        if (Math.abs(rootLevel(root.left)-rootLevel(root.right))>=2){
            return re_balance(root);
        }
        else {
            return root;
        }
    }
    public static Node search(Node root, int value){
        if (root.value==value)return root;
        else if(root.value<value){
            return search(root.right,value);
        }
        else{
            return search(root.left,value);
        }
    }

    public static Node delete(Node root, int value) {
        if (root.value > value) {
            Node tmp=delete(root.left, value);
            root.left =tmp;
            if (tmp!=null) {
                tmp.parent = root;
            }
        }
        else if (root.value < value) {
            Node tmp = delete(root.right, value);
            root.right = tmp;
            if (tmp!=null) {
                tmp.parent = root;
            }
        }
        //最后只剩等于的情况，没有null，因为肯定找得到
        else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                Node successor = successor(root.right);
                swapValue(root, successor);
                root.right=delete_min(root.right);/////////////
                renewSizeAndLevel(root);
                if (Math.abs(rootLevel(root.left)-rootLevel(root.right))>=2){
                    return re_balance(root);
                }
                else {
                    return root;
                }
            }
        }
        renewSizeAndLevel(root);
        if (Math.abs(rootLevel(root.left)-rootLevel(root.right))>=2){
            return re_balance(root);
        }
        else {
            return root;
        }
    }

    private static Node successor(Node x){
        if (x.left!=null){
            return successor(x.left);
        }
        else{
            return x;
        }
    }

    private static void swapValue(Node node1, Node node2) {
        int value = node1.value;
        node1.value = node2.value;
        node2.value = value;
    }

    private static Node delete_min(Node root) {
        if (root.left != null) {
            Node node=delete_min(root.left);
            root.left =node;
            if (node!=null) {
                node.parent = root;
            }
        }
        else return root.right;
        root.level= Math.max(rootLevel(root.left),rootLevel(root.right))+1;
        root.num = size(root.left) + size(root.right)+1;
        return root;
    }
    private static void traverse(Node root){
        if (root==null) return;
        traverse(root.left);
        System.out.print(root.value+" ");
        traverse(root.right);
    }
    private static void traverse_num(Node root){
        if (root==null) return;
        traverse_num(root.left);
        System.out.print(root.level+" ");
        traverse_num(root.right);
    }
    private static Node re_balance(Node root){
        if (rootLevel(root.left)>rootLevel(root.right)){
            if (rootLevel(root.left.left)>=rootLevel(root.left.right)){
                Node newRoot=rightRotate(root);
                renewSizeAndLevel(newRoot.right);
                renewSizeAndLevel(newRoot);
                return newRoot;
            }
            else{
                Node node=leftRotate(root.left);
                root.left=node;
                node.parent=root;
                Node newRoot= rightRotate(root);
                renewSizeAndLevel(newRoot.left);
                renewSizeAndLevel(newRoot.right);
                renewSizeAndLevel(newRoot);
                return newRoot;
            }
        }
        else{
            if (rootLevel(root.right.left)<=rootLevel(root.right.right)){
                Node newRoot=leftRotate(root);
                renewSizeAndLevel(newRoot.left);
                renewSizeAndLevel(newRoot);
                return newRoot;
            }
            else{
                Node node=rightRotate(root.right);
                root.right=node;
                node.parent=root;
                Node newRoot= leftRotate(root);
                renewSizeAndLevel(newRoot.left);
                renewSizeAndLevel(newRoot.right);
                renewSizeAndLevel(newRoot);
                return newRoot;
            }
        }
    }
    private static Node leftRotate(Node root){
        Node tmp=root.right.left;
        root.right.left=root;
        root.parent=root.right;
        root.right=tmp;
        if (tmp!=null){
            tmp.parent=root;
        }
        return root.parent;
    }
    private static Node rightRotate(Node root){
        Node tmp=root.left.right;
        root.left.right=root;
        root.parent=root.left;
        root.left=tmp;
        if (tmp!=null){
            tmp.parent=root;
        }
        return root.parent;
    }
    private static void renewSizeAndLevel(Node root){
        root.level=Math.max(rootLevel(root.left),rootLevel(root.right))+1;
        root.num = size(root.left) + size(root.right)+1;
    }
}
