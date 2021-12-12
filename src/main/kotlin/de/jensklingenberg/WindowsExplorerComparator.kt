package de.jensklingenberg

import java.util.regex.Pattern

class WindowsExplorerComparator : Comparator<String>{
    val splitPattern = Pattern.compile("\\d+|\\.|\\s");


    override fun compare(p0: String?, p1: String?): Int {
       val i1 = splitStringPreserveDelimiter(p0!!).iterator();
        val i2 = splitStringPreserveDelimiter(p1!!).iterator();

        while (true) {
            //Til here all is equal.
            if (!i1.hasNext() && !i2.hasNext()) {
                return 0;
            }
            //first has no more parts -> comes first
            if (!i1.hasNext() && i2.hasNext()) {
                return -1;
            }
            //first has more parts than i2 -> comes after
            if (i1.hasNext() && !i2.hasNext()) {
                return 1;
            }

            val data1: String = i1.next();
            val data2: String = i2.next();
            var result =0;
            try {
                //If both datas are numbers, then compare numbers

                result =  data1.toLong().compareTo(data2.toLong())
                //If numbers are equal than longer comes first
                if (result == 0) {
                    result = -Integer.compare(data1.length, data2.length);
                }
            } catch ( ex:NumberFormatException) {
                //compare text case insensitive
                result = data1.compareTo(data2,true);
            }

            if (result != 0) {
                return result;
            }
        }
    }


    fun splitStringPreserveDelimiter(str:String): List<String> {
        val matcher = splitPattern.matcher(str);
       val list =  ArrayList<String>();
        var pos = 0
        while (matcher.find()) {
            list.add(str.substring(pos, matcher.start()));
            list.add(matcher.group());
            pos = matcher.end();
        }
        list.add(str.substring(pos));
        return list;
    }



}