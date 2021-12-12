package de.jensklingenberg.folders2kt

import de.jensklingenberg.folders2kt.ast.Command
import de.jensklingenberg.folders2kt.objectloader.KtsObjectLoader
import de.jensklingenberg.folders2kt.parser.parseCommands
import java.io.File


fun main(args: Array<String>) {


    val printSource = args.contains("-source")
    val execute = !printSource
    val folderPath = args.get(args.indexOf("-d")+1)
    val cmdfolders =
        File(folderPath).listFolders()

    val commands: List<Command> = cmdfolders.map {
        parseCommands(it)
    }

    var source: String = commands.joinToString(separator = "") {
        it.toString()
    }

    val regx = Regex("(?<=var\\s).*(?=\\s=)")
    regx.findAll(source).toList().map { it.value }.distinct().forEach {
        source = removeVarKeywordAfterFirstUsage(source, it)
    }

    val scriptSource = """
        import java.util.*
        
        fun input(): String {
            val scanner = Scanner(System.`in`)
            return scanner.next() ?: ""
        }
        
        $source
        
         1""".trimIndent()

    if(printSource){
        println(scriptSource.dropLast(1))
    }
    if(execute){
        KtsObjectLoader().load<Int>(scriptSource)
    }
}

/**
 * In Kotlin a variable needs to be declared only once, so we remove every multiple var keyword for a variable
 */
fun removeVarKeywordAfterFirstUsage(source: String, varName: String): String {
    val before = source.substringBefore(varName)
    return before + varName + source.substringAfter(varName).replace("var $varName", varName)
}
