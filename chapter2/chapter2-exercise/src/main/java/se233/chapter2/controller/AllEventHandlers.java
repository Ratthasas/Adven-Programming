package se233.chapter2.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import se233.chapter2.Launcher;
import se233.chapter2.model.Currency;
import se233.chapter2.model.CurrencyEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class AllEventHandlers {




    public static void onRefresh() {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            for(int i = 0 ; i < currencyList.size() ; i++) {
                if (currencyList.get(i).getEdited() ) {
                    currencyList.get(i).setEdited(false);
                    currencyList.get(i).getCurrent().setRate(currencyList.get(i).getCurrent().getInitialRate());
                    Launcher.setCurrencyList(currencyList);
                }
            }
            Launcher.refreshPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onAdd() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Currency");
            dialog.setContentText("Currency code:");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            Optional<String> code = dialog.showAndWait();
            if (code.isPresent()) {
                List<Currency> currencyList = Launcher.getCurrencyList();
                Currency c = new Currency(code.get());
                List<CurrencyEntity> cList = FetchData.fetchRange(c.getShortCode(), 30);
                c.setHistorical(cList);
                c.setCurrent(cList.get(cList.size() - 1));
                currencyList.add(c);
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e){

        }
    }

    public static void onDelete(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index =-1;
            for(int i=0 ; i<currencyList.size() ; i++) {
                if (currencyList.get(i).getShortCode().equals(code) ) {
                    index = i;
                    break;
                }
            }
            if (index !=-1) {
                currencyList.remove(index);
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void onWatch(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index =-1;
            for(int i = 0 ; i < currencyList.size() ; i++) {
                if (currencyList.get(i).getShortCode().equals(code) ) {
                    index = i;
                    break;
                }
            }
            if (index !=-1) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add Watch");
                dialog.setContentText("Rate:");
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                Optional<String> retrievedRate = dialog.showAndWait();
                if (retrievedRate.isPresent()){
                    double rate = Double.parseDouble(retrievedRate.get());
                    currencyList.get(index).setWatch(true);
                    currencyList.get(index).setWatchRate(rate);
                    Launcher.setCurrencyList(currencyList);
                    Launcher.refreshPane();
                }
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();

        }
    }

    public static void unWatch(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index =-1;
            for(int i = 0 ; i < currencyList.size() ; i++) {
                if (currencyList.get(i).getShortCode().equals(code) ) {
                    index = i;
                    break;
                }
            }
            if (index !=-1) {
                    currencyList.get(index).setWatch(false);
                    currencyList.get(index).setWatchRate(0.0);
                    Launcher.setCurrencyList(currencyList);
                    Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void onSetUpValue(){
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Set Base");
            dialog.setContentText("Currency code:");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            Optional<String> code = dialog.showAndWait();
            if (code.isPresent() && !code.get().toLowerCase().equals(FetchData.getBase())) {
                List<Currency> currencyList = Launcher.getCurrencyList();
                ArrayList<String> c = new ArrayList<>();
                Currency base = new Currency(code.get());
                FetchData.setBase(base.getShortCode());
                for(Currency i : Launcher.getCurrencyList()) {
                    c.add(i.getShortCode());
                }
                for(String i : c) {
                    AllEventHandlers.onDelete(i);
                    if(!i.equals(base.getShortCode())) {
                        Currency cu = new Currency(i);
                        List<CurrencyEntity> cList = FetchData.fetchRange(cu.getShortCode(), 30);
                        cu.setHistorical(cList);
                        cu.setCurrent(cList.get(cList.size() - 1));
                        currencyList.add(cu);
                    }
                }
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e){

        }
    }


}

