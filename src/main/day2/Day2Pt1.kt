package day2

import org.omg.CORBA.Object
import java.io.File
import java.io.InputStream
import java.io.ObjectInput
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

/**
 * @author josiah.filleul
 */
fun main() {
    val inputStream: InputStream = File("src/main/day2/input").inputStream()

    val inputString = inputStream.bufferedReader().use { it.readText() }.trim()

    val inputMaps = inputString.split('\n').map { parseInstruction(it) }

    var depth = 0
    var xpos = 0

    inputMaps.forEach{
        when (it["instr"]) {
            "up" -> depth -= it["val"]?.toInt() ?: 0
            "down" -> depth += it["val"]?.toInt() ?: 0
            "forward" -> xpos += it["val"]?.toInt() ?: 0
    }}

    println(depth*xpos)
}

fun parseInstruction( inp: String): Map<String,String>{
    val parts = inp.split(' ')
    return mapOf("instr" to parts[0] , "val" to parts[1])
}