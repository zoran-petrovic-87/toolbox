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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;

/**
 *
 * @author Zoran Petrović <zoran at zoran-software.com>
 */
public class MainController implements Initializable {

    private ResourceBundle rb;

    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
    }

    @FXML
    private void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void showRenameImageByDateTaken() throws IOException {
        String sRb = "com/zoran_software/toolbox/rename/image/bundles/lang";
        String fxml = "rename/image/RenameImageByDateTaken.fxml";
        String title = rb.getString("menu.rename_images_using_date_taken");
        ResourceBundle tabRb = ResourceBundle.getBundle(sRb);
        UtilityJfxInterface.addNewTab(fxml, tabRb, title, tabPane, false);
    }

    @FXML
    private void showRemoveOrphanFiles() throws IOException {
        String sRb = "com/zoran_software/toolbox/remove_orphan_files/bundles/lang";
        String fxml = "remove_orphan_files/RemoveOrphanFiles.fxml";
        String title = rb.getString("menu.remove_orphan_files");
        ResourceBundle tabRb = ResourceBundle.getBundle(sRb);
        UtilityJfxInterface.addNewTab(fxml, tabRb, title, tabPane, false);
    }

    @FXML
    private void showTransliterationOfCyrillic() throws IOException {
        String sRb = "com/zoran_software/toolbox/transliteration_of_cyrillic/bundles/lang";
        String fxml = "transliteration_of_cyrillic/TransliterationOfCyrillic.fxml";
        String title = rb.getString("menu.transliteration_of_cyrillic");
        ResourceBundle tabRb = ResourceBundle.getBundle(sRb);
        UtilityJfxInterface.addNewTab(fxml, tabRb, title, tabPane, false);
    }

    @FXML
    private void showAbout() {
        String version = getClass().getPackage().getImplementationVersion();
        UtilityJfxInterface.showInfoAlert(
                rb.getString("menu.help.about"),
                "Zoran Software Toolbox v" + version,
                "Author: Zoran Petrović\nEmail: zoran@zoran-software.com\nLicense: MIT");
    }

}
