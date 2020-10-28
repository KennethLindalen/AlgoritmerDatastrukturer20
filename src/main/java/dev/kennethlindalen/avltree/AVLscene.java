package dev.kennethlindalen.avltree;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

public class AVLscene extends Scene {
    private Main main;
    private TextField input;
    private TextField finnNodeTF;
    private AVLTree<Integer> arbol = new AVLTree<>();
    private double vGap = 60;
    private double radius = 20;
    private Pane avl;
    public static TextArea infoTA = new TextArea();
    public static String log = "";
    private ArrayList<Integer> nodeListe = new ArrayList<>();

    public AVLscene(Main main) {
        super(new VBox());
        this.main = main;

        /***************
         * GUI Elements *
         ****************/
        VBox mainContent = new VBox(20);
        FlowPane bottom = new FlowPane();

        avl = new Pane();
        input = new TextField();
        finnNodeTF = new TextField();

        Label lblinput = new Label("Element:");
        Label finnNode = new Label("Nte laveste node å finne:");
        Button finnNteLavesteNode = new Button("Finn den nte laveste node:");
        Button insert = new Button("Sett inn node");
        Button delete = new Button("Slett node");
        Button randomTree = new Button("Generer tilfeldig tre");
        Button clear = new Button("Slett alle noder");

        input.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                insert();
            }
        });

        /***********
         *  Styles  *
         ************/
        avl.setMinSize(800, 450);
        avl.setPadding(new Insets(20, 20, 20, 20));
        avl.setStyle("-fx-padding: 30;" +
                "-fx-background-color: white;");

        infoTA.setEditable(false);
        bottom.setHgap(15);
        bottom.setVgap(15);
        bottom.setAlignment(Pos.BOTTOM_LEFT);

        bottom.setPadding(new Insets(10, 10, 10, 15));

        bottom.getChildren().addAll(lblinput, input,finnNode, finnNodeTF, finnNteLavesteNode, insert, delete, clear, randomTree, infoTA);
        mainContent.getChildren().addAll(avl, bottom);


        super.setRoot(mainContent);

        randomTree.setOnAction(e -> {

            randomTree();
            log = (String.format("%sGenerert et tilfeldig tre%n", log));
            infoTA.setText(log);
        });
        clear.setOnAction(e -> {
            avl.getChildren().clear();
            arbol.setOrigin(null);
            log = (String.format("%sSlettet alle noder%n", log));
            infoTA.setText(log);
        });
        insert.setOnAction(e -> {
            insert();
        });
        finnNteLavesteNode.setOnAction(e -> {
            if(Integer.parseInt(finnNodeTF.getText()) > nodeListe.size() - 1){
                nodeListe.clear();
                printInorder(arbol.getOrigin());
                log = (String.format("%sLaveste node på %d posisjon er: %d%n", log,Integer.parseInt(finnNodeTF.getText()),nodeListe.get(Integer.parseInt(finnNodeTF.getText())-1)));
                infoTA.setText(log);
            }else{
                log = (String.format("%sIkke gylding index, ikke mange nok noder%n", log));
                infoTA.setText(log);
            }
        });
        randomTree.setOnAction(e -> {
            randomTree();
        });
        delete.setOnAction(e -> {
            delete();
        });
    }

    public void displayAVLTree() {
        avl.getChildren().clear(); // Clear the pane
        if (arbol.getOrigin() != null) {
            // Display tree recursively
            displayAVLTree(arbol.getOrigin(), avl.getWidth() / 2, vGap, avl.getWidth() / 4);
        }
    }

    private void displayAVLTree(Node root, double x, double y, double hGap) {
        if (root.getLeft() != null) {
            // Draw a line to the left node
            avl.getChildren().add(new Line(x - hGap, y + vGap, x, y));
            // Draw the left subtree recursively
            displayAVLTree(root.getLeft(), x - hGap, y + vGap, hGap / 2);
        }

        if (root.getRight() != null) {
            // Draw a line to the right node
            avl.getChildren().add(new Line(x + hGap, y + vGap, x, y));
            // Draw the right subtree recursively
            displayAVLTree(root.getRight(), x + hGap, y + vGap, hGap / 2);
        }
        // Display a node
        Circle circle = new Circle(x, y, radius);
        circle.setId("circle");
        circle.setStroke(Color.GRAY);
        circle.setStrokeWidth(1);
        circle.setFill(Color.rgb(89, 217, 149));
        avl.getChildren().addAll(circle, new Text(x - 7, y + 4, root.getElement() + ""));
    }
    public void printInorder(Node node) {
        if (node == null)
            return;
        /* first recur on left child */
        printInorder(node.getLeft());
        /* then print the data of node */
        nodeListe.add(Integer.parseInt(node.getElement().toString()));
        /* now recur on right child */
        printInorder(node.getRight());

    }

    private void randomTree(){
        avl.getChildren().clear();
        arbol.setOrigin(null);
        Random randNum = new Random();
        //random array size from 3 to 15
        int setSize = (randNum.nextInt(15)+3);
        //Create a HashSet to get only unique elements
        Set<Integer> set = new LinkedHashSet<Integer>();
        while (set.size() < setSize) {
            set.add(randNum.nextInt(100)+3);
        }
        try{
            for(int x: set){
                arbol.insertElement(x);
            }
        } catch(DuplicateException de) {
        }
        displayAVLTree();
    }


    private void insert(){
        try{
            int toInsert = Integer.parseInt(input.getText());
            try{
                arbol.insertElement(toInsert);
                displayAVLTree();
            }catch(DuplicateException de){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Duplicate");
                alert.setHeaderText(de.getMessage());
                alert.showAndWait();
            }
        }catch(Exception empty){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty field");
            alert.setHeaderText("Please introduce an element");
            alert.showAndWait();
        }
        input.setText("");
    }

    private void delete(){
        if(input.getText().equals(arbol.getOrigin().getElement())){
            arbol.deleteNode(Integer.parseInt(input.getText()));
        } else {
            arbol.deleteNode2(Integer.parseInt(input.getText()));
        }
        displayAVLTree();
        input.setText("");
    }

}