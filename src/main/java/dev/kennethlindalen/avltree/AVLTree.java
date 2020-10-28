package dev.kennethlindalen.avltree;

import static dev.kennethlindalen.avltree.AVLscene.infoTA;
import static dev.kennethlindalen.avltree.AVLscene.log;

public class AVLTree<T extends Comparable<T>> {
    private Node<T> toppRot;

    public Node<T> getToppRot() {
        return toppRot;
    }

    public void setToppRot(Node<T> toppRot) {
        this.toppRot = toppRot;
    }

    //we create the root ot the origin
    public void settInnElement(T element){
        if (sok(element)) {
            log = (String.format("%sNummeret er allerede i treet%n", log));
            infoTA.setText(log);
            infoTA.setScrollTop(Double.MAX_VALUE);
        }
        else {
            toppRot = settInnElement(element, toppRot);
        }
    }

    //search if the element is in the tree
    public boolean sok(T element) {

        Node<T> temp = toppRot; // Start from the root

        while (temp != null) {
            if (element.compareTo(temp.getElement()) < 0)
                temp = temp.getLeft();
            else if (element.compareTo(temp.getElement()) > 0)
                temp = temp.getRight();
            else // element matches temp element
                return true; // Element is found

        }
        return false;
    }

    //insert a new element to the tree
    private Node<T> settInnElement(T element, Node<T> toppRot) {
        if (toppRot == null)
            toppRot = new Node<T>(element);
        else {
            if (element.compareTo(toppRot.getElement()) > 0) {
                log = (String.format("%sSatt inn ny node på høyre side av %s%n", log,toppRot.getElement()));
                infoTA.setText(log);
                infoTA.setScrollTop(Double.MAX_VALUE);
                toppRot.setRight(settInnElement(element, toppRot.getRight()));
                //it is checked that it is balanced
                if (hoyde(toppRot.getLeft()) - hoyde(toppRot.getRight()) == -2) {
                    if (element.compareTo(toppRot.getRight().getElement()) > 0) {
                        log = (String.format("%sVenstre rotasjon på: %s%n", log, toppRot.getElement()));
                        infoTA.setText(log);
                        infoTA.setScrollTop(Double.MAX_VALUE);
                        toppRot = roterTilVenstre(toppRot);
                    } else {
                        log = (String.format("%sDobbel venstre rotasjon på: %s%n", log, toppRot.getElement()));
                        infoTA.setText(log);
                        infoTA.setScrollTop(Double.MAX_VALUE);
                        toppRot = dobbelRoterTilVenstre(toppRot);

                    }
                }
            }
            if (element.compareTo(toppRot.getElement()) < 0) {
                log = (String.format("%sSatt inn ny node på venstre side av %s%n", log,toppRot.getElement()));
                infoTA.setText(log);
                infoTA.setScrollTop(Double.MAX_VALUE);
                toppRot.setLeft(settInnElement(element, toppRot.getLeft()));
                //it is checked that it is balanced
                if (hoyde(toppRot.getLeft()) - hoyde(toppRot.getRight()) == 2) {
                    if (element.compareTo(toppRot.getLeft().getElement()) < 0) {
                        log = (String.format("%sHøyre rotasjon på: %s%n", log, toppRot.getElement()));
                        infoTA.setText(log);
                        infoTA.setScrollTop(Double.MAX_VALUE);
                        toppRot = roterTilHoyre(toppRot);
                    } else {
                        log = (String.format("%sHøyre rotasjon på: %s%n", log, toppRot.getElement()));
                        infoTA.setText(log);
                        infoTA.setScrollTop(Double.MAX_VALUE);
                        toppRot = dobbelRoterTilHoyre(toppRot);

                    }
                }
            }
        }

        int height = maksHoydeMellomVenstreOgHoyre(hoyde(toppRot.getLeft()), hoyde(toppRot.getRight()));
        toppRot.setHeight(height + 1);
        return toppRot;
    }

    //simple rotation to the left
    private Node<T> roterTilVenstre(Node<T> origin) {
        Node<T> temp = origin.getRight();
        origin.setRight(temp.getLeft());
        temp.setLeft(origin);
        origin.setHeight(maksHoydeMellomVenstreOgHoyre(hoyde(origin.getLeft()), hoyde(origin.getRight())) + 1);
        temp.setHeight(maksHoydeMellomVenstreOgHoyre(hoyde(temp.getRight()), hoyde(origin)) + 1);
        return temp;
    }

    //simple rotation to the right
    private Node<T> roterTilHoyre(Node<T> origin) {
        Node<T> temp = origin.getLeft();
        origin.setLeft(temp.getRight());
        temp.setRight(origin);
        origin.setHeight(maksHoydeMellomVenstreOgHoyre(hoyde(origin.getLeft()), hoyde(origin.getRight())) + 1);
        temp.setHeight(maksHoydeMellomVenstreOgHoyre(hoyde(temp.getLeft()), hoyde(origin)) + 1);
        return temp;
    }

