package de.jensklingenberg.parser

import de.jensklingenberg.*
import de.jensklingenberg.ast.DeclareCmd
import de.jensklingenberg.ast.ExpressionsType
import de.jensklingenberg.ast.GtExpr
import de.jensklingenberg.ast.LetCmd
import de.jensklingenberg.ast.LiteralExpr
import de.jensklingenberg.ast.PrintCmd
import de.jensklingenberg.ast.SubExpr
import de.jensklingenberg.ast.Types
import de.jensklingenberg.ast.VarExpr
import de.jensklingenberg.ast.WhileCmd
import de.jensklingenberg.ast.*
import de.jensklingenberg.ast.xExpr
import java.io.File


fun parseCommands(it: File): Command {

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
        else -> TODO()
    }



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