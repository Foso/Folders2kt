package de.jensklingenberg

import de.jensklingenberg.objectloader.KtsObjectLoader
import de.jensklingenberg.parser.parseCommands
import de.jensklingenberg.parser.parseLiteral
import java.io.File
import java.util.*

object newCompo : Comparator<File> {
    val NATURAL_SORT = WindowsExplorerComparator()
    override fun compare(p0: File?, p1: File?): Int {
        return NATURAL_SORT.compare(p0!!.getName(), p1!!.getName());
    }
}

object newCompoTest : Comparator<String> {
    val NATURAL_SORT = WindowsExplorerComparator()
    override fun compare(p0: String?, p1: String?): Int {
        return NATURAL_SORT.compare(p0!!, p1!!);
    }

}


fun readRandomValueFromScript(x1: String): Int {
    //println("Hello Kotlin Scripting World")

    val source = """
        import java.util.*
        
        fun input(): String {
            val scanner = Scanner(System.`in`)
            return scanner.next() ?: ""
        }
        
        var var_0 = input() 
        print(var_0)
        
        
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
  //  input()

     // val result = readRandomValueFromScript("hhh")

 //val test=   parseLiteral("/home/jens/Code/2021/FoldersKt/src/fold/NewFolder")

    val cmdfolders =
        File("/home/jens/Code/2021/jk/Folders.py/sample_programs/HelloWorld").listFolders()


    val cmds = cmdfolders.map {
        parseCommands(it)
    }

    val source: String =  cmds.joinToString(separator = "") {
        it.toString()
    }
    val before = source.substringBefore("var_1")
    val test = before +"var_1"+source.substringAfter("var_1").replace("var var_1","var_1")
    println(source)

    val commsource = """
        import java.util.*
        
        fun input(): String {
            val scanner = Scanner(System.`in`)
            return scanner.next() ?: ""
        }
        
        $source
        
         val x = 10; x
    """.trimIndent()
    KtsObjectLoader().load<Int>(commsource)
}


fun File.listFolders(): List<File> {
    val lengthThenNatural = compareBy<File> { it.absolutePath }
        .then(naturalOrder())
    return this.listFiles().filter { it.isDirectory }.sortedWith(newCompo)
}
