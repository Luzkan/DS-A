/* https://www.geeksforgeeks.org/splay-tree-set-1-insert/

    SPlay Tree is another upgrade to BST tree.
    The main idea is that this tree makes recently used items quick to revisit.    */
public class SPlay<KeyType extends Comparable<KeyType>> extends BST<KeyType> {

    @Override
    protected Node<KeyType> searchNode(Node<KeyType> x, KeyType k) {
        Node<KeyType> node = super.searchNode(x, k);
        addCompModf(1, 0);
        if (node != null) {
            addCompModf(1, 0);
            while (node.getParent() != null) {
                addCompModf(1, 0);
                this.splay(node);
            }
        }
        return node;
    }

    @Override
    protected Node<KeyType> insert(Node<KeyType> z) {
        Node<KeyType> noteToReturn = super.insert(z);
        addCompModf(1, 0);
        if (noteToReturn != null) {
            addCompModf(1, 0);
            while (noteToReturn.getParent() != null) {
                addCompModf(1, 0);
                this.splay(noteToReturn);
            }
        }
        return noteToReturn;
    }

    @Override
    protected Node<KeyType> deleteNode(Node<KeyType> z) {
        Node<KeyType> removed = super.deleteNode(z);
        addCompModf(1, 0);
        if (removed != null) {
            addCompModf(1, 0);
            if (removed.getParent() != null) {
                Node<KeyType> p = removed.getParent();
                addCompModf(1, 0);
                while (p.getParent() != null) {
                    addCompModf(1, 0);
                    this.splay(p);
                }
            }
        }
        return removed;
    }

    /* It's all about the tree rotation
       - Zig (Right Rotation)
       - Zag (Left Rotation)

       In cases:
        (1) Node is root - nothing happens
        (2) Node is child of the root - Zig
        (3) Node has both parent and grandparent -
            (a)  Node is left child of parent and parent is
                 also left child of grand parent (Zig-Zig)
                   OR
                 node is right child of its parent and parent is
                 also right child of grand parent (Zag-Zag)

            (b) Node is left child of parent and parent is
                right child of grand parent (Zig-Zag)
                   OR
                node is right child of its parent and parent is
                left child of grand parent (Zag-Zig)                */



    private void splay(Node<KeyType> node) {
        // [P] will be the parent of the searched node
        Node<KeyType> p = node.getParent();
        addCompModf(1, 0);
        Node<KeyType> g = (p != null) ? p.getParent() : null;

        // Case (1) - do nothing if node was root
        addCompModf(1, 0);
        if (p != null) {

            // Case (2)
            addCompModf(1, 0);
            if (p == root) {
                root = node;
                node.setParent(null);

                // Right Rotate (Zig)
                addCompModf(1, 1);
                if (node == p.getLeft()) {
                    p.setLeft(node.getRight());
                    addCompModf(1, 1);
                    if (node.getRight() != null) {
                        node.getRight().setParent(p);
                        addCompModf(0, 1);
                    }
                    addCompModf(0, 2);
                    node.setRight(p);
                    p.setParent(node);

                // Left Rotate (Zag)
                } else {
                    p.setRight(node.getLeft());
                    addCompModf(1, 1);
                    if (node.getLeft() != null) {
                        addCompModf(0, 1);
                        node.getLeft().setParent(p);
                    }
                    node.setLeft(p);
                    p.setParent(node);
                }

            // Case (3)
            } else {

                // Get [pg] parent of grandparent
                Node<KeyType> pg = g.getParent();
                addCompModf(2, 0);
                if (pg != null && pg.getLeft() == g) {
                    pg.setLeft(node);
                    node.setParent(pg);
                    addCompModf(0, 2);
                } else if (pg != null && pg.getRight() == g) {
                    addCompModf(2, 2);
                    pg.setRight(node);
                    node.setParent(pg);
                } else {
                    addCompModf(2, 1);
                    root = node;
                    node.setParent(null);
                }

                if ((node == p.getLeft() && p == g.getLeft()) || (node == p.getRight() && p == g.getRight())) {
                    addCompModf(4, 0);

                    // Two Right Rotations (Zig-Zig)
                    addCompModf(1, 0);
                    if (node == p.getLeft()) {
                        Node<KeyType> nr = node.getRight();
                        addCompModf(0, 2);
                        node.setRight(p);
                        p.setParent(node);

                        p.setLeft(nr);
                        addCompModf(1, 1);
                        if (nr != null) {
                            addCompModf(0, 1);
                            nr.setParent(p);
                        }

                        Node<KeyType> pr = p.getRight();
                        addCompModf(0, 2);
                        p.setRight(g);
                        g.setParent(p);

                        g.setLeft(pr);
                        addCompModf(1, 1);
                        if (pr != null)
                            pr.setParent(g);

                    // Two Left Rotations (Zag-Zag)
                    } else {
                        Node<KeyType> nl = node.getLeft();
                        addCompModf(0, 2);
                        node.setLeft(p);
                        p.setParent(node);

                        p.setRight(nl);
                        addCompModf(1, 1);
                        if (nl != null)
                            nl.setParent(p);

                        Node<KeyType> pl = p.getLeft();
                        addCompModf(0, 2);
                        p.setLeft(g);
                        g.setParent(p);

                        g.setRight(pl);
                        addCompModf(1, 1);
                        if (pl != null)
                            pl.setParent(g);
                    }
                    return;
                }

                // (Zig-Zag)
                addCompModf(1, 0);
                if (node == p.getLeft()) {
                    Node<KeyType> nl = node.getRight();
                    Node<KeyType> nr = node.getLeft();

                    addCompModf(0, 2);
                    node.setRight(p);
                    p.setParent(node);

                    addCompModf(0, 2);
                    node.setLeft(g);
                    g.setParent(node);

                    p.setLeft(nl);
                    addCompModf(1, 1);
                    if (nl != null)
                        nl.setParent(p);

                    g.setRight(nr);
                    addCompModf(1, 1);
                    if (nr != null)
                        nr.setParent(g);
                    return;
                }

                // (Zag-Zig)
                if (node == p.getRight()) {
                    Node<KeyType> nl = node.getLeft();
                    Node<KeyType> nr = node.getRight();

                    addCompModf(0, 2);
                    node.setLeft(p);
                    p.setParent(node);

                    addCompModf(0, 2);
                    node.setRight(g);
                    g.setParent(node);

                    p.setRight(nl);
                    addCompModf(1, 1);
                    if (nl != null) {
                        addCompModf(0, 1);
                        nl.setParent(p);
                    }

                    g.setLeft(nr);
                    addCompModf(1, 1);
                    if (nr != null) {
                        addCompModf(0, 1);
                        nr.setParent(g);
                    }
                }
            }
        }
    }
}

