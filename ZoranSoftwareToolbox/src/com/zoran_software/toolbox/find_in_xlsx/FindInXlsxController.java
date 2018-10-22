/*
 * The MIT License
 *
 * Copyright 2016 Zoran Petrović <zoran at zoran-software.com>.
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
package com.zoran_software.toolbox.find_in_xlsx;

import com.zoran_software.toolbox.UtilityJfxInterface;
import com.zoran_software.toolbox.find_in_xlsx.SearchOptions.SearchMode;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;

/**
 *
 * @author Zoran Petrović
 */
public class FindInXlsxController implements Initializable {

    private ResourceBundle rb;
    private final DataFormatter formatter = new DataFormatter(Locale.US);
    private boolean isDesktopSupported;
    private File directory;

    @FXML
    private TextField txtDirectory;
    @FXML
    private TextField txtInput;
    @FXML
    private Button btnRun;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TextArea txtOutput;
    @FXML
    private RadioButton radioBtnModeEqual;
    @FXML
    private RadioButton radioBtnModeContains;
    @FXML
    private RadioButton radioBtnModeStartsWith;
    @FXML
    private RadioButton radioBtnModeEndsWith;
    @FXML
    private CheckBox chkIgnoreCase;
    @FXML
    private TextField txtSearchRow;
    @FXML
    private TextField txtSearchColumn;
    @FXML
    private CheckBox chkOpenFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        isDesktopSupported = Desktop.isDesktopSupported();
        if (isDesktopSupported == false) {
            chkOpenFile.setSelected(false);
            chkOpenFile.setDisable(true);
        }
    }

    @FXML
    private void browseDirectory(ActionEvent event) {
        String selectedDirectory = UtilityJfxInterface.browseForDirectory(event, "Select_directory");
        if (selectedDirectory != null) {
            txtDirectory.setText(selectedDirectory);
        }
    }

    @FXML
    private void saveAs(ActionEvent event) {
        UtilityJfxInterface.saveAsTxtFile(
                event,
                txtOutput.getText(),
                "",
                rb.getString("Info"),
                rb.getString("Saved"),
                "",
                rb.getString("Error"),
                rb.getString("Error"),
                "");
    }

    @FXML
    private void run(ActionEvent event) throws IOException, InvalidFormatException {
        // Get directory.
        directory = new File(txtDirectory.getText());
        if (directory.length() < 1) {
            UtilityJfxInterface.showWarningAlert(
                    rb.getString("Warning"),
                    rb.getString("Please_select_directory"),
                    "");
            return;
        }
        // Get search row constraint.
        int searchRow;
        try {
            if (txtSearchRow.getText().length() == 0) {
                txtSearchRow.setText("-1");
            }
            searchRow = Integer.parseInt(txtSearchRow.getText());
            if (searchRow < 1) {
                searchRow = -1;
            }
        } catch (NumberFormatException ex) {
            UtilityJfxInterface.showErrorAlert(
                    rb.getString("Error"),
                    rb.getString("Error_invalid_number_for_search_row"),
                    ex.getMessage());
            return;
        }
        // Get search column constraint.
        int searchColumn;
        try {
            if (txtSearchColumn.getText().length() == 0) {
                txtSearchColumn.setText("-1");
            }
            searchColumn = Integer.parseInt(txtSearchColumn.getText());
            if (searchColumn < 1) {
                searchColumn = -1;
            }
        } catch (NumberFormatException ex) {
            UtilityJfxInterface.showErrorAlert(
                    rb.getString("Error"),
                    rb.getString("Error_invalid_number_for_search_column"),
                    ex.getMessage());
            return;
        }
        // Get search mode.
        SearchMode searchMode = SearchMode.EQUALS;
        if (radioBtnModeContains.isSelected()) {
            searchMode = SearchMode.CONTAINS;
        } else if (radioBtnModeStartsWith.isSelected()) {
            searchMode = SearchMode.STARTS_WITH;
        } else if (radioBtnModeEndsWith.isSelected()) {
            searchMode = SearchMode.ENDS_WITH;
        }
        // Set search options.
        SearchOptions options = new SearchOptions(searchMode, chkIgnoreCase.isSelected(), searchRow, searchColumn);
        // Lock UI.
        btnRun.setDisable(true);
        // Run task.
        progressBar.progressProperty().unbind();
        TaskFindInXlsx task = new TaskFindInXlsx(
                directory, txtInput.getText(), chkOpenFile.isSelected(), options, formatter);
        progressBar.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded((WorkerStateEvent wse) -> {
            // Unlock UI.
            btnRun.setDisable(false);
            // Get result.
            try {
                List<SearchResult> result = task.get();
                StringBuilder lines = new StringBuilder();
                for (SearchResult line : result) {
                    lines.append(line.toString()).append("\n");
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
                btnRun.setDisable(false);
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

}
