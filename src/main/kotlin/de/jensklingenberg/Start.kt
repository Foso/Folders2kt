package de.jensklingenberg
import de.jensklingenberg.parser.parseCommands
import de.jensklingenberg.print.printCmd
import de.jensklingenberg.objectloader.KtsObjectLoader
import java.io.File

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


fun readRandomValueFromScript(x1:String): Int {
    //println("Hello Kotlin Scripting World")

  val source =  """
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


    val cmdfolders =
        File("/home/jens/Code/2021/jk/Folders.py/sample_programs/99Bottles").listFolders()


    val cmds = cmdfolders.map {
        parseCommands(it)
    }

    cmds.forEach {
        val source=  printCmd(it)
        source.split("/n").forEach {
            println(it)
        }
    }

}







fun File.listFolders(): List<File> {
    val lengthThenNatural = compareBy<File> { it.absolutePath }
        .then(naturalOrder())
    return this.listFiles().filter { it.isDirectory }.sorted()
}
