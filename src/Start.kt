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

fun main() {

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

fun printCmd(it: xCommand): String {
    when (it.command) {
        Commandtype.IF -> {}
        Commandtype.WHILE -> {

            val cmd = it as WhileCmd
            val exprText = printExpr(cmd.expr)
            val tt = "WHILE ($exprText) {\n" + cmd.commands.joinToString(separator = "") {
                printCmd(it)
            } + "END WHILE"
            return tt

        }
        Commandtype.DECLARE -> {
            val cmd = it as DeclareCmd
            return "Declare: ${cmd.varName} : ${cmd.types} \n"

        }

        Commandtype.LET -> {
            val cmd = it as LetCmd

            return cmd.varName + printExpr(cmd.expr)

        }
        Commandtype.PRINT -> {
            val cmd = it as PrintCmd
            return "printLn(" + printExpr(cmd.expr) + "  )\n"
        }
        Commandtype.INPUT -> {}
    }
    return ""
}


fun printExpr(xExpr: xExpr): String {
    when (xExpr.expressionsType) {
        ExpressionsType.Variable -> {
            val exp = xExpr as VarExpr
            val k = "val ${exp.varName} "

            return k
        }
        ExpressionsType.Add -> TODO()
        ExpressionsType.Subtract -> {
            val exp = xExpr as SubExpr


            return printExpr(exp.leftExpr) + "-" + printExpr(exp.rightExpr)
        }
        ExpressionsType.Multiply -> TODO()
        ExpressionsType.Divide -> TODO()
        ExpressionsType.Literal -> {
            val exp = xExpr as LiteralExpr
            return " ${exp.value} : ${exp.types} "

        }
        ExpressionsType.Equal -> TODO()
        ExpressionsType.GREATER -> {
            val exp = xExpr as GtExpr

            return printExpr(exp.leftExpr) + ">" + printExpr(exp.rightExpr)
        }
        ExpressionsType.Emty -> TODO()
    }


}

fun parseCommands(it: File): xCommand {

    val cmd = getCmdType(it.listFolders()[0])
    when (cmd) {
        Commandtype.IF -> TODO()
        Commandtype.WHILE -> {
            val expr = parseExpression(it.listFolders()[1])
            val cmds = it.listFolders()[2].listFolders().map { parseCommands(it) }
            return WhileCmd(expr, cmds)
        }
        Commandtype.DECLARE -> {
            val type = parseType(it.listFolders()[1])
            val varName = it.listFolders()[2].listFolders().size.toString()

            return DeclareCmd(type, "var_$varName")
        }
        Commandtype.LET -> {
            val varName = it.listFolders()[1].listFolders().size.toString()
            val expr = parseExpression(it.listFolders()[2])
            return LetCmd("var_$varName", expr)
        }
        Commandtype.PRINT -> {
            val expr = parseExpression(it.listFolders()[1])
            return PrintCmd(expr)

        }
        Commandtype.INPUT -> TODO()
        null -> TODO()
    }

}


fun parseExpression(file: File): xExpr {
    val expFolder = file.listFolders()[0]
    val expType = ExpressionsType.values().find { it.index == expFolder.listFolders().size }

    when (expType) {
        ExpressionsType.Variable -> {
            var varName = file.listFolders()[1].listFolders().size.toString()
            return VarExpr("var_" + varName)
        }
        ExpressionsType.Add -> TODO()
        ExpressionsType.Subtract -> {
            val leftExpr = parseExpression(file.listFolders()[1])
            val rightExpr = parseExpression(file.listFolders()[2])
            return SubExpr(leftExpr, rightExpr)
        }
        ExpressionsType.Multiply -> TODO()
        ExpressionsType.Divide -> TODO()
        ExpressionsType.Literal -> {
            val litTypeFolder = file.listFolders()[1]
            val litType = parseType(litTypeFolder)
            return when (litType) {
                Types.INT -> {
                    val literalFolder = file.listFolders()[2]
                    val word = parseInt(literalFolder.absolutePath)
                    LiteralExpr(litType, word)
                }
                Types.FLOAT -> TODO()
                Types.STRING -> {
                    val literalFolder = file.listFolders()[2]
                    val word = parseLiteral(literalFolder.absolutePath)
                    LiteralExpr(litType, word)
                }
                Types.CHAR -> TODO()
                null -> TODO()
            }
        }
        ExpressionsType.Equal -> TODO()
        ExpressionsType.GREATER -> {
            val leftExpr = parseExpression(file.listFolders()[1])
            val rightExpr = parseExpression(file.listFolders()[2])
            return GtExpr(leftExpr, rightExpr)
        }
        null -> TODO()
    }

    return EmtyExpr()

}

fun parseType(file: File): Types {
    return Types.values().find { it.index == file.listFolders().size } ?: throw Exception("No type found")
}

fun getCmdType(file: File): Commandtype? {
    return Commandtype.values().find { it.index == file.listFolders().size }
}

fun parseLiteral(path: String): String {
    val lengthThenNatural = compareBy<String> { it.length }
        .then(naturalOrder())

    val folder = File(path).listFolders().map { it.absolutePath }
    val word = folder.map { parseChar(it) }.joinToString(separator = "") { it }
    return word
}

fun parseChar(path: String): String {
    val folder = File(path).listFolders()
    val firstFolder = folder[0].listFolders()

    var binFirst = ""

    firstFolder.forEach {
        binFirst = binFirst + it.listFolders().size.toString()
    }

    val secFolder = folder[1].listFolders()

    var binSec = ""

    secFolder.forEach {
        binSec = binSec + it.listFolders().size.toString()
    }


    val charCode = Integer.parseInt(binFirst + binSec, 2);
    val charVal = charCode.toChar()
    // val str = Character((72)charCode).toString();

    return charVal.toString()
}

fun parseInt(path: String): String {
    val folder = File(path).listFolders()
    val firstFolder = folder[0].listFolders()

    var binFirst = ""

    firstFolder.forEach {
        binFirst = binFirst + it.listFolders().size.toString()
    }

    val secFolder = folder[1].listFolders()

    var binSec = ""

    secFolder.forEach {
        binSec = binSec + it.listFolders().size.toString()
    }



    return Integer.parseInt(binFirst + binSec, 2).toString();
}


fun File.listFolders(): List<File> {
    val lengthThenNatural = compareBy<File> { it.absolutePath }
        .then(naturalOrder())
    return this.listFiles().filter { it.isDirectory }.sorted()
}

enum class Commandtype(val index: Int) {
    IF(0), WHILE(1), DECLARE(2), LET(3), PRINT(4), INPUT(5)
}

enum class ExpressionsType(val index: Int) {
    Variable(0), Add(1), Subtract(2), Multiply(3), Divide(4), Literal(5), Equal(6), GREATER(7), Emty(80)
}

enum class Types(val index: Int) {
    INT(0), FLOAT(1), STRING(2), CHAR(3)
}