package aoc.day14

import aoc.readInputAsString
import java.util.Collections.max
import java.util.Collections.min

/**
 * Day 14 Part 1
 *
 * Pretty simples, kotlin made it a pain to split input the way I wanted
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day14/testInput.txt")
    val inputParts = inputString.split("\n")

    var polymerString = inputParts[0]
    val instructions = inputParts.slice(2 until inputParts.size).map{it.split(" -> ")}

    // Map of what should be inserted
    val inserts = mutableMapOf<String,String>()
    for(i in instructions.indices){
        inserts[instructions[i][0]] = instructions[i][1]
    }

    for (i in 1..10){
        polymerString = replacePolymers(polymerString, inserts)
    }

    val popularityMap = polymerString.toList().groupingBy { it }.eachCount()

    println(max(popularityMap.values) - min(popularityMap.values))
}

fun replacePolymers(polymerString: String, inserts: MutableMap<String, String>): String {
    val outList = mutableListOf<String>()

    for(i in 0 until polymerString.length - 1 ){
        val pair = polymerString.substring(i, i + 2)
        outList.add(polymerString[i].toString())
        if( pair in inserts.keys){
            outList.add(inserts[pair]!!)
        }
    }

    outList.add(polymerString.takeLast(1))

    return outList.joinToString("")
}
