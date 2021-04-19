package GUI;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import logic.*;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.processData.Automatas;
import logic.processData.State;
import logic.processData.Transitions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Draw extends Application {
    public static AnchorPane pane;

    @Override
    public void start(Stage primaryStage) {
        startProcess(primaryStage);
    }

    public static void startProcess(Stage primaryStage) {
        pane = new AnchorPane();

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);

        Button load = new Button("Load A File");
        load.setId("SelectInputFileButton");
        load.setTooltip(new Tooltip("Select Input XML File"));

        load.setOnAction(event -> {
            Stage stage = new Stage();

            FileChooser filePath = new FileChooser();
            filePath.setTitle("select file");
            filePath.getExtensionFilters().add(new FileChooser.ExtensionFilter("just xml files", "*.xml"));

            File selectedFile = filePath.showOpenDialog(stage);

            Thread thread = new Thread(() -> Main.main(selectedFile));

            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pane = new AnchorPane();


            thread = new Thread(() -> {
                for (State state : Main.automatas.states) {
                    Platform.runLater(() -> pane.getChildren().add(state.statePane()));
                }

                for (Transitions transition : Main.automatas.transitions) {
                    transition.transitionPane();
                }
            });
            thread.start();
            mainPage(primaryStage);
        });


        Button newFA = new Button("Make A New Project");
        newFA.setOnAction(event -> {
            VBox textBox = new VBox(20);
            textBox.setAlignment(Pos.CENTER);

            HBox text = new HBox(20);
            text.setAlignment(Pos.CENTER);


            Label label = new Label("Automata Type");

            TextField type = new TextField();
            type.setPromptText("Automata Type");

            text.getChildren().addAll(label, type);

            HBox buttonBox = new HBox(20);
            buttonBox.setAlignment(Pos.CENTER);

            Button submit = new Button("Submit");
            submit.setOnAction(event1 -> {
                if (type.getText().equals("")) new Alert(Alert.AlertType.ERROR,
                        "please enter automata type").showAndWait();
                else {
                    Main.automatas = new Automatas(type.getText());
                    mainPage(primaryStage);
                }
            });

            Button back = new Button("Back");
            back.setOnAction(event1 -> startProcess(primaryStage));

            buttonBox.getChildren().addAll(submit, back);
            textBox.getChildren().addAll(text, buttonBox);

            Scene scene = new Scene(textBox, 350, 200);
            scene.getStylesheets().add("GUI/CssFiles/AutomataTypeStyle.css");
            primaryStage.setScene(scene);
        });

        Button exit = new Button("Exit");
        exit.setOnAction(event -> primaryStage.close());

        box.getChildren().addAll(load, newFA, exit);

        primaryStage.setTitle("Welcome");
        Scene scene = new Scene(box, 300, 350);
        scene.getStylesheets().add("GUI/CssFiles/StartPageStyle.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void mainPage(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();


        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        borderPane.setCenter(pane);
        borderPane.setTop(buttonBox);
        BorderPane.setMargin(buttonBox, new Insets(20, 20, 20, 20));

//        pane.setStyle("-fx-background-color: white");
        pane.setId("WorkSpacePane");
        BorderPane.setMargin(pane, new Insets(0, 20, 20, 20));


        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);


        Button menuButton = new Button("Menu");
        menuButton.setOnMouseClicked(event -> new Menu(primaryStage));
        buttonBox.getChildren().addAll(menuButton);

        String cssFilePath = "GUI/CssFiles/MainPageStyle.css";
        scene.getStylesheets().add(cssFilePath);
        primaryStage.setTitle("TOL Project");
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void save(File selectedFile) {
        new Thread(() -> {
            try {
                FileWriter file = new FileWriter(selectedFile == null ? new File("output.xml") : selectedFile);

                BufferedWriter buffer = new BufferedWriter(file);

                buffer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                buffer.write("\t<Automata type=\"" + Main.automatas.type + "\">\n");
                /*
                alphabets
                 */
                buffer.write("\t\t<Alphabets numberOfAlphabets=\"" + (Main.automatas.alphabets.size() - 1) + "\">\n");
                for (String alphabet : Main.automatas.alphabets) {
                    if (alphabet.equals(String.valueOf((char) 955))) continue;
                    buffer.write("\t\t\t<alphabet letter=\"" + alphabet + "\"/>\n");
                }
                buffer.write("\t\t</Alphabets>\n");
                /*
                states
                 */
                buffer.write("\t\t<States numberOfStates=\"" + Main.automatas.states.size() + "\">\n");
                List<State> finals = new ArrayList<>();
                for (State state : Main.automatas.states) {
                    buffer.write("\t\t\t<state name=\"" + state.name + "\" positionX=\"" + state.centerX +
                            "\" positionY=\"" + state.centerY + "\"/>\n");
                    if (state.isFinal) finals.add(state);
                }

                buffer.write("\t\t\t<initialState name=\"" + Main.automatas.initial + "\"/>\n");
                buffer.write("\t\t\t<FinalStates numberOfFinalStates=\"" + finals.size() + "\">\n");
                for (State finalState : finals) {
                    buffer.write("\t\t\t\t<finalState name=\"" + finalState.name + "\"/>\n");
                }
                buffer.write("\t\t\t</FinalStates>\n");

                buffer.write("\t\t</States>\n");

                /*
                transitions
                 */
                buffer.write("\t\t<Transitions numberOfTrans=\"" + Main.automatas.transitions.size() + "\">\n");
                for (Transitions transition : Main.automatas.transitions) {
                    buffer.write("\t\t\t<transition name=\"" + transition.name + "\" source=\"" +
                            transition.start + "\" destination=\"" + transition.end + "\" label=\"" +
                            transition.label + "\"/>\n");
                }
                buffer.write("\t\t</Transitions>\n");

                buffer.write("\t</Automata>\n");
                buffer.flush();
                file.close();
                Platform.runLater(() -> new Alert(Alert.AlertType.INFORMATION, "Saved Successfully").showAndWait());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}