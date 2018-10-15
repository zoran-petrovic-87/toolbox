package com.zoran_software.toolbox.transliteration_of_cyrillic;

import java.util.ArrayList;

/**
 *
 * Copyright 2014 Zoran Petrović (zoran@zoran-software.com)
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
public class TransliterationOfCyrillicTable {

    private final ArrayList<String[]> belarusian;
    private final ArrayList<String[]> bulgarian;
    private final ArrayList<String[]> russian;
    private final ArrayList<String[]> serbian;
    private final ArrayList<String[]> ukrainian;

    TransliterationOfCyrillicTable() {
        belarusian = new ArrayList<>();
        belarusian.add(new String[]{"Щ", "Shch", "щ", "shch"});
        belarusian.add(new String[]{"Ё", "I͡O", "ё", "i͡o"});
        belarusian.add(new String[]{"Ж", "Z͡H", "ж", "z͡h"});
        belarusian.add(new String[]{"Ю", "I͡U", "ю", "i͡u"});
        belarusian.add(new String[]{"Я", "I͡A", "я", "i͡a"});
        belarusian.add(new String[]{"Х", "Kh", "х", "kh"});
        belarusian.add(new String[]{"Ц", "Ts", "ц", "ts"});
        belarusian.add(new String[]{"Ч", "Ch", "ч", "ch"});
        belarusian.add(new String[]{"Ш", "Sh", "ш", "sh"});
        belarusian.add(new String[]{"А", "A", "а", "a"});
        belarusian.add(new String[]{"Б", "B", "б", "b"});
        belarusian.add(new String[]{"В", "V", "в", "v"});
        belarusian.add(new String[]{"Г", "H", "г", "h"});
        belarusian.add(new String[]{"Ґ", "G", "ґ", "g"});
        belarusian.add(new String[]{"Д", "D", "д", "d"});
        belarusian.add(new String[]{"Е", "E", "е", "e"});
        belarusian.add(new String[]{"З", "Z", "з", "z"});
        belarusian.add(new String[]{"І", "I", "і", "i"});
        belarusian.add(new String[]{"Й", "Ĭ", "й", "ĭ"});
        belarusian.add(new String[]{"К", "K", "к", "k"});
        belarusian.add(new String[]{"Л", "L", "л", "l"});
        belarusian.add(new String[]{"М", "M", "м", "m"});
        belarusian.add(new String[]{"Н", "N", "н", "n"});
        belarusian.add(new String[]{"О", "O", "о", "o"});
        belarusian.add(new String[]{"П", "P", "п", "p"});
        belarusian.add(new String[]{"Р", "R", "р", "r"});
        belarusian.add(new String[]{"С", "S", "с", "s"});
        belarusian.add(new String[]{"Т", "T", "т", "t"});
        belarusian.add(new String[]{"У", "U", "у", "u"});
        belarusian.add(new String[]{"Ў", "Ŭ", "ў", "ŭ"});
        belarusian.add(new String[]{"Ф", "F", "ф", "f"});
        belarusian.add(new String[]{"Ы", "Y", "ы", "y"});
        belarusian.add(new String[]{"Ь", "ʹ", "ь", "ʹ"});
        belarusian.add(new String[]{"Э", "Ė", "э", "ė"});

        bulgarian = new ArrayList<>();
        bulgarian.add(new String[]{"Ц", "T͡S", "ц", "t͡s"});
        bulgarian.add(new String[]{"Щ", "Sht", "щ", "sht"});
        bulgarian.add(new String[]{"Ѣ", "I͡E", "ѣ", "i͡e"});
        bulgarian.add(new String[]{"Ю", "I͡U", "ю", "i͡u"});
        bulgarian.add(new String[]{"Я", "I͡A", "я", "i͡a"});
        bulgarian.add(new String[]{"Ж", "Zh", "ж", "zh"});
        bulgarian.add(new String[]{"Х", "Kh", "х", "kh"});
        bulgarian.add(new String[]{"Ч", "Ch", "ч", "ch"});
        bulgarian.add(new String[]{"Ш", "Sh", "ш", "sh"});
        bulgarian.add(new String[]{"А", "A", "а", "a"});
        bulgarian.add(new String[]{"Б", "B", "б", "b"});
        bulgarian.add(new String[]{"В", "V", "в", "v"});
        bulgarian.add(new String[]{"Г", "G", "г", "g"});
        bulgarian.add(new String[]{"Д", "D", "д", "d"});
        bulgarian.add(new String[]{"Е", "E", "е", "e"});
        bulgarian.add(new String[]{"З", "Z", "з", "z"});
        bulgarian.add(new String[]{"И", "I", "и", "i"});
        bulgarian.add(new String[]{"Й", "Ĭ", "й", "ĭ"});
        bulgarian.add(new String[]{"К", "K", "к", "k"});
        bulgarian.add(new String[]{"Л", "L", "л", "l"});
        bulgarian.add(new String[]{"М", "M", "м", "m"});
        bulgarian.add(new String[]{"Н", "N", "н", "n"});
        bulgarian.add(new String[]{"О", "O", "о", "o"});
        bulgarian.add(new String[]{"П", "P", "п", "p"});
        bulgarian.add(new String[]{"Р", "R", "р", "r"});
        bulgarian.add(new String[]{"С", "S", "с", "s"});
        bulgarian.add(new String[]{"Т", "T", "т", "t"});
        bulgarian.add(new String[]{"У", "U", "у", "u"});
        bulgarian.add(new String[]{"Ф", "F", "ф", "f"});
        bulgarian.add(new String[]{"Ъ", "Ŭ", "ъ", "ŭ"});

        russian = new ArrayList<>();
        russian.add(new String[]{"Щ", "Shch", "щ", "shch"});
        russian.add(new String[]{"Ц", "T͡S", "ц", "t͡s"});
        russian.add(new String[]{"Ѣ", "I͡E", "ѣ", "i͡e"});
        russian.add(new String[]{"Ю", "I͡U", "ю", "i͡u"});
        russian.add(new String[]{"Я", "I͡A", "я", "i͡a"});
        russian.add(new String[]{"Ж", "Zh", "ж", "zh"});
        russian.add(new String[]{"Х", "Kh", "х", "kh"});
        russian.add(new String[]{"Ч", "Ch", "ч", "ch"});
        russian.add(new String[]{"Ш", "Sh", "ш", "sh"});
        russian.add(new String[]{"А", "A", "а", "a"});
        russian.add(new String[]{"Б", "B", "б", "b"});
        russian.add(new String[]{"В", "V", "в", "v"});
        russian.add(new String[]{"Г", "G", "г", "g"});
        russian.add(new String[]{"Д", "D", "д", "d"});
        russian.add(new String[]{"Е", "E", "е", "e"});
        russian.add(new String[]{"Ё", "Ë", "ё", "ë"});
        russian.add(new String[]{"З", "Z", "з", "z"});
        russian.add(new String[]{"И", "I", "и", "i"});
        russian.add(new String[]{"І", "Ī", "і", "ī"});
        russian.add(new String[]{"Й", "Ĭ", "й", "ĭ"});
        russian.add(new String[]{"К", "K", "к", "k"});
        russian.add(new String[]{"Л", "L", "л", "l"});
        russian.add(new String[]{"М", "M", "м", "m"});
        russian.add(new String[]{"Н", "N", "н", "n"});
        russian.add(new String[]{"О", "O", "о", "o"});
        russian.add(new String[]{"П", "P", "п", "p"});
        russian.add(new String[]{"Р", "R", "р", "r"});
        russian.add(new String[]{"С", "S", "с", "s"});
        russian.add(new String[]{"Т", "T", "т", "t"});
        russian.add(new String[]{"У", "U", "у", "u"});
        russian.add(new String[]{"Ф", "F", "ф", "f"});
        russian.add(new String[]{"Ъ", "ʺ", "ъ", "ʺ"});
        russian.add(new String[]{"Ы", "Y", "ы", "y"});
        russian.add(new String[]{"Ь", "ʹ", "ь", "ʹ"});
        russian.add(new String[]{"Э", "Ė", "э", "ė"});
        russian.add(new String[]{"Ѳ", "Ḟ", "ѳ", "ḟ"});
        russian.add(new String[]{"Ѵ", "Ẏ", "ѵ", "ẏ"});

        serbian = new ArrayList<>();
        serbian.add(new String[]{"Љ", "Lj", "љ", "lj"});
        serbian.add(new String[]{"Њ", "Nj", "њ", "nj"});
        serbian.add(new String[]{"Џ", "Dž", "џ", "dž"});
        serbian.add(new String[]{"А", "A", "а", "a"});
        serbian.add(new String[]{"Б", "B", "б", "b"});
        serbian.add(new String[]{"В", "V", "в", "v"});
        serbian.add(new String[]{"Г", "G", "г", "g"});
        serbian.add(new String[]{"Д", "D", "д", "d"});
        serbian.add(new String[]{"Ђ", "Đ", "ђ", "đ"});
        serbian.add(new String[]{"Е", "E", "е", "e"});
        serbian.add(new String[]{"Ж", "Ž", "ж", "ž"});
        serbian.add(new String[]{"З", "Z", "з", "z"});
        serbian.add(new String[]{"И", "I", "и", "i"});
        serbian.add(new String[]{"Ј", "J", "ј", "j"});
        serbian.add(new String[]{"К", "K", "к", "k"});
        serbian.add(new String[]{"Л", "L", "л", "l"});
        serbian.add(new String[]{"М", "M", "м", "m"});
        serbian.add(new String[]{"Н", "N", "н", "n"});
        serbian.add(new String[]{"О", "O", "о", "o"});
        serbian.add(new String[]{"П", "P", "п", "p"});
        serbian.add(new String[]{"Р", "R", "р", "r"});
        serbian.add(new String[]{"С", "S", "с", "s"});
        serbian.add(new String[]{"Т", "T", "т", "t"});
        serbian.add(new String[]{"Ћ", "Ć", "ћ", "ć"});
        serbian.add(new String[]{"У", "U", "у", "u"});
        serbian.add(new String[]{"Ф", "F", "ф", "f"});
        serbian.add(new String[]{"Х", "H", "х", "h"});
        serbian.add(new String[]{"Ц", "C", "ц", "c"});
        serbian.add(new String[]{"Ч", "Č", "ч", "č"});
        serbian.add(new String[]{"Ш", "Š", "ш", "š"});

        ukrainian = new ArrayList<>();
        ukrainian.add(new String[]{"Щ", "Shch", "щ", "shch"});
        ukrainian.add(new String[]{"Є", "I͡E", "є", "i͡e"});
        ukrainian.add(new String[]{"Ж", "Z͡H", "ж", "z͡h"});
        ukrainian.add(new String[]{"Ц", "T͡S", "ц", "t͡s"});
        ukrainian.add(new String[]{"Ю", "I͡U", "ю", "i͡u"});
        ukrainian.add(new String[]{"Я", "I͡A", "я", "i͡a"});
        ukrainian.add(new String[]{"Х", "Kh", "х", "kh"});
        ukrainian.add(new String[]{"Ч", "Ch", "ч", "ch"});
        ukrainian.add(new String[]{"Ш", "Sh", "ш", "sh"});
        ukrainian.add(new String[]{"А", "A", "а", "a"});
        ukrainian.add(new String[]{"Б", "B", "б", "b"});
        ukrainian.add(new String[]{"В", "V", "в", "v"});
        ukrainian.add(new String[]{"Г", "H", "г", "h"});
        ukrainian.add(new String[]{"Ґ", "G", "ґ", "g"});
        ukrainian.add(new String[]{"Д", "D", "д", "d"});
        ukrainian.add(new String[]{"Е", "E", "е", "e"});
        ukrainian.add(new String[]{"З", "Z", "з", "z"});
        ukrainian.add(new String[]{"И", "Y", "и", "y"});
        ukrainian.add(new String[]{"І", "I", "і", "i"});
        ukrainian.add(new String[]{"Ї", "Ï", "ї", "ï"});
        ukrainian.add(new String[]{"Й", "Ĭ", "й", "ĭ"});
        ukrainian.add(new String[]{"К", "K", "к", "k"});
        ukrainian.add(new String[]{"Л", "L", "л", "l"});
        ukrainian.add(new String[]{"М", "M", "м", "m"});
        ukrainian.add(new String[]{"Н", "N", "н", "n"});
        ukrainian.add(new String[]{"О", "O", "о", "o"});
        ukrainian.add(new String[]{"П", "P", "п", "p"});
        ukrainian.add(new String[]{"Р", "R", "р", "r"});
        ukrainian.add(new String[]{"С", "S", "с", "s"});
        ukrainian.add(new String[]{"Т", "T", "т", "t"});
        ukrainian.add(new String[]{"У", "U", "у", "u"});
        ukrainian.add(new String[]{"Ф", "F", "ф", "f"});
        ukrainian.add(new String[]{"Ь", "ʹ", "ь", "ʹ"});

    }

    public ArrayList<String[]> getBelarusian() {
        return belarusian;
    }

    public ArrayList<String[]> getBulgarian() {
        return bulgarian;
    }

    public ArrayList<String[]> getRussian() {
        return russian;
    }

    public ArrayList<String[]> getSerbian() {
        return serbian;
    }

    public ArrayList<String[]> getUkrainian() {
        return ukrainian;
    }

}
