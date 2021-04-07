package GUI.Shapes;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;


public class RegularArrow {
    public String label;

    public String name;

    public double startX;
    public double startY;

    public double endX;
    public double endY;

    public AnchorPane pane;

    public RegularArrow(String label, String name, double startX, double startY,
                        double endX, double endY) {

        this.label = label;
        this.name = name;
        this.startX = startX * 6;
        this.startY = startY * 6;
        this.endX = endX * 6;
        this.endY = endY * 6;

        this.pane = regularArray();
    }


    public AnchorPane regularArray() {
        AnchorPane pane = new AnchorPane();
        Path path = new Path();


        Label label = new Label(this.label);
        label.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; " +
                "-fx-border-width: 1; -fx-border-color: red");

        pane.getChildren().addAll(path, label);

        if (startX < endX && startY < endY) {
            MoveTo moveTo = new MoveTo(-30, 30);

            QuadCurveTo first = new QuadCurveTo();
            first.setX((endX - startX) - 60);
            first.setY((endY - startY));
            first.setControlX((endX - startX - 60) / 4 - 30);
            first.setControlY((endY - startY) * 0.75);

            LineTo firstLine = new LineTo((endX - startX) - 67, (endY - startY) + 2);

            MoveTo moveForSecLine = new MoveTo((endX - startX) - 60, (endY - startY));

            LineTo secLine = new LineTo((endX - startX) - 62, (endY - startY) - 7);

            path.getElements().addAll(moveTo, first, firstLine, moveForSecLine, secLine);

            AnchorPane.setLeftAnchor(label, (endX - startX - 60) / 4 - 30);
            AnchorPane.setTopAnchor(label, (endY - startY) / 2);

        } else if (startX > endX && startY > endY) {
            MoveTo moveTo = new MoveTo(-60, 0);

            QuadCurveTo first = new QuadCurveTo();
            first.setX((endX - startX) - 30);
            first.setY((endY - startY) + 30);
            first.setControlX((endX - startX - 30) / 4 - 60);
            first.setControlY((endY - startY + 30) * 0.75);

            LineTo firstLine = new LineTo((endX - startX) - 24, (endY - startY) + 28);

            MoveTo moveForSecLine = new MoveTo((endX - startX) - 30, (endY - startY) + 30);

            LineTo secLine = new LineTo((endX - startX) - 28, (endY - startY) + 36);

            path.getElements().addAll(moveTo, first, firstLine, moveForSecLine, secLine);

            AnchorPane.setLeftAnchor(label, (endX - startX - 30) / 4 - 60);
            AnchorPane.setTopAnchor(label, (endY - startY + 30) / 2 - 10);

        } else if (startX > endX && startY < endY) {
            MoveTo moveTo = new MoveTo(0, 0);

            QuadCurveTo first = new QuadCurveTo();
            first.setX((startX - endX) - 30);
            first.setY((startY - endY) + 30);
            first.setControlX((startX - endX - 30) * 0.75);
            first.setControlY((startY - endY + 30) / 4);

            LineTo firstLine = new LineTo(5, -7);

            LineTo secLine = new LineTo(7, 2);


            path.getElements().addAll(moveTo, first, moveTo, firstLine, moveTo, secLine);

            AnchorPane.setLeftAnchor(label, (startX - endX - 45) * 0.75);
            AnchorPane.setTopAnchor(label, (startY - endY + 30) / 2);

        } else if (startX < endX && startY > endY) {
            MoveTo moveTo = new MoveTo(0, -4);

            QuadCurveTo first = new QuadCurveTo();
            first.setX((endX - startX) - 60);
            first.setY((endY - startY));
            first.setControlX((endX - startX - 60) / 4);
            first.setControlY((endY - startY) * 0.75);

            LineTo firstLine = new LineTo((endX - startX) - 62, (endY - startY) + 7);

            MoveTo moveForSec = new MoveTo((endX - startX) - 60, (endY - startY));


            LineTo secLine = new LineTo((endX - startX) - 67, (endY - startY) - 2);


            path.getElements().addAll(moveTo, first, firstLine, moveForSec, secLine);

            AnchorPane.setLeftAnchor(label, (endX - startX - 90) / 4);
            AnchorPane.setTopAnchor(label, (endY - startY) / 2);

        } else if (startX == endX && startY > endY) {
            MoveTo moveTo = new MoveTo(-60, 0);

            QuadCurveTo first = new QuadCurveTo();
            first.setX(-60);
            first.setY(endY - startY);
            first.setControlX((endY - startY) / 3 - 40);
            first.setControlY((endY - startY) / 2);

            LineTo firstLine = new LineTo(-68, endY - startY + 3);

            MoveTo moveForSec = new MoveTo(-60, endY - startY);

            LineTo secLine = new LineTo(-59, endY - startY + 9);


            path.getElements().addAll(moveTo, first, firstLine, moveForSec, secLine);

            AnchorPane.setLeftAnchor(label, (endY - startY) / 6 - 55);
            AnchorPane.setTopAnchor(label, (endY - startY) / 2 - 10);

        } else if (startX == endX && startY < endY) {
            MoveTo moveTo = new MoveTo(0, 0);

            QuadCurveTo first = new QuadCurveTo();
            first.setX(0);
            first.setY(endY - startY);
            first.setControlX((endY - startY) / 3 - 20);
            first.setControlY((endY - startY) / 2);

            LineTo firstLine = new LineTo(0, endY - startY - 8);

            MoveTo moveForSec = new MoveTo(0, endY - startY);


            LineTo secLine = new LineTo(8, endY - startY - 2);


            path.getElements().addAll(moveTo, first, firstLine, moveForSec, secLine);

            AnchorPane.setLeftAnchor(label, (endY - startY) / 6 - 15);
            AnchorPane.setTopAnchor(label, (endY - startY) / 2 - 10);

        } else if (startX > endX && startY == endY) {
            MoveTo moveTo = new MoveTo(-60, 0);

            QuadCurveTo first = new QuadCurveTo();
            first.setX(endX - startX);
            first.setY(0);
            first.setControlX((endX - startX - 60) / 2);
            first.setControlY(startX - endX - 60);

            LineTo firstLine = new LineTo((endX - startX) + 8, -1);

            MoveTo moveForSec = new MoveTo((endX - startX) - 1, 0);

            LineTo secLine = new LineTo((endX - startX), 8);


            path.getElements().addAll(moveTo, first, firstLine, moveForSec, secLine);

            AnchorPane.setLeftAnchor(label, (endX - startX - 60) / 2 - 5);
            AnchorPane.setTopAnchor(label, (startX - endX - 60) / 2 - 10);

        } else if (startX < endX && startY == endY) {
            MoveTo moveTo = new MoveTo(0, 0);

            QuadCurveTo first = new QuadCurveTo();
            first.setX((endX - startX) - 60);
            first.setY(0);
            first.setControlX((endX - startX - 60) / 2);
            first.setControlY(startX - endX + 60);

            LineTo firstLine = new LineTo((endX - startX) - 60, -8);

            MoveTo moveForSec = new MoveTo((endX - startX) - 60, 0);


            LineTo secLine = new LineTo((endX - startX) - 68, -1);


            path.getElements().addAll(moveTo, first, firstLine, moveForSec, secLine);

            AnchorPane.setLeftAnchor(label, (endX - startX - 60) / 2 - 5);
            AnchorPane.setTopAnchor(label, (startX - endX + 60) / 2 - 10);
        }

        path.setStroke(Color.BLACK);
        path.setStrokeWidth(2);


        return pane;
    }
}