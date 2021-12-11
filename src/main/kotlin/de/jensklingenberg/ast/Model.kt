package de.jensklingenberg.ast


open class Command(val commandtype: Commandtype)

class WhileCmd(val expr: Expr, val commands: List<Command>) : Command(Commandtype.WHILE) {
    override fun toString(): String {
        val exprText = expr.toString()
        return "\nwhile ($exprText) {\n" + commands.joinToString(separator = "") {
            it.toString()
        } + "}\n"
    }
}


class PrintCmd(val expr: Expr) : Command(Commandtype.PRINT) {

    override fun toString(): String {
        val isString = expr is LiteralExpr

        return when (isString) {
            true -> {
                "print(\"$expr\")\n"
            }
            false -> {
                "print($expr)\n"
            }
        }
    }
}

class DeclareCmd(val types: Types, val varName: String) : Command(Commandtype.DECLARE) {
    override fun toString(): String {
        val default :String = when(types){
            Types.INT -> "0"
            Types.FLOAT -> "0.0f"
            Types.STRING -> "\"\""
            Types.CHAR -> "\"\""
        }
        return "var $varName : ${types.ktType} = $default \n"
    }
}

class LetCmd(val varName: String, val expr: Expr) : Command(Commandtype.LET) {
    override fun toString(): String {
        return "$varName=$expr"
    }
}


enum class Commandtype(val index: Int) {
    IF(0), WHILE(1), DECLARE(2), LET(3), PRINT(4), INPUT(5)
}


enum class Types(val index: Int, val ktType: String) {
    INT(0, "Int"), FLOAT(1, "Float"), STRING(2, "String"), CHAR(3, "Char")
}