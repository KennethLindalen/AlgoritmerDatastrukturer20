package dev.kennethlindalen.avltree;

import static dev.kennethlindalen.avltree.AVLScene.*;
/**
 * Metoder for å opprette AVLtreet
 *
 * @author Kenneth Lindalen (161940)
 *
 */

public class AVLTre<T extends Comparable<T>> {
    private Node<T> rotNode;

    public Node<T> getRotNode() {
        return rotNode;
    }

    public void setRotNode(Node<T> rotNode) {
        this.rotNode = rotNode;
    }

    //Hvis element ikke blir funnet blir det satt inn
    public void settInnElement(T element) {
        if (sok(element)) {
            tilLogger("Nummeret er allerede i treet");
        } else {
            rotNode = settInnElement(element, rotNode);
        }
    }

    //Søke etter element i treet
    public boolean sok(T element) {

        Node<T> temp = rotNode; // Start

        while (temp != null) {
            if (element.compareTo(temp.getVerdi()) < 0) {
                temp = temp.getVenstre();
            } else if (element.compareTo(temp.getVerdi()) > 0) {
                temp = temp.getHoyre();
            } else {
                return true;
            }

        }
        return false;
    }

    //Sett inn en ny node(tall) i treet
    private Node<T> settInnElement(T element, Node<T> toppRot) {
        if (toppRot == null) {
            toppRot = new Node<T>(element);
        } else {
            if (element.compareTo(toppRot.getVerdi()) > 0) {
                toppRot.setHoyre(settInnElement(element, toppRot.getHoyre()));
                //Sjekker om treet er balansert
                if (hoyde(toppRot.getVenstre()) - hoyde(toppRot.getHoyre()) == -2) {
                    if (element.compareTo(toppRot.getHoyre().getVerdi()) > 0) {
                        tilLogger("Venstre rotasjon på: " + toppRot.getVerdi());
                        toppRot = roterTilVenstre(toppRot);
                    } else {
                        tilLogger("Dobbel venstre rotasjon på: " + toppRot.getVerdi());
                        toppRot = dobbelRoterTilVenstre(toppRot);
                    }
                }
            }
            if (element.compareTo(toppRot.getVerdi()) < 0) {
                toppRot.setVenstre(settInnElement(element, toppRot.getVenstre()));
                //Sjekker om treet er balansert
                if (hoyde(toppRot.getVenstre()) - hoyde(toppRot.getHoyre()) == 2) {
                    if (element.compareTo(toppRot.getVenstre().getVerdi()) < 0) {
                        tilLogger("Hoyre rotasjon på: " + toppRot.getVerdi());
                        toppRot = roterTilHoyre(toppRot);
                    } else {
                        tilLogger("Dobbel høyre rotasjon på: " + toppRot.getVerdi());
                        toppRot = dobbelRoterTilHoyre(toppRot);
                    }
                }
            }
        }
        int height = hoyesteHoydeMellomVenstreOgHoyre(hoyde(toppRot.getVenstre()), hoyde(toppRot.getHoyre()));
        toppRot.setHoyde(height + 1);
        return toppRot;
    }

    //Enkel rotasjon til venstre
    private Node<T> roterTilVenstre(Node<T> origin) {
        Node<T> temp = origin.getHoyre();
        origin.setHoyre(temp.getVenstre());
        temp.setVenstre(origin);
        origin.setHoyde(hoyesteHoydeMellomVenstreOgHoyre(hoyde(origin.getVenstre()), hoyde(origin.getHoyre())) + 1);
        temp.setHoyde(hoyesteHoydeMellomVenstreOgHoyre(hoyde(temp.getHoyre()), hoyde(origin)) + 1);
        return temp;
    }

    //enkel rotasjon til høyre
    private Node<T> roterTilHoyre(Node<T> origin) {
        Node<T> temp = origin.getVenstre();
        origin.setVenstre(temp.getHoyre());
        temp.setHoyre(origin);
        origin.setHoyde(hoyesteHoydeMellomVenstreOgHoyre(hoyde(origin.getVenstre()), hoyde(origin.getHoyre())) + 1);
        temp.setHoyde(hoyesteHoydeMellomVenstreOgHoyre(hoyde(temp.getVenstre()), hoyde(origin)) + 1);
        return temp;
    }

    //dobbel rotasjon til venstre
    private Node<T> dobbelRoterTilVenstre(Node<T> origin) {
        origin.setHoyre(roterTilHoyre(origin.getHoyre()));
        return roterTilVenstre(origin);
    }

    //dobbel rotasjon til høyre
    private Node<T> dobbelRoterTilHoyre(Node<T> origin) {
        origin.setVenstre(roterTilVenstre(origin.getVenstre()));
        return roterTilHoyre(origin);
    }

    private int hoyesteHoydeMellomVenstreOgHoyre(int a, int b) {
        if (a > b)
            return a;
        else
            return b;
    }

