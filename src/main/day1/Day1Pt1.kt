package day1

import java.io.File
import java.io.InputStream

/**
 * @author josiah.filleul
 */
fun main(){
    val inputStream: InputStream = File("src/main/day1/input.txt").inputStream()

    val inputString = inputStream.bufferedReader().use { it.readText() }.trim()

    val inputNums = inputString.split('\n').map { it.toInt() }

    val diffs = mutableListOf<Int>()

    for (num in 1.rangeTo(inputNums.size - 1)){
        diffs.add(inputNums[num] - inputNums[num - 1])
    }
    println(diffs.count{ it > 0 })
}
