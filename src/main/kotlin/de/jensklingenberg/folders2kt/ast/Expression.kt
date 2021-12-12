package de.jensklingenberg.folders2kt.ast


class GtExpr(private val leftExpr: Expr, private val rightExpr: Expr) : Expr() {
    override fun toString(): String {
        return "$leftExpr>$rightExpr"
    }
}

/**
 * Second and third folders hold expressions to compare
 */
class LtExpr(private val leftExpr: Expr, private val rightExpr: Expr) : Expr() {
    override fun toString(): String {
        return "$leftExpr<$rightExpr"
    }
}

class EqExpr(private val leftExpr: Expr, private val rightExpr: Expr) : Expr() {
    override fun toString(): String {
        return "$leftExpr==$rightExpr"
    }
}

class VarExpr(private val varName: String) : Expr() {
    override fun toString(): String {
        return "var_$varName"
    }
}

class SubExpr(private val leftExpr: Expr, private val rightExpr: Expr) : Expr() {
    override fun toString(): String {
        return "$leftExpr-$rightExpr\n"
    }
}

class MultiplyExpr(private val leftExpr: Expr, private val rightExpr: Expr) : Expr() {
    override fun toString(): String {
        return "$leftExpr*$rightExpr\n"
    }
}

class DivExpr(private val leftExpr: Expr, private val rightExpr: Expr) : Expr() {
    override fun toString(): String {
        return "$leftExpr/$rightExpr\n"
    }
}

class AddExpr(private val leftExpr: Expr, private val rightExpr: Expr) : Expr() {
    override fun toString(): String {
        return "$leftExpr+$rightExpr\n"
    }
}

class LiteralExpr(private val types: Types, private val value: String) : Expr() {
    override fun toString(): String {
       return when(types){
            Types.INT -> {
                value
            }
            Types.FLOAT -> {
                value+"f"
            }
            Types.STRING -> {
                "\""+value+"\""
            }
            Types.CHAR -> {
                "\""+value+"\""
            }
        }

    }
}

open class Expr

enum class ExpressionsType(val index: Int) {
    Variable(0), Add(1), Subtract(2), Multiply(3), Divide(4), Literal(5), Equal(6), GREATER(7), LESSTHAN(7)
}