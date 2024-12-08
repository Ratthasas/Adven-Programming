package se233.chapter1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import se233.chapter1.Launcher;
import se233.chapter1.model.DamageType;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;

import java.util.ArrayList;

public class AllCustomHandler {
    static Armor LastEquipArmor;
    static Weapon LastEquipWeapon;
    static BasedEquipment Selection;
    public static class GenCharacterHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Launcher.setMainCharacter(GenCharacter.setUpCharacter());
            Launcher.setEquippedArmor(null);
            Launcher.setEquippedWeapon(null);
            Launcher.setAllEquipments(GenItemList.setUpItemList());
            Launcher.refreshPane();
        }
        public static void onDragDetected(MouseEvent event, BasedEquipment equipment, ImageView imgView) {
            Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
            db.setDragView(imgView.getImage());
            ClipboardContent content = new ClipboardContent();
            content.put(equipment.DATA_FORMAT, equipment);
            db.setContent(content);
            Selection = (BasedEquipment) db.getContent(BasedEquipment.DATA_FORMAT);
            LastEquipArmor = Launcher.getEquippedArmor();
            LastEquipWeapon = Launcher.getEquippedWeapon();
            event.consume();
        }

    }

    public static void onDragOver(DragEvent event, String type) {
        Dragboard dragboard = event.getDragboard();
        BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);
        if (dragboard.hasContent(BasedEquipment.DATA_FORMAT) && retrievedEquipment.getClass().getSimpleName().equals(type)) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
    }

    public static void onDragDropped(DragEvent event, Label lbl, StackPane imgGroup) {
        boolean dragCompleted = false;
        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        if (dragboard.hasContent(BasedEquipment.DATA_FORMAT)) {
            BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);
            BasedCharacter character = Launcher.getMainCharacter();
            if (retrievedEquipment.getClass().getSimpleName().equals("Weapon")) {
                if (retrievedEquipment.getName().equals("Gun") || retrievedEquipment.getName().equals("Sword")){
                    if (Launcher.getMainCharacter().getType() != DamageType.magical) {
                        if (Launcher.getEquippedWeapon() != null)
                            allEquipments.add(Launcher.getEquippedWeapon());
                        Launcher.setEquippedWeapon((Weapon) retrievedEquipment);
                        character.equipWeapon((Weapon) retrievedEquipment);
                    }
                } else if ((retrievedEquipment.getName().equals("Battlemage Staff"))) {
                    if (Launcher.getEquippedWeapon() != null)
                        allEquipments.add(Launcher.getEquippedWeapon());
                    Launcher.setEquippedWeapon((Weapon) retrievedEquipment);
                    character.equipWeapon((Weapon) retrievedEquipment);
                } else {
                    if (Launcher.getMainCharacter().getType() != DamageType.physical) {
                        if (Launcher.getEquippedWeapon() != null)
                            allEquipments.add(Launcher.getEquippedWeapon());
                        Launcher.setEquippedWeapon((Weapon) retrievedEquipment);
                        character.equipWeapon((Weapon) retrievedEquipment);
                    }
                }
            } else {
                if (Launcher.getMainCharacter().getType() != DamageType.mixed) {
                    if (Launcher.getEquippedArmor() != null)
                        allEquipments.add(Launcher.getEquippedArmor());
                    Launcher.setEquippedArmor((Armor) retrievedEquipment);
                    character.equipArmor((Armor) retrievedEquipment);
                }
            }
            Launcher.setMainCharacter(character);
            Launcher.setAllEquipments(allEquipments);
            Launcher.refreshPane();
            ImageView imgView = new ImageView();
            if (imgGroup.getChildren().size() != 1) {
                imgGroup.getChildren().remove(1);
                Launcher.refreshPane();
            }
            lbl.setText(retrievedEquipment.getClass().getSimpleName() + ":\n" + retrievedEquipment.getName());
            imgView.setImage(new Image(Launcher.class.getResource(retrievedEquipment.getImagepath()).toString()));
            imgGroup.getChildren().add(imgView);
            dragCompleted = true;
        }
        event.setDropCompleted(dragCompleted);
    }

    public static void onEquipDone(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);
        if (LastEquipArmor == Launcher.getEquippedArmor() && LastEquipWeapon == Launcher.getEquippedWeapon()) {
            allEquipments.add(Selection);
        }
        int pos = -1;
        for (int i = 0; i < allEquipments.size(); i++) {
            if (allEquipments.get(i).getName().equals(retrievedEquipment.getName())) {
                pos = i;
            }
        }
        if (pos != -1) {
            allEquipments.remove(pos);
        }
        Launcher.setAllEquipments(allEquipments);
        Launcher.refreshPane();
    }
}

