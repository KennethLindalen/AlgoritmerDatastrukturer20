package dev.kennethlindalen;

import javax.swing.*;
import java.awt.*;

/**
 * Denne klassen inneholder koden for å tegne ett tre.
 */
class FractalTree extends JPanel {

    private int dybde;
    private int vinkel;
    private int stammeLengde;
    private boolean tilfeldig;
    private JLabel melding = new JLabel();

    /**
     * Konstruktør metode
     *
     * @param dybde        hvor mange ledd greinene til treet skal ha.
     * @param tilfeldig    Variabel som gjør treet tilfeldig.
     * @param vinkel       Vinkel på treet når det blir tegnet.
     * @param stammeLengde Lengden på stammen og greinene.
     */
    public FractalTree(int dybde, boolean tilfeldig, int vinkel, int stammeLengde) {
        this.dybde = dybde;
        this.tilfeldig = tilfeldig;
        this.vinkel = vinkel;
        this.stammeLengde = stammeLengde;
    }

    /**
     * Denne metoden er den som inneholder koden for å kunne tegne treet og greinene ved bruk av et Rekursivt kall.
     *
     * @param g                 parameteret g er selve malekosten når treet blir tegnet
     * @param x1                Denne variabelen blir brukt rekursivt til å tegne de forskjellige greinene.
     *                          Den returnerer vinkelen for x-koordinatet for greinene.
     * @param y1                Denne variabelen blir brukt rekursivt til å tegne de forskjellige greinene.
     *                          den returnerer vinkelen for y-koordinatet for greinene.
     * @param grunnVinkel       Start posisjon for greinene som kommer etter stammen.
     * @param dybde             Hvor mange ledd greinene til treet skal har.
     * @param tilfeldig         Variabel som gjør treet tilfeldig.
     * @param fontTykkelse      Tykkelse på "malekosten" når treet blir tegnet.
     * @param stammeLengde      Lengden på stammen og greinene.
     * @param skrivStammelengde Variabelen sitt innhold fremvist tekstlig i GUI.
     */
    private void tegnTreet(Graphics g, int x1, int y1, double grunnVinkel, int dybde,
                           boolean tilfeldig, int fontTykkelse, int stammeLengde, boolean skrivStammelengde) {


        if (dybde == 0) {
            return;
        }

        // Disse blir castet til int fordi Math.cos og Math.sin returnerer double
        int x2 = x1 + (int) (Math.cos(Math.toRadians(grunnVinkel)) * stammeLengde * 4);
        int y2 = y1 + (int) (Math.sin(Math.toRadians(grunnVinkel)) * stammeLengde * 4);

        int avstandFraPunkter = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);

        if (Math.sqrt(avstandFraPunkter) < 2) {
            melding.setText("Gren er under 2px, blir derfor ikke tegnet.");
            return;
        }

        if (tilfeldig) {
            vinkel = 15 + (int) (Math.random() * ((35 - 15) + 1));
        }


        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(fontTykkelse));
        g2.drawLine(x1, y1, x2, y2);

        g.drawString("Grener generasjoner: ", 50, tilfeldig ? 370 : 350);
        g.drawString(String.valueOf(this.dybde), 185, tilfeldig ? 370 : 350);
        g.drawString(tilfeldig ? "" : "Vinkel:", 50, 370);
        g.drawString(tilfeldig ? "" : String.valueOf(vinkel), 95, 370);
        g.drawString("Stamme lengde: ", 50, 390);
        g.drawString(skrivStammelengde ? Math.sqrt(avstandFraPunkter) + "px" : "", 155, 390);
        g.drawString("Tilfeldig vinkel: ", 50, 410);
        g.drawString(tilfeldig ? "Ja" : "Nei", 150, 410);

        tegnTreet(g, x2, y2, grunnVinkel - vinkel, dybde - 1, tilfeldig,
                fontTykkelse - 1, (int) (stammeLengde * 0.9), false);
        tegnTreet(g, x2, y2, grunnVinkel + vinkel, dybde - 1, tilfeldig,
                fontTykkelse - 1, (int) (stammeLengde * 0.9), false);
    }

    /**
     * @return returnerer ønskelig bredde og høyde til treet. Sørger for at treet er innenfor GUI-et.
     */
    public Dimension getPreferredSize() {
        return new Dimension(600, 500);
    }

    /**
     * Denne metoden tegner treet, og gjør det igjen når en bruker gjør en handling som krever at det blir
     * tegnet på nytt. f.eks tilfeldig knappen i GUI.
     * Vi har brukt @Override her ettersom vi ønsker at den skal gjøre det bruker ber om
     *
     * @param g Variabel som blir brukt som malekost.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        tegnTreet(g, 300, 375, -90, this.dybde, this.tilfeldig, 10, stammeLengde, true);
        add(melding);
    }

}