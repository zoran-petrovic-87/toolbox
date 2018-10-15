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

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.concurrent.Task;

/**
 *
 * @author Zoran Petrović <zoran at zoran-software.com>
 */
public class TaskRenameImageByDateTaken extends Task<ArrayList<String>> {

    final private ResourceBundle rb;
    final private List<File> list;
    final private String dateFormat;
    final private String prefix;
    final private String suffix;

    public TaskRenameImageByDateTaken(
            List<File> list, String dateFormat, String prefix, String suffix, ResourceBundle rb) {
        this.list = list;
        this.dateFormat = dateFormat;
        this.prefix = prefix;
        this.suffix = suffix;
        this.rb = rb;
    }

    @Override
    protected ArrayList<String> call() {
        ArrayList<String> r = new ArrayList<>();
        if (list == null) {
            return r;
        }
        int count = this.list.size();
        SimpleDateFormat sdf = new SimpleDateFormat(this.dateFormat);
        for (int i = 0; i < count; i++) {
            File f = this.list.get(i);
            try {
                Metadata metadata = ImageMetadataReader.readMetadata(f);
                // Obtain the Exif directory.
                ExifSubIFDDirectory directory = metadata.getDirectory(ExifSubIFDDirectory.class);
                if (directory != null) {
                    // Query the tag value.
                    Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                    if (date != null) {
                        String newName = prefix + sdf.format(date) + suffix;
                        renameFile(f, newName);
                        r.add(f.getAbsolutePath() + " -> " + newName);
                    } else { // Error parsing date.
                        r.add(rb.getString("Error_parsing_date") + " : " + f.getAbsolutePath());
                    }
                } else { // No EXIF data.
                    r.add(rb.getString("Error_No_EXIF_data") + " : " + f.getAbsolutePath());
                }
            } catch (ImageProcessingException | IOException ex) {
                r.add(rb.getString("Error_with_colon") + " : " + f.getAbsolutePath() + " " + ex.getMessage());
            } finally {
                this.updateProgress(i + 1, count);
            }
        }

        return r;
    }

    /**
     * Renames the file.
     *
     * @param f The file.
     * @param newName The new name.
     * @return <c>true</c> if rename was successful.
     * @throws IOException Thrown when file already exists.
     */
    private boolean renameFile(File f, String newName) throws IOException {
        String name = f.getName();
        String newFilePath = f.getParent() + File.separatorChar + newName + name.substring(name.lastIndexOf("."));
        File newFile = new File(newFilePath);
        if (!newFile.exists()) {
            return f.renameTo(newFile);
        } else {
            throw new java.io.IOException(rb.getString("File_already_exists") + " (" + newFile.getName() + ")");
        }
    }
}
