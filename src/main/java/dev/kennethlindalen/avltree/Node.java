package dev.kennethlindalen.avltree;
/**
 * Klasse for oppretting av noder i AVL treet.
 *
 * @author Kenneth Lindalen (161940)
 * @author Lars Stian Fagerlid (163357)
 */
public class Node<T> {
    private T verdi;
    private Node<T> venstre;
    private Node<T> hoyre;
    private int hoyde;

    public Node(T element, Node<T> venstre, Node<T> hoyre) {
        super();
        this.venstre = venstre;
        this.verdi = element;
        this.hoyre = hoyre;
        hoyde = 1 + Math.max(hoyde(venstre), hoyde(hoyre));
    }

    public int hoyde(Node<T> a) {
        if (a == null)
            return -1;
        return 1 + Math.max(hoyde(a.getVenstre()), hoyde(a.getHoyre()));
    }

    public Node(T element) {
        this.verdi = element;
    }

    public Node<T> getHoyre() {
        return hoyre;
    }

    public void setHoyre(Node<T> hoyre) {
        this.hoyre = hoyre;
    }

    public Node<T> getVenstre() {
        return venstre;
    }

    public void setVenstre(Node<T> venstre) {
        this.venstre = venstre;
    }

    public T getVerdi() {
        return verdi;
    }

    public void setVerdi(T verdi) {
        this.verdi = verdi;
    }

    public int getHoyde() {
        return hoyde;
    }

    public void setHoyde(int hoyde) {
        this.hoyde = hoyde;
    }

}
