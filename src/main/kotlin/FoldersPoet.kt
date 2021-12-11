import java.io.File

fun main() {

   val file =  File("/home/jens/Code/2021/FoldersKt/src/fold")

    val char = "H"

val test = Integer.toBinaryString(char.toInt())
test
}



fun createZero(path:String){
    File(path).mkdirs()
}

fun createOne(path:String){
    File(path).mkdir()
}

