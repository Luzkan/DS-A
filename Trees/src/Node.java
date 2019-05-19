public class Node<KeyType extends Comparable<KeyType>> implements Comparable<Node<KeyType>>{

    //                             [parent]
    //                                |
    // A node consist of a          [key]
    //                              /   \
    //                    [left child] [right child]

    private Color color;
    enum Color {
        RED,
        BLACK
    }

    private KeyType key;
    private Node<KeyType> left = null;
    private Node<KeyType> right = null ;
    private Node<KeyType> parent = null;

    Node(KeyType key) {
        this.key = key;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }


    public Node<KeyType> getLeft() {
        return left;
    }
    public void setLeft(Node<KeyType> left) {
        this.left = left;
    }


    public Node<KeyType> getRight() {
        return right;
    }
    public void setRight(Node<KeyType> right) {
        this.right = right;
    }


    public Node<KeyType> getParent() {
        return parent;
    }
    public void setParent(Node<KeyType> parent) {
        this.parent = parent;
    }


    public KeyType getKey() {
        return this.key;
    }
    public void setKey(KeyType key) {
        this.key = key;
    }

    // Returns -1, 0, or 1 according to whether the value of expression is negative, zero or positive
    @Override
    public int compareTo(Node<KeyType> node) {
        return this.key.compareTo(node.getKey());
    }

    @Override
    public String toString() {
        String result = "Node [";
        result += this.key == null ? "null]\t" : this.key.toString() + "]\t(Color: ";
        result += this.color == null ? "null)\t\t" : this.color.toString() + ")\t\t";
        if (this.parent != null && this.parent.key != null) {
            result += "has parent: [" + this.parent.key + "]\t(Color: " + this.parent.color + ")";
        } else {
            result += "is the root of the tree.";
        }
        return result;
    }



}
