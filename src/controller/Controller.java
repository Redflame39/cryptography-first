package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import model.*;

public class Controller {

    private ToggleGroup outputType = new ToggleGroup();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button encrypt;

    @FXML
    private Button decrypt;

    @FXML
    private ComboBox<String> encryption;

    @FXML
    private TextField key;

    @FXML
    private RadioButton toScreenBtn;

    @FXML
    private RadioButton toFileBtn;

    @FXML
    private TextArea inputText;

    @FXML
    private TextArea outputText;

    @FXML
    void initialize() {
        assert encryption != null : "fx:id=\"encryption\" was not injected: check your FXML file 'sample.fxml'.";
        assert encrypt != null : "fx:id=\"encrypt\" was not injected: check your FXML file 'sample.fxml'.";
        assert key != null : "fx:id=\"key\" was not injected: check your FXML file 'sample.fxml'.";
        assert decrypt != null : "fx:id=\"decrypt\" was not injected: check your FXML file 'sample.fxml'.";
        assert inputText != null : "fx:id=\"inputText\" was not injected: check your FXML file 'sample.fxml'.";
        assert outputText != null : "fx:id=\"outputText\" was not injected: check your FXML file 'sample.fxml'.";
        assert toScreenBtn != null : "fx:id=\"toScreenBtn\" was not injected: check your FXML file 'sample.fxml'.";
        assert toFileBtn != null : "fx:id=\"toFileBtn\" was not injected: check your FXML file 'sample.fxml'.";

        encryption.getItems().addAll("Железнодорожная изгородь", "Шифр Плейфейра", "Шифр Вижинера");

        encryption.setValue("Железнодорожная изгородь");

        toScreenBtn.setToggleGroup(outputType);
        toFileBtn.setToggleGroup(outputType);

        outputType.selectToggle(toScreenBtn);

    }

