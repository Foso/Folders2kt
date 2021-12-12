package de.jensklingenberg.folders2kt.ast


open class Command


class IfCmd(private val expr: Expr, private val commands: List<Command>) : Command() {
    override fun toString(): String {
        val exprText = expr.toString()
        return "\nif ($exprText) {\n" + commands.joinToString(separator = "") {
            it.toString()
        } + "}\n"
    }
}

class WhileCmd(private val expr: Expr, private val commands: List<Command>) : Command() {
    override fun toString(): String {
        val exprText = expr.toString()
        return "\nwhile ($exprText) {\n" + commands.joinToString(separator = "") {
            it.toString()
        } + "}\n"
    }
}

class InputCmd(private val varName: String) : Command() {
    override fun toString(): String {
        return "var var_$varName = input() \n"
    }
}

class PrintCmd(private val expr: Expr) : Command() {

    override fun toString(): String {
        val isString = expr is LiteralExpr

        return when (isString) {
            true -> {
                "print($expr)\n"
            }
            false -> {
                "print($expr)\n"
            }
        }
    }
}

class DeclareCmd(private val types: Types, private val varName: String) : Command() {
    override fun toString(): String {
        val default: String = when (types) {
            Types.INT -> "0"
            Types.FLOAT -> "0.0f"
            Types.STRING, Types.CHAR -> "\"\""
        }
        return "var $varName : ${types.ktType} = $default \n"
    }
}

class LetCmd(private val varName: String, private val expr: Expr) : Command() {
    override fun toString(): String {
        return "var $varName = $expr \n "
    }
}


enum class CommandType(val index: Int) {
    IF(0), WHILE(1), DECLARE(2), LET(3), PRINT(4), INPUT(5)
}


enum class Types(val index: Int, val ktType: String) {
    INT(0, "Int"), FLOAT(1, "Float"), STRING(2, "String"), CHAR(3, "String")
}