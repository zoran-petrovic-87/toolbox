/*
 * The MIT License
 *
 * Copyright 2017 Zoran Petrović <zoran at zoran-software.com>.
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
package com.zoran_software.toolbox.remove_orphan_files;

import com.zoran_software.toolbox.UtilityJfxInterface;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Zoran Petrović <zoran at zoran-software.com>
 */
public class RemoveOrphanFilesController implements Initializable {

    private ResourceBundle rb;
    private String parentExtension;
    private List<String> childrenExtensions;
    private File directory;
    private boolean allowParentExtInChildName;
    @FXML
    private Button btnFind;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtDirectory;
    @FXML
    private TextField txtParentExtension;
    @FXML
    private TextField txtChildrenExtensions;
    @FXML
    private CheckBox chkAllowParentExtInChildName;
    @FXML
    private TextArea txtOutput;
    @FXML
    private ProgressBar progressBarFind;
    @FXML
    private ProgressBar progressBarDelete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
    }

    @FXML
    private void find(ActionEvent event) {
        // Get directory.
        directory = new File(txtDirectory.getText());
        // Get parent extension.
        parentExtension = txtParentExtension.getText().toLowerCase();
        if (!parentExtension.startsWith(".")) {
            parentExtension = "." + parentExtension;
        }
        if (parentExtension.length() < 2) {
            UtilityJfxInterface.showWarningAlert(
                    rb.getString("Warning"),
                    rb.getString("Please_enter_parent_extension"),
                    "");
            return;
        }
        // Get children extensions.
        childrenExtensions = new ArrayList<>();
        String[] tempChildrenExtensions = txtChildrenExtensions.getText().split(",");
        for (int i = 0; i < tempChildrenExtensions.length; i++) {
            tempChildrenExtensions[i] = tempChildrenExtensions[i].toLowerCase().trim();
            if (tempChildrenExtensions[i].length() < 1) {
                continue;
            }
            if (!tempChildrenExtensions[i].startsWith(".")) {
                tempChildrenExtensions[i] = "." + tempChildrenExtensions[i];
            }
            childrenExtensions.add(tempChildrenExtensions[i]);
        }
        if (childrenExtensions.size() < 1) {
            UtilityJfxInterface.showWarningAlert(
                    rb.getString("Warning"),
                    rb.getString("Please_enter_at_least_one_children_extension"),
                    "");
            return;
        }
        // Get if is the parent extension in child name selection allowed.
        allowParentExtInChildName = chkAllowParentExtInChildName.isSelected();
        // Lock UI.
        btnFind.setDisable(true);
        btnDelete.setDisable(true);
        // Run task.
        progressBarFind.progressProperty().unbind();
        TaskFindOrphanFiles task = new TaskFindOrphanFiles(
                parentExtension, childrenExtensions, directory, allowParentExtInChildName);
        progressBarFind.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded((WorkerStateEvent wse) -> {
            // Unlock UI.
            btnFind.setDisable(false);
            btnDelete.setDisable(false);
            // Get result.
            try {
                List<String> result = task.get();
                StringBuilder lines = new StringBuilder();
                for (String line : result) {
                    lines.append(line).append("\n");
                }
                txtOutput.setText(lines.toString());
                UtilityJfxInterface.showInfoAlert(
                        rb.getString("Info"),
                        rb.getString("Done"),
                        "");
            } catch (InterruptedException | ExecutionException ex) {
                UtilityJfxInterface.showErrorAlert(
                        rb.getString("Unknown_error"),
                        "",
                        ex.getMessage());
            }
        });
        task.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnFind.setDisable(false);
                btnDelete.setDisable(false);
                Exception ex = (Exception) newValue;
                UtilityJfxInterface.showErrorAlert(
                        rb.getString("Unknown_error"),
                        "",
                        ex.getMessage());
            }
        });
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void delete(ActionEvent event) {
        // Get files to delete.
        if (txtOutput.getText().length() < 1) {
            UtilityJfxInterface.showWarningAlert(
                    rb.getString("Warning"),
                    rb.getString("You_need_to_find_files"),
                    "");
            return;
        }
        String[] files = txtOutput.getText().split("\n");
        // Lock UI.
        btnFind.setDisable(true);
        btnDelete.setDisable(true);
        // Run task.
        progressBarDelete.progressProperty().unbind();
        TaskDeleteFiles task = new TaskDeleteFiles(files);
        progressBarDelete.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded((WorkerStateEvent wse) -> {
            // Unlock UI.
            btnFind.setDisable(false);
            btnDelete.setDisable(false);
            // Get result.
            try {
                task.get();
                UtilityJfxInterface.showInfoAlert(
                        rb.getString("Info"),
                        rb.getString("Done"),
                        "");
            } catch (InterruptedException | ExecutionException ex) {
                UtilityJfxInterface.showErrorAlert(
                        rb.getString("Unknown_error"),
                        "",
                        ex.getMessage());
            }
        });
        task.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnFind.setDisable(false);
                btnDelete.setDisable(false);
                Exception ex = (Exception) newValue;
                UtilityJfxInterface.showErrorAlert(
                        rb.getString("Unknown_error"),
                        "",
                        ex.getMessage());
            }
        });
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void browseDirectory(ActionEvent event) {
        String selectedDirectory = UtilityJfxInterface.browseForDirectory(event);
        if (selectedDirectory != null) {
            txtDirectory.setText(selectedDirectory);
        }
    }

}
