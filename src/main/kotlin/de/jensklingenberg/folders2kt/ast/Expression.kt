package de.jensklingenberg.folders2kt.ast


open class CompareExpr(
    private val leftExpr: Expression,
    private val rightExpr: Expression,
    private val keyword: String
) : Expression() {
    override fun toString(): String = "$leftExpr$keyword$rightExpr"
}

class GtExpr(leftExpr: Expression, rightExpr: Expression) : CompareExpr(leftExpr, rightExpr, ">")
class LtExpr(leftExpr: Expression, rightExpr: Expression) : CompareExpr(leftExpr, rightExpr, "<")
class EqExpr(leftExpr: Expression, rightExpr: Expression) : CompareExpr(leftExpr, rightExpr, "=")
class SubExpr(leftExpr: Expression, rightExpr: Expression) : CompareExpr(leftExpr, rightExpr, "-")
class MultiplyExpr(leftExpr: Expression, rightExpr: Expression) : CompareExpr(leftExpr, rightExpr, "*")
class DivExpr(leftExpr: Expression, rightExpr: Expression) : CompareExpr(leftExpr, rightExpr, "/")
class AddExpression(leftExpr: Expression, rightExpr: Expression) : CompareExpr(leftExpr, rightExpr, "+")

class VarExpr(private val varName: String) : Expression() {
    override fun toString(): String = "var_$varName"
}

class LiteralExpr(private val types: Types, private val value: String) : Expression() {
    override fun toString(): String {
        return when (types) {
            Types.INT -> {
                value
            }
            Types.FLOAT -> {
                value + "f"
            }
            Types.STRING, Types.CHAR -> {
                "\"" + value + "\""
            }
        }

    }
}

open class Expression

enum class ExpressionsType(val folders: Int) {
    Variable(0), Add(1), Subtract(2), Multiply(3), Divide(4), Literal(5), Equal(6), GREATER(7), LESSTHAN(7)
}