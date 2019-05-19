import java.util.Stack;

/* https://www.geeksforgeeks.org/binary-search-tree-data-structure/

   Left subtree contains only nodes with keys smaller than the node's key
   Right subtree of course has greater keys
   And this condition goes trough recursively trough the entire BST Tree

                                8
                              /   \
                             3     10
                            / \      \
                           1   6      14
                              / \     /
                             4   7   13

   Operations like minimum / maximum can be done very fast
   (goes always left/right) and search is fast too                          */

public class BST<KeyType extends Comparable<KeyType>> extends Tree<KeyType> {
    protected Node<KeyType> root;
    private Node<KeyType> nullGuard;
    private Boolean test = false;

    BST() {
        root = null;
        nullGuard = null;
    }

    // Override Methods that have a non-node'ish input yet in which we will convert it to proper form
    @Override
    public void insert(KeyType z) {
        insert(new Node<>(z));
    }

    @Override
    public void delete(KeyType z) {
        Node<KeyType> toDelete = searchNode(root, z);
        if (toDelete != nullGuard) deleteNode(toDelete);
    }

    @Override
    public void search(KeyType key) {
        // nullGuard if we reached a leaf without finding the desired key
        if (searchNode(this.root, key) != nullGuard) {
            if(!test) System.out.println("Key: [" + key + "] exists in the tree.");
        } else {
            if(!test) System.out.println("Key: [" + key + "] not found.");
        }
    }

    @Override
    public void inOrder() {
        System.out.println();
        inorderTraverse(this.root);
        System.out.println();
    }

    //   Insertion:
    //    - Our insertion is a leaf [z]
    //    - Search for our inserted key until we hit a leaf [x]
    //    - Insert there our key as a child
    //  The worst case time complexity of search and insert operations is O(h) where h is height of Binary Search Tree
    //  and the worst case for height of tree could be all elements so O(n). Ex: insert 100..1 in that order.
    protected Node<KeyType> insert(Node<KeyType> z) {
        Node<KeyType> y = nullGuard;
        Node<KeyType> x = this.root;

        // Until we reach leaf
        addCompModf(1, 0);
        while (x != nullGuard) {

            // Remember previous [x] as [y]
            y = x;
            addCompModf(2, 0);

            // If the object (node) z is lesser than X then
            if (z.compareTo(x) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        // We found the leaf this will be the parent of our inserted key
        z.setParent(y);

        // Decide whether our key will be left or right child (or root if root was nullGuard)
        addCompModf(1, 1);
        if (y == nullGuard) {
            addCompModf(0, 1);
            this.root = z;
        } else if (z.compareTo(y) < 0) {
            addCompModf(1, 1);
            y.setLeft(z);
        } else {
            addCompModf(1, 1);
            y.setRight(z);
        }
        return z;
    }

    //   Deletion cases:
    //    (1) Node is a leaf:
    //          - Just Delete
    //    (2) Node has only one child:
    //          - Copy child to the node and deleteNode the child
    //    (3) Node has two children:
    //          - Find inorder successor of the node.
    //          - Copy contents of the inorder successor to the node
    //          - Delete the inorder successor.
    protected Node<KeyType> deleteNode(Node<KeyType> del) {

        // Check for childs if they exist
        addCompModf(1, 0);
        if (del.getLeft() == nullGuard) {
            return transplant(del, del.getRight());

        } else if (del.getRight() == nullGuard) {
            addCompModf(1, 0);
            return transplant(del, del.getLeft());

        // As it turns out both exist (3)
        } else {

            // Get far left of the right child - he will be the successor to the node
            addCompModf(1, 0);
            Node<KeyType> y = minimum(del.getRight());

            // If the successor isn't a child of the key we want to deleteNode
            // Swap place with it's right child before the big swap
            addCompModf(1, 0);
            if (y.getParent() != del) {
                transplant(y, y.getRight());
                addCompModf(0, 2);
                y.setRight(del.getRight());
                y.getRight().setParent(y);
            }

            transplant(del, y);
            addCompModf(0, 2);
            y.setLeft(del.getLeft());
            y.getLeft().setParent(y);
            return y;
        }
    }


    // Switching/swaping method to support deleteNode method
    private Node<KeyType> transplant(Node<KeyType> u, Node<KeyType> v) {

        // If was root
        addCompModf(1, 0);
        if (u.getParent() == nullGuard) {
            addCompModf(0, 1);
            this.root = v;

        // If the Deleted Key was left child then set Child to be the new Child of Key's Parent
        } else if (u == u.getParent().getLeft()) {
            addCompModf(1, 1);
            u.getParent().setLeft(v);

        // Do the same if it was right child instead
        } else {
            addCompModf(1, 1);
            u.getParent().setRight(v);
        }

        // And inform the child about it's new parent
        addCompModf(1, 0);
        if (v != nullGuard) {
            addCompModf(0, 1);
            v.setParent(u.getParent());
        }
        return v;
    }

    // Traverse to the far left of the BST to get minimum
    protected Node<KeyType> minimum(Node<KeyType> x) {

        addCompModf(1, 0);
        while (x.getLeft() != nullGuard) {
            addCompModf(1, 0);
            x = x.getLeft();
        }
        return x;
    }

    // Start from far left (== min. key)
    private void inorderTraverse(Node<KeyType> x) {
        // Check if the tree even exist
        if (x == nullGuard) return;

        Stack<Node<KeyType>> s = new Stack<>();
        Node<KeyType> keyTrav = x;

        // Go through the entire tree heading maximally to the left everytime...
        while (keyTrav != nullGuard || s.size() > 0) {
            while (keyTrav != nullGuard) {
                s.push(keyTrav);
                keyTrav = keyTrav.getLeft();
            }
            keyTrav = s.pop();
            System.out.println(keyTrav);

            // ... and if everything from left was taken then move right
            keyTrav = keyTrav.getRight();
        }
    }

    // Traverse from root [node] to the [key]
    protected Node<KeyType> searchNode(Node<KeyType> node, KeyType key) {

        // While we didn't reach a leaf or the key that we search for
        addCompModf(2, 0);
        while (node != nullGuard && key.compareTo(node.getKey()) != 0) {
            addCompModf(3, 0);
            if (key.compareTo(node.getKey()) < 0) {
                node = node.getLeft();
            } else if (key.compareTo(node.getKey()) > 0) {
                node = node.getRight();
            } else {
                return node;
            }
        }
        return node;
    }
}
