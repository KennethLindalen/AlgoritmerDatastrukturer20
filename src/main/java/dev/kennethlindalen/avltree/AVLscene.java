package dev.kennethlindalen.avltree;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;


/**
 * Oppretter gui og setter opp for hva som skal skje ut i fra hvilke handlinger som er gjort i GUI
 *
 * @author Kenneth Lindalen (161940)
 * @author Lars Stian Fagerlid (163357)
 */
public class AVLscene extends Scene {
    private TextField input;
    private TextField finnNodeTF;
    private AVLTree<Integer> avlTre = new AVLTree<>();
    private double vGap = 60;
    private Pane avlPane;
    public static TextArea infoTA = new TextArea();
    public static String log = "";
    private ArrayList<Integer> nodeListe = new ArrayList<>();

    // Opprettelsen av GUI
    public AVLscene(Main main) {
        super(new VBox());

        VBox vindu = new VBox(0);
        FlowPane bottom = new FlowPane();

        avlPane = new Pane();
        input = new TextField();
        finnNodeTF = new TextField();


        // Elementer i GUI
        Label lblinput = new Label("Element:");
        Label finnNode = new Label("Nte laveste node å finne:");
        Button finnNteLavesteNode = new Button("Finn den nte laveste node:");
        Button settInnKnapp = new Button("Sett inn node");
        Button slettKnapp = new Button("Slett node");
        Button tilfeldigTreKnapp = new Button("Generer tilfeldig tre");
        Button slettAlleNoderKnapp = new Button("Slett alle noder");

        // Stilsetting av elementer

        avlPane.setMinSize(800, 450);
        avlPane.setPadding(new Insets(20, 20, 20, 20));
        avlPane.setStyle("-fx-padding: 30;" +
                "-fx-background-color: white;");

        infoTA.setEditable(false);
        bottom.setHgap(15);
        bottom.setVgap(15);
        bottom.setAlignment(Pos.BOTTOM_LEFT);

        bottom.setPadding(new Insets(10, 10, 10, 15));

        bottom.getChildren().addAll(lblinput, input, finnNode, finnNodeTF, finnNteLavesteNode, settInnKnapp, slettKnapp, slettAlleNoderKnapp, tilfeldigTreKnapp, infoTA);
        vindu.getChildren().addAll(avlPane, bottom);


        super.setRoot(vindu);

        tilfeldigTreKnapp.setOnAction(e -> {
            tilfeldigTre();
            log = (String.format("%sGenerert et tilfeldig tre%n", log));
            infoTA.setText(log);
            infoTA.setScrollTop(Double.MAX_VALUE);
        });

        slettAlleNoderKnapp.setOnAction(e -> {
            avlPane.getChildren().clear();
            avlTre.setRotNode(null);
            log = (String.format("%sSlettet alle noder%n", log));
            infoTA.setText(log);
            infoTA.setScrollTop(Double.MAX_VALUE);
        });

        settInnKnapp.setOnAction(e -> {
            settInn();
        });

        finnNteLavesteNode.setOnAction(e -> {
            nodeListe.clear();
            if (finnNodeTF.getText().equals("")){
                log = (String.format("%sSøkefeltet er tomt%n", log));
                infoTA.setText(log);
                infoTA.setScrollTop(Double.MAX_VALUE);
            } else if (Integer.parseInt(finnNodeTF.getText()) > nodeListe.size() - 1) {
                printInorder(avlTre.getRotNode());
                if (Integer.parseInt(finnNodeTF.getText()) < nodeListe.size() - 1) {
                    log = (String.format("%sLaveste node på %d posisjon er: %d%n", log, Integer.parseInt(finnNodeTF.getText()), nodeListe.get(Integer.parseInt(finnNodeTF.getText()) - 1)));
                    infoTA.setText(log);
                    infoTA.setScrollTop(Double.MAX_VALUE);
                }else {
                    log = (String.format("%sIkke gylding index, ikke mange nok noder%n", log));
                    infoTA.setText(log);
                    infoTA.setScrollTop(Double.MAX_VALUE);
                }
            }
        });

        tilfeldigTreKnapp.setOnAction(e -> {
            tilfeldigTre();
            log = (String.format("%sGenerert et tilfeldig tre%n", log));
            infoTA.setText(log);
            infoTA.setScrollTop(Double.MAX_VALUE);
        });

        slettKnapp.setOnAction(e -> {
            if (input.getText().equals("")){
                log = (String.format("%sSøkefeltet er tomt%n", log));
                infoTA.setText(log);
                infoTA.setScrollTop(Double.MAX_VALUE);
            }else {
                slett();
            }
        });
    }

