package dev.kennethlindalen;

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
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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


        JButton tilfeldigVinkelKnapp = new JButton();
        tilfeldigVinkelKnapp.setText("Tilfeldige Vinkler");
        tilfeldigVinkelKnapp.setPreferredSize(new Dimension(150,30));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        optionsPanel.add(tilfeldigVinkelKnapp, gridBagConstraints);

        JButton genererTreKnapp = new JButton();
        genererTreKnapp.setText("Generer Tre");
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