    //Hent ut høyden til en node
    private int hoyde(Node<T> node) {
        if (node == null)
            return -1;
        else
            return node.getHoyde();
    }

    // Finn den absolutt minste noden
    private Node<T> finnMinste(Node<T> node) {
        if (node == null)
            return node;

        while (node.getVenstre() != null)
            node = node.getVenstre();
        return node;
    }

    //Driver metode for slettNode
    public void slettNode(T element) {
        slettNode(rotNode, element);
    }

    //Sletter en node
    private Node<T> slettNode(Node<T> node, T element) {
        if (node == null) {
            return node;
        }
        if (element.compareTo(node.getVerdi()) < 0) {
            node.setVenstre(slettNode(node.getVenstre(), element));
        } else if (element.compareTo(node.getVerdi()) > 0) {
            node.setHoyre(slettNode(node.getHoyre(), element));
        } else if (node.getVenstre() != null && node.getHoyre() != null) {
            node.setVerdi(finnMinste(node.getHoyre()).getVerdi());
            node.setHoyre(slettNode(node.getHoyre(), node.getVerdi()));
        } else if (node.getVenstre() != null || node.getHoyre() != null) {
            if (node.getHoyre() == null) {
                node.setVerdi(finnMinste(node.getVenstre()).getVerdi());
                node.setHoyre(slettNode(node.getVenstre(), node.getVerdi()));
                node.setVenstre(null);
            } else {
                node.setVerdi(finnMinste(node.getHoyre()).getVerdi());
                node.setHoyre(slettNode(node.getHoyre(), node.getVerdi()));
                node.setHoyre(null);
            }
        } else {
            node = (node.getVenstre() != null) ? node.getVenstre() : node.getHoyre();
        }
        return node;
    }

    public void slettNode2(T x) {
        slettNode2(rotNode, x);
    }

    private Node<T> slettNode2(Node<T> a, T x) {
        if (a == null) {
            return a;
        }
        if (a.getVerdi().compareTo(x) == 0) {
            return slettHovedRot(a);
        }
        if (a.getVerdi().compareTo(x) > 0) {
            a.setVenstre(slettNode2(a.getVenstre(), x));
        } else {
            a.setHoyre(slettNode2(a.getHoyre(), x));
        }
        return balanser(a);
    }

    private Node<T> slettHovedRot(Node<T> currentRoot) {
        if (currentRoot.getVenstre() == null)
            return currentRoot.getHoyre();
        if (currentRoot.getHoyre() == null)
            return currentRoot.getVenstre();

        Node<T> r1 = currentRoot.getVenstre();
        Node<T> father = currentRoot;

        while (r1.getHoyre() != null) {
            father = r1;
            r1 = r1.getHoyre();
        }
        currentRoot.setVerdi(r1.getVerdi());
        if (father == currentRoot)
            father.setVenstre(r1.getVenstre());
        else
            father.setHoyre(r1.getVenstre());
        return currentRoot;
    }

    private Node<T> balanser(Node<T> a) {
        kalkulerHoyde(a);
        if (hoydeAvTreet(a.getVenstre()) - hoydeAvTreet(a.getHoyre()) == 2) {
            if (hoydeAvTreet(a.getVenstre().getVenstre()) < hoydeAvTreet(a.getVenstre().getHoyre()))
                a.setVenstre(rotasjon1(a.getVenstre()));
            return rotasjon2(a);
        } // else
        if (hoydeAvTreet(a.getVenstre()) - hoydeAvTreet(a.getHoyre()) == -2) {
            if (hoydeAvTreet(a.getHoyre().getHoyre()) < hoydeAvTreet(a.getHoyre().getVenstre()))
                a.setHoyre(rotasjon2(a.getHoyre()));
            return rotasjon1(a);
        }
        return a;
    }

    private Node<T> rotasjon1(Node<T> current) {
        Node<T> b = current.getHoyre();
        Node<T> c = new Node<>(current.getVerdi(), current.getVenstre(), b.getVenstre());
        Node<T> r = new Node<>(b.getVerdi(), c, b.getHoyre());
        return r;
    }

    private Node<T> rotasjon2(Node<T> current) {
        Node<T> c = current.getVenstre();
        Node<T> b = new Node<>(current.getVerdi(), c.getHoyre(), current.getHoyre());
        Node<T> r = new Node<>(c.getVerdi(), c.getVenstre(), b);
        return r;
    }

    private void kalkulerHoyde(Node<T> nodeC) {
        if (nodeC != null) {
            kalkulerHoyde(nodeC.getVenstre());
            kalkulerHoyde(nodeC.getHoyre());
            nodeC.setHoyde(1 + Math.max(hoydeAvTreet(nodeC.getVenstre()), hoydeAvTreet(nodeC.getHoyre())));
        }
    }

    public int hoydeAvTreet(Node<T> a) {
        if (a == null) {
            return -1;
        }
        return 1 + Math.max(hoydeAvTreet(a.getVenstre()), hoydeAvTreet(a.getHoyre()));
    }
}
