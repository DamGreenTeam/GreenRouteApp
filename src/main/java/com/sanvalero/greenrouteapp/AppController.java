package com.sanvalero.greenrouteapp;

import com.sanvalero.greenrouteapp.domain.Location;
import com.sanvalero.greenrouteapp.domain.Road;
import com.sanvalero.greenrouteapp.service.RoadService;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/**
 * Creado por @ author: Pedro Orós
 * el 08/04/2021
 */
public class AppController implements Initializable {

    public TableView<Road> tvData;
    public TableView<Location> tvDataL;
    public ComboBox<String> cbChoose;
    public Label lStatus, lName, lDate, lLength, lToll;
    public ProgressBar pbLoad;
    public WebView wvGraphic;

    private ObservableList<Road> myRoads = FXCollections.observableArrayList();
    private ObservableList<Location> myLocations = FXCollections.observableArrayList();

    private WebEngine engine;

    private RoadService roadService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTableColumns();

        fillTableLColumns();

        String[] selection = new String[]{"Roads", "Locations"};
        cbChoose.setValue("Roads");
        cbChoose.setItems(FXCollections.observableArrayList(selection));

        engine = wvGraphic.getEngine();

        roadService = new RoadService();

        CompletableFuture.runAsync(() -> {
            pbLoad.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loadingRoads();})
                .whenComplete((string, throwable) -> pbLoad.setVisible(false));
    }

    @FXML
    public void start(Event event) {
        tvData.getItems().clear();
        tvDataL.getItems().clear();
        String whatToShow = cbChoose.getValue();

        switch (whatToShow) {
            case "Roads":
                pbLoad.setVisible(true);
                CompletableFuture.runAsync(() -> {
                    pbLoad.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loadingRoads();})
                        .whenComplete((string, throwable) -> pbLoad.setVisible(false));

                break;

            case "Locations":
                engine.load("");
                lName.setText("");
                lDate.setText("");
                lLength.setText("");
                lToll.setText("");

                pbLoad.setVisible(true);
                CompletableFuture.runAsync(() -> {
                    pbLoad.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loadingLocations();})
                        .whenComplete((string, throwable) -> pbLoad.setVisible(false));

                break;
        }
    }

    @FXML
    public void dataDetail() {
        Road road = tvData.getSelectionModel().getSelectedItem();

        String image = road.getImage();
        engine.load(image);

        String name = road.getName();
        String date = road.getBuildDate();
        String length = String.valueOf(road.getLength());
        String option = "";
        option = (road.isToll()) ? "Yes" : "No";

        lName.setText(name);
        lDate.setText(date);
        lLength.setText(length);
        lToll.setText(option);
    }

    public void fillTableColumns() {
        Field[] fields = Road.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("id") || field.getName().equals("image"))
                continue;
            TableColumn<Road, String> column = new TableColumn<>(field.getName());
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tvData.getColumns().add(column);
        }
        tvData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void fillTableLColumns() {
        Field[] fields = Location.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("id"))
                continue;
            TableColumn<Location, String> column = new TableColumn<>(field.getName());
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tvDataL.getColumns().add(column);
        }
        tvDataL.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void loadingRoads() {
        tvData.setItems(myRoads);

        roadService.getAllRoads()
                .flatMap(Observable::from)
                .doOnCompleted(() -> System.out.println("Listado de carreteras descargado"))
                .subscribeOn(Schedulers.from(Executors.newCachedThreadPool()))
                .subscribe(road -> myRoads.add(road));
    }

    public void loadingLocations() {
        tvDataL.setItems(myLocations);

        roadService.getAllLocations()
                .flatMap(Observable::from)
                .doOnCompleted(() -> System.out.println("Listado de localidades descargado"))
                .subscribeOn(Schedulers.from(Executors.newCachedThreadPool()))
                .subscribe(location -> myLocations.add(location));
    }

    public void transitionLabel(int seconds) {
        lStatus.setVisible(true);
        PauseTransition visiblePause = new PauseTransition((Duration.seconds(seconds)));
        visiblePause.setOnFinished(event -> lStatus.setVisible(false));
        visiblePause.play();
    }

    public void loadingWarning() {
        lStatus.setText("Loading Data...");
        transitionLabel(2);
    }
}
