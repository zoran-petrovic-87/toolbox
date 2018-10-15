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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;

/**
 *
 * @author Zoran Petrović <zoran at zoran-software.com>
 */
public class TaskFindOrphanFiles extends Task<List<String>> {

    final private String parentExtension;
    final private List<String> childrenExtensions;
    final private File directory;
    final private boolean allowParentExtInChildName;

    public TaskFindOrphanFiles(String parentExtension, List<String> childrenExtensions, File directory,
            boolean allowParentExtInChildName) {
        this.parentExtension = parentExtension;
        this.childrenExtensions = childrenExtensions;
        this.directory = directory;
        this.allowParentExtInChildName = allowParentExtInChildName;
    }

    @Override
    protected List<String> call() {
        List<String> r = new ArrayList<>();
        List<File> nonParentFiles = new ArrayList<>();
        List<File> parentFiles = new ArrayList<>();
        File[] tempFiles = this.directory.listFiles();

        // Separate parent files from children candidates.
        for (int i = 0; i < tempFiles.length; i++) {
            if (!tempFiles[i].isFile()) {
                continue;
            }
            if (tempFiles[i].getAbsolutePath().toLowerCase().endsWith(this.parentExtension)) {
                parentFiles.add(tempFiles[i]);
            } else {
                for (int j = 0; j < this.childrenExtensions.size(); j++) {
                    if (tempFiles[i].getAbsolutePath().toLowerCase().endsWith(this.childrenExtensions.get(j))) {
                        nonParentFiles.add(tempFiles[i]);
                        break;
                    }
                }
            }

        }

        // Get orphans.
        for (int i = 0; i < nonParentFiles.size(); i++) {
            boolean hasParent = false;
            String nonParentFile = getFileNameWithoutExtension(nonParentFiles.get(i));
            for (int j = 0; j < parentFiles.size(); j++) {
                String parentFile = getFileNameWithoutExtension(parentFiles.get(j));
                String parentFileWithExt = parentFiles.get(j).getName().toLowerCase();
                if (nonParentFile.toLowerCase().equals(parentFile)) {
                    hasParent = true;
                    break;
                } else if (this.allowParentExtInChildName && nonParentFile.toLowerCase().equals(parentFileWithExt)) {
                    hasParent = true;
                    break;
                }
            }
            if (!hasParent) {
                r.add(nonParentFiles.get(i).getAbsolutePath());
            }
            this.updateProgress(i + 1, nonParentFiles.size());
        }
        return r;
    }

    /**
     * Gets the file name without extension.
     *
     * @param file The file.
     * @return The file name without extension.
     */
    public static String getFileNameWithoutExtension(File file) {
        String fileName = file.getName();
        int pos = fileName.lastIndexOf(".");
        if (pos > 0 && pos < (fileName.length() - 1)) {
            fileName = fileName.substring(0, pos);
        }
        return fileName;
    }
}
