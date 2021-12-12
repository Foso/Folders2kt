import java.io.File

fun main() {

    val path= "/home/jens/Code/2021/FoldersKt/src/fold"

    val word = "Hello World!"

    word.toCharArray().forEachIndexed { letterIndex, c ->

        val bin = Integer.toBinaryString(word.toCharArray()[letterIndex].code.toByte().toInt()).padStart(8,'0')

        println(bin)

        val letterPathFolder = path+"/NewFolder/$letterIndex"
        File(letterPathFolder).mkdirs()

        val first = bin.toCharArray().take(4)
        val right = bin.toCharArray().takeLast(4)


        File(letterPathFolder+"/0").mkdirs()

        first.forEachIndexed { index, c ->
            if(c=='0'){
                File(letterPathFolder+"/0/$index").mkdirs()
            }
            if(c=='1'){
                File(letterPathFolder+"/0/$index/1").mkdirs()
            }
        }
        File(letterPathFolder+"/1").mkdirs()
        right.forEachIndexed { index, c ->
            if(c=='0'){
                File(letterPathFolder+"/1/$index").mkdirs()
            }
            if(c=='1'){
                File(letterPathFolder+"/1/$index/1").mkdirs()
            }
        }



    }

}



fun createZero(path:String){
    File(path).mkdirs()
}

fun createOne(path:String){
    File(path+"/"+"Hallo").mkdir()
}