// Previous implementation (NullPointerException bug)
/*              // a) Zig Zig
                if(node == p.getLeft() && p == g.getLeft()){
                    rightRotate(g);
                    rightRotate(p);
                    return;
                }

                // a) Zag Zag
                if(node == p.getRight() && p == g.getRight()){
                    leftRotate(g);
                    leftRotate(p);
                    return;
                }

                // b) Zag Zig
                if(node == p.getLeft() && p == g.getRight()){
                    rightRotate(g);
                    leftRotate(p);
                    return;
                }

                // b) Zig Zag
                if(node == p.getRight() && p == g.getLeft()){
                    rightRotate(p);
                    leftRotate(g);
                    return;
                }



        private void rightRotate(Node<KeyType> g) {
        // Names relative to X: (P)arent, (G)randparent, (U)ncle

        // Left [P]
        Node<KeyType> p = g.getLeft();


        // If that child even existed, update it
        addCompModf(1, 0);
        if (p.getRight() != null) {
            // [G] gets right [P] child (3)
            g.setLeft(p.getRight());
            // Then [P]'s childs have new parent (g)
            p.getRight().setParent(g);
        }

        // [P] new parent is [G] parent
        p.setParent(g.getParent());

        // If [G] was Root
        addCompModf(1, 0);
        if (g.getParent() == null) {
            // It means [P] is new root
            this.root = p;

            // If [G] was right child
        } else if (g == g.getParent().getRight()) {
            addCompModf(1, 0);
            g.getParent().setRight(p);

            // If [G] was left child
        } else {
            addCompModf(1, 0);
            g.getParent().setLeft(p);
        }

        // [P] is now parent of [G]
        p.setRight(g);
        g.setParent(p);
    }

    private void leftRotate(Node<KeyType> g) {
        // Names relative to X: (P)arent, (G)randparent, (U)ncle

        // Right [P]
        Node<KeyType> p = g.getRight();

        // If that child even existed, update it
        addCompModf(1, 0);
        if (p.getLeft() != null) {
            // [G] gets right [P] child (3)
            g.setRight(p.getLeft());
            // Then [P]'s childs have new parent (g)
            p.getLeft().setParent(g);
        }

        // [P] new parent is [G] parent
        p.setParent(g.getParent());

        // If [G] was Root
        addCompModf(1, 0);
        if (g.getParent() == null) {
            // It means [P] is new root
            this.root = p;
            // If [G] was right child
        } else if (g == g.getParent().getLeft()) {
            addCompModf(1, 0);
            g.getParent().setLeft(p);

            // If [G] was left child
        } else {
            addCompModf(1, 0);
            g.getParent().setRight(p);
        }

        // [P] is now parent of [G]
        p.setLeft(g);
        g.setParent(p);
    }
 */