open class xCommand(val command: Commandtype)
open class xExpr(val expressionsType: ExpressionsType)
class xType

class WhileCmd(val expr: xExpr, val commands: List<xCommand>) : xCommand(Commandtype.WHILE)
class PrintCmd(val expr: xExpr) : xCommand(Commandtype.PRINT)
class DeclareCmd(val types: Types, val varName: String) : xCommand(Commandtype.DECLARE)
class LetCmd(val varName: String, val expr: xExpr) : xCommand(Commandtype.LET)
class GtExpr(val leftExpr: xExpr, val rightExpr: xExpr) : xExpr(ExpressionsType.GREATER)
class VarExpr(val varName: String) : xExpr(ExpressionsType.Variable)
class SubExpr(val leftExpr: xExpr, val rightExpr: xExpr) : xExpr(ExpressionsType.Subtract)
class LiteralExpr(val types: Types, val value: String) : xExpr(ExpressionsType.Literal)
class EmtyExpr() : xExpr(ExpressionsType.Emty)
