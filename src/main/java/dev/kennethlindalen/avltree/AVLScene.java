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

import java.util.*;


/**
 * Oppretter gui og setter opp for hva som skal skje ut i fra hvilke handlinger som er gjort i GUI
 *
 * @author Kenneth Lindalen (161940)
 *
 */

public class AVLScene extends Scene {
    private TextField input;
    private TextField finnNodeTF;
    private AVLTre<Integer> avlTre = new AVLTre<>();
    private double vGap = 60;
    private Pane avlPane;
    public static TextArea infoTA = new TextArea();
    public static String log = "";
    private ArrayList<Integer> nodeListe = new ArrayList<>();

    // Opprettelsen av GUI
    public AVLScene(Main main) {
        super(new VBox());

        VBox vindu = new VBox(0);
        FlowPane bunn = new FlowPane();

        avlPane = new Pane();
        input = new TextField();
        finnNodeTF = new TextField();


        // Elementer i GUI
        Label generellInputLabel = new Label("Søk/Slett/Sett inn Element:");
        Label finnNode = new Label("Nte laveste node å finne:");
        Button finnNteLavesteNode = new Button("Finn den nte laveste node:");
        Button settInnKnapp = new Button("Sett inn node");
        Button slettKnapp = new Button("Slett node");
        Button sokKnapp = new Button("Søk node");
        Button tilfeldigTreKnapp = new Button("Generer tilfeldig tre");
        Button slettAlleNoderKnapp = new Button("Slett alle noder");
        Button printInOrderKnapp = new Button("Logg Inorder");
        Button printPreOrderKnapp = new Button("Logg Preorder");
        Button printPostOrderKnapp = new Button("Logg Postorder");
        Button printLevelOrderKnapp = new Button("Logg Levelorder");

        // Stilsetting av elementer

        avlPane.setMinSize(800, 450);
        avlPane.setPadding(new Insets(20, 20, 20, 20));
        avlPane.setStyle("-fx-background-color: white;");

        infoTA.setEditable(false);
        infoTA.setPrefWidth(750);

        bunn.setHgap(15);
        bunn.setVgap(15);
        bunn.setAlignment(Pos.BOTTOM_CENTER);

        bunn.setPadding(new Insets(10, 10, 10, 10));

        bunn.getChildren().addAll(generellInputLabel, input, finnNode, finnNodeTF,
                finnNteLavesteNode, settInnKnapp, slettKnapp, slettAlleNoderKnapp,
                sokKnapp, tilfeldigTreKnapp, infoTA, printPreOrderKnapp, printInOrderKnapp, printPostOrderKnapp, printLevelOrderKnapp);

        vindu.getChildren().addAll(avlPane, bunn);


        super.setRoot(vindu);

        tilfeldigTreKnapp.setOnAction(e -> {
            tilfeldigTre();
            tilLogger("Generert et tilfeldig tre");
        });

        slettAlleNoderKnapp.setOnAction(e -> {
            avlPane.getChildren().clear();
            avlTre.setRotNode(null);
            tilLogger("Slettet alle noder");

        });

        settInnKnapp.setOnAction(e -> {
            tilLogger("Setter inn: " + input.getText());
            settInn();
        });

        sokKnapp.setOnAction(e -> {
            if (avlTre.sok(Integer.parseInt(input.getText()))) {
                tilLogger(input.getText() + " er i treet.");
            } else {
                tilLogger(input.getText() + " er ikke i treet.");
            }
        });

        finnNteLavesteNode.setOnAction(e -> {
            nodeListe.clear();
            if (finnNodeTF.getText().equals("")) {
                tilLogger("Søkefeltet er tomt");
            } else if (Integer.parseInt(finnNodeTF.getText()) > nodeListe.size() - 1) {
                opprettInorderListe(avlTre.getRotNode());
                if (Integer.parseInt(finnNodeTF.getText()) < nodeListe.size() - 1) {
                    tilLogger("Laveste node på " + Integer.parseInt(finnNodeTF.getText()) + " posisjon er: " + nodeListe.get(Integer.parseInt(finnNodeTF.getText()) - 1));
                } else {
                    tilLogger("Ikke gylding index, ikke mange nok noder.");
                }
            }
        });

        slettKnapp.setOnAction(e -> {
            if (input.getText().equals("")) {
                tilLogger("Søkefeltet er tomt.");
            } else {
                slett();
            }
        });
        printInOrderKnapp.setOnAction(e -> {
            nodeListe.clear();
            opprettInorderListe(avlTre.getRotNode());
            tilLogger("InOrder: " + listPrintPrettier(nodeListe));
        });

        printPreOrderKnapp.setOnAction(e -> {
            nodeListe.clear();
            preOrder(avlTre.getRotNode());
            tilLogger("PreOrder: " + listPrintPrettier(nodeListe));
        });

        printPostOrderKnapp.setOnAction(e -> {
            nodeListe.clear();
            postOrder(avlTre.getRotNode());
            tilLogger("PostOrder: " + listPrintPrettier(nodeListe));
        });

        printLevelOrderKnapp.setOnAction(e -> {
            nodeListe.clear();
            levelOrderTraversal(avlTre.getRotNode());
            tilLogger("LevelOrder: " + listPrintPrettier(nodeListe));
        });
    }

