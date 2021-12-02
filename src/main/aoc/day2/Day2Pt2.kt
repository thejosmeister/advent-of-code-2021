package aoc.day2

import aoc.readInputAsString

/**
 * Day 2 Part 2
 *
 * Will be interesting to see if the submarine becomes a big feature like the 2019 intcode machine.
 */
fun main() {
    val inputString = readInputAsString("src/main/aoc/day2/input.txt")

    val inputMaps = inputString.split('\n').map { parseInstruction(it) }

    var depth = 0
    var xpos = 0
    var aim = 0

    inputMaps.forEach {
        when (it["instr"]) {
            "up" -> aim -= it["val"]?.toInt() ?: 0
            "down" -> aim += it["val"]?.toInt() ?: 0
            "forward" -> {
                xpos += it["val"]?.toInt() ?: 0
                depth += aim * (it["val"]?.toInt() ?: 0)
            }
        }
    }

    println(depth * xpos)
}
