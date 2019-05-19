
/* https://www.geeksforgeeks.org/red-black-tree-set-1-introduction-2/

   Self balancing BST: Red-Black Tree

   Rules:
    1. Every node is either black or red
    2. Root is always black
    3. Red node cannot have red child/parent
    4. Any path from a node to any of its descendant has same number of black nodes

                              (7,B)
                          /            \
                 (3,B)                     (18,R)
                /     \                /            \
               NIL    NIL         (10,B)              (22,B)
                                /        \            /    \
                            (8,R)        (11,R)     NIL   (26,R)
                            /   \        /   \            /     \
                          NIL  NIL      NIL   NIL        NIL    NIL

   Operations are just as good as in BST. (search, max, min, insert, delete = O(h)
   But it's better because we keep height to [logn] instead of [n]          */

public class RBT<KeyType extends Comparable<KeyType>> extends BST<KeyType> {
    private Node<KeyType> nullGuard = new Node<>(null);
    private Node<KeyType> root = nullGuard;
    private Boolean test = true;

    @Override
    public void insert(KeyType value) {
        // Same as in BST
        Node<KeyType> z = new Node<>(value);
        Node<KeyType> y = nullGuard;
        Node<KeyType> x = this.root;

        addCompModf(1, 0);
        while (x != nullGuard) {
            y = x;
            addCompModf(1, 0);
            if (z.compareTo(x) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        z.setParent(y);
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

        // Add NIL to left and right of the new Node
        addCompModf(0, 3);
        z.setLeft(nullGuard);
        z.setRight(nullGuard);

        // Set it up as red and proceed to fix the tree
        z.setColor(Node.Color.RED);
        insertFixUp(z);
    }

    @Override
    public void delete(KeyType z) {
        Node<KeyType> del = searchNode(root, z);
        if (del != nullGuard) deleteNodeRBT(del);
    }

    @Override
    public void inOrder() {
        System.out.println();
        inorderTraverse(this.root);
        System.out.println();
    }

    @Override
    public void search(KeyType value) {
        if (searchNode(this.root, value) != nullGuard) {
            if(!test) System.out.println("1");
        } else {
            if(!test) System.out.println("0");
        }
    }

    // https://www.geeksforgeeks.org/red-black-tree-set-2-insert/

    private void insertFixUp(Node<KeyType> z) {
        addCompModf(1, 0);

        // If [new] node parent is NOT black keep looping
        while (z.getParent().getColor() == Node.Color.RED) {
            addCompModf(1, 0);
            // Parent is a left child
            if (z.getParent() == z.getParent().getParent().getLeft()) {
                Node<KeyType> y = z.getParent().getParent().getRight();

                // If uncle is RED
                // <grand parent must have been black from property (4)>
                addCompModf(1, 0);
                if (y.getColor() == Node.Color.RED) {

                    // Change colors of parent and uncle to BLACK
                    addCompModf(0, 3);
                    z.getParent().setColor(Node.Color.BLACK);
                    y.setColor(Node.Color.BLACK);

                    // Repeat in while loop with GRANDPARENT.
                    z.getParent().getParent().setColor(Node.Color.RED);
                    z = z.getParent().getParent();

                // If uncle is BLACK
                // <grand parent must have been black from property (4)>
                // There can be four cases which we need to solve
                } else {
                    addCompModf(1, 0);
                    // Left Right Case if Z is Right Child
                    if (z == z.getParent().getRight()) {
                        z = z.getParent();
                        leftRotate(z);
                    }
                    addCompModf(0, 2);

                    // Left Left Case & Color Swap
                    z.getParent().setColor(Node.Color.BLACK);
                    z.getParent().getParent().setColor(Node.Color.RED);
                    rightRotate(z.getParent().getParent());
                }

            // Parent is a right child
            } else {
                Node<KeyType> y = z.getParent().getParent().getLeft();

                // If uncle is RED
                // <grand parent must have been black from property (4)>
                addCompModf(1, 0);
                if (y.getColor() == Node.Color.RED) {

                    // Change colors of parent and uncle to BLACK
                    addCompModf(0, 3);
                    z.getParent().setColor(Node.Color.BLACK);
                    y.setColor(Node.Color.BLACK);

                    // Repeat in while loop with GRANDPARENT.
                    z.getParent().getParent().setColor(Node.Color.RED);
                    z = z.getParent().getParent();

                // If uncle is BLACK
                // <grand parent must have been black from property (4)>
                // There can be four cases which we need to solve
                } else {
                    // Right Left Case
                    addCompModf(1, 0);
                    if (z == z.getParent().getLeft()) {
                        z = z.getParent();
                        rightRotate(z);
                    }

                    // Right Right Case & Color Swap
                    addCompModf(0, 2);
                    z.getParent().setColor(Node.Color.BLACK);
                    z.getParent().getParent().setColor(Node.Color.RED);
                    leftRotate(z.getParent().getParent());
                }
            }
        }
        addCompModf(0, 1);
        this.root.setColor(Node.Color.BLACK);
    }

    //          Right Rotation   (left left case)
    //
    //           G                              P
    //        P     U                        X     G
    //     X   3   4 5         ->           1 2   3  U
    //    1 2                                       4 5
    //
    private void rightRotate(Node<KeyType> g) {
        // Names relative to X: (P)arent, (G)randparent, (U)ncle

        // Left [P]
        Node<KeyType> p = g.getLeft();
        // [G] gets right [P] child (3)
        addCompModf(0, 1);
        g.setLeft(p.getRight());

        // If that child even existed, update it
        addCompModf(1, 0);
        if (p.getRight() != nullGuard) {
            // Then [P]'s childs have new parent (g)
            addCompModf(0, 1);
            p.getRight().setParent(g);
        }

        // [P] new parent is [G] parent
        addCompModf(0, 1);
        p.setParent(g.getParent());

        // If [G] was Root
        addCompModf(1, 0);
        if (g.getParent() == nullGuard) {
            // It means [P] is new root
            addCompModf(0, 1);
            this.root = p;

        // If [G] was right child
        } else if (g == g.getParent().getRight()) {
            addCompModf(1, 1);
            g.getParent().setRight(p);

        // If [G] was left child
        } else {
            addCompModf(1, 1);
            g.getParent().setLeft(p);
        }

        // [P] is now parent of [G]
        addCompModf(0, 2);
        p.setRight(g);
        g.setParent(p);
    }

    //          Left Rotation   (right right case)
    //
    //           G                            P
    //        U     P                      G     X
    //       1 2   3  X         ->       U  3   4 5
    //               4 5                1 2
    //
    private void leftRotate(Node<KeyType> g) {
        // Names relative to X: (P)arent, (G)randparent, (U)ncle

        // Right [P]
        Node<KeyType> p = g.getRight();
        // [G] gets right [P] child (3)
        addCompModf(0, 1);
        g.setRight(p.getLeft());

        // If that child even existed, update it
        addCompModf(1, 0);
        if (p.getLeft() != nullGuard) {
            // Then [P]'s childs have new parent (g)
            addCompModf(0, 1);
            p.getLeft().setParent(g);
        }

        // [P] new parent is [G] parent
        addCompModf(0, 1);
        p.setParent(g.getParent());

        // If [G] was Root
        addCompModf(1, 0);
        if (g.getParent() == nullGuard) {
            // It means [P] is new root
            addCompModf(0, 1);
            this.root = p;

        // If [G] was right child
        } else if (g == g.getParent().getLeft()) {
            addCompModf(1, 1);
            g.getParent().setLeft(p);

        // If [G] was left child
        } else {
            addCompModf(1, 1);
            g.getParent().setRight(p);
        }

        // [P] is now parent of [G]
        p.setLeft(g);
        g.setParent(p);
    }

    // https://www.geeksforgeeks.org/red-black-tree-set-3-delete-2/
    private void deleteNodeRBT(Node<KeyType> del) {
        // Store node to fix as [x]
        Node<KeyType> x;
        Node.Color initColor = del.getColor();

        // Check for childs if they exist
        addCompModf(1, 0);
        if (del.getLeft() == nullGuard) {
            x = del.getRight();
            transplant(del, del.getRight());

        } else if (del.getRight() == nullGuard) {
            addCompModf(1, 0);
            x = del.getLeft();
            transplant(del, del.getLeft());

        // As it turns out both exist (3)
        } else {
            
            // Get far left of the right child and it's color - he will be the successor to the node
            addCompModf(1, 0);
            Node<KeyType> y = minimum(del.getRight());
            initColor = y.getColor();


            // If the successor isn't a child of the key we want to deleteNodeRBT
            // Swap place with it's right child before the big swap
            x = y.getRight();
            addCompModf(1, 0);
            if (y.getParent() != del) {
                transplant(y, y.getRight());
                addCompModf(0, 2);
                y.setRight(del.getRight());
                y.getRight().setParent(y);
            } else {
                addCompModf(0, 1);
                x.setParent(y);
            }

            transplant(del, y);
            addCompModf(0, 3);
            y.setLeft(del.getLeft());
            y.getLeft().setParent(y);
            y.setColor(del.getColor());
        }

        addCompModf(1, 0);
        if (initColor == Node.Color.BLACK) {
            deleteFixUp(x);
        }
    }

    private void deleteFixUp(Node<KeyType> x) {
        addCompModf(2, 0);
        while (x != this.root && x.getColor() == Node.Color.BLACK) {

            // [X] is left Child
            addCompModf(3, 0);
            if (x == x.getParent().getLeft()) {
                // Get [S]ibling of [X]
                Node<KeyType> s = x.getParent().getRight();

                // If x's sibling is red -> perform rotation (c)
                addCompModf(1, 0);
                if (s.getColor() == Node.Color.RED) {
                    addCompModf(0, 2);
                    s.setColor(Node.Color.BLACK);
                    x.getParent().setColor(Node.Color.RED);
                    leftRotate(x.getParent());
                    s = x.getParent().getRight();
                }

                // If siblings children are blacks (or nulls, it's the same) -> perform recoloring (b)
                addCompModf(2, 0);
                if (s.getLeft().getColor() == Node.Color.BLACK && s.getRight().getColor() == Node.Color.BLACK) {
                    addCompModf(0, 1);
                    s.setColor(Node.Color.RED);
                    x = x.getParent();

                // One of the children was red (a) -> perform
                } else {

                    // Keep in mind we rotate on [S] which was Right Child
                    // Right Left case
                    addCompModf(1, 0);
                    if (s.getRight().getColor() == Node.Color.BLACK) {
                        addCompModf(0, 2);
                        s.getLeft().setColor(Node.Color.BLACK);
                        s.setColor(Node.Color.RED);
                        rightRotate(s);
                        s = x.getParent().getRight();
                    }

                    // Right Right & Recolor
                    addCompModf(0, 3);
                    s.setColor(x.getParent().getColor());
                    x.getParent().setColor(Node.Color.BLACK);
                    s.getRight().setColor(Node.Color.BLACK);
                    leftRotate(x.getParent());
                    x = this.root;
                }
            // X is right Child
            } else {
                // Get [S]ibling of [X]
                Node<KeyType> s = x.getParent().getLeft();

                // If x's sibling is red -> perform rotation
                addCompModf(1, 0);
                if (s.getColor() == Node.Color.RED) {
                    addCompModf(0, 2);
                    s.setColor(Node.Color.BLACK);
                    x.getParent().setColor(Node.Color.RED);
                    rightRotate(x.getParent());
                    s = x.getParent().getLeft();
                }

                // If siblings children are blacks (or nulls, it's the same) -> perform recoloring (b)
                addCompModf(1, 0);
                if (s.getRight().getColor() == Node.Color.BLACK && s.getLeft().getColor() == Node.Color.BLACK) {
                    addCompModf(0, 1);
                    s.setColor(Node.Color.RED);
                    x = x.getParent();

                // One of the children was red (a) -> perform
                } else {

                    // Keep in mind we rotate on [S] which was Left Child
                    // Right Left case
                    addCompModf(1, 0);
                    if (s.getLeft().getColor() == Node.Color.BLACK) {
                        addCompModf(0, 2);
                        s.getRight().setColor(Node.Color.BLACK);
                        s.setColor(Node.Color.RED);
                        leftRotate(s);
                        s = x.getParent().getLeft();
                    }

                    // Left Left Case & Recolor
                    addCompModf(0, 3);
                    s.setColor(x.getParent().getColor());
                    x.getParent().setColor(Node.Color.BLACK);
                    s.getLeft().setColor(Node.Color.BLACK);
                    rightRotate(x.getParent());
                    x = this.root;
                }
            }
        }
        // Finally set the color to black
        addCompModf(0, 1);
        x.setColor(Node.Color.BLACK);
    }

    // RBT have it's own recursive function starting from root to avoid null printing
    private void inorderTraverse(Node<KeyType> x) {
        if (x != nullGuard) {
            inorderTraverse(x.getLeft());
            System.out.println(x);
            inorderTraverse(x.getRight());
        }
    }

    public Node<KeyType> minimum(Node<KeyType> x) {
        addCompModf(1, 0);
        while (x.getLeft() != nullGuard) {
            addCompModf(1, 0);
            x = x.getLeft();
        }
        return x;
    }

    private void transplant(Node<KeyType> u, Node<KeyType> v) {
        addCompModf(1, 0);
        if (u.getParent() == nullGuard) {
            addCompModf(0, 1);
            this.root = v;
        } else if (u == u.getParent().getLeft()) {
            addCompModf(1, 1);
            u.getParent().setLeft(v);
        } else {
            addCompModf(1, 1);
            u.getParent().setRight(v);
        }
        addCompModf(0, 1);
        v.setParent(u.getParent());
    }

}
