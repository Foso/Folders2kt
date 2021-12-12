package de.jensklingenberg.parser

import de.jensklingenberg.ast.AddExpr
import de.jensklingenberg.ast.Command
import de.jensklingenberg.ast.CommandType
import de.jensklingenberg.ast.DeclareCmd
import de.jensklingenberg.ast.DivExpr
import de.jensklingenberg.ast.EqExpr
import de.jensklingenberg.ast.Expr
import de.jensklingenberg.ast.ExpressionsType
import de.jensklingenberg.ast.GtExpr
import de.jensklingenberg.ast.IfCmd
import de.jensklingenberg.ast.InputCmd
import de.jensklingenberg.ast.LetCmd
import de.jensklingenberg.ast.LiteralExpr
import de.jensklingenberg.ast.LtExpr
import de.jensklingenberg.ast.MultiplyExpr
import de.jensklingenberg.ast.PrintCmd
import de.jensklingenberg.ast.SubExpr
import de.jensklingenberg.ast.Types
import de.jensklingenberg.ast.VarExpr
import de.jensklingenberg.ast.WhileCmd
import de.jensklingenberg.listFolders
import java.io.File


fun parseCommands(it: File): Command {
    val cmdFolder = it.listFolders()[0]
    when (getCmdType(cmdFolder)) {
        CommandType.IF -> {
            val exprFolder = it.listFolders()[1]
            val expr = parseExpression(exprFolder)

            val cmdsFolder = it.listFolders()[2]
            val cmds = cmdsFolder.listFolders().map { parseCommands(it) }
            return IfCmd(expr, cmds)
        }
        CommandType.WHILE -> {
            val exprFolder = it.listFolders()[1]
            val expr = parseExpression(exprFolder)

            val cmdsFolder = it.listFolders()[2]
            val cmds = cmdsFolder.listFolders().map { parseCommands(it) }
            return WhileCmd(expr, cmds)
        }
        CommandType.DECLARE -> {
            val type = parseType(it.listFolders()[1])
            val varName = it.listFolders()[2].listFolders().size.toString()

            return DeclareCmd(type, "var_$varName")
        }
        CommandType.LET -> {
            val varName = it.listFolders()[1].listFolders().size.toString()
            val expr = parseExpression(it.listFolders()[2])
            return LetCmd("var_$varName", expr)
        }
        CommandType.PRINT -> {
            val expr = parseExpression(it.listFolders()[1])
            return PrintCmd(expr)

        }
        CommandType.INPUT -> {
            val varName = it.listFolders()[1].listFolders().size.toString()
            return InputCmd(varName)
        }
    }

}


fun parseExpression(file: File): Expr {
    val expFolder = file.listFolders()[0]
    val expType = ExpressionsType.values().find { it.index == expFolder.listFolders().size }

    when (expType) {
        ExpressionsType.Variable -> {
            val varName = file.listFolders()[1].listFolders().size.toString()
            return VarExpr( varName)
        }
        ExpressionsType.Add -> {
            val leftExpr = parseExpression(file.listFolders()[1])
            val rightExpr = parseExpression(file.listFolders()[2])
            return AddExpr(leftExpr, rightExpr)
        }
        ExpressionsType.Subtract -> {
            val leftExpr = parseExpression(file.listFolders()[1])
            val rightExpr = parseExpression(file.listFolders()[2])
            return SubExpr(leftExpr, rightExpr)
        }
        ExpressionsType.Multiply -> {
            val leftExpr = parseExpression(file.listFolders()[1])
            val rightExpr = parseExpression(file.listFolders()[2])
            return MultiplyExpr(leftExpr, rightExpr)
        }
        ExpressionsType.Divide -> {
            val leftExpr = parseExpression(file.listFolders()[1])
            val rightExpr = parseExpression(file.listFolders()[2])
            return DivExpr(leftExpr, rightExpr)
        }
        ExpressionsType.Literal -> {
            val litTypeFolder = file.listFolders()[1]
            return when (val litType = parseType(litTypeFolder)) {
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
                Types.CHAR -> {
                    val literalFolder = file.listFolders()[2]
                    val word = parseChar(literalFolder.absolutePath)
                    LiteralExpr(litType, word)
                }
            }
        }
        ExpressionsType.Equal -> {
            val leftExpr = parseExpression(file.listFolders()[1])
            val rightExpr = parseExpression(file.listFolders()[2])
            return EqExpr(leftExpr, rightExpr)
        }
        ExpressionsType.GREATER -> {
            val leftExpr = parseExpression(file.listFolders()[1])
            val rightExpr = parseExpression(file.listFolders()[2])
            return GtExpr(leftExpr, rightExpr)
        }
        ExpressionsType.LESSTHAN -> {
            val leftExpr = parseExpression(file.listFolders()[1])
            val rightExpr = parseExpression(file.listFolders()[2])
            return LtExpr(leftExpr, rightExpr)
        }
        null -> TODO()
        else -> TODO()
    }


}

fun parseType(file: File): Types {
    return Types.values().find { it.index == file.listFolders().size } ?: throw Exception("No type found")
}

fun getCmdType(file: File): CommandType {
    return CommandType.values().find { it.index == file.listFolders().size }?: throw Exception("No cmd found")
}

fun parseLiteral(path: String): String {
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