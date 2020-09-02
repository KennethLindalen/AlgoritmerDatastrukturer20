package dev.kennethlindalen;

import javax.swing.*;
import java.awt.*;

class FractalTree extends JPanel {

    private int dybde;
    private int vinkel;
    private int stammeLengde;
    private boolean tilfeldig;
    private JLabel melding = new JLabel();

    public FractalTree(int dybde, boolean tilfeldig, int vinkel, int stammeLengde) {
        this.dybde = dybde;
        this.tilfeldig = tilfeldig;
        this.vinkel = vinkel;
        this.stammeLengde = stammeLengde;
    }

    private void tegnTreet(Graphics g, int x1, int y1, double grunnVinkel, int dybde,
                           boolean tilfeldig, int fontTykkelse, int stammeLengde, boolean skrivStammelengde) {


        if (dybde == 0) {
            return;
        }

        // Disse blir castet til int fordi Math.cos og Math.sin returnerer double
        int x2 = x1 + (int) (Math.cos(Math.toRadians(grunnVinkel)) * stammeLengde * 4);
        int y2 = y1 + (int) (Math.sin(Math.toRadians(grunnVinkel)) * stammeLengde * 4);

        if (Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)) < 2) {
            melding.setText("Gren er under 2px, blir derfor ikke tegnet.");
            return;
        }

        if (tilfeldig) {
            vinkel = 15 + (int) (Math.random() * ((35 - 15) + 1));
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(fontTykkelse));
        g2.drawLine(x1, y1, x2, y2);

        g.drawString("Grener: ", 50, tilfeldig ? 370 : 350);
        g.drawString(String.valueOf(this.dybde), 100, tilfeldig ? 370 : 350);
        g.drawString(tilfeldig ? "" : "Vinkel:", 50, 370);
        g.drawString(tilfeldig ? "" : String.valueOf(vinkel), 95, 370);
        g.drawString("Stamme lengde: ", 50, 390);
        g.drawString(skrivStammelengde ? Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)) + "px" : "", 155, 390);
        g.drawString("Tilfeldig vinkel: ", 50, 410);
        g.drawString(tilfeldig ? "Ja" : "Nei", 150, 410);

        tegnTreet(g, x2, y2, grunnVinkel - vinkel, dybde - 1, tilfeldig,
                fontTykkelse - 1, (int) (stammeLengde * 0.9), false);
        tegnTreet(g, x2, y2, grunnVinkel + vinkel, dybde - 1, tilfeldig,
                fontTykkelse - 1, (int) (stammeLengde * 0.9), false);
    }

    public Dimension getPreferredSize() {
        return new Dimension(600, 500);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        tegnTreet(g, 300, 375, -90, this.dybde, this.tilfeldig, 8, stammeLengde, true);
        add(melding);
    }

}