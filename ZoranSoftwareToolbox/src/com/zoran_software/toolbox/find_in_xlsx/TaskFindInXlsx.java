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

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Zoran Petrović <zoran at zoran-software.com>
 */
public class TaskFindInXlsx extends Task<List<SearchResult>> {

    final private File directory;
    final private String key;
    final private boolean openFile;
    final private SearchOptions options;
    final private DataFormatter formatter;

    public TaskFindInXlsx(File directory, String key, boolean openFile, SearchOptions options, DataFormatter formatter) {
        this.directory = directory;
        this.key = key;
        this.openFile = openFile;
        this.options = options;
        this.formatter = formatter;
    }

    @Override
    protected List<SearchResult> call() throws IOException, InvalidFormatException {

        List<SearchResult> r = new ArrayList<>();
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().toLowerCase().endsWith(".xlsx")) {
                r.addAll(find(files[i]));
            }
            this.updateProgress(i + 1, files.length);
        }
        return r;
    }

    private List<SearchResult> find(File file) throws IOException, InvalidFormatException {
        List<SearchResult> r = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file);
        int sCount = workbook.getNumberOfSheets();
        SearchOptions o = options;
        String k = key;
        for (int i = 0; i < sCount; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            for (Row row : sheet) {
                for (Cell cell : row) {
                    int iCellRow = cell.getRowIndex();
                    int iCellCol = cell.getColumnIndex();
                    if (o.getSearchRow() == -1 || o.getSearchRow() == iCellRow + 1) {
                        if (o.getSearchColumn() == -1 || o.getSearchColumn() == iCellCol + 1) {
                            String val = formatter.formatCellValue(cell);
                            if (o.isIgnoreCase()) {
                                val = val.toLowerCase();
                                k = k.toLowerCase();
                            }
                            if (o.getSearchMode() == SearchOptions.SearchMode.EQUALS && val.equals(k)) {
                                r.add(new SearchResult(file.getAbsolutePath(), iCellRow, iCellCol));
                            } else if (o.getSearchMode() == SearchOptions.SearchMode.CONTAINS && val.contains(k)) {
                                r.add(new SearchResult(file.getAbsolutePath(), iCellRow, iCellCol));
                            } else if (o.getSearchMode() == SearchOptions.SearchMode.STARTS_WITH && val.startsWith(k)) {
                                r.add(new SearchResult(file.getAbsolutePath(), iCellRow, iCellCol));
                            } else if (o.getSearchMode() == SearchOptions.SearchMode.ENDS_WITH && val.endsWith(k)) {
                                r.add(new SearchResult(file.getAbsolutePath(), iCellRow, iCellCol));
                            }
                        }
                    }
                }
            }
        }
        if (openFile && r.size() > 0) {
            Desktop.getDesktop().open(file);
        }
        return r;
    }

}
