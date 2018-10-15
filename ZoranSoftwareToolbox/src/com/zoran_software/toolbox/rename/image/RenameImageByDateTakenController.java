/*
 * The MIT License
 *
 * Copyright 2014 Zoran Petrović <zoran at zoran-software.com>.
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
package com.zoran_software.toolbox.rename.image;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author Zoran Petrović <zoran at zoran-software.com>
 */
public class RenameImageByDateTakenController implements Initializable {

    private ResourceBundle rb;
    final Preferences pref = Preferences.userRoot().node(this.getClass().getSimpleName());
    final FileChooser fileChooser = new FileChooser();
    final ObservableList format = FXCollections.observableArrayList();
    @FXML
    private TextField txtDateFormat;
    @FXML
    private TextField txtPrefix;
    @FXML
    private TextField txtSuffix;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ListView listViewFiles;
    @FXML
    private Button btnSelectImages;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        fileChooser.setTitle(rb.getString("Select_images"));
        ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                rb.getString("Images"), "*.jpg", "*.jpeg", "*.tif", "*.tiff");
        fileChooser.getExtensionFilters().addAll(extFilter);
        // Load preferences
        txtDateFormat.setText(pref.get("DateFormat", "yyyy-MM-dd HH-mm-ss"));
        txtPrefix.setText(pref.get("Prefix", ""));
        txtSuffix.setText(pref.get("Suffix", ""));
    }

    @FXML
    private void selectPhotos(ActionEvent event) {
        Stage stage = Stage.class.cast(Control.class.cast(event.getSource()).getScene().getWindow());
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        if (list != null) {
            rename(list);
        }
    }

    private void lockUI(boolean lock) {
        txtDateFormat.setDisable(lock);
        txtPrefix.setDisable(lock);
        txtSuffix.setDisable(lock);
        btnSelectImages.setDisable(lock);
    }
    
    private void rename(List<File> list) {
        lockUI(true);
        String dateFormat = txtDateFormat.getText();
        String prefix = txtPrefix.getText();
        String suffix = txtSuffix.getText();
        TaskRenameImageByDateTaken task = new TaskRenameImageByDateTaken(list, dateFormat, prefix, suffix, rb);
        progressBar.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded((WorkerStateEvent wse) -> {
            try {
                listViewFiles.getItems().setAll(task.get());
                // Save preferences
                pref.put("DateFormat", "" + txtDateFormat.getText());
                pref.put("Prefix", "" + txtPrefix.getText());
                pref.put("Suffix", "" + txtSuffix.getText());
            } catch (InterruptedException | ExecutionException ex) {
                listViewFiles.getItems().setAll(ex.getLocalizedMessage());
            } finally {
                lockUI(false);
            }
        });
        Thread t = new Thread(task);
        t.start();
    }

    public void setListeners(Scene scene) {
        scene.setOnDragOver((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            } else {
                event.consume();
            }
        });

        // Dropping over surface
        scene.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                List<File> list = db.getFiles();
                if (list != null) {
                    rename(list);
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
}
