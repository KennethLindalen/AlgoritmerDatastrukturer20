package dev.kennethlindalen;

import javax.swing.*;
import java.awt.*;

class FractalTree extends JPanel {

    private int dybde;
    private boolean tilfeldig;
    private JLabel melding = new JLabel();

    public FractalTree(int dybde, boolean tilfeldig) {
        this.dybde = dybde;
        this.tilfeldig = tilfeldig;
    }

    private void tegnTreet(Graphics g, int x1, int y1, double grunnVinkel, int dybde, boolean tilfeldig, int fontTykkelse) {

        int vinkel = 20;

        if (dybde == 0) {
            return;
        }

        // Disse blir castet til int fordi Math.cos og Math.sin returnerer double
        int x2 = x1 + (int) (Math.cos(Math.toRadians(grunnVinkel)) * dybde * 8);
        int y2 = y1 + (int) (Math.sin(Math.toRadians(grunnVinkel)) * dybde * 8);

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

        tegnTreet(g, x2, y2, grunnVinkel - vinkel, dybde - 1, tilfeldig, fontTykkelse - 1);
        tegnTreet(g, x2, y2, grunnVinkel + vinkel, dybde - 1, tilfeldig, fontTykkelse - 1);
    }

    public Dimension getPreferredSize() {
        return new Dimension(600, 500);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        tegnTreet(g, 300, 375, -90, this.dybde, this.tilfeldig, 8);
        add(melding);
    }

}