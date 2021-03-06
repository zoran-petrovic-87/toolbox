/*
 * The MIT License
 *
 * Copyright 2018 Zoran Petrović <zoran at zoran-software.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.zoran_software.toolbox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Zoran Petrović <zoran at zoran-software.com>
 */
public class UtilityJfxInterface {

    /**
     * Shows the directory chooser.
     *
     * @param event The action event.
     * @param directoryChooserTitle The directory chooser title.
     * @return The directory absolute path.
     */
    public static String browseForDirectory(ActionEvent event, String directoryChooserTitle) {
        Stage stage = Stage.class.cast(Control.class.cast(event.getSource()).getScene().getWindow());
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(directoryChooserTitle);
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            return selectedDirectory.getAbsolutePath();
        }
        return null;
    }

    /**
     *
     * @param event The action event.
     * @param text The text to save.
     * @param fileChooserTitle The file chooser title.
     * @param alertSuccessTitle The success alert title.
     * @param alertSuccessHeader The success alert header.
     * @param alertSuccessContent The success alert content.
     * @param alertErrorTitle The error alert title.
     * @param alertErrorHeader The error alert header.
     * @param alertErrorContent The error alert content.
     * @return The file absolute path.
     */
    public static String saveAsTxtFile(
            ActionEvent event,
            String text,
            String fileChooserTitle,
            String alertSuccessTitle,
            String alertSuccessHeader,
            String alertSuccessContent,
            String alertErrorTitle,
            String alertErrorHeader,
            String alertErrorContent
    ) {
        Stage stage = Stage.class.cast(Control.class.cast(event.getSource()).getScene().getWindow());
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle(fileChooserTitle);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                BufferedWriter bw = Files.newBufferedWriter(
                        Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
                bw.write(text);
                bw.close();
                UtilityJfxInterface.showInfoAlert(
                        alertSuccessTitle,
                        alertSuccessHeader,
                        alertSuccessContent);
            } catch (IOException ex) {
                UtilityJfxInterface.showErrorAlert(
                        alertErrorTitle,
                        alertErrorHeader,
                        alertErrorContent + "\n\n" + ex.getMessage());
            }
            return file.getAbsolutePath();
        }
        return null;
    }

    /**
     * Shows an error alert.
     *
     * @param title The title.
     * @param header The header text.
     * @param content The content.
     */
    public static void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Shows a warning alert.
     *
     * @param title The title.
     * @param header The header text.
     * @param content The content.
     */
    public static void showWarningAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Shows an info alert.
     *
     * @param title The title.
     * @param header The header text.
     * @param content The content.
     */
    public static void showInfoAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Adds a new tab to the specified tab pane.
     *
     * @param fxml The FXML resource.
     * @param rb The resource bundle.
     * @param title The tab title.
     * @param tabPane The tab pane.
     * @param unique If <code>true</code> new tab will not be created if a tab with the same title already exists.
     * @throws IOException
     */
    public static void addNewTab(String fxml, ResourceBundle rb, String title, TabPane tabPane, boolean unique)
            throws IOException {
        if (unique) {
            for (Tab tab : tabPane.getTabs()) {
                if (tab.getText().equals(title)) {
                    return;
                }
            }
        }
        FXMLLoader fxmlLoader = new FXMLLoader(UtilityJfxInterface.class.getResource(fxml), rb);
        AnchorPane ap = (AnchorPane) fxmlLoader.load();
        Tab tab = new Tab();
        tab.setText(title);
        tab.setContent(ap);
        tabPane.getTabs().add(tab);
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(tab);
    }
}
