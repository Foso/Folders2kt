package de.jensklingenberg.ast


class GtExpr(val leftExpr: Expr, val rightExpr: Expr) : Expr(ExpressionsType.GREATER) {
    override fun toString(): String {
        return "$leftExpr>$rightExpr"
    }
}

class VarExpr(val varName: String) : Expr(ExpressionsType.Variable) {
    override fun toString(): String {
        return varName
    }
}

class SubExpr(private val leftExpr: Expr, private val rightExpr: Expr) : Expr(ExpressionsType.Subtract) {
    override fun toString(): String {
        return "$leftExpr-$rightExpr\n"
    }
}


class LiteralExpr(val types: Types, val value: String) : Expr(ExpressionsType.Literal) {
    override fun toString(): String {
        return value
    }
}

open class Expr(val expressionsType: ExpressionsType)

enum class ExpressionsType(val index: Int) {
    Variable(0), Add(1), Subtract(2), Multiply(3), Divide(4), Literal(5), Equal(6), GREATER(7)
}