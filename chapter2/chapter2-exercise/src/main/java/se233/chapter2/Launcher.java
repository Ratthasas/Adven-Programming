package se233.chapter2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import se233.chapter2.controller.Initialize;
import se233.chapter2.controller.RefreshTask;
import se233.chapter2.model.Currency;
import se233.chapter2.view.CurrencyParentPane;
import se233.chapter2.view.TopPane;

import java.util.List;
import java.util.concurrent.ExecutionException;

//import static jdk.internal.misc.InnocuousThread.newThread;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    private static Stage primaryStage;
    private static FlowPane mainPane;
    private static TopPane topPane;
    private static CurrencyParentPane currencyParentPane;
    private static List<Currency> currencyList;


    public static List<Currency> getCurrencyList() {
        return currencyList;
    }

    public static void setCurrencyList(List<Currency> currencyList) {
        Launcher.currencyList = currencyList;
    }

    @Override
    public void start(Stage stage) throws ExecutionException, InterruptedException {

        primaryStage = stage;

        primaryStage.setTitle("Currency Watcher");
        primaryStage.setResizable(false);
        //System.out.println(FetchData.fetchRange("USD", 6));
        currencyList = Initialize.initializeApp();
        initMainPane();
        Scene mainScene = new Scene(mainPane);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        RefreshTask r = new RefreshTask();
        Thread th= new Thread(r);
        th.setDaemon(true);
        th.start();
    }

    public void initMainPane() throws ExecutionException, InterruptedException {
        mainPane = new FlowPane();
        topPane = new TopPane();
        currencyParentPane = new CurrencyParentPane(currencyList);
        mainPane.getChildren().add(topPane);
        mainPane.getChildren().add(currencyParentPane);
    }
    public static void refreshPane() throws InterruptedException, ExecutionException {
        topPane.refreshPane();
        currencyParentPane.refreshPane(currencyList);
        primaryStage.sizeToScene();
    }
}
