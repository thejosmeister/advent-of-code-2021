package aoc.day2

import aoc.readInputAsString

/**
 * Day 2 Part 1
 *
 * Don't think my use of the Map stuff was the best way to do it in Kotlin.
 * There must be a way to not strongly type a map if returning one from a function?
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day2/input.txt")
    val inputMaps = inputString.split('\n').map { parseInstruction(it) }

    var depth = 0
    var xpos = 0

    inputMaps.forEach {
        when (it["instr"]) {
            "up" -> depth -= it["val"]?.toInt() ?: 0
            "down" -> depth += it["val"]?.toInt() ?: 0
            "forward" -> xpos += it["val"]?.toInt() ?: 0
        }
    }

    println(depth * xpos)
}

fun parseInstruction(inp: String): Map<String, String> {
    val parts = inp.split(' ')
    return mapOf("instr" to parts[0], "val" to parts[1])
}
