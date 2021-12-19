package aoc.day8

import aoc.readInputAsString

/**
 * Day 8 Part 1
 *
 * The long preamble nearly kills off all enthusiasm but the easy part 1 keeps you going.
 */
fun main() {
    val inputString = readInputAsString("src/main/java/aoc/day8/input.txt")
    val inputValues = inputString.split("\n").map { it.split(" | ").map { it.split(" ") } }

    var out = 0
    val goodNums = arrayOf(2, 3, 4, 7)

    inputValues.forEach {
        it[1].forEach { _it ->
            if (_it.length in goodNums) {
                out++
            }
        }
    }

    print(out)

}
