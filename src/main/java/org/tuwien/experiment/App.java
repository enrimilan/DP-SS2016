package org.tuwien.experiment;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.tuwien.experiment.utils.Utils;
import org.tuwien.experiment.entity.Correlation;
import org.tuwien.experiment.entity.Earthquake;
import org.tuwien.experiment.entity.Volcano;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class App extends Application {

    private static ArrayList<Volcano> volcanoes;
    private static ArrayList<Earthquake> earthquakes;
    private static ArrayList<Correlation> correlations;
    private static Button resultButton, volcanoesButton, earthquakesButton;
    private static double maxDist;
    private static String current;

    public static void main(String[] args) {
        if(args.length != 3) {
            throw new IllegalArgumentException("USAGE: experiment VOLCANOES_FILE EARTHQUAKES_FILE MAX_VOLCANO_DIAMETER");
        }
        volcanoes = Utils.parseVolcanoes(args[0]);
        earthquakes = Utils.parseEarthquakes(args[1]);
        maxDist = Double.parseDouble(args[2]);
        correlations = checkForCorrelationBetween(volcanoes, earthquakes, maxDist);
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        // load the map
        WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getClassLoader().getResource("googlemap.html").toString());

        resultButton = new Button("Experiment result");
        resultButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showExperiment(webEngine);
            }
        });

        volcanoesButton = new Button("Volcanoes");
        volcanoesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addVolcanoes(webEngine);
            }
        });
        earthquakesButton = new Button("Earthquakes");
        earthquakesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addEarthquakes(webEngine);
            }
        });

        final Button clear = new Button("Clear map");
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                webEngine.reload();
                current = null;
                resultButton.setDisable(false);
                volcanoesButton.setDisable(false);
                earthquakesButton.setDisable(false);
            }
        });

        final Button screenshot = new Button("Export as image");
        screenshot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WritableImage snapshot = primaryStage.getScene().snapshot(null);
                String filename = "map";
                if(current != null){
                    filename = current;
                }
                File file = new File(filename+".png");
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ToolBar toolBar = new ToolBar();
        toolBar.getItems().addAll(resultButton, volcanoesButton, earthquakesButton, clear, screenshot);

        BorderPane root = new BorderPane();
        root.setCenter(webView);
        root.setTop(toolBar);

        primaryStage.setTitle("Experiment");
        Scene scene = new Scene(root,1000,700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showExperiment(WebEngine webEngine) {
        current = "experiment_result";
        resultButton.setDisable(true);
        volcanoesButton.setDisable(true);
        earthquakesButton.setDisable(true);
        for(Correlation c : correlations) {
            webEngine.executeScript("document.addVolcanoCircle("+c.getVolcano().getLatitude()+", "+c.getVolcano().getLongitude()+", "+maxDist+")");
            for(Earthquake e : c.getEarthquakes()) {
                webEngine.executeScript("document.addBlueDot("+e.getLatitude()+", "+e.getLongitude()+")");
            }
        }
    }

    private void addVolcanoes(WebEngine webEngine) {
        current = "volcanoes";
        resultButton.setDisable(true);
        volcanoesButton.setDisable(true);
        for(Volcano v : volcanoes) {
            webEngine.executeScript("document.addVolcanoMarker("+v.getLatitude()+", "+v.getLongitude()+")");
        }
    }

    private void addEarthquakes(WebEngine webEngine) {
        current = "earthquakes";
        resultButton.setDisable(true);
        earthquakesButton.setDisable(true);
        for(Earthquake e : earthquakes) {
            webEngine.executeScript("document.addEarthquakeData("+e.getLatitude()+", "+e.getLongitude()+")");
        }
        webEngine.executeScript("document.createHeatMapOfEarthquakes()");
    }


    private static ArrayList<Correlation> checkForCorrelationBetween(ArrayList<Volcano> volcanoes, ArrayList<Earthquake> earthquakes, double maxDist) {
        int index = 0;
        ArrayList<Correlation> correlations = new ArrayList<>();
        for(Volcano v : volcanoes) {

            Correlation correlation = new Correlation(v);
            for(Earthquake e : earthquakes) {
                if(Utils.calculateDistanceBetween(v.getLatitude(), v.getLongitude(), e.getLatitude(), e.getLongitude())<=maxDist) {
                    correlation.addDependentEarthquake(e);
                }
            }
            if(correlation.getEarthquakes().size()>0) {
                correlations.add(correlation);
                System.out.println("Correlation: " + correlation.getVolcano().getLocation() + "-> " + correlation.getEarthquakes().size() + " earthquakes");
                index ++;
            }
        }
        System.out.println("Approx " + (double) (index * 100 / volcanoes.size()) + "% of the vulcanoes ("+ index +") depend on earthquakes");
        return correlations;
    }


}