    public void visAVLTre() {
        // Sletter alt som allerede er vist slik at ikke mange iterasjoner ligger oppå hverandre
        avlPane.getChildren().clear();
        if (avlTre.getRotNode() != null) {
            // Gå gjennom treet og viser det på skjermen ut i fra rekursjon
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
        circle.setFill(Color.rgb(255, 255, 255));
        avlPane.getChildren().addAll(circle, new Text(x - 7, y + 4, root.getVerdi() + ""));
    }

    private void opprettInorderListe(Node node) {
        if (node == null)
            return;
        // Gå så langt til venstre som det er mulig, kan den ikke mer så legger den verdien i nodeListe
        opprettInorderListe(node.getVenstre());
        nodeListe.add(Integer.parseInt(node.getVerdi().toString()));
        // Gjør deretter det samme på høyre noder.
        opprettInorderListe(node.getHoyre());

    }

    public static void tilLogger(String melding) {
        log = (String.format("%s%s%n", log, melding));
        infoTA.setText(log);
        // Scroll til bun av logger (Ja, forholdsvis "hacky" måte..)
        infoTA.selectPositionCaret(infoTA.getLength());
        infoTA.deselect();
    }

    private void tilfeldigTre() {
        avlPane.getChildren().clear();
        avlTre.setRotNode(null);
        Random rnd = new Random();
        //Genererer 10 noder ved bruk av HashSet for å bare få unike nummer
        Set<Integer> set = new LinkedHashSet<>();
        while (set.size() < 10) {
            set.add(rnd.nextInt(100) + 3);
        }
        for (int x : set) {
            avlTre.settInnElement(x);
        }
        visAVLTre();
    }

    // Sett inn ny node(tall) i treet
    private void settInn() {
        try {
            avlTre.settInnElement(Integer.parseInt(input.getText()));
            visAVLTre();
        } catch (Exception empty) {
            tilLogger("Kan ikke sette inn tomt element.");
        }
        input.setText("");
    }

    //Slett node(tall) fra treet
    private void slett() {
        if (input.getText().equals(avlTre.getRotNode().getVerdi())) {
            avlTre.slettNode(Integer.parseInt(input.getText()));
            tilLogger("Slettet node: " + input.getText());
        } else {
            avlTre.slettNode2(Integer.parseInt(input.getText()));
            tilLogger("Slettet node: " + input.getText());
        }
        visAVLTre();
        input.setText("");
    }

    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        nodeListe.add(Integer.parseInt(node.getVerdi().toString()));
        preOrder(node.getVenstre());
        preOrder(node.getHoyre());
    }

    public void postOrder(Node node) {
        if (node != null) {
            postOrder(node.getVenstre());
            postOrder(node.getHoyre());
            nodeListe.add(Integer.parseInt(node.getVerdi().toString()));
        }
    }

    public void levelOrderTraversal(Node startNode) {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(startNode);
        while (!queue.isEmpty()) {
            Node tempNode = queue.poll();
            nodeListe.add(Integer.parseInt(tempNode.getVerdi().toString()));
            if (tempNode.getVenstre() != null)
                queue.add(tempNode.getVenstre());
            if (tempNode.getHoyre() != null)
                queue.add(tempNode.getHoyre());
        }
    }

    public String listPrintPrettier(ArrayList<Integer> lst){
        String account = Arrays.toString(lst.toArray());
        return account.substring(1,account.length()-1);
    }

}