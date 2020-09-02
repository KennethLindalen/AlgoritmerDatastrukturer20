package dev.kennethlindalen;

import javax.swing.*;
import java.awt.*;

class FractalTree extends JPanel {

    private int dybde;
    private boolean tilfeldig;
    JLabel melding = new JLabel("", JLabel.CENTER);

    public FractalTree(int dybde, boolean tilfeldig) {
        this.dybde = dybde;
        this.tilfeldig = tilfeldig;
    }

    private void tegnTreet(Graphics g, int x1, int y1, double grunnVinkel, int dybde, boolean tilfeldig) {

        int vinkel = 20;

        if (dybde == 0) {
            return;
        }

        int x2 = x1 + (int) (Math.cos(Math.toRadians(grunnVinkel)) * dybde * 8);
        int y2 = y1 + (int) (Math.sin(Math.toRadians(grunnVinkel)) * dybde * 8);

        if (Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)) < 2) {
            melding.setText("Gren er under 2px, blir derfor ikke tegnet.");
            return;
        }

        if (tilfeldig) {
            vinkel = 15 + (int) (Math.random() * ((25 - 15) + 1));
        }
        g.drawLine(x1, y1, x2, y2);
        tegnTreet(g, x2, y2, grunnVinkel - vinkel, dybde - 1, tilfeldig);
        tegnTreet(g, x2, y2, grunnVinkel + vinkel, dybde - 1, tilfeldig);
    }

    public Dimension getPreferredSize() {
        return new Dimension(600, 500);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        tegnTreet(g, 300, 550, -90, this.dybde, this.tilfeldig);
        add(melding);
    }

}