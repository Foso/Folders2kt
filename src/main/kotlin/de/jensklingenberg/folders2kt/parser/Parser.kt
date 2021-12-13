package de.jensklingenberg.folders2kt.parser

import de.jensklingenberg.folders2kt.ast.AddExpression
import de.jensklingenberg.folders2kt.ast.Command
import de.jensklingenberg.folders2kt.ast.CommandType
import de.jensklingenberg.folders2kt.ast.DeclareCmd
import de.jensklingenberg.folders2kt.ast.DivExpr
import de.jensklingenberg.folders2kt.ast.EqExpr
import de.jensklingenberg.folders2kt.ast.Expression
import de.jensklingenberg.folders2kt.ast.ExpressionsType
import de.jensklingenberg.folders2kt.ast.GtExpr
import de.jensklingenberg.folders2kt.ast.IfCmd
import de.jensklingenberg.folders2kt.ast.InputCmd
import de.jensklingenberg.folders2kt.ast.LetCmd
import de.jensklingenberg.folders2kt.ast.LiteralExpr
import de.jensklingenberg.folders2kt.ast.LtExpr
import de.jensklingenberg.folders2kt.ast.MultiplyExpr
import de.jensklingenberg.folders2kt.ast.PrintCmd
import de.jensklingenberg.folders2kt.ast.SubExpr
import de.jensklingenberg.folders2kt.ast.Types
import de.jensklingenberg.folders2kt.ast.VarExpr
import de.jensklingenberg.folders2kt.ast.WhileCmd
import de.jensklingenberg.folders2kt.listFolders
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


fun parseExpression(file: File): Expression {
    val expFolder = file.listFolders()[0]
    val expType = ExpressionsType.values().find { it.folders == expFolder.listFolders().size }
        ?: throw Exception("Expression not found")

    when (expType) {
        ExpressionsType.Variable -> {
            val varName = file.listFolders()[1].listFolders().size.toString()
            return VarExpr(varName)
        }
        ExpressionsType.Add -> {
            val leftExpr = parseExpression(file.listFolders()[1])
            val rightExpr = parseExpression(file.listFolders()[2])
            return AddExpression(leftExpr, rightExpr)
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
                    val word = parseCharr(literalFolder.absolutePath, Types.INT)
                    LiteralExpr(litType, word)
                }
                Types.FLOAT -> {
                    val literalFolder = file.listFolders()[2]
                    val word = parseCharr(literalFolder.absolutePath, Types.FLOAT)
                    LiteralExpr(litType, word)
                }
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


    }


}

fun parseType(file: File): Types {
    return Types.values().find { it.index == file.listFolders().size } ?: throw Exception("No type found")
}

fun getCmdType(file: File): CommandType {
    return CommandType.values().find { it.folders == file.listFolders().size } ?: throw Exception("No cmd found")
}

fun parseLiteral(path: String): String {
    val folder = File(path).listFolders().map { it.absolutePath }
    return folder.map { parseChar(it) }.joinToString(separator = "") { it }
}

fun parseChar(path: String): String {
    val folder = File(path).listFolders()
    val firstFolder = folder[0].listFolders()

    var binFirst = ""

    firstFolder.forEach {
        binFirst += it.listFolders().size.toString()
    }

    val secFolder = folder[1].listFolders()

    var binSec = ""

    secFolder.forEach {
        binSec += it.listFolders().size.toString()
    }


    val charCode = Integer.parseInt(binFirst + binSec, 2);
    val charVal = charCode.toChar()

    return charVal.toString()
}


fun parseCharr(path: String, float: Types): String {
    val folder = File(path).listFolders()
    val firstFolder = folder[0].listFolders()

    var binFirst = ""

    firstFolder.forEach {
        binFirst += it.listFolders().size.toString()
    }

    val secFolder = folder[1].listFolders()

    var binSec = ""

    secFolder.forEach {
        binSec += it.listFolders().size.toString()
    }

    val charCode = Integer.parseInt(binFirst + binSec, 2);

    return when (float) {
        Types.INT, Types.FLOAT -> {
            charCode.toString();
        }
        Types.STRING, Types.CHAR -> {
            val charVal = charCode.toChar()

            return charVal.toString()
        }
    }
}