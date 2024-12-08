package se233.chapter1.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import se233.chapter1.Launcher;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.Weapon;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static se233.chapter1.controller.AllCustomHandler.onDragDropped;
import static se233.chapter1.controller.AllCustomHandler.onDragOver;

public class EquipPane extends ScrollPane {
    private static final Logger logger = LogManager.getLogger(EquipPane.class);
    private Weapon equippedWeapon;
    private Armor equippedArmor;
    private Button unequipAllButton;

    public EquipPane() {
        setupUI();
    }

    private void setupUI() {
        VBox equipmentInfoPane = new VBox(10);
        equipmentInfoPane.setAlignment(Pos.CENTER);
        equipmentInfoPane.setPadding(new Insets(25, 25, 25, 25));

        Label weaponLbl = new Label("Weapon:");
        Label armorLbl = new Label("Armor:");
        StackPane weaponImgGroup = createImageGroup();
        StackPane armorImgGroup = createImageGroup();

        updateWeaponDisplay(weaponLbl, weaponImgGroup);
        updateArmorDisplay(armorLbl, armorImgGroup);

        weaponImgGroup.setOnDragOver(e -> onDragOver(e, "Weapon"));
        armorImgGroup.setOnDragOver(e -> onDragOver(e, "Armor"));
        weaponImgGroup.setOnDragDropped(e -> onDragDropped(e, weaponLbl, weaponImgGroup));
        armorImgGroup.setOnDragDropped(e -> onDragDropped(e, armorLbl, armorImgGroup));

        unequipAllButton = new Button("Unequip All");
        unequipAllButton.setOnAction(new UnequipAllHandler());

        equipmentInfoPane.getChildren().addAll(weaponLbl, weaponImgGroup, armorLbl, armorImgGroup, unequipAllButton);
        this.setStyle("-fx-background-color: Red;");
        this.setContent(equipmentInfoPane);

        weaponImgGroup.setOnDragDropped(e -> {
            onDragDropped(e, weaponLbl, weaponImgGroup);
            if (equippedWeapon != null) {
                logger.info("Equipped weapon: " + equippedWeapon.getName());
            }
        });
        armorImgGroup.setOnDragDropped(e -> {
            onDragDropped(e, armorLbl, armorImgGroup);
            if (equippedArmor != null) {
                logger.info("Equipped armor: " + equippedArmor.getName());
            }
        });
    }

    private StackPane createImageGroup() {
        StackPane imgGroup = new StackPane();
        ImageView bg = new ImageView(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        imgGroup.getChildren().add(bg);
        return imgGroup;
    }

    private void updateWeaponDisplay(Label weaponLbl, StackPane weaponImgGroup) {
        if (equippedWeapon != null) {
            weaponLbl.setText("Weapon:\n" + equippedWeapon.getName());
            ImageView weaponImg = new ImageView(new Image(Launcher.class.getResource(equippedWeapon.getImagepath()).toString()));
            weaponImgGroup.getChildren().add(weaponImg);
        } else {
            weaponLbl.setText("Weapon:");
        }
    }

    private void updateArmorDisplay(Label armorLbl, StackPane armorImgGroup) {
        if (equippedArmor != null) {
            armorLbl.setText("Armor:\n" + equippedArmor.getName());
            ImageView armorImg = new ImageView(new Image(Launcher.class.getResource(equippedArmor.getImagepath()).toString()));
            armorImgGroup.getChildren().add(armorImg);
        } else {
            armorLbl.setText("Armor:");
        }
    }

    private class UnequipAllHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            unequipAll();
        }
    }

    public void drawPane(Weapon equippedWeapon, Armor equippedArmor) {
        this.equippedWeapon = equippedWeapon;
        this.equippedArmor = equippedArmor;
        setupUI(); // Refresh UI with new equipment
    }

    private void unequipAll() {
        if (Launcher.getEquippedWeapon() != null) {
            Launcher.getMainCharacter().setPower(Launcher.getMainCharacter().getPower() - Launcher.getEquippedWeapon().getPower());
            Launcher.getAllEquipments().add(Launcher.getEquippedWeapon());
            Launcher.setEquippedWeapon(null);
        }
        if (Launcher.getEquippedArmor() != null) {
            Launcher.getMainCharacter().setDefense(Launcher.getMainCharacter().getDefense() - Launcher.getEquippedArmor().getDefense());
            Launcher.getMainCharacter().setResistance(Launcher.getMainCharacter().getResistance() - Launcher.getEquippedArmor().getResistance());
            Launcher.getAllEquipments().add(Launcher.getEquippedArmor());
            Launcher.setEquippedArmor(null);
        }
        drawPane(null, null); // Clear the display
        Launcher.refreshPane(); // Refresh the main pane
    }

    public void onMouseRelease(MouseEvent event) {
        // Your logic to equip the item
        String equippedItem = "Your item"; // Replace with actual item name or reference

        // Log the equipped item
        logger.info("Equipped item: " + equippedItem);
    }
}