    @FXML
    private void handleEncrypt() {

        String inputMessage;

        if (outputType.getSelectedToggle() == toFileBtn) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose input file");
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extensionFilter);
            File inputFile = fileChooser.showOpenDialog(Main.getPrimaryStage());
            if (inputFile == null) {
                return;
            }
            FileInput fileInput = new FileInput(inputFile.getPath());
            inputMessage = fileInput.read();
        } else
            inputMessage = inputText.getText();

        String[] fields = checkCorrect(this.key.getText(), inputMessage);

        inputMessage = fields[1];
        String key = fields[0];

        if (inputMessage.isEmpty()) {
            showIsEmptyAlert("исходный текст");
            return;
        }
        if (key.isEmpty()) {
            showIsEmptyAlert("ключ");
            return;
        }

        switch (encryption.getValue()) {
            case "Железнодорожная изгородь":
                if (Integer.parseInt(key) <= 0) {
                    showIsEmptyAlert("ключ не может быть отрицательным и нулем");
                    return;
                }
                RailFenceEncryption railFence = new RailFenceEncryption(inputMessage);
                railFence.encryptRailFence(Integer.parseInt(key));
                writeData(railFence.getEncryptedText(), "output_encrypted.txt");
                break;
            case "Шифр Плейфейра":
                PleifeirEncryption pleifeirEncryption = new PleifeirEncryption(inputMessage);
                pleifeirEncryption.encryptPleifeir(key);
                writeData(pleifeirEncryption.getEncryptedText(), "output_encrypted.txt");
                break;
            case "Шифр Вижинера":
                VigenereEncryption vigenereEncryption = new VigenereEncryption(inputMessage);
                vigenereEncryption.encryptVigenereProgressive(key);
                writeData(vigenereEncryption.getEncryptedText(), "output_encrypted.txt");
                break;
            default:
                break;
        }
    }

    @FXML
    private void handleDecrypt() {

        String inputMessage;

        if (outputType.getSelectedToggle() == toFileBtn) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose input file");
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extensionFilter);
            File inputFile = fileChooser.showOpenDialog(Main.getPrimaryStage());
            if (inputFile == null)
                return;
            FileInput fileInput = new FileInput(inputFile.getPath());
            inputMessage = fileInput.read();
        } else {
            inputMessage = inputText.getText();
        }

        String[] fields = checkCorrect(this.key.getText(), inputMessage);

        inputMessage = fields[1];
        String key = fields[0];

        if (inputMessage.isEmpty()) {
            showIsEmptyAlert("исходный текст");
            return;
        }
        if (key.isEmpty()) {
            showIsEmptyAlert("ключ");
            return;
        }

        switch (encryption.getValue()) {
            case "Железнодорожная изгородь":
                if (Integer.parseInt(key) <= 0) {
                    showIsEmptyAlert("ключ не может быть отрицательным и нулем");
                    return;
                }
                RailFenceEncryption railFence = new RailFenceEncryption(inputMessage);
                railFence.decryptRailFence(Integer.parseInt(key));
                writeData(railFence.getDecryptedText(), "output_decrypted.txt");
                break;
            case "Шифр Плейфейра":
                PleifeirEncryption pleifeirEncryption = new PleifeirEncryption(inputMessage);
                pleifeirEncryption.decryptPleifeir(key);
                writeData(pleifeirEncryption.getDecryptedText(), "output_decrypted.txt");
                break;
            case "Шифр Вижинера":
                VigenereEncryption vigenereEncryption = new VigenereEncryption(inputMessage);
                vigenereEncryption.decryptVigenereProgressive(key);
                writeData(vigenereEncryption.getDecryptedText(), "output_decrypted.txt");
                break;
        }

    }

    private String[] checkCorrect(String key, String message) {
        switch (encryption.getValue()) {
            case "Железнодорожная изгородь":
                key = checkKey(key, "eng", 1);
                message = checkLanguage(message, "eng");
                break;

            case "Шифр Плейфейра":
                key = checkKey(key, "eng", 2);
                message = checkLanguage(message, "eng");
                break;

            case "Шифр Вижинера":
                key = checkKey(key, "rus", 2);
                message = checkLanguage(message, "rus");
                break;
        }
        return new String[]{key, message};
    }

    private String checkKey(String key, String lang, int type) {
        key = transformInputKey(type, key);
        if (type != 1)
            key = checkLanguage(key, lang);
        return key;
    }

    private String checkLanguage(String key, String lang) {
        StringBuilder sb = new StringBuilder();
        switch (lang) {
            case "rus":
                for (int i = 0; i < key.length(); i++) {
                    char ch = key.charAt(i);
                    if ((ch >= 'а' && ch <= 'я') || (ch >= 'А' && ch <= 'Я') || ch == 'ё' || ch == 'Ё') {
                        sb.append(ch);
                    }
                }
                break;

            case "eng":
                for (int i = 0; i < key.length(); i++) {
                    char ch = key.charAt(i);
                    if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                        sb.append(ch);
                    }
                }
                break;
        }
        return sb.toString();
    }

    private String transformInputKey(int type, String key) {
        StringBuilder result = new StringBuilder();
        switch (type) {
            case 1: //set type to 1 if key must be digit-only
                for (int i = 0; i < key.length(); i++) {
                    if (Character.isDigit(key.charAt(i)) || key.charAt(i) == '-')
                        result.append(key.charAt(i));
                }
                break;
            case 2: //set type to 2 if key must be letter-only
                for (int i = 0; i < key.length(); i++) {
                    if (Character.isLetter(key.charAt(i)))
                        result.append(key.charAt(i));
                }
                break;
            default:
                throw new RuntimeException("This shouldn't have happened");
        }
        return result.toString();
    }

    private void writeData(String text, String file) {
        if (outputType.getSelectedToggle() == toFileBtn) {
            FileOutput fileOutput = new FileOutput(file);
            fileOutput.write(text);
        }
        if (outputType.getSelectedToggle() == toScreenBtn) {
            outputText.setText(text);
        }
    }

    private void showIsEmptyAlert(String emptyField) {
        Alert isEmpty = new Alert(Alert.AlertType.INFORMATION);
        String msg = "Одно или несколько полей пусты или введены некорректно: ".concat(emptyField);
        isEmpty.setHeaderText(msg);
        isEmpty.setContentText("Проверьте введенные данные.");

        isEmpty.showAndWait();
    }

}

