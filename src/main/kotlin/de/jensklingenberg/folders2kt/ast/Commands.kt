package de.jensklingenberg.folders2kt.ast


open class Command


class IfCmd(private val expr: Expression, private val commands: List<Command>) : Command() {
    override fun toString(): String {
        return "\nif ($expr) {\n" + commands.joinToString(separator = "") {
            it.toString()
        } + "}\n"
    }
}

class WhileCmd(private val expr: Expression, private val commands: List<Command>) : Command() {
    override fun toString(): String {
        return "\nwhile ($expr) {\n" + commands.joinToString(separator = "") {
            it.toString()
        } + "}\n"
    }
}

class InputCmd(private val varName: String) : Command() {
    override fun toString(): String {
        return "var var_$varName = input() \n"
    }
}

class PrintCmd(private val expr: Expression) : Command() {
    override fun toString(): String {
        return "print($expr)\n"
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

class LetCmd(private val varName: String, private val expression: Expression) : Command() {
    override fun toString(): String {
        return "var $varName = $expression \n "
    }
}

enum class CommandType(val folders: Int) {
    IF(0), WHILE(1), DECLARE(2), LET(3), PRINT(4), INPUT(5)
}

enum class Types(val index: Int, val ktType: String) {
    INT(0, "Int"), FLOAT(1, "Float"), STRING(2, "String"), CHAR(3, "String")
}