    //double rotation to the left
    private Node<T> dobbelRoterTilVenstre(Node<T> origin) {
        origin.setRight(roterTilHoyre(origin.getRight()));
        return roterTilVenstre(origin);
    }

    //double rotation to the right
    private Node<T> dobbelRoterTilHoyre(Node<T> origin) {
        origin.setLeft(roterTilVenstre(origin.getLeft()));
        return roterTilHoyre(origin);
    }
    private int maksHoydeMellomVenstreOgHoyre(int a, int b) {
        if (a > b)
            return a;
        else
            return b;
    }

    //get the specific height of a node
    private int hoyde(Node<T> node) {
        if (node == null)
            return -1;
        else
            return node.getHeight();
    }

    private Node<T> finnMinste(Node<T> node) {
        if (node == null)
            return node;

        while (node.getLeft() != null)
            node = node.getLeft();
        return node;
    }

    public void slettNode(T element) {
        slettNode(toppRot, element);
    }

    private Node<T> slettNode(Node<T> node, T element) {
        if (node == null) {
            return node;
        }
        if (element.compareTo(node.getElement()) < 0) {
            node.setLeft(slettNode(node.getLeft(), element));
        } else if (element.compareTo(node.getElement()) > 0) {
            node.setRight(slettNode(node.getRight(), element));
        } else if (node.getLeft() != null && node.getRight() != null) {
            node.setElement(finnMinste(node.getRight()).getElement());
            node.setRight(slettNode(node.getRight(), node.getElement()));
        } else if (node.getLeft() != null || node.getRight() != null) {
            if (node.getRight() == null) {
                node.setElement(finnMinste(node.getLeft()).getElement());
                node.setRight(slettNode(node.getLeft(), node.getElement()));
                node.setLeft(null);
            } else {
                node.setElement(finnMinste(node.getRight()).getElement());
                node.setRight(slettNode(node.getRight(), node.getElement()));
                node.setRight(null);
            }
        } else {
            node = (node.getLeft() != null) ? node.getLeft() : node.getRight();
        }
        return node;
    }

    public void slettNode2(T x) {
        slettNode2(toppRot, x);
    }

    private Node<T> slettNode2(Node<T> a, T x) {
        if (a == null) {
            return a;
        }
        if (a.getElement().compareTo(x) == 0) {
            return slettHovedRot(a);
        }
        if (a.getElement().compareTo(x) > 0) {
            a.setLeft(slettNode2(a.getLeft(), x));
        } else {
            a.setRight(slettNode2(a.getRight(), x));
        }
        return balanser(a);
    }

    private Node<T> slettHovedRot(Node<T> currentRoot) {
        if (currentRoot.getLeft() == null)
            return currentRoot.getRight();
        if (currentRoot.getRight() == null)
            return currentRoot.getLeft();

        Node<T> r1 = currentRoot.getLeft();
        Node<T> father = currentRoot;

        while (r1.getRight() != null) {
            father = r1;
            r1 = r1.getRight();
        }
        currentRoot.setElement(r1.getElement());
        if (father == currentRoot)
            father.setLeft(r1.getLeft());
        else
            father.setRight(r1.getLeft());
        return currentRoot;
    }

    private Node<T> balanser(Node<T> a) {
        kalkulerHoyde(a);
        if (hoydeAvTreet(a.getLeft()) - hoydeAvTreet(a.getRight()) == 2) {
            if (hoydeAvTreet(a.getLeft().getLeft()) < hoydeAvTreet(a.getLeft().getRight()))
                a.setLeft(rotasjon1(a.getLeft()));
            return rotasjon2(a);
        } // else
        if (hoydeAvTreet(a.getLeft()) - hoydeAvTreet(a.getRight()) == -2) {
            if (hoydeAvTreet(a.getRight().getRight()) < hoydeAvTreet(a.getRight().getLeft()))
                a.setRight(rotasjon2(a.getRight()));
            return rotasjon1(a);
        }
        return a;
    }

    private Node<T> rotasjon1(Node<T> current) {
        Node<T> b = current.getRight();
        Node<T> c = new Node<>(current.getElement(), current.getLeft(), b.getLeft());
        Node<T> r = new Node<>(b.getElement(), c, b.getRight());
        return r;
    }

    private Node<T> rotasjon2(Node<T> current) {
        Node<T> c = current.getLeft();
        Node<T> b = new Node<>(current.getElement(), c.getRight(), current.getRight());
        Node<T> r = new Node<>(c.getElement(), c.getLeft(), b);
        return r;
    }

    private void kalkulerHoyde(Node<T> nodeC) {
        if (nodeC != null) {
            kalkulerHoyde(nodeC.getLeft());
            kalkulerHoyde(nodeC.getRight());
            nodeC.setHeight(1 + Math.max(hoydeAvTreet(nodeC.getLeft()), hoydeAvTreet(nodeC.getRight())));
        }
    }

    public int hoydeAvTreet(Node<T> a) {
        if (a == null) {
            return -1;
        }
        return 1 + Math.max(hoydeAvTreet(a.getLeft()), hoydeAvTreet(a.getRight()));
    }
}
