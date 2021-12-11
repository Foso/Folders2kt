package de.jensklingenberg

import de.jensklingenberg.objectloader.KtsObjectLoader
import de.jensklingenberg.parser.parseCommands
import java.io.File
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

object newCompo : Comparator<File>{
    val NATURAL_SORT = WindowsExplorerComparator()
    override fun compare(p0: File?, p1: File?): Int {
       return  NATURAL_SORT.compare(p0!!.getName(), p1!!.getName());
    }
}

object newCompoTest : Comparator<String>{
    val NATURAL_SORT = WindowsExplorerComparator()
    override fun compare(p0: String?, p1: String?): Int {
        return  NATURAL_SORT.compare(p0!!, p1!!);
    }

}

object mycompo : Comparator<File> {
    override fun compare(p0: File?, p1: File?): Int {
        val short0 = p0!!.absolutePath.substringAfterLast("/")
        val short1 = p1!!.absolutePath.substringAfterLast("/")
        var pp = -1



        if (short0.length <= short1.length) {
            short0.toCharArray().forEachIndexed { index, c ->
                val secChar = short1.toCharArray()[index]
                if (c < secChar) {
                    pp = 1
                }
            }
        } else {
            short1.toCharArray().forEachIndexed { index, char ->
                val secChar = short0.toCharArray()[index]
                if (char < secChar) {
                    pp = -1
                }
            }
        }


        return pp

    }
}


object mycompo2 : Comparator<String> {
    override fun compare(p0: String?, p1: String?): Int {
        val short0 = p0!!.substringAfterLast("/")
        val short1 = p1!!.substringAfterLast("/")
        var pp = 0

        if (short0.length <= short1.length) {
            short0.toCharArray().forEachIndexed { index, c ->
                val secChar = short1.toCharArray()[index]
                if (c < secChar) {
                    pp = 1
                }
            }
        } else {
            short1.toCharArray().forEachIndexed { index, char ->
                val secChar = short0.toCharArray()[index]
                if (char < secChar) {
                    pp = 1
                }
            }
        }


        return pp

    }
}


fun readRandomValueFromScript(x1: String): Int {
    //println("Hello Kotlin Scripting World")

    val source = """
        println("$x1")
        var test1 = 5

        while(test1>0){
            println(test1.toString()+"Bottles")
            test1 = test1-1
        }
        val x = 10; x
    """.trimIndent()

    return KtsObjectLoader().load(source)
}

fun main() {

    //val result = readRandomValueFromScript("hhh")

    val folders = mutableListOf(
        "/home/jens/Code/2021/jk/Folders.py/sample_programs/HelloWorld/New folder/New folder (2)/New Folder (3)/New Folder (10)",
        "/home/jens/Code/2021/jk/Folders.py/sample_programs/HelloWorld/New folder/New folder (2)/New Folder (3)/New Folder (11)",
       "/home/jens/Code/2021/jk/Folders.py/sample_programs/HelloWorld/New folder/New folder (2)/New Folder (3)/New Folder (12)",
        "/home/jens/Code/2021/jk/Folders.py/sample_programs/HelloWorld/New folder/New folder (2)/New Folder (3)/New Folder (13)",
         "/home/jens/Code/2021/jk/Folders.py/sample_programs/HelloWorld/New folder/New folder (2)/New Folder (3)/New Folder (2)",
        "/home/jens/Code/2021/jk/Folders.py/sample_programs/HelloWorld/New folder/New folder (2)/New Folder (3)/New Folder (3)",
         "/home/jens/Code/2021/jk/Folders.py/sample_programs/HelloWorld/New folder/New folder (2)/New Folder (3)/New Folder (4)",
       "/home/jens/Code/2021/jk/Folders.py/sample_programs/HelloWorld/New folder/New folder (2)/New Folder (3)/New Folder (5)",
        "/home/jens/Code/2021/jk/Folders.py/sample_programs/HelloWorld/New folder/New folder (2)/New Folder (3)/New Folder (6)",
         "/home/jens/Code/2021/jk/Folders.py/sample_programs/HelloWorld/New folder/New folder (2)/New Folder (3)/New Folder (7)",
        "/home/jens/Code/2021/jk/Folders.py/sample_programs/HelloWorld/New folder/New folder (2)/New Folder (3)/New Folder (8)",
         "/home/jens/Code/2021/jk/Folders.py/sample_programs/HelloWorld/New folder/New folder (2)/New Folder (3)/New Folder (9)",
        "/home/jens/Code/2021/jk/Folders.py/sample_programs/HelloWorld/New folder/New folder (2)/New Folder (3)/New folder"
    )

  val test=  folders.sortedWith(newCompoTest)
    val max = folders.maxOf { it.length }

    fun retEmtpy(re:Int):String{
        return (0 until re).joinToString(separator = "") {
            " "
        }
    }

    val newd =folders.map {
        val diff = max-it.length

        it + retEmtpy(diff)

    }


    val cmdfolders =
        File("/home/jens/Code/2021/jk/Folders.py/sample_programs/99Bottles").listFolders()


    val cmds = cmdfolders.map {
        parseCommands(it)
    }

    val source = cmds.joinToString(separator = "") {
       it.toString()
    }
    println(source)
}


fun File.listFolders(): List<File> {
    val lengthThenNatural = compareBy<File> { it.absolutePath }
        .then(naturalOrder())
    return this.listFiles().filter { it.isDirectory }.sortedWith(newCompo)
}
