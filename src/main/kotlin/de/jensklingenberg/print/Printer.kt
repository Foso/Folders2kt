package de.jensklingenberg.print
import de.jensklingenberg.ast.*


fun printExpr(xExpr: xExpr): String {

    when (xExpr.expressionsType) {
        ExpressionsType.Variable -> {
            val exp = xExpr as VarExpr

            return exp.varName
        }
        ExpressionsType.Add -> TODO()
        ExpressionsType.Subtract -> {
            val exp = xExpr as SubExpr
            return printExpr(exp.leftExpr) + "-" + printExpr(exp.rightExpr) +"\n"
        }
        ExpressionsType.Multiply -> TODO()
        ExpressionsType.Divide -> TODO()
        ExpressionsType.Literal -> {
            val exp = xExpr as LiteralExpr
            return exp.value

        }
        ExpressionsType.Equal -> TODO()
        ExpressionsType.GREATER -> {
            val exp = xExpr as GtExpr

            return printExpr(exp.leftExpr) + ">" + printExpr(exp.rightExpr)
        }

    }


}

fun printCmd(it: Command): String {
    when (it.commandtype) {
        Commandtype.IF -> {}
        Commandtype.WHILE -> {

            val cmd = it as WhileCmd
            val exprText = printExpr(cmd.expr)
            return "while ($exprText) {\n" + cmd.commands.joinToString(separator = "") {
                printCmd(it)
            } + "}"

        }
        Commandtype.DECLARE -> {
            val cmd = it as DeclareCmd
            return "var ${cmd.varName} : ${cmd.types.ktType} \n"
        }

        Commandtype.LET -> {
            val cmd = it as LetCmd
            return cmd.varName+ "="+ printExpr(cmd.expr)
        }
        Commandtype.PRINT -> {
            val cmd = it as PrintCmd
            val isString = cmd.expr is LiteralExpr && cmd.expr.types == Types.STRING

            return when(isString){
                true->{
                    "print(\"" + printExpr(cmd.expr) + "\")\n"
                }
                false->{
                    "print(" + printExpr(cmd.expr) + ")\n"
                }
            }

        }
        Commandtype.INPUT -> {}
    }
    return ""
}