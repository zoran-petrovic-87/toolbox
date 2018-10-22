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
package com.zoran_software.toolbox.transliteration_of_cyrillic;

import com.zoran_software.toolbox.UtilityJfxInterface;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Zoran Petrović <zoran at zoran-software.com>
 */
public class TransliterationOfCyrillicController implements Initializable {

    private ResourceBundle rb;
    private final Preferences pref = Preferences.userRoot().node(this.getClass().getSimpleName());
    private final FileChooser fileChooser = new FileChooser();
    private String[] items;
    private final TransliterationOfCyrillicTable transliteratioTable = new TransliterationOfCyrillicTable();
    private ArrayList<String[]> tTable = new ArrayList<>();
    private String[] latin, cyrillic;
    @FXML
    private ComboBox cboTransliterationTable;
    @FXML
    private TextArea txtInput;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button btnOpenTextFile;
    @FXML
    private Button btnSaveTextFile;
    @FXML
    private Button btnToLatin;
    @FXML
    private Button btnToCyrillic;
    @FXML
    private Button btnShowTransliterationTable;
    @FXML
    private Label lblCurrentScript;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        items = new String[]{
            rb.getString("Belarusian"),
            rb.getString("Bulgarian"),
            rb.getString("Russian"),
            rb.getString("Serbian"),
            rb.getString("Ukrainian")
        };
        cboTransliterationTable.getItems().clear();
        cboTransliterationTable.getItems().addAll(FXCollections.observableArrayList(items));
        // Load preferences.
        cboTransliterationTable.getSelectionModel().select(pref.getInt("TransliterationTable", 0));
        eventScriptChanged();
    }

    @FXML
    private void eventScriptChanged() {
        lblCurrentScript.setText(cboTransliterationTable.getSelectionModel().getSelectedItem().toString());
    }

    @FXML
    private void openFile(ActionEvent event) throws IOException {
        Stage stage = Stage.class.cast(Control.class.cast(event.getSource()).getScene().getWindow());
        fileChooser.setTitle(rb.getString("Open_text_file"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            txtInput.setText(
                    new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())), StandardCharsets.UTF_8));
        }
    }

    @FXML
    private void saveAs(ActionEvent event) {
        UtilityJfxInterface.saveAsTxtFile(
                event,
                txtInput.getText(),
                "",
                rb.getString("Info"),
                rb.getString("Saved"),
                "",
                rb.getString("Error"),
                rb.getString("Error"),
                "");
    }

    @FXML
    private void showTransliterationTable(ActionEvent event) {
        StringBuilder sb = new StringBuilder();
        setTransliterationTable();
        int s = tTable.size();
        for (int i = 0; i < s; i++) {
            sb
                    .append(tTable.get(i)[0]).append(" ").append(tTable.get(i)[1]).append(" ")
                    .append(tTable.get(i)[2]).append(" ").append(tTable.get(i)[3]).append("\n");
        }
        txtInput.setText(sb.toString());
    }

    @FXML
    private void convertToLatin() {
        setTransliterationTable();
        lockUI(true);
        final TaskReplaceEach task = new TaskReplaceEach(txtInput.getText(), cyrillic, latin);
        task.setOnSucceeded((WorkerStateEvent wse) -> {
            try {
                txtInput.setText(task.get());
                // Save preferences
                pref.putInt("TransliterationTable", cboTransliterationTable.getSelectionModel().getSelectedIndex());
            } catch (InterruptedException | ExecutionException ex) {
                UtilityJfxInterface.showErrorAlert(
                        rb.getString("Unknown_error"),
                        "",
                        ex.getMessage());
            } finally {
                lockUI(false);
            }
        });
        progressBar.progressProperty().bind(task.progressProperty());
        Thread t = new Thread(task);
        t.start();
    }

    @FXML
    private void convertToCyrillic() {
        setTransliterationTable();
        lockUI(true);
        final TaskReplaceEach task = new TaskReplaceEach(txtInput.getText(), latin, cyrillic);
        task.setOnSucceeded((WorkerStateEvent wse) -> {
            try {
                txtInput.setText(task.get());
                // Save preferences
                pref.putInt("TransliterationTable", cboTransliterationTable.getSelectionModel().getSelectedIndex());
            } catch (InterruptedException | ExecutionException ex) {
                UtilityJfxInterface.showErrorAlert(
                        rb.getString("Unknown_error"),
                        "",
                        ex.getMessage());
            } finally {
                lockUI(false);
            }
        });
        progressBar.progressProperty().bind(task.progressProperty());
        Thread t = new Thread(task);
        t.start();
    }

    private void setTransliterationTable() {
        switch (cboTransliterationTable.getSelectionModel().getSelectedIndex()) {
            case 0:
                tTable = transliteratioTable.getBelarusian();
                break;
            case 1:
                tTable = transliteratioTable.getBulgarian();
                break;
            case 2:
                tTable = transliteratioTable.getRussian();
                break;
            case 3:
                tTable = transliteratioTable.getSerbian();
                break;
            case 4:
                tTable = transliteratioTable.getUkrainian();
                break;
            default:
                break;
        }
        int s = tTable.size();
        latin = new String[2 * s];
        cyrillic = new String[2 * s];
        int k = 0;
        for (int i = 0; i < s; i++) {
            latin[k] = tTable.get(i)[1];
            latin[k + 1] = tTable.get(i)[3];
            cyrillic[k] = tTable.get(i)[0];
            cyrillic[k + 1] = tTable.get(i)[2];
            k += 2;
        }
    }

    private void lockUI(boolean lock) {
        txtInput.setDisable(lock);
        cboTransliterationTable.setDisable(lock);
        btnOpenTextFile.setDisable(lock);
        btnSaveTextFile.setDisable(lock);
        btnToLatin.setDisable(lock);
        btnToCyrillic.setDisable(lock);
        btnShowTransliterationTable.setDisable(lock);
    }

}
