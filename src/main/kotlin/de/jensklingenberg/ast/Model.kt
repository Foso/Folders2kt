package de.jensklingenberg.ast



open class Command(val commandtype: Commandtype)
open class xExpr(val expressionsType: ExpressionsType)

class WhileCmd(val expr: xExpr, val commands: List<Command>) : Command(Commandtype.WHILE)
class PrintCmd(val expr: xExpr) : Command(Commandtype.PRINT)
class DeclareCmd(val types: Types, val varName: String) : Command(Commandtype.DECLARE)
class LetCmd(val varName: String, val expr: xExpr) : Command(Commandtype.LET)
class GtExpr(val leftExpr: xExpr, val rightExpr: xExpr) : xExpr(ExpressionsType.GREATER)
class VarExpr(val varName: String) : xExpr(ExpressionsType.Variable)
class SubExpr(val leftExpr: xExpr, val rightExpr: xExpr) : xExpr(ExpressionsType.Subtract)
class LiteralExpr(val types: Types, val value: String) : xExpr(ExpressionsType.Literal)


enum class Commandtype(val index: Int) {
    IF(0), WHILE(1), DECLARE(2), LET(3), PRINT(4), INPUT(5)
}

enum class ExpressionsType(val index: Int) {
    Variable(0), Add(1), Subtract(2), Multiply(3), Divide(4), Literal(5), Equal(6), GREATER(7)
}

enum class Types(val index: Int,val ktType:String) {
    INT(0,"Int"), FLOAT(1,"Float"), STRING(2,"String"), CHAR(3,"Char")
}