    public void visAVLTre() {
        avlPane.getChildren().clear(); // Sletter alt som allerede er vist slik at ikke mange iterasjoner ligger oppå hverandre
        if (avlTre.getRotNode() != null) {
            // Gå gjennom treet og vis det ut i fra rekursjon
            visAVLTre(avlTre.getRotNode(), avlPane.getWidth() / 2, vGap, avlPane.getWidth() / 4);
        }
    }

    private void visAVLTre(Node root, double x, double y, double hGap) {
        if (root.getVenstre() != null) {
            // Tegner linje til venstre node
            avlPane.getChildren().add(new Line(x - hGap, y + vGap, x, y));
            // Tegner venstre subtreet
            visAVLTre(root.getVenstre(), x - hGap, y + vGap, hGap / 2);
        }

        if (root.getHoyre() != null) {
            // Tegner linje til høyre node
            avlPane.getChildren().add(new Line(x + hGap, y + vGap, x, y));
            // Tegner høyre subtreet
            visAVLTre(root.getHoyre(), x + hGap, y + vGap, hGap / 2);
        }
        // Opprettelsen av sirkelene for nodene
        Circle circle = new Circle(x, y, 25);
        circle.setId("circle");
        circle.setStroke(Color.GRAY);
        circle.setStrokeWidth(1);
        circle.setFill(Color.rgb(89, 162, 217));
        avlPane.getChildren().addAll(circle, new Text(x - 7, y + 4, root.getVerdi() + ""));
    }

    public void printInorder(Node node) {
        if (node == null)
            return;
        // Gå så langt til venstre som det er mulig, kan den ikke mer så legger den verdien i nodeListe
        printInorder(node.getVenstre());
        /* then print the data of node */
        nodeListe.add(Integer.parseInt(node.getVerdi().toString()));
        // Gjør deretter det samme på høyre noder.
        printInorder(node.getHoyre());

    }

    private void tilfeldigTre() {
        avlPane.getChildren().clear();
        avlTre.setRotNode(null);
        Random rnd = new Random();
        //Tilfeldig antall størrelse på LinkedHashSet mellom 3 og 20
        int setSize = (rnd.nextInt(20) + 3);
        //Genererer via HashSet for å bare få unike nummer
        Set<Integer> set = new LinkedHashSet<Integer>();
        while (set.size() < setSize) {
            set.add(rnd.nextInt(100) + 3);
        }
        for (int x : set) {
            avlTre.settInnElement(x);
        }
        infoTA.setScrollTop(Double.MAX_VALUE);
        visAVLTre();

    }

    private void settInn() {
        try {
            int toInsert = Integer.parseInt(input.getText());
            avlTre.settInnElement(toInsert);
            visAVLTre();
        } catch (Exception empty) {
            log = (String.format("%sKan ikke sette inn tomt element%n", log));
            infoTA.setText(log);
            infoTA.setScrollTop(Double.MAX_VALUE);
        }
        input.setText("");
    }

    private void slett() {
        if (input.getText().equals(avlTre.getRotNode().getVerdi())) {
            avlTre.slettNode(Integer.parseInt(input.getText()));
            log = (String.format("%sSlettet node: %s%n", log, input.getText()));
            infoTA.setText(log);
            infoTA.setScrollTop(Double.MAX_VALUE);
        } else {
            avlTre.slettNode2(Integer.parseInt(input.getText()));
            log = (String.format("%sSlettet node: %s%n", log, input.getText()));
            infoTA.setText(log);
            infoTA.setScrollTop(Double.MAX_VALUE);
        }
        visAVLTre();
        input.setText("");
    }

}