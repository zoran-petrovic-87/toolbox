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

/**
 *
 * @author Zoran Petrović
 */
public class SearchOptions {

    public enum SearchMode {
        EQUALS, CONTAINS, STARTS_WITH, ENDS_WITH
    }

    private SearchMode searchMode;
    private boolean ignoreCase;
    private int searchRow;
    private int searchColumn;

    public SearchOptions(SearchMode searchMode, boolean ignoreCase, int searchRow, int searchColumn) {
        this.searchMode = searchMode;
        this.ignoreCase = ignoreCase;
        this.searchRow = searchRow;
        this.searchColumn = searchColumn;
    }

    public SearchMode getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(SearchMode searchMode) {
        this.searchMode = searchMode;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public int getSearchRow() {
        return searchRow;
    }

    public void setSearchRow(int searchRow) {
        this.searchRow = searchRow;
    }

    public int getSearchColumn() {
        return searchColumn;
    }

    public void setSearchColumn(int searchColumn) {
        this.searchColumn = searchColumn;
    }

}
