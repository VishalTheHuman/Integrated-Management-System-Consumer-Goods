package com.example.jfk;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Slot {

    public static final String BREAKFAST_SLOT_PATH = "../data/Breakfast_Slot.txt";
    public static final String LUNCH_SLOT_PATH = "../data/Lunch_Slot.txt";
    public static final String DINNER_SLOT_PATH = "../data/Dinner_Slot.txt";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea breakfastSlot;

    @FXML
    private CheckBox breakfastchk;

    @FXML
    private TextArea dinnerSlot;

    @FXML
    private CheckBox dinnerchk;

    @FXML
    private TextField itemName;

    @FXML
    private TextArea lunchSlot;

    @FXML
    private CheckBox lunchchk;

    @FXML
    void add(ActionEvent event) {
        if(!itemName.getText().equals("")){
            String item = itemName.getText();
            if (breakfastchk.isSelected()) {
                appendToFile(BREAKFAST_SLOT_PATH, item);
                breakfastchk.setSelected(false);
            }

            if (lunchchk.isSelected()) {
                appendToFile(LUNCH_SLOT_PATH, item);
                lunchchk.setSelected(false);
            }

            if (dinnerchk.isSelected()) {
                appendToFile(DINNER_SLOT_PATH, item);
                dinnerchk.setSelected(false);
            }
        }
        getAllText();
        itemName.setText("");
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Menu.fxml");
    }

    @FXML
    void clear(ActionEvent event) {
        if (breakfastchk.isSelected()) {
            clearFile(BREAKFAST_SLOT_PATH);
            breakfastchk.setSelected(false);
        }
        if (lunchchk.isSelected()) {
            clearFile(LUNCH_SLOT_PATH);
            lunchchk.setSelected(false);
        }
        if (dinnerchk.isSelected()) {
            clearFile(DINNER_SLOT_PATH);
            dinnerchk.setSelected(false);
        }
        getAllText();
        itemName.setText("");
    }

    @FXML
    void remove(ActionEvent event) {
        if(!itemName.getText().equals("")){
            String item = itemName.getText();
            if (breakfastchk.isSelected()) {
                removeFromFile(BREAKFAST_SLOT_PATH, item);
                breakfastchk.setSelected(false);
            }
            if (lunchchk.isSelected()) {
                removeFromFile(LUNCH_SLOT_PATH, item);
                lunchchk.setSelected(false);
            }
            if (dinnerchk.isSelected()) {
                removeFromFile(DINNER_SLOT_PATH, item);
                dinnerchk.setSelected(false);
            }
        }
        getAllText();
        itemName.setText("");
    }

    @FXML
    void getAllText() {
        String text = readFromFile(BREAKFAST_SLOT_PATH);
        breakfastSlot.setText(text);
        String text1 = readFromFile(LUNCH_SLOT_PATH);
        lunchSlot.setText(text1);
        String text2 = readFromFile(DINNER_SLOT_PATH);
        dinnerSlot.setText(text2);
    }

    private String readFromFile(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    void initialize() {
        assert breakfastSlot != null : "fx:id=\"breakfastSlot\" was not injected: check your FXML file 'Slot.fxml'.";
        assert breakfastchk != null : "fx:id=\"breakfastchk\" was not injected: check your FXML file 'Slot.fxml'.";
        assert dinnerSlot != null : "fx:id=\"dinnerSlot\" was not injected: check your FXML file 'Slot.fxml'.";
        assert dinnerchk != null : "fx:id=\"dinnerchk\" was not injected: check your FXML file 'Slot.fxml'.";
        assert itemName != null : "fx:id=\"itemName\" was not injected: check your FXML file 'Slot.fxml'.";
        assert lunchSlot != null : "fx:id=\"lunchSlot\" was not injected: check your FXML file 'Slot.fxml'.";
        assert lunchchk != null : "fx:id=\"lunchchk\" was not injected: check your FXML file 'Slot.fxml'.";
        getAllText();
    }

    private void appendToFile(String filePath, String content) {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(content + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFile(String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getAllText();
    }

    private void removeFromFile(String filePath, String lineToRemove) {
        try {
            Path path = Path.of(filePath);
            List<String> fileLines = Files.readAllLines(path);
            fileLines.removeIf(line -> line.equals(lineToRemove));
            Files.write(path, fileLines, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
