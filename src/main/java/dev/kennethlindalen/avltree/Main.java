package dev.kennethlindalen.avltree;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Oppretter hoved vinduet
 *
 * @author Kenneth Lindalen (161940)
 *
 */
public class Main extends Application {
    private Stage mainStage;
    private Scene AVLscene = new AVLScene(this);

    public void start(Stage stage) throws Exception {
        mainStage = stage;
        stage.setTitle("Obligatorisk oppgave 2: Algoritmer og Datastrukturer");
        stage.setWidth(800);
        stage.setHeight(820);

        stage.show();
        stage.setScene(AVLscene);

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void closeStage() {
        mainStage.close();
    }

}

