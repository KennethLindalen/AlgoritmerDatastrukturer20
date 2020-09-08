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
     * @param vinkel       Base vinkel på treet når det blir tegnet.
     * @param stammeLengde Grunn lengde på stamme som gir basis på lengde på grener.
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
     * @param g                 parameteret g er selve "lerettet" når treet blir tegnet på.
     * @param x1                Denne variabelen blir brukt rekursivt til å tegne de forskjellige greinene.
     *                          Start posisjon x for tegning av stamme/gren.
     * @param y1                Denne variabelen blir brukt rekursivt til å tegne de forskjellige greinene.
     *                          Start posisjon y for tegning av stamme/gren.
     * @param grunnVinkel       Start posisjon for greinene som kommer etter stammen.
     * @param dybde             Hvor mange ledd greinene til treet skal ha.
     * @param tilfeldig         Forteller om grenene skal ha tilfeldig vinkel under generering.
     * @param strekTykkelse     Tykkelse på strek(gren/stamme) for hvert ledd av generering av tre.
     * @param stammeLengde      Grunn lengde på stamme som gir basis på lengde på grener.
     * @param skrivStammelengde Gir beskjed om lengde skal bli printet tekstlig på skjerm.
     *                          Overlapper all teksten hvis ikke denne er her.
     */
    private void tegnTreet(Graphics g, int x1, int y1, double grunnVinkel, int dybde,
                           boolean tilfeldig, int strekTykkelse, int stammeLengde, boolean skrivStammelengde) {

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
        g2.setStroke(new BasicStroke(strekTykkelse));
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
                strekTykkelse - 1, (int) (stammeLengde * 0.9), false);
        tegnTreet(g, x2, y2, grunnVinkel + vinkel, dybde - 1, tilfeldig,
                strekTykkelse - 1, (int) (stammeLengde * 0.9), false);
    }

    /**
     * @return returnerer ønskelig bredde og høyde til canvas(lerret).
     */
    public Dimension getPreferredSize() {
        return new Dimension(600, 500);
    }

    /**
     * Oppretter komponenten til treet, dette er for første generering av treet og opprettelse av komponenten og første henting av
     * graphics fra Super(JPanel)
     *
     * @param g henter canvas(lerret) fra Super(JPanel)
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        tegnTreet(g, 300, 375, -90, this.dybde, this.tilfeldig, 10, stammeLengde, true);
        add(melding);
    }

}