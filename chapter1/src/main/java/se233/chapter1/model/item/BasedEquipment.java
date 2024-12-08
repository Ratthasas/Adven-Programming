package se233.chapter1.model.item;

import javafx.scene.input.DataFormat;
import java.io.Serializable;

public class BasedEquipment implements Serializable {
    public static final DataFormat DATA_FORMAT = new DataFormat("src.main.java.se233.chapter1.model.item.BasedEquipment");
    protected String name;
    protected String imgpath;

    // Constructor to initialize name and imgpath
    public BasedEquipment(String name, String imgpath) {
        this.name = name;
        this.imgpath = imgpath;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getImagepath() {
        return imgpath;
    }

    public void setImagepath(String imgpath) {
        this.imgpath = imgpath;
    }

    @Override
    public String toString() {
        return name;
    }
}
