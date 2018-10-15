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

import javafx.concurrent.Task;

/**
 *
 * @author Zoran Petrović <zoran at zoran-software.com>
 */
public class TaskReplaceEach extends Task<String> {

    private final String inputText;
    private final String[] searchList;
    private final String[] replacementList;

    public TaskReplaceEach(String inputText, String[] searchList, String[] replacementList) {
        this.inputText = inputText;
        this.searchList = searchList;
        this.replacementList = replacementList;
    }

    @Override
    protected String call() throws Exception {
        int i = searchList.length;
        int j = replacementList.length;
        if (i != j) {
            return null;
        }
        StringBuilder sb = new StringBuilder(inputText);
        for (int k = 0; k < i; k++) {
            replaceAll(sb, searchList[k], replacementList[k]);
            updateProgress(k + 1, i);
        }
        return sb.toString();
    }

    public static void replaceAll(StringBuilder builder, String from, String to) {
        int index = builder.indexOf(from);
        while (index != -1) {
            builder.replace(index, index + from.length(), to);
            index += to.length(); // Move to the end of the replacement
            index = builder.indexOf(from, index);
        }
    }
}
