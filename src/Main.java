import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.*;

import java.text.DecimalFormat;
import java.util.*;

import java.util.ArrayList;

public class Main {
    static final ArrayList<wave> alW = new ArrayList<>();
    private static GraphicsContext gc;
    private static Image image;
    private static Timer timer;
    private static Emitter e;
    private static AnimationTimer at;
    private static int windowWidth = 500;
    private static int windowHeight = 500;
    static int count = 0;


    public static void main(String[] args) {
        new JFXPanel();
        Platform.runLater(Main::launch);
        Platform.runLater(Main::control);
    }

    private static void close() {
        timer.cancel();
        System.exit(0);
    }

    private static void control() {
        Stage stage = new Stage();
        stage.setTitle("Control");
        stage.setWidth(200);
        stage.setHeight(500);
        stage.setResizable(false);
        stage.setOnCloseRequest(we -> close());
        stage.getIcons().add(new Image("/tie.png"));
        stage.show();

        Group group = new Group();
        Scene scene = new Scene(group);
        stage.setScene(scene);

        VBox vb = new VBox();
        vb.setSpacing(10);
        group.getChildren().add(vb);

        Button up = new Button("Up!");
        up.setOnAction((ActionEvent) -> e.upSpeed());
        vb.getChildren().add(up);

        Button down = new Button("Down!");
        down.setOnAction((ActionEvent) -> e.downSpeed());
        vb.getChildren().add(down);

        Button pause = new Button("Pause");
        pause.setOnAction((ActionEvent) -> at.stop());
        vb.getChildren().add(pause);

        Button play = new Button("Play");
        play.setOnAction((ActionEvent) -> at.start());
        vb.getChildren().add(play);
    }

    private synchronized static void launch() throws ConcurrentModificationException {
        Stage stage = new Stage();
        stage.setTitle("Sound Wave test");
        stage.setWidth(windowWidth);
        stage.setHeight(windowHeight);
        stage.setResizable(false);
        stage.setOnCloseRequest(we -> close());
        stage.getIcons().add(new Image("/tie.png"));
        stage.show();

        Group group = new Group();
        Scene scene = new Scene(group);
        Canvas canvas = new Canvas();
        canvas.setWidth(windowWidth);
        canvas.setHeight(windowHeight);
        group.getChildren().add(canvas);
        stage.setScene(scene);

        DecimalFormat df = new DecimalFormat("#.00");

        image = new Image("/tie.png");

        VBox vb = new VBox();
        vb.setSpacing(10);
        group.getChildren().add(vb);

        e = new Emitter(3, 50, windowWidth); //Edit the 2nd number to change the frequency

        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.GRAY);
        gc.setFont(new Font("Comic Sans MS", 24));
        int a = (int) Math.floor((1 / e.getFreq()) * 1000);
        timer = new Timer();
        timer.schedule(new ping(e), 0, a);
        at = new AnimationTimer() {
            private long last = 0;

            @Override
            public void handle(long now) {
                if (now - last >= 16_000_000) {
                    gc.fillRect(0, 0, windowWidth, windowHeight);
                    gc.drawImage(image, e.update() - 60, windowHeight / 2 - 60, 120, 120);
                    expandWave();
                    gc.strokeText("Time: " + System.currentTimeMillis(), 50, 34);
                    gc.strokeText("Speed: " + df.format(e.tieSpeed), 50, 94);
                    gc.strokeText("Mach: " + df.format(e.tieSpeed / 343), 50, 114);
                    last = now;
                }
            }
        };
        at.start();
    }

    private synchronized static void expandWave() throws ConcurrentModificationException {

        ArrayList<wave> killList = new ArrayList<>();
        try {
            for (wave w : alW) {
                gc.strokeOval(w.getStartX() - w.getRadius() / 2, (windowHeight / 2) - w.getRadius() / 2, w.getRadius(), w.getRadius());
                w.update();
                if (w.getRadius() >= windowHeight) killList.add(w);
            }
        }catch (ConcurrentModificationException cme) {
            System.out.println("ouch!");
        }

        alW.removeAll(killList);

    }
}


//Stage is 1500m long