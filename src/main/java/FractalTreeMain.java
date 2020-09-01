import javax.swing.*;
import java.awt.*;

public class FractalTreeMain{


    public static void main(String[] args) {
        lagGUI();
    }

    public static void lagGUI() {
        JFrame frame = new JFrame();
        frame.setTitle("Fraktal tre");
        frame.setSize(600, 800);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        final JPanel treePanel = new JPanel();
        treePanel.add(new FractalTree(11, false));

        final JPanel optionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JLabel treeGenerations = new JLabel();
        treeGenerations.setText("Gren generasjoner: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        optionsPanel.add(treeGenerations, gridBagConstraints);

        final JTextField treeGenerationsInput = new JTextField();
        treeGenerationsInput.setPreferredSize(new Dimension(150,25));
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        optionsPanel.add(treeGenerationsInput, gridBagConstraints);


        JButton vinkelOffset = new JButton();
        vinkelOffset.setText("Tilfeldige vinkler");
        vinkelOffset.setPreferredSize(new Dimension(150,30));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        optionsPanel.add(vinkelOffset, gridBagConstraints);

        JButton genererTreKnapp = new JButton();
        genererTreKnapp.setText("Generer tre");
        genererTreKnapp.setPreferredSize(new Dimension(150,30));
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;

        optionsPanel.add(genererTreKnapp, gridBagConstraints);

        mainPanel.add(treePanel);
        mainPanel.add(optionsPanel);
        frame.add(mainPanel);

        frame.setVisible(true);
    }
}

class FractalTree extends JPanel {

    private int dybde;
    private boolean tilfeldig;

    public FractalTree(int dybde, boolean tilfeldig) {
        this.dybde = dybde;
        this.tilfeldig = tilfeldig;
    }

    private void drawTree(Graphics g, int x1, int y1, double grunnVinkel, int dybde, boolean tilfeldig) {

        int vinkel = 20;

        if (dybde == 0) {
            return;
        }

        if (tilfeldig) {
            vinkel = 15 + (int)(Math.random() * ((25 - 15) + 1));
        }

        int x2 = x1 + (int) (Math.cos(Math.toRadians(grunnVinkel)) * dybde * 8.0);
        int y2 = y1 + (int) (Math.sin(Math.toRadians(grunnVinkel)) * dybde * 8.0);
        g.drawLine(x1, y1, x2, y2);
        drawTree(g, x2, y2, grunnVinkel - vinkel, dybde - 1, tilfeldig);
        drawTree(g, x2, y2, grunnVinkel + vinkel, dybde - 1, tilfeldig);
    }

    public Dimension getPreferredSize() {
        return new Dimension(600, 500);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        drawTree(g, 300, 550, -90, this.dybde, this.tilfeldig);
    }

}