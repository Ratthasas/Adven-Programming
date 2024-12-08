package se233.chapter2.controller.draw;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import se233.chapter2.controller.AllEventHandlers;
import se233.chapter2.model.Currency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class DrawTopAreaTask implements Callable<Pane> {

    private Currency currency;
    private Button watch;
    private Button delete;
    private Button unWatch;
    private  Button SetUp;


    public DrawTopAreaTask(Currency currency) {
        this.watch = new Button("Watch");
        this.unWatch = new Button("UnWatch");
        this.SetUp = new Button("Set Base");
        this.delete = new Button("Delete");
        this.watch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onWatch(currency.getShortCode());
            }
        });
        this.delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onDelete(currency.getShortCode());
            }
        });
        this.unWatch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.unWatch(currency.getShortCode());
            }
        });
        this.SetUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onSetUpValue();
            }
        });
    }

    @Override
    public Pane call() throws Exception {
        HBox topArea = new HBox(10);
        topArea.setPadding(new Insets(5));
        topArea.getChildren().addAll(watch,unWatch,SetUp,delete);
        ((HBox) topArea).setAlignment(Pos.CENTER_RIGHT);
        return topArea;
    }
}