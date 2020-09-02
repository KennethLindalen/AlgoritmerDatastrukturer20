package dev.kennethlindalen;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FractalTreeMain {


    public static void main(String[] args) {
        lagGUI();
    }

    public static void lagGUI() {

        final int[] generasjonAntall = new int[1];
        final boolean[] tilfeldigBool = {false};
        final int[] vinkel = new int[1];
        final int[] rotGrenLengde = new int[1];

        JFrame frame = new JFrame();
        frame.setTitle("Fraktal tre");
        frame.setSize(600, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        final JPanel treePanel = new JPanel();
        treePanel.add(new FractalTree(9, false, 20, 15));

        final JPanel optionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        generasjonAntall[0] = 9;
        vinkel[0] = 25;
        rotGrenLengde[0] = 15;

        JLabel rotGrenLengdeLabel = new JLabel();
        rotGrenLengdeLabel.setText("Lende på rot gren: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        optionsPanel.add(rotGrenLengdeLabel, gridBagConstraints);


        final JSlider rotGrenLengdeSlider = new JSlider(JSlider.HORIZONTAL, 10, 25, 15);
        rotGrenLengdeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rotGrenLengde[0] = rotGrenLengdeSlider.getValue();
                treePanel.removeAll();
                treePanel.add(new FractalTree(generasjonAntall[0], tilfeldigBool[0], vinkel[0], rotGrenLengde[0]));
                treePanel.revalidate();

            }
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        optionsPanel.add(rotGrenLengdeSlider, gridBagConstraints);

        JLabel treeGenerations = new JLabel();
        treeGenerations.setText("Gren generasjoner: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        optionsPanel.add(treeGenerations, gridBagConstraints);

        final JSlider treGenerasjonSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 9);
        treGenerasjonSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                generasjonAntall[0] = treGenerasjonSlider.getValue();
                treePanel.removeAll();
                treePanel.add(new FractalTree(generasjonAntall[0], tilfeldigBool[0], vinkel[0], rotGrenLengde[0]));
                treePanel.revalidate();

            }
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        optionsPanel.add(treGenerasjonSlider, gridBagConstraints);

        JLabel bestemVinkel = new JLabel();
        bestemVinkel.setText("Grunnvinkel for gren: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        optionsPanel.add(bestemVinkel, gridBagConstraints);


        final JSlider vinkelSlider = new JSlider(JSlider.HORIZONTAL, 1, 90, 25);
        vinkelSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                vinkel[0] = vinkelSlider.getValue();
                treePanel.removeAll();
                treePanel.add(new FractalTree(generasjonAntall[0], tilfeldigBool[0], vinkel[0], rotGrenLengde[0]));
                treePanel.revalidate();

            }
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        optionsPanel.add(vinkelSlider, gridBagConstraints);

        JButton tilfeldigVinkelKnapp = new JButton();
        tilfeldigVinkelKnapp.setText("Tilfeldige Vinkler");
        tilfeldigVinkelKnapp.setPreferredSize(new Dimension(150, 30));
        tilfeldigVinkelKnapp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tilfeldigBool[0] = !tilfeldigBool[0];
                treePanel.removeAll();
                treePanel.add(new FractalTree(generasjonAntall[0], tilfeldigBool[0], vinkel[0], rotGrenLengde[0]));
                treePanel.revalidate();
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        optionsPanel.add(tilfeldigVinkelKnapp, gridBagConstraints);

        mainPanel.add(treePanel);
        mainPanel.add(optionsPanel);
        frame.add(mainPanel);
        frame.pack();

        frame.setVisible(true);
    }
}